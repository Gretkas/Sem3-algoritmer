package oving7;

import java.io.IOException;
import java.util.Arrays;

public class CompressionKlient {
    public static void main(String[] args) throws IOException {
        LZ lz = new LZ();
        Huffmann hm = new Huffmann();
        hm.compress(lz.compress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverse.lyx"),"C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverseComp.lyx");


        Huffmann hm2 = new Huffmann();
        LZ lz2 = new LZ();
        lz2.decompress(hm2.deCompress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverseComp.lyx"),"C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverseDecomp.lyx");
    }
}
