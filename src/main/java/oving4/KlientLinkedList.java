package oving4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Sergio Martinez
 * @author Robin C. Vold
 * @author Sigmund Ole Granaas
 * @author Ilona Podliashanyk
 */
public class KlientLinkedList {
    public static HashTableLinkedList<String> hash = new HashTableLinkedList<String>(4);

    public static void main(String[] args) throws Exception {

        /*
        -------------------------------------------------------------------------
        You will need to change the filepath to get the program running correctly
        -------------------------------------------------------------------------
        */

        readFIle();

        System.out.println("Number of elements in the list: " + hash.getNumberOfElements());
        System.out.println("Size of the list: " + hash.getSize());
        System.out.println("Current load factor is " + ((float)hash.getNumberOfElements()/hash.getSize()));
        System.out.println("Is the name 'Sergio,Martinez' in the list: " + hash.search(stringToInt("Sergio,Martinez"),"Sergio,Martinez"));
        System.out.println("Is the name 'Lars,Monsen' in the list: " + hash.search(stringToInt("Lars,Monsen"),"Lars,Monsen"));

        //If you want to see the contents of the list
        /*for (int i = 0; i < hash.getNodes().length; i++) {
            if(hash.getNodes()[i] != null){
                System.out.println(hash.getNodes()[i].toString());
            }
            else System.out.println("null");

        }*/
    }

    public static int stringToInt(String str){
        int result = 11;
        for(int i=0;i< str.length();i++){
            result = 17 * result + str.charAt(i);
        }
        return result;
    }

    public static void readFIle() throws Exception {
        //Change the file-path
        File file = new File("C:\\Users\\robvo\\Desktop\\navn20.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null){

            hash.add(st,stringToInt(st));
        }
    }
}
