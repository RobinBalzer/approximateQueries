package ClassicalAnswers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProductAutomatonGraph {
    public Set<ProductAutomatonNode> nodes;

    public ProductAutomatonGraph() {
        nodes = new HashSet<>();
    }

    // public void addProductAutomatonNode(ProductAutomatonNode... n) {
    //    nodes.addAll(Collections.emptyList());
    // }

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

    public void printGraph() {

        for (ProductAutomatonNode node : nodes) {
            for (ProductAutomatonEdge edge : node.edges) {
                edge.print();
            }
        }
    }

}
