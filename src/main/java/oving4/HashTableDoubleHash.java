package oving4;

import java.util.Arrays;

public class HashTableDoubleHash {
    private int[] list;
    private int size;
    private float loadFactor;
    private int threshold;
    private int numberOfElements;
    private int numCollitions;


    public HashTableDoubleHash() {
        this(1<<12,0.5f);
    }

    public HashTableDoubleHash(int size,float loadFactor) {
        this.numberOfElements = 0;
        this.numCollitions = 0;
        this.size = size;
        this.loadFactor = loadFactor;
        float temp = size*loadFactor;
        this.threshold = (int) temp;
        this.list = new int[size];
    }


    public void add(int value){
        int hash = this.hash(value);
        numberOfElements++;

        if(list[hash] == 0){
            list[hash] = value;
        }
        else{
            numCollitions++;
            int hash2 = hash2(value);
            hash += hash2;
            if(hash >= size){
                hash %= size;
            }
            while (list[hash] != 0){
                numCollitions++; //unsure if this counts as a collition or not
                hash += hash2;
                if(hash >= size){
                    hash %= size;
                }
            }
            list[hash] = value;
        }
        if(numberOfElements >= threshold) doubleSize();
    }


    private void doubleSize(){
        this.size <<= 1;
        this.threshold <<= 1;
        this.numberOfElements = 0;
        this.numCollitions = 0;

        int[] oldList = Arrays.copyOf(list,list.length);
        list = new int[size];

        for (int i = 0; i < oldList.length; i++) {
            if(oldList[i] != 0){
                this.add(oldList[i]);
            }
        }
    }


    public boolean search(int value){
        int hash = hash(value);
        if(hash >= size){
            hash %= size;
        }
        int hash2 = hash2(value);
        int stopIndex = hash;
        while (list[hash] != value){
            hash += hash2;
            if(hash >= size){
                hash %= size;
            }
            if(hash == stopIndex){
                return false;
            }
        }
        return true;
    }




    private int hash(int value){
        int result = value % size;
        if(result < 0)  return result + size;
        return result;
    }

    public int hash2(int value){
        int result = 1 + (value % (size-1));
        if(result < 0)  return result + size;
        return result;
    }


    public int getSize() {
        return size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int[] getList() {
        return list;
    }

    public int getNumCollitions() {
        return numCollitions;
    }
}
