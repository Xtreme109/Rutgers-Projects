/*************************************************************************
 *  Compilation:  javac CheckDigit.java
 *  Execution:    java CheckDigit 020131452
 *
 *  @author:Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 *  Takes a 12 or 13 digit integer as a command line argument, then computes
 *  and displays the check digit
 *
 *  java CheckDigit 048231312622
 *  0
 *
 *  java CheckDigit 9780470458310
 *  0
 * 
 *  java CheckDigit 9780470454310
 *  8
 * 
 *  Print only the check digit character, nothing else.
 *
 *************************************************************************/

public class CheckDigit {

    public static void main (String[] args) {

        long digit = Long.parseLong(args[0]);

        long dividend1 = digit;
        long dividend2 = digit;
        long total1 = 0;
        long total2 = 0;

        
        while (dividend1 > 0) {
            long a = dividend1 % 10;
            dividend1 = dividend1 / 10;
            dividend1 = dividend1 / 10;
            total1 += a;
        }
        

        while (dividend2 > 0) {
            dividend2 = dividend2 / 10;
            long a = dividend2 % 10;
            dividend2 = dividend2 / 10;
            total2 += a;
        }
        
        long check = ((total1 % 10) + (((total2 % 10) * 3) % 10)) % 10;
        System.out.println(check);

    }
}