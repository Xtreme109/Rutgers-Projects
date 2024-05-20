/*************************************************************************
 *  Compilation:  javac PolygonTransform.java
 *  Execution:    java PolygonTransform
 *
 *  @author:Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 *************************************************************************/

public class PolygonTransform {


    // Returns a new array that is an exact copy of the given array. 
    // The given array is not mutated. 
    public static double[] copy(double[] array) {
        double[] newarray;
        newarray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            newarray[i] = array[i];
        }

        return newarray;
	
    }
    
    // Scales the given polygon by the factor alpha. 
    public static void scale(double[] x, double[] y, double alpha) {

        for (int i = 0; i < x.length; i++) {
            x[i] = x[i] * alpha;
            y[i] = y[i] * alpha;
        }
        
        
    }
    
    // Translates the given polygon by (dx, dy). 
    public static void translate(double[] x, double[] y, double dx, double dy) {

        for (int i = 0; i < x.length; i++) {
        x[i] = x[i] + dx;
        y[i] = y[i] + dy;
        }
        
    }
    
    // Rotates the given polygon theta degrees counterclockwise, about the origin. 
    public static void rotate(double[] x, double[] y, double theta) {

        theta = Math.toRadians(theta);
        

	    for (int i = 0; i < x.length; i++) {
        double xcos = x[i] * Math.cos(theta);
        double ycos = y[i] * Math.cos(theta);
        double xsin = x[i] * Math.sin(theta);
        double ysin = y[i] * Math.sin(theta);

        x[i] = xcos - ysin;
        y[i] = ycos + xsin;
        }

    }

    // Tests each of the API methods by directly calling them. 
    public static void main(String[] args) {
        
    }
}
