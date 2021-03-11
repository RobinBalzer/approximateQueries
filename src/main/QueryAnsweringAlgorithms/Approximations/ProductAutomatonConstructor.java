package Approximations;

import DataProvider.DataProvider;
import Database.DatabaseEdge;
import Database.DatabaseGraph;
import Database.DatabaseNode;
import Query.QueryEdge;
import Query.QueryGraph;
import Query.QueryNode;
import Transducer.TransducerEdge;
import Transducer.TransducerGraph;
import Transducer.TransducerNode;

import java.util.HashMap;
import java.util.HashSet;


public class ProductAutomatonConstructor {

    public String choice;
    public DataProvider dataProvider;

    QueryGraph queryGraph;
    TransducerGraph transducerGraph;
    DatabaseGraph databaseGraph;

    ProductAutomatonGraph productAutomatonGraph;


    /**
     * constructor.
     * We call it with a String for our choice of data.
     * The constructor then overwrites the queryGraph, transducerGraph and databaseGraph with the according data received from the dataProvider.
     *
     * @param choice the data choice.
     */
    ProductAutomatonConstructor(String choice) {
        this.choice = choice;
        dataProvider = new DataProvider(choice);

        queryGraph = dataProvider.queryGraph;
        transducerGraph = dataProvider.transducerGraph;
        databaseGraph = dataProvider.databaseGraph;

        productAutomatonGraph = new ProductAutomatonGraph();
    }

    public ProductAutomatonConstructor(QueryGraph queryGraph, TransducerGraph transducerGraph, DatabaseGraph databaseGraph) {
        this.queryGraph = queryGraph;
        this.transducerGraph = transducerGraph;
        this.databaseGraph = databaseGraph;
        productAutomatonGraph = new ProductAutomatonGraph();
    }


    /**
     * this function constructs the productAutomaton.
     * It takes the queryGraph, transducerGraph and databaseGraph and adds edges to the productAutomatonGraph if the following conditions hold:
     * (a) the queryGraph has an edge whose label is represented in the databaseGraph
     * (b) the queryGraph has an edge whose label can be replaced with a transducedLabel at cost k AND the transducedLabel is represented in the databaseGraph.
     * <p>
     * <p>
     * NOTE:
     * we do not simulate runs through the graphs yet. We simply add possible edges.
     * Later we check whether a path is valid. (i.e. first element is an initial state and last element is a final state)
     * This is the productAutomaton and does not represent a final solution!
     * NOTE:
     * we need an arbitrary transducer node for III (1),
     * because we do not care about the transducer there however we still need a transducerNode for the ProductAutomatonNode.
     * That's the reason for calling "addArbitraryTransducerNode".
     * <br/> --- <br/>
     * we portion the whole procedure into 3 main parts for clarity reasons.
     * <br/> --- <br/>
     * part (I):
     * we loop over every queryNode.
     * we loop over every edge of this queryNode (giving us the source, target and label)
     * we take the label of this edge.
     * <br/> --- <br/>
     * part (II):
     * we take the label and run it through the transducer.
     * if we find any "transduced" label we save the corresponding edge in the HashSet "fittingTransducerEdges". (giving us its source, target and cost)
     * <br/> --- <br/>
     * part (III):
     * we check whether
     * (1) the label              is present in the databaseGraph.
     * -> Yes?: we add the following edge to the productAutomaton:
     * (source) -[label/label/0]-> (target)
     * <p>
     * (2) the "transduced" label is present in the databaseGraph.
     * -> Yes?: we add the following edge to the productAutomaton:
     * (source) -[label/transducedLabel/cost]-> (target)
     * <br/> --- <br/>
     * <br/> --- <br/>
     * adding more functionality
     * <br/> --- <br/>
     * <br/> --- <br/>
     * part (IV) : epsilon edges
     * We expand the transducer to accept epsilon edges. "" (empty string) currently represents epsilon.
     * They can have two forms.
     * (1) (source) -[read: epsilon | write: String | cost: k]-> (target) ["incoming epsilon edges"]
     * This means we read the empty word and replace it with a String at cost k.
     * This can only happen if we are in a final queryState. We do not change the state of the query.
     * (basically we can only read the empty word at the end of our query. that means we concatenate the queryWord with a string. This cannot be done inside the queryString.
     * <br/> --- <br/>
     * (2) (source) -[read: String | write: epsilon | cost: k]-> (target) ["outgoing epsilon edges"]
     * This means we read a string and replace it with epsilon (empty String).
     * This can happen everywhere.
     * Doing this will forward the queryAutomaton despite not reading a String (since we replaced it with epsilon).
     */

    public void construct() {
        HashMap<String, ProductAutomatonNode> temporaryNodes = new HashMap<>();
        HashSet<TransducerEdge> fittingTransducerEdges = new HashSet<>();
        HashSet<TransducerEdge> fittingEpsilonTransducerEdges = new HashSet<>();

        //part (I)
        for (QueryNode queryNode : queryGraph.nodes) {
            for (QueryEdge queryEdge : queryNode.edges) {
                String localQueryLabel = queryEdge.label;

                // part (II)
                // NOTE: here we also add edges of the form (IV | 2) "outgoing epsilon edges"
                fittingTransducerEdges.clear();
                for (TransducerNode transducerNode : transducerGraph.nodes) {
                    for (TransducerEdge transducerEdge : transducerNode.edges) {
                        if (localQueryLabel.equals(transducerEdge.incomingString)) {
                            fittingTransducerEdges.add(transducerEdge);
                        }

                        // add incoming epsilon edges where they can be applied
                        if (queryNode.isFinalState()) {
                            if (transducerEdge.incomingString.isBlank()) {
                                fittingTransducerEdges.add(transducerEdge);
                            }
                        }
                    }
                }

                // part (III)
                for (DatabaseNode databaseNode : databaseGraph.nodes) {
                    for (DatabaseEdge databaseEdge : databaseNode.edges) {
                        String localDatabaseLabel = databaseEdge.label;

                        // TODO: delete this part (III (1)) and adjust the javadoc.
                        // code cleanup: put this into the addEdge function.
                        // (1)
                        /*
                        if (localQueryLabel.equals(localDatabaseLabel)) {
                            // found a fitting (classical) edge!

                            // we need the arbitrary transducer node now. TODO: replace "__" with "arbitrary"
                            TransducerNode arbitraryTransducerNode = new TransducerNode("__", true, true);
                            transducerGraph.addTransducerObjectNode(arbitraryTransducerNode);

                            //create source and target nodes
                            ProductAutomatonNode sourceNode1 = new ProductAutomatonNode(queryEdge.source, arbitraryTransducerNode, databaseEdge.source, queryEdge.source.isInitialState(), queryEdge.source.isFinalState());
                            ProductAutomatonNode targetNode1 = new ProductAutomatonNode(queryEdge.target, arbitraryTransducerNode, databaseEdge.target, queryEdge.target.isInitialState(), queryEdge.target.isFinalState());

                            // filter out potential duplicates
                            // TODO: duplicates are created above but never used
                            //  (only to get the identifier and find out that this identifier is already in use and we take the original instance instead of the duplicate instance)
                            //  remove this issue for better performance
                            //  (TODO: do this with the TODO below aswell!)
                            if (temporaryNodes.containsKey(sourceNode1.getIdentifier())) {
                                sourceNode1 = temporaryNodes.get(sourceNode1.getIdentifier());
                            } else temporaryNodes.put(sourceNode1.getIdentifier(), sourceNode1);

                            if (temporaryNodes.containsKey(targetNode1.getIdentifier())) {
                                targetNode1 = temporaryNodes.get(targetNode1.getIdentifier());
                            } else temporaryNodes.put(targetNode1.getIdentifier(), targetNode1);

                            // add the edge to the productAutomaton
                            productAutomatonGraph.addProductAutomatonEdge(sourceNode1, targetNode1, localQueryLabel, localQueryLabel, 0.0);
                        }

                         */

                        // (2)
                        // for all transducer edges that were found in part (II):
                        //      check whether
                        for (TransducerEdge transducerEdge : fittingTransducerEdges) {

                            // part (IV)
                            // (2) outgoing epsilon edges
                            // condition: we read a String and replace that string with epsilon at cost k.
                            // basically we move forward in the query and transducer but stay at the same place in the database.

                            if (transducerEdge.outgoingString.isBlank()) {
                                boolean sourceInitialState2 = (queryEdge.source.isInitialState() && transducerEdge.source.isInitialState());
                                boolean sourceFinalState2 = (queryEdge.source.isFinalState() && transducerEdge.source.isFinalState());

                                boolean targetInitialState2 = (queryEdge.target.isInitialState() && transducerEdge.target.isInitialState());
                                boolean targetFinalState2 = (queryEdge.target.isFinalState() && transducerEdge.target.isFinalState());

                                // create source and target nodes
                                ProductAutomatonNode sourceNode4 = new ProductAutomatonNode(queryEdge.source, transducerEdge.source, databaseEdge.source, sourceInitialState2, sourceFinalState2);
                                ProductAutomatonNode targetNode4 = new ProductAutomatonNode(queryEdge.target, transducerEdge.target, databaseEdge.source, targetInitialState2, targetFinalState2);

                                if (temporaryNodes.containsKey(sourceNode4.getIdentifier())) {
                                    sourceNode4 = temporaryNodes.get(sourceNode4.getIdentifier());

                                } else temporaryNodes.put(sourceNode4.getIdentifier(), sourceNode4);

                                if (temporaryNodes.containsKey(targetNode4.getIdentifier())) {
                                    targetNode4 = temporaryNodes.get(targetNode4.getIdentifier());
                                } else temporaryNodes.put(targetNode4.getIdentifier(), targetNode4);

                                // add the edge to the productAutomaton
                                productAutomatonGraph.addProductAutomatonEdge(sourceNode4, targetNode4, localQueryLabel, "", transducerEdge.cost);

                            }


                            if (transducerEdge.outgoingString.equals(localDatabaseLabel)) {
                                // found a fitting (transduced) edge!

                                // part (IV)
                                // (1) incoming epsilon edges
                                // condition: we are in a final query state, read epsilon and replace it with a String at cost k.
                                if (transducerEdge.incomingString.isBlank()
                                        && queryNode.isFinalState()) {
                                    // add edge
                                    boolean sourceInitialState1 = (queryEdge.source.isInitialState() && transducerEdge.source.isInitialState());
                                    boolean sourceFinalState1 = (queryEdge.source.isFinalState() && transducerEdge.source.isFinalState());

                                    boolean targetInitialState1 = (queryEdge.target.isInitialState() && transducerEdge.target.isInitialState());
                                    boolean targetFinalState1 = (queryEdge.target.isFinalState() && transducerEdge.target.isFinalState());

                                    // create source and target nodes
                                    ProductAutomatonNode sourceNode3 = new ProductAutomatonNode(queryEdge.source, transducerEdge.source, databaseEdge.source, sourceInitialState1, sourceFinalState1);
                                    ProductAutomatonNode targetNode3 = new ProductAutomatonNode(queryEdge.source, transducerEdge.target, databaseEdge.target, targetInitialState1, targetFinalState1);

                                    if (temporaryNodes.containsKey(sourceNode3.getIdentifier())) {
                                        sourceNode3 = temporaryNodes.get(sourceNode3.getIdentifier());

                                    } else temporaryNodes.put(sourceNode3.getIdentifier(), sourceNode3);

                                    if (temporaryNodes.containsKey(targetNode3.getIdentifier())) {
                                        targetNode3 = temporaryNodes.get(targetNode3.getIdentifier());
                                    } else temporaryNodes.put(targetNode3.getIdentifier(), targetNode3);

                                    // add the edge to the productAutomaton
                                    productAutomatonGraph.addProductAutomatonEdge(sourceNode3, targetNode3, "", localDatabaseLabel, transducerEdge.cost);
                                }


                                // create booleans for initial and final states. (check query and transducer only since database always is initial and final)
                                boolean sourceInitialState = (queryEdge.source.isInitialState() && transducerEdge.source.isInitialState());
                                boolean sourceFinalState = (queryEdge.source.isFinalState() && transducerEdge.source.isFinalState());

                                boolean targetInitialState = (queryEdge.target.isInitialState() && transducerEdge.target.isInitialState());
                                boolean targetFinalState = (queryEdge.target.isFinalState() && transducerEdge.target.isFinalState());

                                // create source and target nodes
                                ProductAutomatonNode sourceNode2 = new ProductAutomatonNode(queryEdge.source, transducerEdge.source, databaseEdge.source, sourceInitialState, sourceFinalState);
                                ProductAutomatonNode targetNode2 = new ProductAutomatonNode(queryEdge.target, transducerEdge.target, databaseEdge.target, targetInitialState, targetFinalState);

                                // filter out potential duplicates TODO: do this with getInstance()
                                if (temporaryNodes.containsKey(sourceNode2.getIdentifier())) {
                                    sourceNode2 = temporaryNodes.get(sourceNode2.getIdentifier());

                                } else temporaryNodes.put(sourceNode2.getIdentifier(), sourceNode2);

                                if (temporaryNodes.containsKey(targetNode2.getIdentifier())) {
                                    targetNode2 = temporaryNodes.get(targetNode2.getIdentifier());
                                } else temporaryNodes.put(targetNode2.getIdentifier(), targetNode2);

                                // add the edge to the productAutomaton
                                productAutomatonGraph.addProductAutomatonEdge(sourceNode2, targetNode2, localQueryLabel, localDatabaseLabel, transducerEdge.cost);
                            }
                        }
                    }
                }
            }
        }
    }

    // TODO: use this function to addEdges instead of duplicate code above!!
    private void addEdge(QueryEdge queryEdge, TransducerEdge transducerEdge, DatabaseEdge databaseEdge, String edgeType) {

        boolean sourceInitialState;
        boolean sourceFinalState;
        boolean targetInitialState;
        boolean targetFinalState;

        String queryLabel = queryEdge.label;
        String databaseLabel = databaseEdge.label;

        String incomingTransducerLabel = transducerEdge.incomingString;
        String outgoingTransducerLabel = transducerEdge.outgoingString;

        ProductAutomatonNode sourceNode;
        ProductAutomatonNode targetNode;

        Double cost;

        // set some values depending on the type of the edge...
        switch (edgeType) {
            case "classical":
                // do something ...
                break;
            case "approximated":
                // do something ...
                break;
            case "incomingEpsilon":
                // do something ...
                break;
            case "outgoingEpsilon":
                // do something
                break;
        }

        // create source and target nodes if they are not already there (duplicate check!)
        // call productAutomatonGraph.addProductAutomatonEdge(sourceNode, targetNode, queryLabel, databaseLabel, cost);

    }
}
