package oving4;

import java.util.Arrays;

public class HashTable<T> {
    private Node[] nodes;
    private int size;
    private float loadFactor;
    private int threshold;
    private int numberOfBuckets;


    public HashTable() {
        this(1<<4);
    }

    public HashTable(int size) {
        this.numberOfBuckets = 0;
        this.size = size;
        this.loadFactor = 0.75f;
        this.threshold = (int) loadFactor*size;
        this.nodes = new Node[size];
    }


    public void add(T value, int key){
        int hash = this.hash(key);
        Node<T> node = new Node<>(value,key,hash);
        this.add(node);
    }

    private void add(Node node){
        int hash = node.getHash();
        if(nodes[hash] != null){
            nodes[hash] = node;
            numberOfBuckets++;
        }
        else{
            Node<T> currentNode = nodes[hash];
            while(currentNode.getNodeNext() != null){
                currentNode = currentNode.getNodeNext();
            }
            currentNode.setNodeNext(node);
        }
        if(numberOfBuckets >= threshold) doubleSize();
    }


    private void doubleSize(){
        this.size <<= 1;
        this.threshold <<= 1;

        Node[] newNodes = new Node[size];
        Node[] oldNodes = Arrays.copyOf(nodes,nodes.length);
        nodes = newNodes;
        for (int i = 0; i < oldNodes.length; i++) {
            if(oldNodes[i] != null) {
                this.add(oldNodes[i]);
                if(oldNodes[i].getNodeNext() != null){
                    Node currentNode = oldNodes[i];

                    while(currentNode.getNodeNext() != null){

                        currentNode = currentNode.getNodeNext();
                        this.add(currentNode);
                    }
                }
            }
        }
    }

//TODO: resten


    private int hash(int key){
        return key % size;
    }
}
