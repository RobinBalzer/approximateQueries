package ClassicalAnswers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProductAutomatonGraph {
    public Set<ProductAutomatonNode> nodes;

    /**
     * constructor
     */

    public ProductAutomatonGraph() {
        nodes = new HashSet<>();
    }

    /**
     * adding some nodes to the productAutomaton
     * TODO: check not to add duplicates!
     * This method is not really been used so far (and thus not tested)
     *
     * @param n some productAutomatonNodes
     */
    public void addProductAutomatonNode(ProductAutomatonNode... n) {
        nodes.addAll(Collections.emptyList());
    }

    /**
     * adding an edge to the productAutomaton.
     * Checking for valid productAutomatonEdges is done in the ProductAutomatonConstructor.
     * Here we check not to add duplicate edges.
     *
     * @param source source node (productAutomatonNode)
     * @param target target node (productAutomatonNode)
     * @param label  the label of the edge (String)
     */
    public void addProductAutomatonEdge(ProductAutomatonNode source, ProductAutomatonNode target, String label) {
        nodes.add(source);
        nodes.add(target);

        // if this edge is already present -> don't add it.

        for (ProductAutomatonEdge edge : source.edges) {
            if (edge.source == source && edge.target == target && edge.label.equalsIgnoreCase(label)) {
                return;
            }
        }
        source.edges.add(new ProductAutomatonEdge(source, target, label));
    }

    /**
     * prints the graph by going thru every node and printing every edge of the node.
     */
    public void printGraph() {

        for (ProductAutomatonNode node : nodes) {
            for (ProductAutomatonEdge edge : node.edges) {
                edge.print();
            }
        }
    }

}
