package spiderman;

public class Cluster {
    private int dimensionNumber;
    private int numOfCanonEvents;
    private int dimensionWeight;

    public Cluster() {
        dimensionNumber = 0;
        numOfCanonEvents = 0;
        dimensionWeight = 0;
    }
    
    public Cluster(int a, int b, int c) {
        this.dimensionNumber = a;
        this.numOfCanonEvents = b;
        this.dimensionWeight = c;
    }

    public int getdimensionNumber() {
        return dimensionNumber;
    }

    public void setdimensionNumber(int dimensionNumber) {
        this.dimensionNumber = dimensionNumber;
    }

    public int getnumOfCanonEvents() {
        return numOfCanonEvents;
    }

    public void setnumOfCanonEvents(int numOfCanonEvents) {
        this.numOfCanonEvents = numOfCanonEvents;
    }

    public int getdimensionWeight() {
        return dimensionWeight;
    }

    public void setdimensionWeight(int dimensionWeight) {
        this.dimensionWeight = dimensionWeight;
    }
}
