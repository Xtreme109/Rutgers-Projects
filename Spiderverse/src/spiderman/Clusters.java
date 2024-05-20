package spiderman;

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
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to 
 *    that dimension in order (space separated)
 *    n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */

public class Clusters {

    static class LinkedList {
        Node front;

        public void addFirst(Cluster data) {
            Node newNode = new Node(data);

            if (front == null) {
                front = newNode;
            } else {
                newNode.setNext(front);
                front = newNode;
            }
        }

        public void addLast(Cluster data) {
            Node end = new Node(data);

            if (front == null) {
                front = end;
            } else {
                Node current = front;
                while (current.getNext() != null) {
                    current = current.getNext();
                }

                current.setNext(end);
            }
        }

        // Method to print the list data for debugging
        public void printList() {
            Node temp = front;

            while (temp != null) {
                System.out.print(temp.getCluster().getdimensionNumber() + " ");
                temp = temp.getNext();
            }
        }
    }

    public static LinkedList[] clusterList;
    

    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }

        clusterBuilder(args[0]);
        
        StdOut.setFile(args[1]);
        for (int i = 0; i < clusterList.length; i++) {
            Node temp = clusterList[i].front;

            while (temp != null) {
                StdOut.print(temp.getCluster().getdimensionNumber() + " ");
                temp = temp.getNext();
            }
            
            StdOut.println();
        }
    }

    public static void clusterBuilder(String file) {
        StdIn.setFile(file);
        int numOfDimensions = StdIn.readInt();
        int tableSize = StdIn.readInt();
        double capacity = StdIn.readDouble();


        if (clusterList == null) {
            clusterList = new LinkedList[tableSize];
        }

        for (int i = 0; i < tableSize; i++) {
            clusterList[i] = new LinkedList();
        }

        int dimensionsAdded = 0;
        for (int i = 0; i < numOfDimensions; i++) {
            int dimensionNumber = StdIn.readInt();
            int hashIndex = dimensionNumber % tableSize;
            Cluster cluster = new Cluster (dimensionNumber, StdIn.readInt(), StdIn.readInt());
            clusterList[hashIndex].addFirst(cluster);
            dimensionsAdded++;

            double loadFactor = (double) dimensionsAdded / tableSize;
            if (loadFactor >= capacity) {
                clusterList = resize(clusterList);

                tableSize = clusterList.length;
            }
        }

        tableSize = clusterList.length;
        for (int i = 0; i < tableSize; i++) {
            if (i == 0) {
                clusterList[i].addLast(clusterList[tableSize-1].front.getCluster());
                clusterList[i].addLast(clusterList[tableSize-2].front.getCluster());
            } else if (i == 1) {
                clusterList[i].addLast(clusterList[0].front.getCluster());
                clusterList[i].addLast(clusterList[tableSize-1].front.getCluster());
            } else {
                clusterList[i].addLast(clusterList[i-1].front.getCluster());
                clusterList[i].addLast(clusterList[i-2].front.getCluster());
            }
        }
    }

    public static LinkedList[] resize(LinkedList[] table) {
        int newTableSize = table.length * 2;
        LinkedList[] newClusterTable = new LinkedList[newTableSize];
        
        for (int i = 0; i < newTableSize; i++) {
            newClusterTable[i] = new LinkedList();
        }
        
        for (LinkedList list : table) {
            Node current = list.front;

            while (current != null) {
                int newHashIndex = current.getCluster().getdimensionNumber() % newTableSize;
                Cluster clusterCopy = new Cluster(current.getCluster().getdimensionNumber(), current.getCluster().getnumOfCanonEvents(), current.getCluster().getdimensionWeight());
                newClusterTable[newHashIndex].addFirst(clusterCopy);
                current = current.getNext();
            }
        }

        return newClusterTable;
    }
}
