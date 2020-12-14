package ClassicalAnswers;

import Database.DatabaseNode;
import Query.QueryNode;
import org.javatuples.Pair;

import java.util.LinkedList;

public class ProductAutomatonNode {

    Pair<QueryNode, DatabaseNode> identifier;
    Boolean initialState;
    Boolean finalState;
    public LinkedList<ProductAutomatonEdge> edges;

    /**
     * constructor for a productAutomatonNode. it has the form
     * (queryNode, databaseNode) and has booleans for being an initial or final state.
     *
     * @param queryNode    the corresponding queryNode
     * @param databaseNode the corresponding databaseNode
     * @param initialState is this an initial state
     * @param finalState   is this a final state?
     */
    public ProductAutomatonNode(QueryNode queryNode, DatabaseNode databaseNode, Boolean initialState, Boolean finalState) {
        identifier = Pair.with(queryNode, databaseNode);

        this.initialState = initialState;
        this.finalState = finalState;
        edges = new LinkedList<>();
    }

}
