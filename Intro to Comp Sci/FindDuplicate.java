/*************************************************************************
 *  Compilation:  javac FindDuplicate.java
 *  Execution:    java FindDuplicate
 *
 *  @author:Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 * FindDuplicate that reads n integer arguments from the command line 
 * into an integer array of length n, where each value is between is 1 and n, 
 * and displays true if there are any duplicate values, false otherwise.
 *
 *  % java FindDuplicate 10 8 5 4 1 3 6 7 9
 *  false
 *
 *  % java FindDuplicate 4 5 2 1 
 *  true
 *************************************************************************/

public class FindDuplicate {

    public static void main(String[] args) {

		int[] intarray;
		intarray = new int[args.length];
		boolean duplicate = false;


		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args.length; j++) {
				int a = Integer.parseInt(args[i]);
				int b = Integer.parseInt(args[j]);

				if (a == b) {
					if (i != j) {
						duplicate = true;
					}
				}

			}

			intarray[i] = Integer.parseInt(args[i]);
		}

		System.out.println(duplicate);
	}
}
