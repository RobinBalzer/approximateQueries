package ClassicalAnswers;

import java.util.LinkedList;

public class CalculatingClassicalAnswers {

    public LinkedList<ProductAutomatonEdge> answerSet;
    String choice = "example_2020";
    ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(choice);

    // construct a productAutomaton and print it

    public LinkedList<ProductAutomatonEdge> calculate(){
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