package spiderman;
import java.util.*;

public class Table {
    private List<List<Dimension>> dimensionList;
    private Hashtable<Integer, ArrayList<Dimension>> adjlist;
    private ArrayList<Person> people;

    private int dimensionsAdded;
    private int size;
    private double threshold;
    

    public Table(int initial_size, double threshold) {
        this.size = initial_size;
        this.threshold = threshold;
        dimensionList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            dimensionList.add(new ArrayList<>());
        }
        dimensionsAdded = 0;

        people = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void addPerson(Person p) {
        people.add(p);
    }

    public Person getPerson(String name) {
        for (Person p : people){
            if( p.getName().equals(name))
                return p;
        }
        return null;
    }


    public List<List<Dimension>> getdimensionList() {
        return dimensionList;
    }

    private void rehash() {
        int double_size = size * 2;

        List<List<Dimension>> newdimensionList = new ArrayList<>(double_size);

        for (int i = 0; i < double_size; i++) {
            newdimensionList.add(new ArrayList<>());
        }
        for (List<Dimension> cluster : dimensionList) {
            for (Dimension dimension : cluster) {
                int newIndex = dimension.getNumber() % double_size;
                newdimensionList.get(newIndex).add(0, dimension);
            }
        }
        dimensionList = newdimensionList;
        size = double_size;
    }

    public void insert(Dimension dimension) {
        int index = dimension.getNumber() % size;
        dimensionList.get(index).add(0, dimension);
        dimensionsAdded++;
        if ((double) dimensionsAdded / size >= threshold) {
            rehash();
        }
    }
   
    public void wrapClusters() {
        for (int i = 0; i < dimensionList.size(); i++) {
            List<Dimension> cluster = dimensionList.get(i);
            List<Dimension> prev1 = dimensionList.get((i - 1 + size) % size);
            List<Dimension> prev2 = dimensionList.get((i - 2 + size) % size);
            if (!prev1.isEmpty()) {
                cluster.add(prev1.get(0));
            }
            if (!prev2.isEmpty()) {
                cluster.add(prev2.get(0));
            }
        }
    }
    
 

    public Hashtable<Integer, ArrayList<Dimension>> createAdjacencyList() {
        adjlist = new Hashtable<>();

        for (int i = 0; i < dimensionList.size(); i++) {
            for (int j = 0; j< dimensionList.get(i).size(); j++){
                Dimension dim = dimensionList.get(i).get(j);
                int d = dim.getNumber();

                if (!adjlist.containsKey(d)){
                    adjlist.put(d, new ArrayList<>());
                    adjlist.get(d).add(dim);
                }

                if (j != 0) {
                    adjlist.get(d).add(dimensionList.get(i).get(0));
                    adjlist.get(dimensionList.get(i).get(0).getNumber()).add(dim);
                }

            }
        }

        return adjlist;
    }

}
