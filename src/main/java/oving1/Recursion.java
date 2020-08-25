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
     * Method for calculating x to the power of n in logarithmic time. Uses exponentiation by squaring. Odd numbers does not match the formula as specified in the assignment.
     * Instead of decrementing odd numbers by one before the dividing, the number is rounded down after division by integers. This gives the exact same result.
     *
     * @param x the x
     * @param n the n
     * @return The result of the calculation
     */
    public double exp2(double x, int n){
        if(n == 0){
            return 1;
        }
        double d = exp2(x*x, n/2);
        if( n%2 == 0){
            return d;
        } else{
            return x*d;
        }
    }

    /**
     * Method for checking computation time of different algorithms in milliseconds. Shamelessly stolen from the lecture notes.
     *
     * @return The average computational time.
     */
    public String timecalculator(int methodID,double x, int n){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        switch (methodID){
            case 1:
                do{
                    Math.pow(x,n);
                    slutt = new Date();
                    ++runder;
                }
                while (slutt.getTime()-start.getTime() < 1000);
                tid = (double)(slutt.getTime()-start.getTime()) / runder;
                return "Nanosekunder pr. runde: " + (tid * 1000000);
            case 2:
                do{
                    exp(x,n);
                    slutt = new Date();
                    ++runder;
                }
                while (slutt.getTime()-start.getTime() < 1000);
                tid = (double)(slutt.getTime()-start.getTime()) / runder;
                return "Nanosekunder pr. runde: " + (tid * 1000000);
            case 3:
                do{
                    exp2(x,n);
                    slutt = new Date();
                    ++runder;
                }
                while (slutt.getTime()-start.getTime() < 1000);
                tid = (double)(slutt.getTime()-start.getTime()) / runder;
                return "Nanosekunder pr. runde: " + (tid * 1000000);
        }
        return null;

    }

    /**
     * Running different algorithms and printing them out.
     */
    public static void main(String[]args){
        Recursion recursion = new Recursion();

        System.out.println("\nx=2, n=10:");
        System.out.println(recursion.exp(2, 10));
        System.out.println(Math.pow(2 , 10));
        System.out.println(recursion.exp2(2, 10));
        System.out.println("----------------------");

        System.out.println("\nx=3, n=14:");
        System.out.println(recursion.exp(3, 14));
        System.out.println(Math.pow(3 , 14));
        System.out.println(recursion.exp2(3, 14));
        System.out.println("----------------------");

        System.out.println("\nx=1,001, n=1000:");
        System.out.println(recursion.exp(1.001, 1000));
        System.out.println(Math.pow(1.001 , 1000));
        System.out.println(recursion.exp2(1.001, 1000));


        System.out.println("\nTime calculation:");
        System.out.println("\nMath.pow:");
        System.out.println(recursion.timecalculator(1,1.001,10));
        System.out.println(recursion.timecalculator(1,1.001,100));
        System.out.println(recursion.timecalculator(1,1.001,1000));
        System.out.println("----------------------");

        System.out.println("\nexp oppg2:");
        System.out.println(recursion.timecalculator(3,1.001,10));
        System.out.println(recursion.timecalculator(3,1.001,100));
        System.out.println(recursion.timecalculator(3,1.001,1000));
        System.out.println(recursion.timecalculator(3,1.001,10000));
        System.out.println(recursion.timecalculator(3,1.001,100000));
        System.out.println("----------------------");

        System.out.println("\nexp oppg1:");
        System.out.println(recursion.timecalculator(2,1.001,10));
        System.out.println(recursion.timecalculator(2,1.001,100));
        System.out.println(recursion.timecalculator(2,1.001,1000));


    }
}