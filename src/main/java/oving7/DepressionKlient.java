package oving7;

import java.io.IOException;

public class DepressionKlient {
    public static void main(String[] args) throws IOException {
        Huffmann hm = new Huffmann();
        LZ lz = new LZ();
        lz.decompress(hm.deCompress("C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverseComp.lyx"),"C:\\Users\\robvo\\Desktop\\resources\\oving7\\diverseDecomp.lyx");

    }
}
