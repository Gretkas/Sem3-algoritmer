package oving1;

import java.util.Date;

/**
 * Different ways of calculating exponents by using recursion.
 * Calculating processing time of different exponential implementations
 */
public class Recursion {

    /**
     * Method for calculating x to the power of n using recursion.
     * Using incremental recursion (n-1) with linear time.
     * for each n, one call occurs. This resembles an iterative approach similar to for loops.
     *
     * @param x the x
     * @param n the n
     * @return calculated answer as a double
     */
    public double exp(double x, int n){
        if(n == 0){
            return 1;
        }
        double d = exp(x, n-1);
        x *= d;
        return x;
    }

    /**
     * Child method for linearly calculating exponentials with even n
     *
     * @param exp        the exp
     * @param n          the n
     * @param currentEXP the The current calculated value of x to the power of n
     * @return the result of the calculation
     */
    public Double expCalc2Par(Double exp, int n, Double currentEXP){
        if(n != 1){
            currentEXP *= exp;
            return expCalc2Par(exp, n - 1, currentEXP);
        }else{
            return currentEXP;
        }
    }

    /**
     * Child method for linearly calculating exponentials with odd n
     *
     * @param exp        the exp
     * @param n          the n
     * @param currentEXP the The current calculated value of x to the power of n
     * @return The result of the calculation
     */
    public Double expCalc2Odd(Double exp, int n, Double currentEXP){
        if(n >= 2) {
            currentEXP *= exp;
            return expCalc2Odd(exp, n - 1, currentEXP);
        }else{
            return currentEXP;
        }
    }

    /**
     * This method was supposed to calculate x to the power of n in linear time, but in half the time, as the starting index is n/2.
     * The algorithm is correct, but does not reduce calculation time, and in some cases can increase calculation time. The reason for this is unknown, as we didn't bother debugging after finding a better approach.
     *
     * @param x the x
     * @param n the n
     * @return The result of the calculation
     */
    public Double exp2(Double x, int n){
        if (n == 0){
            return 1.0;
        }else if(n < 0){
            throw new IllegalArgumentException("Don't even try");
        }else if(n % 2 == 0){
            return expCalc2Par(x*x, n/2, x*x);
        }else{
            return x * expCalc2Odd(x*x, (n-1)/2, x*x);
        }
    }

    /**
     * Method for calculating x to the power of n in logarithmic time. Uses exponentiation by squaring. Odd numbers does not match the formula as specified in the assignment.
     * Instead of decrementing odd numbers by one before the dividing, the number is rounded down after division by integers. This gives the exact same result.
     *
     * @param x the x
     * @param n the n
     * @return The result of the calculation
     */
    public double exp3(double x, int n){
        if(n == 0){
            return 1;
        }
        double bong = exp3(x*x, n/2);
        if( n%2 == 0){ return bong; } else{ return x*bong; }
    }

    /**
     * Method for checking computation time of different algorithms in milliseconds. Shamelessly stolen from the lecture notes.
     *
     * @return The average computational time.
     */
    public String timecalculator(){
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

    /**
     * Running different algorithms and printing them out.
     */
    public static void main(String[]args){
        Recursion recursion = new Recursion();

        System.out.println(recursion.exp(2, 10));
        System.out.println(Math.pow(2 , 10));
        System.out.println(recursion.exp3(2, 10));
        System.out.println(recursion.timecalculator());
    }
}