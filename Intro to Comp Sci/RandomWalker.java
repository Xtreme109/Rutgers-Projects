

/*************************************************************************
 *  Compilation:  javac RandomWalker.java
 *  Execution:    java RandomWalker 10
 *
 *  @author:Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 * The program RandomWalker that takes an int command-line argument n
 * and simulates the motion of a random walk for n steps. Print the
 * location at each step (including the starting point), treating the
 * starting point as the origin (0, 0). Also, print the square of the
 * final Euclidean distance from the origin.
 *
 *  % java RandomWalker 10
 * (0,0)
 * (-1,0)
 * (-1,-1)
 * (-1,-2)
 * (-1,-3)
 * (-1,-4)
 * (-1,-5)
 * (0,-5)
 * (-1,-5)
 * (-2,-5)
 * (-2,-4)
 * Squared distance = 20.0
 *
 *************************************************************************/

public class RandomWalker {

    public static void main(String[] args) {
    
    int steps = Integer.parseInt(args[0]);
    int xcord = 0;
    int ycord = 0;


    System.out.println("(" + xcord + "," + ycord + ")");

    for (int i = 0; i < steps; i++) {
        double direction = Math.random() * 4;
        int compass = (int)direction;

        if (compass == 0) { // North
            xcord += 1;
        }else if (compass == 1) {  // South
            xcord -= 1;
        }else if (compass == 2) {  // East
            ycord += 1;
        }else if (compass == 3) {  // West
            ycord-= 1;
        }

        System.out.println("(" + xcord + "," + ycord + ")");
    }
    
    double euclid = Math.pow((xcord - 0),2) + Math.pow((ycord - 0),2);
    System.out.println("Squared distance = " + euclid);
	
    }
}
