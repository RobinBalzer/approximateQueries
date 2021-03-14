package Transducer;

// class specifying a transducer edge.
// this edge is more special than the standard query edge.
// we need a 5-tuple, with source and target nodes, incoming and outgoing strings and a cost (doing this replacement operation)
public class TransducerEdge {

    public TransducerNode source;
    public TransducerNode target;
    public String incomingString;
    public String outgoingString;
    public double cost;

    TransducerEdge(TransducerNode source, TransducerNode target, String incomingString, String outgoingString, double cost) {
        this.source = source;
        this.target = target;
        this.incomingString = incomingString.toLowerCase();
        this.outgoingString = outgoingString.toLowerCase();
        this.cost = cost;
    }

    public String toString() {
        return String.format("(%s) -[%s|%s|%s]-> (%s)", source.identifier, incomingString, outgoingString, cost, target.identifier);
    }

    public void print() {
        System.out.println(toString());
    }
}
