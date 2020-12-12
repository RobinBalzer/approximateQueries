import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// class for specifying the query in form of a graph. it consists of QueryNodes and QueryEdges.
// so far we can add nodes or edges to it.
public class QueryGraph {
    private Set<QueryNode> nodes;

    QueryGraph() {
        nodes = new HashSet<>();
    }

    public void addQueryObjectNode(QueryNode... n) {
        nodes.addAll(Arrays.asList(n));
    }

    public void addQueryObjectEdge(QueryNode source, QueryNode target, String label) {

        // .add on a hashSet does not add an element that is already present! We don't need to check whether the node is already created.

        nodes.add(source);
        nodes.add(target);

        // if this edge is already present -> don't add it.

        for (QueryEdge edge : source.edges) {
            if (edge.source == source && edge.target == target && edge.label.equalsIgnoreCase(label)) {
                return;
            }
        }
        source.edges.add(new QueryEdge(source, target, label));
    }

    public void printGraph() {
        for (QueryNode node : nodes) {
            for (QueryEdge edge : node.edges) {
                edge.print();
            }
        }
    }
}
