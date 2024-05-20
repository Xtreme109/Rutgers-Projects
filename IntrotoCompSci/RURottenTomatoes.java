/*************************************************************************
 *  Compilation:  javac RURottenTomatoes.java
 *  Execution:    java RURottenTomatoes
 *
 *  @author:Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 * RURottenTomatoes creates a 2 dimensional array of movie ratings 
 * from the command line arguments and displays the index of the movie 
 * that has the highest sum of ratings.
 *
 *  java RURottenTomatoes 3 2 5 2 3 3 4 1
 *  0
 *************************************************************************/

public class RURottenTomatoes {

    public static void main(String[] args) {

		int rows = Integer.parseInt(args[0]);
		int columns = Integer.parseInt(args[1]);
		int count = 0;

		int[][] ratings;
		ratings = new int[columns][rows];

		int[] totals;
		totals = new int[columns];


		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ratings[j][i] = Integer.parseInt(args[count+2]);
				totals[j] += ratings[j][i];
				count++;
			}
		}


		int biggest = 0;
		int index = 0;
		for (int i = 0; i < columns; i++) {
			int a = totals[i];
			if (a > biggest) {
				biggest = a;
				index = i;
			}
		}

		System.out.println(index);


	}
}
