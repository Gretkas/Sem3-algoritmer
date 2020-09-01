package oving3;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class BinarySearchTree {
    Node root;

    public BinarySearchTree(String value) {
        if (!value.contains(",")  && !value.contains(" ")) {
        this.root = new Node(value);
        }
        else if (value.contains(",")) {
            String [] notes = value.split(",");
            this.root = new Node(notes[0]);
            for (int i = 1; i < notes.length; i++) {
                add(notes[i]);
            }
        }else if (value.contains(" ")) {
            String [] notes = value.split(" ");
            this.root = new Node(notes[0]);
            for (int i = 1; i < notes.length; i++) {
                add(notes[i]);
            }
        }

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
        while (!queue.isEmpty() && queue.size()<1024) {
            Node currentNote = queue.remove();
            if (!currentNote.getValue().equals("_")) {
                if (currentNote.getLeft() != null) {
                    queue.add(currentNote.getLeft());
                    words.add(currentNote.getLeft().getValue());
                }else {
                    words.add("_");
                    queue.add(new Node("_"));
                }
                if (currentNote.getRight() != null) {
                    queue.add(currentNote.getRight());
                    words.add(currentNote.getRight().getValue());
                }else {
                    words.add("_");
                    queue.add(new Node("_"));
                }
            }else {
                words.add("_");
                words.add("_");
                queue.add(new Node("_"));
                queue.add(new Node("_"));
            }
        }
        return words;
    }

    public void printTree() {
        LinkedList<String> words = getWords();
        int counter = 1;
        while (words.size() != 0) {

            for (int i = 0; i < counter; i++) {

                StringBuilder builder = new StringBuilder();
                if (words.size() != 0) {

                    for (int j = 0; j < (64/counter)-(words.get(0).length()/2); j++) {
                        builder.append(" ");
                    }
                }
                System.out.print(builder.toString());

                if (words.size() != 0) {
                    if (words.get(0) == "_" ) {
                        System.out.print("");
                        words.remove();
                    }
                    else {
                        System.out.print(words.remove());
                    }
                }
                System.out.print(builder.toString());
            }
            System.out.println();
            System.out.println();
            System.out.println();
            counter *= 2;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree binarySearchTree = new BinarySearchTree(scanner.nextLine());
        binarySearchTree.printTree();
        while (true) {
            binarySearchTree.add(scanner.nextLine());
            binarySearchTree.printTree();
        }
    }
}
