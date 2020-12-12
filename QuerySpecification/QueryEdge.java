// class for specifying a query edge.
// A node is labeled and has a source (QueryNode object) and destination (QueryNode object)
// all labels are stored in lowercase to prevent casing-errors!
// using uppercase labels doesn't throw an error but it'll still get stored in lowercase.
public class QueryEdge {

    QueryNode source;
    QueryNode target;
    String label;

    QueryEdge(QueryNode source, QueryNode target, String label) {
        this.source = source;
        this.target = target;
        this.label = label.toLowerCase();
    }

    public String toString() {
        return String.format("(%s) -[%s]-> (%s)", source.identifier, label, target.identifier);
    }

    public void print() {
        System.out.println(toString());
    }
}
