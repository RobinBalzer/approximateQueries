package ClassicalAnswers;

public class ProductAutomatonEdge {

    ProductAutomatonNode source;
    ProductAutomatonNode target;
    String label;

    ProductAutomatonEdge(ProductAutomatonNode source, ProductAutomatonNode target, String label) {
        this.source = source;
        this.target = target;
        this.label = label.toLowerCase();
    }

    public String toString() {
        return String.format("(%s, %s) -[%s]-> (%s, %s)",
                source.identifier.getValue0().identifier, source.identifier.getValue1().identifier,
                label,
                target.identifier.getValue0().identifier, target.identifier.getValue1().identifier);
    }

    public void print() {
        System.out.println(toString());
    }
}
