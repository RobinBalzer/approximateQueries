package Query;

import java.util.LinkedList;

// class for specifying a query node.
// the node has an identifier and is possibly an initialState or a finalState.
public class QueryNode {

    String identifier;
    Boolean initialState;
    Boolean finalState;
    LinkedList<QueryEdge> edges;

    public QueryNode(String id, Boolean initialState, Boolean finalState) {
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