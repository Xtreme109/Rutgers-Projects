package spiderman;
import java.util.*;

public class Spiderverse {

    private Set<Dimension> nodes = new HashSet<>();
    
    public void addNode(Dimension nodeA) {
        nodes.add(nodeA);
    }

    public Set<Dimension> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Dimension> nodes) {
        this.nodes = nodes;
    }


    public Spiderverse Dijkstra(Spiderverse graph, Dimension root) {

        root.setDistance(0);
        Set<Dimension> unreachedVs = new HashSet<>();

        Set<Dimension> reachedVs = new HashSet<>();
        unreachedVs.add(root);

        while (unreachedVs.size() != 0) {
            Dimension currentDim = getLowestDistanceNode(unreachedVs);
            unreachedVs.remove(currentDim);
            for (Map.Entry<Dimension, Integer> adjacencyPair : currentDim.getAdjacentNodes().entrySet()) {
                Dimension adjacentNode = adjacencyPair.getKey();
                Integer weight_edge = adjacencyPair.getValue();

                if (!reachedVs.contains(adjacentNode)) {
                    getMinDistance(adjacentNode, weight_edge, currentDim);
                    unreachedVs.add(adjacentNode);
                }
            }
            reachedVs.add(currentDim);
        }
        return graph;
    }

    private static Dimension getLowestDistanceNode(Set<Dimension> unreachedVs) {
        Dimension lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Dimension n : unreachedVs) {
            int nodeDistance = n.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = n;
            }
        }
        return lowestDistanceNode;
    }

    private static void getMinDistance(Dimension evaluatDimension, Integer weight_edge, Dimension root) {
        Integer rootDistance = root.getDistance();
        if (rootDistance + weight_edge < evaluatDimension.getDistance()) {
            evaluatDimension.setDistance(rootDistance + weight_edge);
            LinkedList<Dimension> shortestPath = new LinkedList<>(root.getShortestPath());
            shortestPath.add(root);
            evaluatDimension.setShortestPath(shortestPath);
        }
    }

   

}