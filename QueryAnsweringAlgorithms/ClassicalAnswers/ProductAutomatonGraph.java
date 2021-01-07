package ClassicalAnswers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProductAutomatonGraph {
    public Set<ProductAutomatonNode> nodes;
    public Set<ProductAutomatonNode> initialNodes = new HashSet<>();
    public Set<ProductAutomatonNode> finalNodes = new HashSet<>();

    /**
     * constructor
     */

    public ProductAutomatonGraph() {
        nodes = new HashSet<>();
    }

    /**
     * adding one node at a time to the set of nodes.
     * does check for duplicates and doesn't add them
     * also updates the set of initial and final states upon adding a new node.
     *
     * @param productAutomatonNode one productAutomatonNode
     */
    public void addProductAutomatonNode(ProductAutomatonNode productAutomatonNode) {
        // TODO: is this line useless??
        // nodes.addAll(Collections.emptyList());

        for (ProductAutomatonNode node : nodes) {
            if (node.identifier.getValue0().identifier.equalsIgnoreCase(productAutomatonNode.identifier.getValue0().identifier) &&
                    node.identifier.getValue1().identifier.equalsIgnoreCase(productAutomatonNode.identifier.getValue1().identifier)) {
                return;
            }
        }
        nodes.add(productAutomatonNode);
        if (productAutomatonNode.initialState) {
            initialNodes.add(productAutomatonNode);
        }

        if (productAutomatonNode.finalState) {
            finalNodes.add(productAutomatonNode);
        }
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
        addProductAutomatonNode(source);
        addProductAutomatonNode(target);

        // if this edge is already present -> don't add it.

        for (ProductAutomatonEdge edge : source.edges) {
            if (edge.source == source && edge.target == target && edge.label.equalsIgnoreCase(label)) {
                return;
            }
        }
        source.edges.add(new ProductAutomatonEdge(source, target, label));
    }

    /**
     * TODO: refactor print methods into proper .txt file output.
     * prints the graph by going thru every node and printing every edge of the node.
     */
    public void printGraph() {

        for (ProductAutomatonNode node : nodes) {
            for (ProductAutomatonEdge edge : node.edges) {
                edge.print();
            }
        }
    }

    // TODO: this method should be useless since we already check that in the addProductAutomatonNode function.
    public void updateInitialNodes() {
        for (ProductAutomatonNode node : nodes) {
            if (node.initialState) {
                initialNodes.add(node);
            }
        }
    }

    // TODO: this method should be useless since we already check that in the addProductAutomatonNode function.
    public void updateFinalNodes() {
        for (ProductAutomatonNode node : nodes) {
            if (node.finalState) {
                finalNodes.add(node);
            }
        }
    }
}
