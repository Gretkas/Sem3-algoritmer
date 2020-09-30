package oving4;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class NodeLinkedList<T> {
    private T value;
    private NodeLinkedList<T> nodeNext;
    private int key;
    private int hash;

    public NodeLinkedList(T value, int key, int hash) {
        this.value = value;
        this.key = key;
        this.hash = hash;
    }



    public T getValue() {
        return value;
    }
    public NodeLinkedList<T> getNodeNext() {
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
    public void setNodeNext(NodeLinkedList<T> nodeNext) {
        this.nodeNext = nodeNext;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public void setHash(int hash) {
        this.hash = hash;
    }

    public NodeLinkedList<T> copyOf(){
        return new NodeLinkedList<>(this.value,this.key,this.hash);
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
