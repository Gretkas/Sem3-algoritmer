package oving7;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class Huffmann {
    private int[] frequencies;
    private byte[] byteArrayFile;
    private Node[] nodes;
    private Node rootNode;

    /**
     * Instantiates a new Huffmann.
     */
    public Huffmann() {
        this.frequencies = new int[256];
    }


    /**
     * Decompress to file.
     *
     * @param inputPath the input path
     * @throws IOException the io exception
     */
    public void deCompressToFile(String inputPath) throws IOException {
        StringBuilder outputPath = new StringBuilder();
        if (inputPath.contains(".")) {
            String[] arr = inputPath.split("\\.");
            arr[arr.length-2] += "Decomp";
            for (int i = 0; i < arr.length-1; i++) {
                outputPath.append(arr[i]).append(".");
            }
            outputPath.append(arr[arr.length-1]);
        }else {
            outputPath.append(inputPath).append("Decomp");
        }

        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputPath.toString())));
        utfil.write(deCompress(inputPath));
        utfil.flush();
        utfil.close();
    }


    /**
     * Decompress file as a byte array.
     * @param inputPath the input path
     * @return the decompressed byte array
     * @throws IOException the io exception
     */
    public byte[] deCompress(String inputPath) throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(inputPath)));
        generateFrequencyArray(innfil);
        byteArrayFile = new byte[innfil.available()];
        innfil.readFully(byteArrayFile, 0, innfil.available());
        innfil.close();
        ArrayList<Byte> deCompressedOutput = new ArrayList<>(byteArrayFile.length);


        generateTree();
        deCompressionHelper(deCompressedOutput);
        byte[] output = new byte[deCompressedOutput.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = deCompressedOutput.get(i);
        }
        return output;
    }

    private void generateFrequencyArray(DataInputStream innfil) throws IOException {
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = innfil.readInt();
        }
    }


    private void deCompressionHelper(ArrayList<Byte> deCompressedOutput) throws IOException {
        Node currentNode = rootNode;
        byte currentOutputChar;
        int numChars = findNumChars();   //Using this to prevent the last byte from writing extra chars. Change to long if compressing larger files than 2Gb

        int index = 0;
        while (index < byteArrayFile.length){
            String binaryString = Integer.toBinaryString(byteArrayFile[index++] & 0xff);
            int bitsLeft = binaryString.length();      //number of bits left to write from current byte
            int leadingZerosLeft = 8-binaryString.length(); //number of leading-zeros left to write from current byte

            // While there are chars/bits left to write
            while(numChars > 0 && (bitsLeft > 0 || leadingZerosLeft > 0)){
                byte currentBit;
                if(bitsLeft > 0){
                    currentBit = (byte) (binaryString.charAt((bitsLeft--)-1)-48);
                }else{
                    currentBit = 0;
                    leadingZerosLeft--;
                }

                // Tree traversal
                if(currentBit == 1) currentNode = currentNode.getNodeRight();
                else currentNode = currentNode.getNodeLeft();

                if(currentNode.isLeafNode()){
                    currentOutputChar = (byte) (currentNode.getAsciiValue()-128);
                    deCompressedOutput.add(currentOutputChar);
                    numChars--;
                    currentNode = rootNode;
                }
            }
        }
    }

    private int findNumChars() {
        int numChars = 0;
        for (int i = 0; i < frequencies.length; i++) {
            numChars += frequencies[i];
        }
        return numChars;
    }


    /**
     * Compress from file.
     *
     * @param inputPath the input path
     * @throws IOException the io exception
     */
    public void compressFromFile(String inputPath) throws IOException {
        StringBuilder outputPath = new StringBuilder();
        if (inputPath.contains(".")) {
            String[] arr = inputPath.split("\\.");
            arr[arr.length-2] += "HM";
            for (int i = 0; i < arr.length-1; i++) {
                outputPath.append(arr[i]).append(".");
            }
            outputPath.append(arr[arr.length-1]);
        }else {
            outputPath.append(inputPath).append("HM");
        }
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(inputPath)));
        byteArrayFile = new byte[innfil.available()];
        innfil.readFully(byteArrayFile, 0, innfil.available());
        innfil.close();
       compress(byteArrayFile, outputPath.toString());
    }

    /**
     * Compress.
     *
     * @param fileByteArray the byte-array we need to compress
     * @param outputPath the output path
     * @throws IOException the io exception
     */
    public void compress(byte[] fileByteArray, String outputPath) throws IOException {
        byteArrayFile = fileByteArray;

        for (int i = 0; i < byteArrayFile.length; i++) {
            frequencies[byteArrayFile[i]+128]++;
        }
        generateTree();
        compressionHelper(outputPath);
    }

    private void compressionHelper(String outputPath) throws IOException {
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputPath)));
        utfil.write(frequenciesToByteArr());
        int remainingInputBits = 0, remainingOutputBits = 0;    //free-bits in current input and output byte
        byte currentInputByte, currentOutputByte = 0;
        int index = 0;
        long currentBitString = 0;

        //while there is more to write
        while(index < byteArrayFile.length || (remainingOutputBits == 0 && index == byteArrayFile.length && remainingInputBits != 0)){

            if(remainingOutputBits == 0){
                if(index != 0)utfil.writeByte(currentOutputByte);
                currentOutputByte = 0;
                remainingOutputBits = 8;
            }
            if((remainingInputBits == 0)){
                currentInputByte = byteArrayFile[index++];
                String tempBitString = nodes[currentInputByte+128].reverseBitString();
                currentBitString = Long.parseLong(tempBitString, 2);
                remainingInputBits = tempBitString.length();
            }

            //We have free space in both bytes
            while(remainingInputBits > 0 && remainingOutputBits > 0){
                currentOutputByte |= (currentBitString << (8-remainingOutputBits)); //Adds as many bits as possible without replacing the old ones
                if(remainingInputBits >= remainingOutputBits){
                    remainingInputBits -= remainingOutputBits;
                    currentBitString >>= remainingOutputBits;
                    remainingOutputBits = 0;
                }else{
                    remainingOutputBits -= remainingInputBits;
                    remainingInputBits = 0;
                }
            }
        }
        if(remainingOutputBits != 8) utfil.writeByte(currentOutputByte);    //Avoid EOF errors
        utfil.flush();
        utfil.close();
    }

    private byte[] frequenciesToByteArr() {
        byte[] bytes = new byte[256<<2];
        for (int i = 0; i < frequencies.length; i++) {
            int j = i<<2;
            bytes[j] = (byte) ((frequencies[i]>>24) & 0xff);
            bytes[j+1] = (byte) ((frequencies[i]>>16) & 0xff);
            bytes[j+2] = (byte) ((frequencies[i]>>8) & 0xff);
            bytes[j+3] = (byte) (frequencies[i] & 0xff);
        }
        return bytes;
    }


    private void generateTree(){
        PriorityQueue<Node> frequencyPQ = new PriorityQueue<>();
        nodes = new Node[256];

        for (int i = 0; i < nodes.length; i++) {
            Node n = new Node(frequencies[i],i);
            frequencyPQ.add(n);
            nodes[i] = n;
        }

        while(frequencyPQ.size() > 1){
            //Splice the two least frequent nodes, and adds the root of the resulting tree
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
