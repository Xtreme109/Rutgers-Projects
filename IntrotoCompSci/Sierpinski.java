/*************************************************************************
 *  Compilation:  javac Sierpinski.java
 *  Execution:    java Sierpinski
 *
 *  @author: Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 *************************************************************************/

public class Sierpinski {

    // Height of an equilateral triangle whose sides are of the specified length. 
    public static double height(double length) {

        double h = 0;
        double sq = Math.sqrt(3);
        h = sq * length/2;

        return h;
    }

    // Draws a filled equilateral triangle whose bottom vertex is (x, y) 
    // of the specified side length. 
    public static void filledTriangle(double x, double y, double length) {
        
        double h = height(length);
        double[] xaxis = {x,x-(length/2),x+(length/2)};  //so the problem was that you just divided the x-coords but,
        double[] yaxis = {y,h+y,h+y};                    //you didnt consider how the coords looked relative to their starting pos
	    StdDraw.filledPolygon(xaxis, yaxis);             //it was the same problem with the y coordinates
    }

    // Draws a Sierpinski triangle of order n, such that the largest filled 
    // triangle has bottom vertex (x, y) and sides of the specified length. 
    public static void sierpinski(int n, double x, double y, double length) {
        System.out.println(length);
        filledTriangle(x, y, length);
        
        double h = height(length);
        if (n > 1) {
            sierpinski(n-1, x, h+y, length/2);          //relative starting pos matters for the x-values here too
            sierpinski(n-1, x-(length/2), y, length/2);
            sierpinski(n-1, x+(length/2), y, length/2);
        }

    }

    // Takes an integer command-line argument n; 
    // draws the outline of an equilateral triangle (pointed upwards) of length 1; 
    // whose bottom-left vertex is (0, 0) and bottom-right vertex is (1, 0); and 
    // draws a Sierpinski triangle of order n that fits snugly inside the outline. 
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        double h = height(1);

	    double[] xaxis = {0,.5,1};
        double[] yaxis = {0,h,0};
	    StdDraw.polygon(xaxis, yaxis);

        sierpinski(n, .5, 0, .5);
    }
}
