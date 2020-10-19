package oving7;

public class Node implements Comparable<Node> {
    private int frequency, asciiValue;
    private String bitString;
    private boolean leafNode;
    private Node nodeLeft, nodeRight;

    public Node(int frequency, int asciiValue) {
        this.frequency = frequency;
        this.asciiValue = asciiValue;
        this.bitString = "";
        this.leafNode = true;
    }

    public Node(int frequency, Node nodeLeft, Node nodeRight) {
        this.frequency = frequency;
        this.leafNode = false;
        this.nodeLeft = nodeLeft;
        this.nodeRight = nodeRight;
    }


    public void appendBitString(String bit){
        this.bitString = bit + bitString;
    }

    public String reverseBitString(){
        StringBuilder sb = new StringBuilder(bitString);
        return sb.reverse().toString();
    }


    @Override
    public int compareTo(Node o) {
        return this.frequency - o.getFrequency();
    }





    public int getFrequency() {
        return frequency;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public int getAsciiValue() {
        return asciiValue;
    }
    public void setAsciiValue(int asciiValue) {
        this.asciiValue = asciiValue;
    }
    public String getBitString() {
        return bitString;
    }
    public void setBitString(String bitString) {
        this.bitString = bitString;
    }
    public boolean isLeafNode() {
        return leafNode;
    }
    public void setLeafNode(boolean leafNode) {
        this.leafNode = leafNode;
    }
    public Node getNodeLeft() {
        return nodeLeft;
    }
    public void setNodeLeft(Node nodeLeft) {
        this.nodeLeft = nodeLeft;
    }
    public Node getNodeRight() {
        return nodeRight;
    }
    public void setNodeRight(Node nodeRight) {
        this.nodeRight = nodeRight;
    }


}
