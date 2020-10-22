package oving7;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Only works with UTF-8 encoded files!
 */
public class LZ {

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
     * Sorry for ugly method
     * @param filePath the file path
     * @throws IOException the io exception
     */
    public byte[] compress(String filePath) throws IOException {
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
        byte[] bFilArr = new byte[innfil.available()];
        innfil.readFully(bFilArr, 0, innfil.available());
        innfil.close();

        StringBuffer sequences = new StringBuffer();                       //This is the sliding window
        ArrayList<Byte> compressedOutput = new ArrayList<>();              //This is the complete output
        StringBuilder currentSequence = new StringBuilder();               //The current sequence of characters that is being handled
        StringBuilder currentUncompressedSequence = new StringBuilder();   //The current sequence of characters that where unable to be compressed

        int prevIndex = -1; //the index of the current match in the sliding window
        int indexDos;       //a temporary index to check for matches
        int byteIndex = 0;  //current position in file byte-array

        while (byteIndex < bFilArr.length){ //while not end of file

            byte currentByte = bFilArr[byteIndex++];
            byte[] currentChar ;
            int charLength = 1; //the length of the current char in number of bytes


            if(currentByte >= 0){   //if the character is one byte
                currentChar = new byte[1];
                currentChar[0] = currentByte;
                currentSequence.append((char)currentChar[0]);
            }
            else {  //if character is multiple bytes
                String binaryString = Integer.toBinaryString(currentByte & 0xff);
                for (int i = 1; i < 4; i++) {   //counts the number of bytes in the character
                    if(binaryString.charAt(i) == 49) charLength++;
                    else break;
                }
                int tempIndex = 1;
                currentChar = new byte[charLength];
                currentChar[0] = currentByte;
                while(tempIndex < charLength){  //read extra byte to complete the character
                    currentChar[tempIndex++] = (byte) (bFilArr[byteIndex++] & 0xff);
                }
                currentSequence.append(new String(currentChar));
            }

            indexDos = sequences.indexOf(currentSequence.toString());   //was a match found?

            //no match was found, or end of file, or either sequence is longer than what can be expressed in one byte
            if(indexDos < 0 || byteIndex == bFilArr.length || currentSequence.length() > 126 || currentUncompressedSequence.length() > 126){
                sequences.append(currentSequence.toString());
                int compressionLength = 6;  //The length a sequence must reach before it is worth compressing.
                // This number was found by testing many numbers. For some reason larger numbers cause the program to crash. Sadly cant find out why due to time constraints.

                // If a compressable sequence was found, that means that the uncompressed sequence must be printed to preserve the order of the file,
                // or if the uncompressed sequence is longer than what can be expressed in one byte
                if (currentSequence.length() > compressionLength || currentUncompressedSequence.length() > 126) {
                    writeUncompressed(compressedOutput, currentUncompressedSequence);
                    currentUncompressedSequence = new StringBuilder();
                }

                //if a compressable sequence was found, write it
                if(currentSequence.length() > compressionLength) writeCompressed(currentChar,currentSequence,prevIndex,compressedOutput);
                //If current sequence is not worth compressing append it to the uncompressed sequence
                else currentUncompressedSequence.append(currentSequence.toString());

                currentSequence = new StringBuilder();
                if(sequences.length() > 1<<15) trimList(sequences); //Moves the sliding window
            }else { //A match was found, adjust indexes
                prevIndex = indexDos;
            }
        }
        if (currentUncompressedSequence.length()>0) {   //To prevent end of file errors
            writeUncompressed(compressedOutput, currentUncompressedSequence);
        }
        byte[] compressedOutputArray = new byte[compressedOutput.size()];
        for (int i = 0; i < compressedOutput.size(); i++) {
            compressedOutputArray[i] = compressedOutput.get(i);
        }
        return compressedOutputArray;
    }

    private void writeCompressed(byte[] currentChar, StringBuilder currentSequence, int prevIndex, ArrayList<Byte> compressedOutput) {
        String strCurrentCharLength = new String(currentChar);
        int sequenceLength = currentSequence.length()-strCurrentCharLength.length();

        compressedOutput.add((byte) (sequenceLength & 0xff));   //Length of the compressed sequence
        compressedOutput.add((byte) (prevIndex>>8));            //Match index expressed as two bytes
        compressedOutput.add((byte) (prevIndex & 0xff));        //Match index expressed as two bytes
        //Adds the last character
        if(currentChar.length == 1){
            compressedOutput.add((byte) (currentChar[0] & 0xff));
        }
        else{
            for (int i = 0; i < currentChar.length; i++) {
                compressedOutput.add((byte) (currentChar[i] & 0xff));
            }
        }
    }

    /**
     * This whole method might be unnecessary, we created it to deal with 4-byte characters
     * We kept it due to time constraints. Removing it would lead to a lot of remodeling
     * Removing it would speed up the program, but shouldnt affect compression a lot
     * @param currentSequence
     * @return
     */
    private int handleLength(StringBuilder currentSequence) {
        int length = currentSequence.length();
        int charLength = 1;
        String currentChar = "";
        byte[] bytes = currentSequence.toString().getBytes();

        //checks the length of each character, and adjusts the sequence length accordingly
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
        byte[] currentSequenceBytes = uncompressedOut.toString().getBytes();
        compressedOutput.add((byte) (-length & 0xff));
        for (int i = 0; i < currentSequenceBytes.length; i++) {
            compressedOutput.add(currentSequenceBytes[i]);
        }
    }

    private void trimList(StringBuffer sequences){
        sequences.delete(0, sequences.length() - (1 << 15));
    }

}
