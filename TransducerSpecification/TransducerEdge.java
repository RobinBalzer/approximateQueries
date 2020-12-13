// class specifying a transducer edge.
// this edge is more special than the standard query edge.
// we need a 5-tuple, with source and target nodes, incoming and outgoing strings and a cost (doing this replacement operation)
public class TransducerEdge {

    TransducerNode source;
    TransducerNode target;
    String incomingString;
    String outgoingString;
    int cost;

    TransducerEdge(TransducerNode source, TransducerNode target, String incomingString, String outgoingString, int cost) {
        this.source = source;
        this.target = target;
        this.incomingString = incomingString.toLowerCase();
        this.outgoingString = outgoingString.toLowerCase();
        this.cost = cost;
    }

    public String toString() {
        return String.format("(%s) -[%s|%s|%d]-> (%s)", source.identifier, incomingString, outgoingString, cost, target.identifier);
    }

    public void print() {
        System.out.println(toString());
    }
}
