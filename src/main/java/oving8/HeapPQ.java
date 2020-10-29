package oving8;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class HeapPQ {
    private Node[] priorityQueue;

    public HeapPQ(int numberOfNodes) {
        priorityQueue = new Node[numberOfNodes];
    }


    public void add(Node n, int index){
        priorityQueue[index] = n;
    }

    public Node get(int index){
        return priorityQueue[index];
    }



    /**
     * sets start node of the priorityqueue to the index zero*/
    public void startPriorityQueue(int startIndex){
        priorityQueue[startIndex].setDist(0);
        if(priorityQueue[startIndex] instanceof NodeAStar){
            ((NodeAStar) priorityQueue[startIndex]).setStartDist(0);
        }
        swapPQ(0,startIndex);
    }



    private void bubbleDown(int index){
        int swapIndex = (index<<1)+1;
        int right = (index+1)<<1;
        if(swapIndex>=priorityQueue.length || right>=priorityQueue.length) return;
        int dist = priorityQueue[swapIndex].getDist();

        if(dist > priorityQueue[right].getDist()){
            dist = priorityQueue[right].getDist();
            swapIndex = right;
        }
        if(priorityQueue[swapIndex].isFinished()) return;
        if (priorityQueue[index].getDist() > dist){
            swapPQ(index,swapIndex);
            bubbleDown(swapIndex);
        }
    }

    public void bubbleUp(int index){
        int parentIndex = (index-1)>>1;
        if(parentIndex<0) return;

        if(priorityQueue[index].getDist() < priorityQueue[parentIndex].getDist()){
            swapPQ(index,parentIndex);
            bubbleUp(parentIndex);
        }
    }

    public void bubbleUpAStar(int index){
        int parentIndex = (index-1)>>1;
        if(parentIndex<0) return;

        if(priorityQueue[index].getDist() < priorityQueue[parentIndex].getDist()){
            swapPQ(index,parentIndex);
            bubbleUpAStar(parentIndex);
        }else if(priorityQueue[index].getDist() == priorityQueue[parentIndex].getDist()){
            if(((NodeAStar)priorityQueue[index]).getHeuristicDist() < ((NodeAStar)priorityQueue[parentIndex]).getHeuristicDist()){
                swapPQ(index,parentIndex);
                bubbleUpAStar(parentIndex);
            }
        }
    }

    public Node pop(int lastIndex){
        Node n = priorityQueue[0];
        n.setFinished(true);
        swapPQ(0,lastIndex);
        bubbleDown(0);
        return n;
    }

    private void swapPQ(int i, int j){
        Node n = priorityQueue[i];
        priorityQueue[i].setCurrentIndex(j);
        priorityQueue[j].setCurrentIndex(i);
        priorityQueue[i] = priorityQueue[j];
        priorityQueue[j] = n;
    }

    public void sortResult(){
        Arrays.sort(priorityQueue, Comparator.comparingInt(Node::getNodeNumber));
    }
}
