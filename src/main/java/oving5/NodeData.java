package oving5;

public class NodeData {
    private int dist;
    private Node previous;
    private static final int inf = 1000000000;
    private int foundTime,finishedTime;
    private static int time;

    public NodeData() {
        this.dist = inf;
    }

    public int getDist() {
        return dist;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public static int getInf() {
        return inf;
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
