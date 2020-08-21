package oving1;

import java.util.Date;

public class Kalkis {

    /**
     * The program performs same exponentiation by two different recursion
     * algorithms.
     * The first one is a linear recursion, where time complexity T(n) = Theta(n).
     * The second one is logarithmic, and therefore more efficient.
     * Time complexity of the second algorithm can be proven to be logarithmic
     * in a following analysis:
     * T(n) = {1, when n = 0; T(n/2) + 1, when n is even; T((n-1)/2) + 1,
     * when n is odd}.
     * T(1) = Theta(1).
     * T(n/2) + 1: a = 1, b = 2, k = 0 -> 2^0 == 1 -> T(n/2) + 1 =
     * Theta(n^0*log(n)) = Theta(log(n)).
     * T((n-1)/2) + 1: a = 1, b = 2, k = 0 (as above), therefore
     * T((n-1)/2) + 1 = Theta(log(n)).
     * Q.E.D. biiish
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(toPower(1.001, 5000));
        System.out.println(timecalculator());

        System.out.println(toPowerSplit(1.001, 5000));
        System.out.println(timecalculator());
    }

    /**
     * Method for exponentiation via recursion.
     * T(n) = T(n-1) + n, where T(n) is time complexity and recursive step
     * T(n -1) is linear.
     * T(n) = {1, when n = 0; x*x^n-1, when n > 0}, where n = 0 is base case.
     * Negative exponents are not the focus of this method.
     * @param base
     * @param exponent
     * @return 1 in base case when exponent equals 0; recursive call otherwise.
     */
    public static double toPower(double base, int exponent) {
        if (exponent == 0) return 1;
        else return base*toPower(base, exponent-1);
    }

    /**
     * Method for exponentiation via one recursive call with half of the
     * data size.
     * T(n) = {1, when n = 0; x*(x^2)^((n-1)/2), when n is odd;
     * (x^2)^(n/2), when n is even}, where T(n) is time complexity.
     * Both recursive steps are logarithmic.
     * Negative exponents are not the focus of this method.
     * @param base
     * @param exponent
     * @return 1 in base case when exponent equals 0; recursive call otherwise.
     */
    public static double toPowerSplit(double base, int exponent) {
        if (exponent == 0) return 1;
        else if (exponent%2 == 0) return toPowerSplit(base*base, exponent/2);
        else return base*toPowerSplit(base*base, (exponent - 1)/2);
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
}
