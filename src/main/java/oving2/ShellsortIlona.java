package oving2;

import java.util.Date;
import java.util.Random;

public class ShellsortIlona {

    enum TabellType{
       Random, Sortert, Baklengst_sortert
    }

    public static void shellSortGonnet(int []t, double deletall) {
        int s = t.length/2;
        while (s > 0) {
            for (int i = s; i < t.length; ++i) {
                int j = i;
                int flytt = t[i];
                while (j >= s && flytt < t[j-s]) {
                    t[j] = t[j-s];
                    j -= s;
                }
                t[j] = flytt;
            }
            s = (s > 1 && s < deletall) ? 1 : (int)(s / deletall);
        }
    }

    public static void shellSortKnuth(int[] t) {
        int s = 1;
        while (s < t.length) {
            s = s * 3 + 1;
        }
        while (s > 0) {
            for (int i = s; i < t.length; ++i) {
                int j = i;
                int flytt = t[i];
                while (j >= s && flytt < t[j-s]) {
                    t[j] = t[j-s];
                    j -= s;
                }
                t[j] = flytt;
            }
            s /= 3;
        }
    }

    public static String timecalculator(){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do{
            Math.pow(1.00001,10000);
            slutt = new Date();
            ++runder;
        }

        while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)(slutt.getTime()-start.getTime()) / runder;
        return "Millisekund pr. runde:" + (tid * 10000);
    }

    public static int[] getTabellRandom(int length, int upperBound) {
        Random randomGen = new Random(17);
        int[] tabell = new int[length];
        for (int i = 0; i < length; i++) {
            tabell[i] = randomGen.nextInt(upperBound);
        }
        return tabell;
    }

    public static int[] getTabellSortert(int length) {
        int[] tabell = new int[length];
        for (int i = 0; i < length; i++) {
            tabell[i] = i;
        }
        return tabell;
    }

    public static int[] getTabellBaklengst(int length) {
        int[] tabell = new int[length];
        for (int i = 0; i < length; i++) {
            tabell[i] = length - i;
        }
        return tabell;
    }

    public static int sjekkSum(int []t) {
        int sum = 0;
        for (int i = 0; i < t.length; i++) {
            sum += t[i];
        }
        return sum;
    }

    public static boolean sjekkRekkefoelge(int []t) {
        for (int i = 0; i < t.length - 1; i++) {
            if (t[i] > t[i + 1]) return false;
        }
        return true;
    }

    public static void gjoerGonnet(int[] tabell, double deletall, TabellType type) {
        System.out.println("Gonnets sekvens med deletallet " + deletall);
        System.out.println("\t" + type + " tabell:");
        int sumFoer = sjekkSum(tabell);
        long startTid = System.nanoTime();
        shellSortGonnet(tabell, deletall);
        long sluttTid = System.nanoTime();
        System.out.println("\t\t" + timecalculator());
        long varighet = (sluttTid - startTid)/1000000;
        System.out.println("\t\tTotal tid: " + varighet + " millisekunder");
        System.out.println("\t\tSjekk sum: " + (sumFoer == sjekkSum(tabell)));
        System.out.println("\t\tSjekk rekkefoelge: " + sjekkRekkefoelge(tabell));
        System.out.println("==============================================\n");
    }

    public static void gjoerKnuth(int[] tabell, TabellType type) {
        System.out.println("Knuths sekvens ((s^k - 1)/2): ");
        System.out.println("\t" + type + " tabell:");
        int sumFoer = sjekkSum(tabell);
        long startTid = System.nanoTime();
        shellSortKnuth(tabell);
        long sluttTid = System.nanoTime();
        System.out.println("\t\t" + timecalculator());
        long varighet = (sluttTid - startTid)/1000000;
        System.out.println("\t\tTotal tid: " + varighet + " millisekunder");
        System.out.println("\t\tSjekk sum: " + (sumFoer == sjekkSum(tabell)));
        System.out.println("\t\tSjekk rekkefoelge: " + sjekkRekkefoelge(tabell));
        System.out.println("==============================================\n");
    }

    public static void gjoerOppgave2(int[] tabell, double deletall,
                                     TabellType type) {
        System.out.println("Gonnets sekvens med deletallet " + deletall);
        System.out.println("\t" + type + " tabell av størrelsen :" + tabell.length);
        int sumFoer = sjekkSum(tabell);
        long startTid = System.nanoTime();
        shellSortGonnet(tabell, deletall);
        long sluttTid = System.nanoTime();
        System.out.println("\t\t" + timecalculator());
        long varighet = (sluttTid - startTid)/1000000;
        System.out.println("\t\tTotal tid: " + varighet + " millisekunder");
        System.out.println("\t\tSjekk sum: " + (sumFoer == sjekkSum(tabell)));
        System.out.println("\t\tSjekk rekkefoelge: " + sjekkRekkefoelge(tabell));
        System.out.println("==============================================\n");
    }

    public static void main(String[] args) {
        int lengde = 1000000;
        int upperBound = 10;
        TabellType type = TabellType.Random;

//        gjoerGonnet(getTabellRandom(lengde, upperBound),
//                1.7, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound),
//                2.2, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound),
//                3, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound),
//                4, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound),
//                100, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound),
//                1000, type);
//
//        gjoerKnuth(getTabellRandom(lengde, upperBound), type);

//        gjoerGonnet(getTabellRandom(lengde, upperBound), 2.2, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound), 2.3, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound), 2.4, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound), 2.6, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound), 2.7, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound), 2.8, type);
//
//        gjoerGonnet(getTabellRandom(lengde, upperBound), 3.0, type);

        gjoerOppgave2(getTabellRandom(100000000, upperBound),
                2.8, type);

        gjoerOppgave2(getTabellRandom(300000000, upperBound),
                2.8, type);
    }
}
