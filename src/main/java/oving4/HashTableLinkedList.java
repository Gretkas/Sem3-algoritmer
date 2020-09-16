package oving4;

import java.util.Arrays;

public class HashTableLinkedList<T> {
    private Node[] nodes;
    private int size;
    private float loadFactor;
    private int threshold;
    private int numberOfElements;


    public HashTableLinkedList() {
        this(1<<4);
    }

    public HashTableLinkedList(int size) {
        this.numberOfElements = 0;
        this.size = size;
        this.loadFactor = 0.75f;
        float temp = size*loadFactor;
        this.threshold = (int) temp;
        this.nodes = new Node[size];
    }


    public void add(T value, int key){
        int hash = this.hash(key);
        Node<T> node = new Node<>(value,key,hash);
        this.add(node);
    }

    private void add(Node<T> node){
        int hash = node.getHash();
        numberOfElements++;
        if(nodes[hash] == null){
            nodes[hash] = node;

        }
        else{
            System.out.println("Collision at: " + hash);
            Node<T> currentNode = nodes[hash];
            while(!(currentNode.getNodeNext() == null)){
                currentNode = currentNode.getNodeNext();
            }
            currentNode.setNodeNext(node);
        }
        if(numberOfElements >= threshold) doubleSize();
    }


    private void doubleSize(){
        this.size <<= 1;
        this.threshold <<= 1;
        this.numberOfElements = 0;

        Node[] oldNodes = Arrays.copyOf(nodes,nodes.length);
        nodes = new Node[size];
        for (int i = 0; i < oldNodes.length; i++) {
            if(oldNodes[i] != null) {

                this.add((T) oldNodes[i].getValue(),oldNodes[i].getKey());

                if(oldNodes[i].getNodeNext() != null){
                    Node currentNode = oldNodes[i];

                    while(currentNode.getNodeNext() != null){
                        currentNode = currentNode.getNodeNext();
                        this.add((T) currentNode.getValue(), currentNode.getKey());
                    }
                }
            }
        }
    }


    public boolean search(int key, T value){

        int hash = hash(key);
        if(nodes[hash] != null){
            Node currentNode = nodes[hash];
            while (currentNode.getNodeNext() != null){
                if(value.equals(currentNode.getValue())) return true;
                currentNode = currentNode.getNodeNext();
            }
            return value.equals(currentNode.getValue());
        }
        return false;
    }




    private int hash(int key){
        int result = key % size;
        if(result < 0)  return result + size;
        return result;
    }


    public int getSize() {
        return size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
