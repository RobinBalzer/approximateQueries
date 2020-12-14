package ClassicalAnswers;

import DataProvider.DataProvider;
import Database.DatabaseEdge;
import Database.DatabaseGraph;
import Database.DatabaseNode;
import Query.QueryEdge;
import Query.QueryGraph;
import Query.QueryNode;

public class ProductAutomatonConstructor {

    // create all necessary variables
    public String choice;
    public DataProvider dataProvider;

    QueryGraph queryGraph;
    DatabaseGraph databaseGraph;

    ProductAutomatonGraph productAutomatonGraph;

    /**
     * constructor. We call it with a String for our choice of data.
     * Then the constructor overwrites the queryGraph and database objects with the according data
     * that is received from the dataProvider.
     *
     * @param choice
     */
    ProductAutomatonConstructor(String choice) {
        this.choice = choice;
        dataProvider = new DataProvider(choice);

        queryGraph = dataProvider.queryGraph;
        databaseGraph = dataProvider.databaseGraph;

        productAutomatonGraph = new ProductAutomatonGraph();

    }

    /**
     * construction of the productAutomaton.
     * <p>
     * basically for
     * p, p' states in the queryAutomaton and
     * r, r' states in the databaseAutomaton
     * label an arbitrary String
     * <p>
     * we add an edge
     * (p, r) -[label]-> (p', r')
     * if (p) -[label]-> (p') is an edge in the queryGraph AND
     * if (r) -[label]-> (r') is an edge in the databaseGraph
     * <p>
     * the idea is taken from the productAutomatonConstruction of the paper
     * regular path queries under approximate semantics
     * by Grahne and Thomo (year: 2006)
     * <p>
     * without considering the transducer yet. This will be done later on in the package @see Approximations
     */
    public void construct() {

        // go over every QueryEdge
        // check if there are labels in the database
        for (QueryNode queryNode : queryGraph.nodes) {
            for (QueryEdge queryEdge : queryNode.edges) {
                String localQueryLabel = queryEdge.label;

                // for every databaseEdge
                // check the label for equality
                for (DatabaseNode databaseNode : databaseGraph.nodes) {
                    for (DatabaseEdge databaseEdge : databaseNode.edges) {
                        String localDatabaseLabel = databaseEdge.label;

                        if (localQueryLabel.equals(localDatabaseLabel)) {
                            // found a fitting edge!

                            //create source and target nodes
                            ProductAutomatonNode sourceNode = new ProductAutomatonNode(queryEdge.source, databaseEdge.source, queryEdge.source.isInitialState(), queryEdge.source.isFinalState());
                            ProductAutomatonNode targetNode = new ProductAutomatonNode(queryEdge.target, databaseEdge.target, queryEdge.target.isInitialState(), queryEdge.target.isFinalState());

                            // add the edge to the productAutomaton
                            productAutomatonGraph.addProductAutomatonEdge(sourceNode, targetNode, localQueryLabel);
                        }
                    }
                }
            }
        } //end of nested for loops
    }
}
