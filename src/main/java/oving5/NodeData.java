package oving5;

public class NodeData {
    private Node previousNode;
    private int foundTime,finishedTime;
    private static int time;


    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }


    public int getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(int foundTime) {
        this.foundTime = foundTime;
    }

    public int getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(int finishedTime) {
        this.finishedTime = finishedTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static int readTime(){
        time++;
        return time;
    }

    public static void nullTime(){
        time = 0;
    }
}
