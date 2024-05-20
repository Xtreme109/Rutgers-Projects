/*************************************************************************
 *  Compilation:  javac LargestOfFive.java
 *  Execution:    java LargestOfFive 35 10 32 1 8
 *
 *  @author:Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 *  Takes five distinct integers as command-line arguments and prints the 
 *  largest value
 *
 *
 *  % java LargestOfFive 35 10 32 1 8
 *  35
 *
 *  Assume the inputs are 5 distinct integers.
 *  Print only the largest value, nothing else.
 *
 *************************************************************************/

public class LargestOfFive {

    public static void main (String[] args) {
        int biggest = Integer.parseInt(args[0]);

        for (int i = 0; i < 4; i++) {
            int a = Integer.parseInt(args[i]);
            
            if (biggest < a) {
                biggest = a;
            }
        }
        
        System.out.println(biggest);
    }
}