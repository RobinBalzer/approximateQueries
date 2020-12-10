import java.util.*;

public class GraphWeighted {
    private Set<NodeWeighted> nodes;


    GraphWeighted() {
        nodes = new HashSet<>();
    }

    public void addNote(NodeWeighted... n) {
        nodes.addAll(Arrays.asList(n));
    }

    public void addEdge(NodeWeighted source, NodeWeighted destination, double weight) {
        nodes.add(source);
        nodes.add(destination);

        addEdgeHelper(source, destination, weight);

    }

    private void addEdgeHelper(NodeWeighted a, NodeWeighted b, double weight) {
        for (EdgeWeighted edge : a.edges) {
            if (edge.source == a && edge.destination == b) {
                edge.weight = weight;
                return;
            }
        }

        a.edges.add(new EdgeWeighted(a, b, weight));
    }

    public void printEdges() {
        for (NodeWeighted node : nodes) {
            LinkedList<EdgeWeighted> edges = node.edges;

            if (edges.isEmpty()) {
                System.out.println("Node " + node.name + " has no edges.");
                continue;
            }
            System.out.print("Node " + node.name + " has edges to: ");

            for (EdgeWeighted edge : edges) {
                System.out.print(edge.destination + "(" + edge.weight + ") ");
            }
            System.out.println();
        }
    }

    public boolean hasEdge(NodeWeighted source, NodeWeighted destination) {
        LinkedList<EdgeWeighted> edges = source.edges;
        for (EdgeWeighted edge : edges) {
            if (edge.destination == destination) {
                return true;
            }

        }
        return false;
    }

    public void resetNodesVisited() {
        for (NodeWeighted node : nodes) {
            node.unvisit();
        }
    }

    // ***********************************
    // ***********************************
    // ***  graph algorithms from here
    // ***********************************
    // ***********************************

    public void DijkstraShortestPath(NodeWeighted start, NodeWeighted end) {
        HashMap<NodeWeighted, NodeWeighted> changedAt = new HashMap<>();
        changedAt.put(start, null);

        // Keeps track of the shortest path we've found so far for every node
        HashMap<NodeWeighted, Double> shortestPathMap = new HashMap<>();

        // setting every nodes' shortest path weight to positive infinity to start
        // except the starting node whose shortest path weight is 0
        for (NodeWeighted node : nodes) {
            if (node == start) {
                shortestPathMap.put(start, 0.0);
            } else shortestPathMap.put(node, Double.POSITIVE_INFINITY);
        }

        // Now we go through all the nodes we can go from the starting node
        for (EdgeWeighted edge : start.edges) {
            shortestPathMap.put(edge.destination, edge.weight);
            changedAt.put(edge.destination, start);
        }

        start.visit();

        while (true) {
            NodeWeighted currentNode = closestReachableUnvisited(shortestPathMap);
            // If we haven't reached the end node yet, and there isn't another
            // reachable node the path between start and end doesn't exist
            // which means that these nodes are not connected.

            if (currentNode == null) {
                System.out.println("There isn't a path between " + start.name + " and " + end.name);
                return;
            }

            // If the closest non-visited node is our destination, we want to print the path

            if (currentNode == end) {
                System.out.println("The path with the smallest weight between " + start.name + " and " + end.name + " is:");
                NodeWeighted child = end;

                String path = end.name;
                while (true) {
                    NodeWeighted parent = changedAt.get(child);
                    if (parent == null) {
                        break;
                    }

                    // Since our changedAt map keeps track of child -> parent relations
                    // in order to print the path we need to add the parent before the child
                    // and its descendants

                    path = parent.name + " " + path;
                    child = parent;
                }
                System.out.println(path);
                System.out.println("The path costs: " + shortestPathMap.get(end));
                return;
            }
            currentNode.visit();

            // Now we go through all the unvisited nodes our current node has an edge to
            // and check whether its shortest path value is better when going through our current node
            // than whether we had before

            for (EdgeWeighted edge : currentNode.edges) {
                if (edge.destination.isVisited())
                    continue;

                if (shortestPathMap.get(currentNode)
                        + edge.weight
                        < shortestPathMap.get(edge.destination)) {
                    shortestPathMap.put(edge.destination, shortestPathMap.get(currentNode) + edge.weight);
                    changedAt.put(edge.destination, currentNode);
                }
            }


        }

    }

    private NodeWeighted closestReachableUnvisited(HashMap<NodeWeighted, Double> shortestPathMap) {
        double shortestDistance = Double.POSITIVE_INFINITY;
        NodeWeighted closestReachableNode = null;
        for (NodeWeighted node : nodes) {
            if (node.isVisited())
                continue;

            double currentDistance = shortestPathMap.get(node);
            if (currentDistance == Double.POSITIVE_INFINITY)
                continue;

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                closestReachableNode = node;
            }
        }
        return closestReachableNode;
    }
}
