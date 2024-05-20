package spiderman;

import java.util.*;
/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * SpotInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * Two integers (line seperated)
 *      i.    Line one: The currenting dimension of Spot (int)
 *      ii.   Line two: The dimension Spot wants to go to (int)
 * 
 * Step 4:
 * TrackSpotOutputFile name is passed in through the command line as args[3]
 * Output to TrackSpotOutputFile with the format:
 * 1. One line, listing the dimenstional number of each dimension Spot has visited (space separated)
 * 
 * @author Seth Kelley
 */

public class TrackSpot {
    
    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.TrackSpot <dimension INput file> <spiderverse INput file> <spot INput file> <trackspot OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        
        StdIn.setFile(args[0]); // dimension.in

        int num_dimensions = StdIn.readInt();
        Table sv = new Table(StdIn.readInt(), StdIn.readInt());

        int index = 0;
        while (index < num_dimensions){
            sv.insert(new Dimension(StdIn.readInt(), StdIn.readInt(), StdIn.readInt()));
            index++;
        }


        sv.wrapClusters();

        StdIn.setFile(args[2]); //spot.in
        int current = StdIn.readInt();
        int destination = StdIn.readInt();


        StdOut.setFile(args[3]); // trackspot.out

        Hashtable<Integer, ArrayList<Dimension>> adjlist = sv.createAdjacencyList();

        Hashtable<Integer, Boolean> dfs = new Hashtable<>();

        for (Integer d : adjlist.keySet()){
            dfs.put(d, false);
        }

        ArrayList<Integer> visited = new ArrayList<>();
        ArrayList<Integer> stack = new ArrayList<>();

        stack.add(current);
        while (!stack.isEmpty() || stack.size() != 0) {
            current = stack.remove(0);
            if (dfs.get(current) == false){
                visited.add(current);
                dfs.put(current, true);

                for (int i = adjlist.get(current).size()-1; i >= 0; i--) {
                    int node = adjlist.get(current).get(i).getNumber();
                    if (dfs.get(node) == false)
                        stack.add(0, node);
                }
            }
        }

        for (Integer dim : visited){
            if (dim == destination) break;
            StdOut.print(dim + " ");
        }
        StdOut.print(destination);

    }

}
