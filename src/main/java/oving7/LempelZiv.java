package oving7;

import java.io.*;
import java.util.ArrayList;

public class LempelZiv {






}


/*

//?pne filer:
innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(inn_navn)));
utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(ut_navn)));

Javatips for begge deloppgaver side 5 av 5
//Lese data fra fil inn i byte-array:
// byte []data : arrayet vi leser inn i
// int posisjon : index i byte-array for det vi leser inn
// int mengde : antall byte vi vil lese inn
innfil.readFully(data, posisjon, mengde);
//Lese inn ?n byte
byte x;
x = innfil.readByte();
//Har ogs?:
short s = innfil.readShort();
char c = innfil.readChar();
int i = innfil.readInt();
long l = innfil.readLong();
//Skrive data fra byte-array til fil:
utfil.write(data, posisjon, mengde);
//Skrive ?n byte til fil:
byte singlebyte = 17;
utfil.writeByte(singlebyte);
//Har ogs?:
//utfil.writeChar(char c);
//utfil.writeShort(short s);
//utfil.writeInt(int i);
//utfil.writeLong(long l);
//Hente 13 bit fra long1, 8 bit fra long2 og 4 bit fra long3,
//og f? det inn i et byte-array:
byte[] data = new byte[3];
long long1 = 0b1101000010011; //13 bit
long long2 = 0b11100111; //8 bit
long long3 = 0b010; //3 bit
//8 f?rste bit fra long1 til data[0]
//?vrige bits maskeres bort med &
data[0] = (byte)(long1 & 0b11111111);
//5 gjenv?rende bit fra long1 til data[1]
//h?yreskiftet fjerner bits vi allerede har lagt i data[0]
//trenger ikke maskere fordi resterende bits i long1 er 0.
data[1] = (byte)(long1 >> 8);
//data[1] har plass til 3 av de 8 bit fra long2
//venstreskifter 5 plasser fordi de 5 f?rste bits i data[1] er i bruk fra f?r
//trenger ikke maskere vekk bits fordi bits over 256 ikke g?r inn i en byte uansett
data[1] |= (byte)(long2 << 5);
//5 gjenv?rende bit fra long2 til data[2]
//h?yreskift fjerner de bits vi allerede la i data[1]
data[2] = (byte)(long2 >> 3);
//data[2] har plass til de 3 bit fra long3
data[2] |= (byte)(long3 << 5);
System.out.printf("%x %x %x\n", data[0], data[1], data[2]);

 */