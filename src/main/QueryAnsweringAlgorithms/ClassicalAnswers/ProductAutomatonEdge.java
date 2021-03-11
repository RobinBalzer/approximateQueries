package ClassicalAnswers;

public class ProductAutomatonEdge {

    ProductAutomatonNode source;
    ProductAutomatonNode target;
    String label;

    /**
     * for the easier productAutomaton (for classical answers only) we do not need the transducer hence our edges are fairly straight forward.
     *
     * @param source the source node (a productAutomatonNode)
     * @param target the target node (a productAutomatonNode)
     * @param label  the label (simply a string)
     */

    ProductAutomatonEdge(ProductAutomatonNode source, ProductAutomatonNode target, String label) {
        this.source = source;
        this.target = target;
        this.label = label.toLowerCase();
    }

    /**
     * for formatting.
     * @return  the edge in a human-readable format. (cypher-style)
     */
    public String toString() {
        return String.format("(%s, %s) -[%s]-> (%s, %s)",
                source.identifier.getValue0().identifier, source.identifier.getValue1().identifier,
                label,
                target.identifier.getValue0().identifier, target.identifier.getValue1().identifier);
    }

    /**
     * prints the edges
     */
    public void print() {
        System.out.println(toString());
    }
}
