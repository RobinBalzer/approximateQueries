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
    Boolean visited = false;

    public ProductAutomatonNode(){

    }

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

    public void visit() {
        this.visited = true;
    }

    public void unvisit(){
        this.visited = false;
    }

    public void print(){
        System.out.println(toString());
    }

    public String toString(){
        return String.format("(%s, %s)", identifier.getValue0().identifier, identifier.getValue1().identifier);
    }
}
