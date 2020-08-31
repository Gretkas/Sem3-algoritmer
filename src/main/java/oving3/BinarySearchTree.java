package oving3;

import java.util.ArrayList;
import java.util.LinkedList;

public class BinarySearchTree {
    Node root;

    public BinarySearchTree(String value) {
        this.root = new Node(value);
    }

    public void add(String value) {
        addHelper(root, value);
    }

    private void addHelper(Node currentNode, String value) {
        if (value.compareToIgnoreCase(currentNode.getValue())>0) {
            if (currentNode.getRight() == null) {
                currentNode.setRight(new Node(value));
                return ;
            }
            addHelper(currentNode.getRight(), value);
        } else if (value.compareToIgnoreCase(currentNode.getValue() )<0) {
            if (currentNode.getLeft() == null) {
                currentNode.setLeft(new Node(value));
                return ;
            }
            addHelper(currentNode.getLeft(), value);
        }
    }

    private LinkedList<String> getWords() {
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(root);
        LinkedList<String> words = new LinkedList<String>();
        words.add(root.getValue());
        while (!queue.isEmpty()) {
            Node currentNote = queue.remove();
            if (currentNote.getLeft() != null) {
                queue.add(currentNote.getLeft());
                words.add(currentNote.getLeft().getValue());
            }
            if (currentNote.getRight() != null) {
                queue.add(currentNote.getRight());
                words.add(currentNote.getRight().getValue());
            }
        }
        return words;
    }

    public void printTree() {
        LinkedList<String> words = getWords();
        int counter = 1;
        int lines = (int) (Math.log(words.size()) / Math.log(2))+1;
        while (counter <= lines) {
            for (int i = 0; i < counter; i++) {
                System.out.print(words.remove()+" ");
            }
            System.out.println();
            counter *= 2;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree("test");
        binarySearchTree.add("voon");
        binarySearchTree.add("bark");
        binarySearchTree.printTree();
    }
}
