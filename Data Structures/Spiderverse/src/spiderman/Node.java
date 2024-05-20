package spiderman;

public class Node {
    private Cluster data;
    private Node next;

    public Node() {
        data = null;
        next = null;
    }
    
    public Node(Cluster data) {
        this.data = data;
        this.next = null;
    }

    public Cluster getCluster() {
        return data;
    }

    public void setCluster(Cluster data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
