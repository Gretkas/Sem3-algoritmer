package oving7;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Only works with UTF-8 encoded files!
 */
public class LZ {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        LZ lz = new LZ();
        lz.compressToFile("C:\\Users\\robvo\\Desktop\\resources\\oving7\\fusk.txt");
        lz.decompressFromFile("C:\\Users\\robvo\\Desktop\\resources\\oving7\\fuskLZ.txt");
    }


    /**
     * Compress.
     * Helper method for compress. Handles the output path if only running LZ and not huffmann
     *
     * @param inputPath the input path
     * @throws IOException the io exception
     */
    public void compressToFile(String inputPath) throws IOException {
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
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputPath.toString())));

       
        utfil.write(compress(inputPath));
        utfil.flush();
        utfil.close();
    }

    /**
     * Decompress.
     * Helper method for deCompress. Handles the output path if only running LZ and not huffmann
     *
     * @param inputPath the input path
     * @throws IOException the io exception
     */
    public void decompressFromFile(String inputPath) throws IOException {
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
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(inputPath)));

        byte[] bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        decompress(bFilArr,outputPath.toString());
        innfil.close();
    }


    /**
     * Decompress.
     *
     * @param bFilArr the file as a byte array
     * @param outputPath  the outputPath
     * @throws IOException the io exception
     */
    public void decompress(byte[] bFilArr, String outputPath) throws IOException {
        StringBuffer sequences = new StringBuffer();    //this is the sliding window
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputPath)));
        int byteIndex = 0;  //Index in the file-array


        while (byteIndex < bFilArr.length) {
            int currentBlockLength = bFilArr[byteIndex++];  //if positive data is compressed, if not data is uncompressed;

            if(currentBlockLength > 0){ //This if handles compressed data
                byteIndex = handleCompressedData(byteIndex,bFilArr,sequences,currentBlockLength,utfil);
            }
            else{ //This block handles uncompressed data
                byteIndex = handleUncompressedData(byteIndex,bFilArr,sequences,currentBlockLength,utfil);
            }

            //if the sliding window is over its max size
            if(sequences.length() >= 1<<15) trimList(sequences);
        }
        utfil.flush();
        utfil.close();
    }


    private int handleUncompressedData(int byteIndex, byte[] bFilArr, StringBuffer sequences, int currentBlockLength, DataOutputStream utfil) throws IOException {
        int length = Math.abs(currentBlockLength);  //the length of the current block
        StringBuilder currentSequence = new StringBuilder();

        for (int i = 0; i < length; i++) {
            byte currentByte = bFilArr[byteIndex++];
            if(currentByte >= 0) {  //if char is one byte
                currentSequence.append((char)currentByte);
            }
            else {  //if char is more than one byte
                byteIndex = handleSpecialChar(bFilArr,byteIndex, currentByte, currentSequence);
            }
        }

        sequences.append(currentSequence.toString());
        utfil.write(currentSequence.toString().getBytes());

        return byteIndex;
    }


    private int handleCompressedData(int byteIndex, byte[] bFilArr, StringBuffer sequences, int currentBlockLength, DataOutputStream utfil) throws IOException {
        StringBuilder currentSequence = new StringBuilder();

        short index = (short)(((bFilArr[byteIndex++] & 0xFF) << 8) | (bFilArr[byteIndex++] & 0xFF)); //What is the index of our compressed data in the sliding window

        for (int i = index;(i <index + currentBlockLength); i++) {  //collect our sequence from the sliding window
            currentSequence.append(sequences.charAt(i));
        }

        //handles the last character of the sequence
        byte currentByte = bFilArr[byteIndex++];
        byte[] currentChar;

        if(currentByte >= 0){ //if character is one byte
            currentChar = new byte[1];
            currentChar[0] = currentByte;
            currentSequence.append((char)currentChar[0]);
        }else { //if character is more than one byte
            byteIndex = handleSpecialChar(bFilArr, byteIndex, currentByte,currentSequence);
        }

        //adds the sequence to the output, and appends it to the sliding window.
        sequences.append(currentSequence.toString());
        utfil.write(currentSequence.toString().getBytes());

        return byteIndex;
    }


    private int handleSpecialChar(byte[] bFilArr,int byteIndex, byte currentByte, StringBuilder currentSequence) {
        byte[] currentChar;
        int charLength = 1;
        String binaryString = Integer.toBinaryString(currentByte & 0xff);
        // How many ones at the start of the first byte
        // (in UTF-8 this will be equal to the number of bytes in the character, if the character is longer than one byte)
        for (int i = 1; i < 4; i++) {
            if(binaryString.charAt(i) == 49) charLength++;
            else break;
        }

        int tempIndex = 1;
        currentChar = new byte[charLength];
        currentChar[0] = currentByte;
        while(tempIndex < charLength){  //read as many extra bytes as we need to complete the character
            currentChar[tempIndex++] = bFilArr[byteIndex++];
        }
        currentSequence.append(new String(currentChar));
        return byteIndex;
    }


    /**
     * Compress.
     *
     * @param filePath the file path
     * @throws IOException the io exception
     */
    public byte[] compress(String filePath) throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
        byte[] bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());

        StringBuffer sequences = new StringBuffer();
        ArrayList<Byte> compressedOutput = new ArrayList<>();
        StringBuilder currentSequence = new StringBuilder();
        StringBuilder currentUncompressedSequence = new StringBuilder();
        byte[] currentOutputBlock = {};

        boolean lastUncompressed = true;
        int prevIndex = -1;
        int indexDos;
        int byteIndex = 0;

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
                    currentChar[tempIndex++] = (byte) (bFilArr[byteIndex++] & 0xff); //la til &0xff
                }
                currentSequence.append(new String(currentChar));
            }

            indexDos = sequences.indexOf(currentSequence.toString());

            if(indexDos < 0 || byteIndex == bFilArr.length || currentSequence.length() > 126 || currentUncompressedSequence.length() > 126){
                if (currentSequence.length() > 3) {
                    lastUncompressed = false;
                }
                sequences.append(currentSequence.toString());
                if (!lastUncompressed || currentUncompressedSequence.length() > 126) { //la til: || currentUncompressedSequence.length() > 127
                    writeUncompressed(compressedOutput, currentUncompressedSequence);
                    currentUncompressedSequence = new StringBuilder();
                }
                if(currentSequence.length() > 3){



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
                            currentOutputBlock[i] = (byte) (currentChar[i-3] & 0xff);
                        }
                    }

                    for (int i = 0; i < currentOutputBlock.length; i++) {
                        compressedOutput.add(currentOutputBlock[i]);
                    }
                }else {
                    currentUncompressedSequence.append(currentSequence.toString());
                    lastUncompressed = true;
                }
                currentSequence = new StringBuilder();
                if(sequences.length() > 1<<15) trimList(sequences);
            }else {
                prevIndex = indexDos;
            }
        }
        if (currentUncompressedSequence.length()>0) {
            writeUncompressed(compressedOutput, currentUncompressedSequence);
        }
        innfil.close();
        byte[] compressedOutputArray = new byte[compressedOutput.size()];
        for (int i = 0; i < compressedOutput.size(); i++) {
            compressedOutputArray[i] = compressedOutput.get(i);
        }
        return compressedOutputArray;
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

    private void writeUncompressed(ArrayList<Byte> compressedOutput, StringBuilder uncompressedOut) throws IOException {
        int length = handleLength(uncompressedOut);
        byte[] currentOutputBlock;
        byte[] currentSequenceBytes = uncompressedOut.toString().getBytes();
        currentOutputBlock = new byte[currentSequenceBytes.length+1];
        currentOutputBlock[0] = (byte) (-length & 0xff);
        for (int i = 0; i < currentSequenceBytes.length; i++) {
            currentOutputBlock[i+1] = currentSequenceBytes[i];
        }
        for (int i = 0; i < currentOutputBlock.length; i++) {
            compressedOutput.add(currentOutputBlock[i]);
        }
    }

    private void trimList(StringBuffer sequences){
        sequences.delete(0, sequences.length() - (1 << 15));
    }

}
