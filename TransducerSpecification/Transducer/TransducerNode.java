package Transducer;

import java.util.LinkedList;

// class for specifying a transducer node.
// similar to the Query.QueryNode we have an identifier and booleans for initial or final states.
public class TransducerNode {

    String identifier;
    Boolean initialState;
    Boolean finalState;
    LinkedList<TransducerEdge> edges;

    public TransducerNode(String id, Boolean initialState, Boolean finalState) {
        identifier = id;
        this.initialState = initialState;
        this.finalState = finalState;
        edges = new LinkedList<>();
    }

    boolean isInitialState() {
        return initialState;
    }

    boolean isFinalState() {
        return finalState;
    }


}
