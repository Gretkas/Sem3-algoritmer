package oving7;

import java.io.*;
import java.util.PriorityQueue;

public class Huffmann {
    private int[] frequencies;
    private byte[] byteArrayFile;
    private Node[] nodes;
    private Node rootNode;

    public Huffmann() {
        this.frequencies = new int[256];
    }


    public void compress(String inputPath) throws IOException {
        String[] arr = inputPath.split("\\.");
        arr[arr.length-2] += "HM";
        StringBuilder outputPath = new StringBuilder();
        for (int i = 0; i < arr.length-1; i++) {
            outputPath.append(arr[i]).append(".");
        }
        outputPath.append(arr[arr.length-1]);
        compress(inputPath, outputPath.toString());
    }


    public void compress(String inputPath, String outputPath) throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(inputPath)));
        byteArrayFile = new byte[innfil.available()];
        innfil.readFully(byteArrayFile, 0, innfil.available());
        innfil.close();

        for (int i = 0; i < byteArrayFile.length; i++) {
            frequencies[byteArrayFile[i]+128]++;
        }
        generateTree();
        compressionHelper(outputPath);
    }


    private void compressionHelper(String outputPath) throws IOException {
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputPath)));
        utfil.write(frequenciesToByteArr());
        int remainingInputBits = 0, remainingOutputBits = 0;
        byte currentInputByte, currentOutputByte = 0;
        int index = 0;
        long currentBitString = 0;

        while(index < byteArrayFile.length){

            if(remainingOutputBits == 0){
                if(index != 0)utfil.writeByte(currentOutputByte);
                currentOutputByte = 0;
                remainingOutputBits = 8;
            }
            if((remainingInputBits == 0)){
                currentInputByte = byteArrayFile[index++];
                String tempBitString = nodes[currentInputByte+128].getBitString();
                currentBitString = Long.parseLong(tempBitString);
                remainingInputBits = tempBitString.length();
            }

            while(remainingInputBits > 0 && remainingOutputBits > 0){
                if(remainingInputBits >= remainingOutputBits){
                    /*(2^n)-1*/
                    byte bitOperator = (byte) (Math.pow(2,remainingOutputBits)-1);
                    currentOutputByte |= ((currentBitString & bitOperator) << (8-remainingOutputBits));
                    remainingInputBits -= remainingOutputBits;
                    currentBitString >>= remainingOutputBits;
                    remainingOutputBits = 0;

                }else{
                    currentOutputByte |= (currentBitString << (8-remainingOutputBits));
                    remainingOutputBits -= remainingInputBits;
                    remainingInputBits = 0;
                }
            }
        }
        utfil.flush();
        utfil.close();

    }

    private byte[] frequenciesToByteArr() {
        byte[] bytes = new byte[256<<2];
        for (int i = 0; i < frequencies.length; i++) {
            bytes[i] = (byte) (frequencies[i] & 0xff);
            bytes[i+1] = (byte) ((frequencies[i]>>8) & 0xff);
            bytes[i+2] = (byte) ((frequencies[i]>>16) & 0xff);
            bytes[i+3] = (byte) ((frequencies[i]>>24) & 0xff);
        }
        return bytes;
    }


    public void deCompress(){}

    private void generateTree(){
        PriorityQueue<Node> frequencyPQ = new PriorityQueue<>();
        nodes = new Node[256];

        for (int i = 0; i < nodes.length; i++) {
            Node n = new Node(frequencies[i],i);
            frequencyPQ.add(n);
            nodes[i] = n;
        }

        while(frequencyPQ.size() > 1){
            frequencyPQ.add(spliceNodes(frequencyPQ.poll(),frequencyPQ.poll()));
        }

        rootNode = frequencyPQ.poll();
    }


    private Node spliceNodes(Node n1, Node n2){
        int freq = n1.getFrequency() + n2.getFrequency();
        Node splicedRoot = new Node(freq,n1,n2);
        updateBitstrings(splicedRoot);
        return splicedRoot;
    }


    private void updateBitstrings(Node node){
        updateBitstring(node.getNodeLeft(),"0");
        updateBitstring(node.getNodeRight(),"1");
    }

    private void updateBitstring(Node node, String bitString){
        if(node.isLeafNode()){
            node.appendBitString(bitString);
        }else {
            updateBitstring(node.getNodeLeft(),bitString);
            updateBitstring(node.getNodeRight(),bitString);
        }
    }



}
