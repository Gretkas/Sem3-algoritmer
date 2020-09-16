package oving4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Klient {
    public static HashTableLinkedList<String> hash = new HashTableLinkedList<String>(4);

    public static void main(String[] args) throws Exception {
        readFIle();

        System.out.println("Current loadfactor is " + ((float)hash.getNumberOfElements()/hash.getSize()));
        System.out.println(hash.getNumberOfElements());
        System.out.println(hash.getSize());
        System.out.println(hash.search(stringToInt("Arvid Jr,Kirkbakk"),"Arvid Jr,Kirkbakk"));

        for (int i = 0; i < hash.getNodes().length; i++) {
            if(hash.getNodes()[i] != null){
                System.out.println(hash.getNodes()[i].toString());
            }
            else System.out.println("null");

        }
    }

    public static int stringToInt(String str){
        int result = 11;
        for(int i=0;i< str.length();i++){
            result = 17 * result + str.charAt(i);
        }
        return result;
    }

    public static void readFIle() throws Exception {
        File file = new File("C:\\Users\\robvo\\Desktop\\navn20.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null){

            hash.add(st,stringToInt(st));
        }
    }
}
