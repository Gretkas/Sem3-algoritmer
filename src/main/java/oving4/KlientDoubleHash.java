package oving4;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class KlientDoubleHash {
    private static HashTableDoubleHash hashTableDH = new HashTableDoubleHash(12001349,1f);
    private static int[] array = new int[(int)1e7];
    private static int[] numCollArray = new int[(int)1e7];

    public static void main(String[] args) {
        Random random = new Random();


        for(int i=0;i<1e7;i++){
            array[i] = random.nextInt(Integer.MAX_VALUE);
        }

        System.out.println("Time for our HashTable: " + timeHashTableDoubleHash());
        for (int i = 0; i < numCollArray.length; i++) {
            if(numCollArray[i] == 0) break;
            System.out.println("Number of collisions: " + numCollArray[i] + " in round number " + (i+1));
        }
        System.out.println("Our methods loadfactor: " + ((double)hashTableDH.getNumberOfElements()/hashTableDH.getSize()));
        System.out.println("------------------------------------------");
        System.out.println("Time for Javas HashSet: "+ timeJavaHashSet());
        System.out.println("Time for Javas HashMap: "+ timeJavaHashMap());
    }


    private static double timeHashTableDoubleHash(){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do{
            hashTableDH = new HashTableDoubleHash(12001349,1f);
            for (int i = 0; i < 1e7; i++) {
                hashTableDH.add(array[i]);
            }
            numCollArray[runder] = hashTableDH.getNumCollitions();
            slutt = new Date();
            ++runder;
        }
        while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)(slutt.getTime()-start.getTime()) / runder;
        return tid;
    }


    private static double timeJavaHashMap(){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        HashMap<Integer,Integer> hm = null;
        do{
            hm = new HashMap<>(1<<24);
            for (int i = 0; i < 1e7; i++) {
                hm.put(array[i], array[i]);
            }
            slutt = new Date();
            ++runder;
        }
        while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)(slutt.getTime()-start.getTime()) / runder;
        return tid;
    }

    private static double timeJavaHashSet(){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        HashSet<Integer> hm = null;
        do{
            hm = new HashSet<>(1<<24);
            for (int i = 0; i < 1e7; i++) {
                hm.add(array[i]);
            }
            slutt = new Date();
            ++runder;
        }
        while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)(slutt.getTime()-start.getTime()) / runder;
        return tid;
    }


    private static double timeJavaHashMapConcurrent(){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        ConcurrentHashMap<Integer,Integer> hm = null;
        do{
            hm = new ConcurrentHashMap<>();
            for (int i = 0; i < 1e7; i++) {
                hm.put(array[i],array[i]);
            }
            slutt = new Date();
            ++runder;
        }
        while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)(slutt.getTime()-start.getTime()) / runder;
        return tid;
    }
}
