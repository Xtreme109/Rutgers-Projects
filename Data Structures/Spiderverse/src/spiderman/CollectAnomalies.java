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
 * HubInputFile name is passed through the command line as args[2]
 * Read from the HubInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * CollectedOutputFile name is passed in through the command line as args[3]
 * Output to CollectedOutputFile with the format:
 * 1. e Lines, listing the Name of the anomaly collected with the Spider who
 *    is at the same Dimension (if one exists, space separentated) followed by 
 *    the Dimension number for each Dimension in the route (space separentated)
 * 
 * @author Seth Kelley
 */

public class CollectAnomalies {
    
    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.CollectAnomalies <dimension INput file> <spiderverse INput file> <hub INput file> <collected OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        StdIn.setFile(args[0]); // dimension.in

        int num_dimensions = StdIn.readInt();
        Table sv = new Table(StdIn.readInt(), StdIn.readInt());

        for (int i = 0; i < num_dimensions; i++) {
            sv.insert(new Dimension(StdIn.readInt(), StdIn.readInt(), StdIn.readInt()));
        }
        sv.wrapClusters();

        
        
        StdIn.setFile(args[2]); // hub.in
        int hub = StdIn.readInt();


        StdIn.setFile(args[1]); // spiderverse.in
        int people = StdIn.readInt();

        ArrayList<Person> spiders = new ArrayList<>();
        ArrayList<Person> anomalies = new ArrayList<>();

        for (int i = 0; i < people; i++) {
            int dim = StdIn.readInt();
            String name = StdIn.readString();
            int signature = StdIn.readInt();

            Person p = new Person(dim, name, signature);
            sv.addPerson(p);
            if (dim != hub && dim != signature) 
                anomalies.add(p);
            if (dim == signature)  
                spiders.add(p);

        }

        Hashtable<Integer, ArrayList<Dimension>> adjlist = sv.createAdjacencyList();
        
        Hashtable<Integer,Integer> distance = new Hashtable<>();
        for (Integer i : adjlist.keySet()){
            distance.put(i, Integer.MAX_VALUE);       
        }
        Hashtable<Integer,Integer> parent = new Hashtable<>();
        for (Integer i : adjlist.keySet()){
            parent.put(i, -1);                    
        }

        Queue<Integer> queue = new LinkedList<>();
        distance.put(hub, 0);
        queue.add(hub);

        while (!queue.isEmpty()) {
            int removed_dim = queue.poll();

            for (int i = 0; i < adjlist.get(removed_dim).size(); i++) {
                int neighbor = adjlist.get(removed_dim).get(i).getNumber();
                if (distance.get(neighbor) == Integer.MAX_VALUE) {
                    parent.put(neighbor, removed_dim);
                    distance.put(neighbor, 1 + distance.get(removed_dim));
                    queue.add(neighbor);
                }
            }
        }


        StdOut.setFile(args[3]);

        for (Person iter: anomalies) {
            String anomaly = iter.getName();
            int atd = iter.getDimension();
            String spider = ""; 
            for (Person s: spiders) {
                if (s.getDimension() == atd) {
                    spider = s.getName();
                    break;
                }
            }

            // the variable path has the shortest path
            List<Integer> path = new ArrayList<>();
            int currentNode = atd;
            path.add(atd);
            while (parent.get(currentNode) != -1) {
                path.add(parent.get(currentNode));
                currentNode = parent.get(currentNode);
            }

            // output path from beginning dimension to destination dimension
            if (spider.isBlank()){
                StdOut.print(anomaly + " " );
                for (int i = path.size() - 1; i > 0; i--)
                    StdOut.print(path.get(i) + " ");
            }
            else {
                StdOut.print(anomaly + " " + spider + " ");
            }
            for (int i = 0; i < path.size(); i++)
                StdOut.print(path.get(i) + " ");
            StdOut.println();

        }
        
    }
}
