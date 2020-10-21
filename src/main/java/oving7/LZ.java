package oving7;

import java.io.*;
import java.util.Arrays;

public class LZ {
    private byte[] bFilArr;
    private StringBuffer sequences = new StringBuffer();

    public static void main(String[] args) throws IOException {
        LZ lz = new LZ();
        lz.compress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverse.lyx");
        lz.decompress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverseLZ.lyx");
    }

    public void compress(String inputPath) throws IOException {
        StringBuilder outputPath = new StringBuilder();
        if (inputPath.contains(".")) {
            String[] arr = inputPath.split("\\.");
            arr[arr.length-2] += "LZ";
            for (int i = 0; i < arr.length-1; i++) {
                outputPath.append(arr[i]).append(".");
            }
            outputPath.append(arr[arr.length-1]);
        }else {
            outputPath.append(inputPath).append("LZ");
        }
        compress(inputPath, outputPath.toString());
    }

    public void decompress(String inputPath) throws IOException {
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
        decompress(inputPath, outputPath.toString());
    }

    public void decompress(String compressedFile, String output) throws IOException {
        sequences = new StringBuffer();
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(compressedFile)));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
        byte[] currentOutputBlock;

        bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        int byteIndex = 0;


        while (byteIndex < bFilArr.length) {
            int currentBlockLength = bFilArr[byteIndex++];
            byte currentByte;
            byte[] currentChar;
            int charLength = 1;




            if(currentBlockLength > 0){
                byte[] tempShort = {bFilArr[byteIndex++],bFilArr[byteIndex++]};
                short index = (short)(((tempShort[0] & 0xFF) << 8) | (tempShort[1] & 0xFF));    //her kan det være feil!!!
                StringBuilder currentSequence = new StringBuilder();


                for (int i = index;(i <index + currentBlockLength)/* && (i< sequences.length())*/; i++) { //var -1 før
                    currentSequence.append(sequences.charAt(i));
                }


                //start av test
                /*if(currentSequence.charAt(currentSequence.length()) > ){
                    currentChar = new byte[1];
                    currentChar[0] = currentByte;
                    currentSequence.append((char)currentChar[0]);
                }else {

                    String binaryString = Integer.toBinaryString(currentByte & 0xff);
                    for (int i = 1; i < 4; i++) {
                        if(binaryString.charAt(i) == 49) charLength++;
                        else break;
                    }
                }*/
                //slutt av test

                //går tom for characerts


                currentByte = bFilArr[byteIndex++];

                if(currentByte >= 0){
                    currentChar = new byte[1];
                    currentChar[0] = currentByte;
                    currentSequence.append((char)currentChar[0]);
                }else {

                    String binaryString = Integer.toBinaryString(currentByte & 0xff);
                    for (int i = 1; i < 4; i++) {
                        if(binaryString.charAt(i) == 49) charLength++;
                        else break;
                    }

                    int tempIndex = 1;
                    currentChar = new byte[charLength];
                    currentChar[0] = currentByte;
                    while(tempIndex < charLength){
                        currentChar[tempIndex++] = bFilArr[byteIndex++];
                    }
                    currentSequence.append(new String(currentChar));
                }

                currentOutputBlock = currentSequence.toString().getBytes();
                sequences.append(currentSequence.toString());
            }
            else{
                int length = Math.abs(currentBlockLength);
                currentOutputBlock = new byte[length];

                StringBuilder currentSequence = new StringBuilder();

                for (int i = 0; i < length; i++) {
                    charLength = 1;
                    currentByte = bFilArr[byteIndex++];
                    if(currentByte >= 0) {

                        currentOutputBlock[i] = currentByte;
                        currentSequence.append((char)currentOutputBlock[i]);
                    }
                    else {
                        String binaryString = Integer.toBinaryString(currentByte & 0xff);
                        for (int j = 1; j < 4; j++) {
                            if (binaryString.charAt(j) == 49) charLength++;
                            else break;
                        }

                        int tempIndex = 1;
                        currentChar = new byte[charLength];
                        currentChar[0] = currentByte;
                        while (tempIndex < charLength) {
                            currentChar[tempIndex++] = bFilArr[byteIndex++];
                        }

                        currentSequence.append(new String(currentChar));
                    }
                }
                currentOutputBlock = currentSequence.toString().getBytes();
                sequences.append(currentSequence.toString());
            }


            utfil.write(currentOutputBlock);
            if(sequences.length() > 1<<15) trimList();
        }
        innfil.close();
        utfil.flush();
        utfil.close();
    }



    public void compress(String filePath, String outPath) throws IOException {
        sequences = new StringBuffer();
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outPath)));
        int prevIndex = -1;
        int indexDos;
        StringBuilder currentSequence = new StringBuilder();
        byte[] currentOutputBlock = {};

        bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        int byteIndex = 0;
        boolean lastUncompressed = true;
        StringBuilder uncompressedOut = new StringBuilder();


        while (byteIndex < bFilArr.length){

            byte currentByte = bFilArr[byteIndex++];
            byte[] currentChar ;
            int charLength = 1;



            if(currentByte >= 0){
                currentChar = new byte[1];
                currentChar[0] = currentByte;
                currentSequence.append((char)currentChar[0]);
            }else {

                String binaryString = Integer.toBinaryString(currentByte & 0xff);
                for (int i = 1; i < 4; i++) {
                    if(binaryString.charAt(i) == 49) charLength++;
                    else break;
                }
                int tempIndex = 1;
                currentChar = new byte[charLength];
                currentChar[0] = currentByte;
                while(tempIndex < charLength){
                    currentChar[tempIndex++] = bFilArr[byteIndex++];
                }
                currentSequence.append(new String(currentChar));
            }

            indexDos = sequences.indexOf(currentSequence.toString());

            if(indexDos < 0 || byteIndex == bFilArr.length || currentSequence.length() > 126){
                int length = handleLength(currentSequence);
                if (length > 2) {
                    lastUncompressed = false;
                }
                sequences.append(currentSequence.toString());
                if (!lastUncompressed) {
                    writeUncompressed(utfil, uncompressedOut);
                    uncompressedOut = new StringBuilder();
                }
                if(length > 2){


                    /*int sequenceLength = currentSequence.length();
                    System.out.println(Integer.toBinaryString(currentSequence.charAt(currentSequence.length()-1) & 0xff));
                    if(Integer.toBinaryString(currentSequence.charAt(currentSequence.length()-2)).charAt(0) == '1' && Integer.toBinaryString(currentSequence.charAt(currentSequence.length()-2)).length() == 8){   //vi har den for å fikse en feil med blokklengden og tegn på 4 bytes
                        System.out.println("if kjører");
                        byte x = (byte) ((byte) currentSequence.charAt(currentSequence.length()-2) & 0xff);
                        String bitStr = Integer.toBinaryString(x);
                        int xLength = 0;
                        for (int i = 0; i < 4; i++) {
                            if (bitStr.charAt(i) == 49) xLength++;
                        }
                        if(xLength == 4) sequenceLength--;
                    }*/


                    String strCurrentCharLength = new String(currentChar);
                    int sequenceLength = currentSequence.length()-strCurrentCharLength.length();

                    currentOutputBlock = new byte[3+charLength];
                    currentOutputBlock[0] = (byte) (sequenceLength & 0xff); //her gjorde vi en forandring. currentSequence.length
                    currentOutputBlock[1] = (byte) (prevIndex>>8);
                    currentOutputBlock[2] = (byte) (prevIndex & 0b11111111);
                    if(charLength == 1){
                        currentOutputBlock[3] = (byte) (currentChar[0] & 0xff);
                    }

                    else{
                        for (int i = 3; i < currentOutputBlock.length; i++) {
                            currentOutputBlock[i] = currentChar[i-3];
                        }
                    }

                    utfil.write(currentOutputBlock);
                }else {
                    uncompressedOut.append(currentSequence.toString());
                    lastUncompressed = true;
                }
                //prevIndex = -1;
                currentSequence = new StringBuilder();
                if(sequences.length() > 1<<15) trimList();
            }else {
                prevIndex = indexDos;
            }
        }
        if (uncompressedOut.length()>0) {
            writeUncompressed(utfil, uncompressedOut);
        }
        innfil.close();
        utfil.flush();
        utfil.close();
    }


    //fin en bedre måte å gjøre dette på dersom mulig
    private int handleLength(StringBuilder currentSequence) {
        int length = currentSequence.length();
        int charLength = 1;
        String currentChar = "";
        byte[] bytes = currentSequence.toString().getBytes();



        for (int i = 0; i < bytes.length; i++) {
            currentChar = Integer.toBinaryString(bytes[i] & 0xff);
            if(currentChar.charAt(0) != '0' && currentChar.length() == 8){


                for (int j = 1; j < 4; j++) {
                    if(currentChar.charAt(j) == 49)charLength++;
                    else break;
                }
                if (charLength == 4){
                    length--;

                }
                i+=charLength-1;
                charLength = 1;
            }

        }
        return length;
    }

    private void writeUncompressed(DataOutputStream utfil, StringBuilder uncompressedOut) throws IOException {
        int length = handleLength(uncompressedOut);
        byte[] currentOutputBlock;
        byte[] currentSequenceBytes = uncompressedOut.toString().getBytes();
        currentOutputBlock = new byte[currentSequenceBytes.length+1];
        currentOutputBlock[0] = (byte) (-length & 0xff);
        for (int i = 0; i < currentSequenceBytes.length; i++) {
            currentOutputBlock[i+1] = currentSequenceBytes[i];
        }
        utfil.write(currentOutputBlock);
    }



    private void trimList(){
        sequences.delete(0, sequences.length() - (1 << 15));
    }



}
