package oving7;

public class Node implements Comparable<Node>{
    private char character;
    private int frequency;
    private Node left, right;

    public Node(char character, int frequency, Node left, Node right){
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;

    }

    public boolean isLeafNode(){
        return left == null && right == null;
    }


    @Override
    public int compareTo(Node o) {
        int frequencyCompare = Integer.compare(this.frequency, o.frequency);
        if(frequencyCompare != 0){
            return frequencyCompare;
        }else{
            return Integer.compare(this.character, o.character);
        }
    }
}
