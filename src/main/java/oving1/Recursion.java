package oving1;

import java.util.Date;

public class Recursion {

        private double exp(double x, int n){
           if(n == 0){
               return 1;
           }
           double d = exp(x, n-1);
            x *= d;
           return x;
        }

        private Double expCalc2Par(Double exp, int n, Double currentEXP){
            if(n != 1){
                currentEXP *= exp;
                return expCalc2Par(exp, n - 1, currentEXP);
            }else{
                return currentEXP;
            }
        }

        private Double expCalc2Odd(Double exp, int n, Double currentEXP){
            if(n >= 2) {
                currentEXP *= exp;
                return expCalc2Odd(exp, n - 1, currentEXP);
            }else{
                return currentEXP;
            }
        }

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

        public double exp3(double x, int n){
            if(n == 0){
                return 1;
            }
            double bong = exp3(x*x, n/2);
            if( n%2 == 0){ return bong; } else{ return x*bong; }
        }
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
            return "Millisekund pr. runde:" + (tid * 100000);
        }

        public static void main(String[]args){
            Recursion recursion = new Recursion();

            System.out.println(recursion.exp(2, 10));
            System.out.println(Math.pow(2 , 10));
            System.out.println(recursion.exp3(2, 10));
            System.out.println(recursion.timecalculator());
        }
}
