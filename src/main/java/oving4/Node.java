package oving4;

public class Node<T> {
    private T value;
    private Node<T> nodeNext;
    private int key;
    private int hash;

    public Node(T value, int key, int hash) {
        this.value = value;
        this.key = key;
        this.hash = hash;
    }



    public T getValue() {
        return value;
    }
    public Node<T> getNodeNext() {
        return nodeNext;
    }
    public int getKey() {
        return key;
    }
    public int getHash(){
        return hash;
    }



    public void setValue(T value) {
        this.value = value;
    }
    public void setNodeNext(Node<T> nodeNext) {
        this.nodeNext = nodeNext;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public void setHash(int hash) {
        this.hash = hash;
    }

    public Node<T> copyOf(){
        return new Node<>(this.value,this.key,this.hash);
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", nodeNext=" + nodeNext +
                ", key=" + key +
                ", hash=" + hash +
                '}';
    }
}
