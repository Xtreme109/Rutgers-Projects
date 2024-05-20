/*************************************************************************
 *  Compilation:  javac RecursiveAppend.java
 *  Execution:    java RecursiveAppend
 *
 *  @author: Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 *************************************************************************/

public class RecursiveAppend {

    // Returns the original string appended to the original string n times 
    public static String appendNTimes (String original, int n) {

        if (n > 0) {
            return original + appendNTimes(original, n-1);
            /*basically if it doesnt loop via a return statement it becomes like a pyramid,
            after one section finishes it returns back to the original section so like n:2, n:1, n:0, n:1, n:2.*/
        } else {
            return original;
        }
    }

    public static void main (String[] args) {
        String test = "cat";

	    System.out.println(appendNTimes(test, 2));
    }
}
