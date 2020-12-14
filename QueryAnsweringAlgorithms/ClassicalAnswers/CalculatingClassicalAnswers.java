package ClassicalAnswers;

import java.util.LinkedList;

// WIP!
// here we want to calculate the answer set.
// we take the productAutomaton and find all paths from an initial to a final state.
// then we return all databaseNodeIdentifiers that are contained in valid paths.
// these are all classical answers to the query.
// TODO: answerSet might not be the right return value...

public class CalculatingClassicalAnswers {

    public LinkedList<ProductAutomatonEdge> answerSet;
    String choice = "example_2020";
    ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(choice);

    // construct a productAutomaton and print it
    // TODO: remove double printGraph (see ProductAutomatonExample) if this is done and called properly!

    public LinkedList<ProductAutomatonEdge> calculate() {
        answerSet = new LinkedList<>();
        productAutomatonConstructor.construct();
        productAutomatonConstructor.productAutomatonGraph.printGraph();

        for (ProductAutomatonNode nodes : productAutomatonConstructor.productAutomatonGraph.nodes) {
            for (ProductAutomatonEdge edge : nodes.edges) {
                // reachability here
            }

        }

        return answerSet;
    }


}