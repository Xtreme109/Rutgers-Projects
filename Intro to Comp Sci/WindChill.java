/*************************************************************************
 *  Compilation:  javac WindChill.java
 *  Execution:    java WindChill 35.0 10.0
 *
 *  @author:Joshua Adebola, jaa399@scarletmail.rutgers.edu, jaa399
 *
 *  That takes two double command-line arguments temperature and velocity 
 *  and prints the wind chill (a double) according to the following:
 *
 *  w = 35.74 + 0.6215 * T + (0.4275 * T - 35.75) v^0.16
 *
 *  % java WindChill 35.0 10.0
 *  27.445420765619037
 *
 *  The formula is not valid if T is larger than 50 in absolute value or if 
 *  v is larger than 120 or less than 3.
 *
 *  Assume the inputs are valid values
 *
 *
 *************************************************************************/

public class WindChill {

    public static void main(String[] args) {
        double temperature = Double.parseDouble(args[0]);
        double velocity = Double.parseDouble(args[1]);

        double z = 0.6215*temperature;
        double x = 35.74 + z;

        double c = 0.4275*temperature;
        double v = c - 35.75;

        double b = Math.pow(velocity, .16);

        double WindChills = x + v*b;

        double w = 35.74 + 0.6215*temperature + (0.4275*temperature - 35.75) * Math.pow(velocity, .16);
        //they both do the same thing but the one above is just more efficient and takes less time.
        
	
	System.out.println(w);
    
    

    }
}
