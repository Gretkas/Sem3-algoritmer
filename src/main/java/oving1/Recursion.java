package oving1;

public class Recursion {
        private Double expCalc(Double x, int n, Double currentX){
            if(n != 1){
                n -= 1;
                currentX *= x;
                return expCalc(x, n, currentX);
            }else {
                return currentX;
            }
        }

        public Double exp(Double x, int n){
            if (n == 0){
                return 1.0;
            }else if(n < 0){
                throw new IllegalArgumentException("Don't even try");
            }
            return expCalc(x, n, x);
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
            if(n == 1){
                return 1;
            }
            double bong = exp3((x*x), n/2);
            return bong;

        }



        public static void main(String[]args){
            Recursion recursion = new Recursion();
            //System.out.println(recursion.exp2(1.00001, 10000));
            //System.out.println(Math.pow(1.00001, 10000));
            System.out.println(recursion.exp3(3, 8));

        }


}
