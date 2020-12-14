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
    QueryNode queryNode = new QueryNode();
    DatabaseNode databaseNode = new DatabaseNode();

    public ProductAutomatonNode(QueryNode queryNode, DatabaseNode databaseNode, Boolean initialState, Boolean finalState) {
        identifier = Pair.with(queryNode, databaseNode);
       // this.identifier.setAt0(queryNode.identifier.toLowerCase());
       //  this.identifier.setAt1(databaseNode.identifier);

        this.initialState = initialState;
        this.finalState = finalState;
        edges = new LinkedList<>();
    }

}
