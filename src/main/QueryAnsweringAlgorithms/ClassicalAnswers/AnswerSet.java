package ClassicalAnswers;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnswerSet {
    String choice;
    List<Pair> answerSet;
    Pair<String, String> concreteAnswers;
    ProductAutomatonConstructor productAutomatonConstructor;
    Set<ProductAutomatonNode> initialNodes;
    ProductAutomatonNode currentProcessedInitialNode;
    Set<ProductAutomatonNode> finalNodes;
    Set<ProductAutomatonNode> nodes;

    /**
     * constructor for AnswerSet objects.
     *
     * @param choice choice for the DataSet.
     *               the data will be provided by the DataProvider, which is called by the ProductAutomatonConstructor.
     */
    AnswerSet(String choice) {
        this.choice = choice;
        this.answerSet = new ArrayList<>();
        this.productAutomatonConstructor = new ProductAutomatonConstructor(choice);

        ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(choice);
        productAutomatonConstructor.construct();

        this.initialNodes = productAutomatonConstructor.productAutomatonGraph.initialNodes;
        this.finalNodes = productAutomatonConstructor.productAutomatonGraph.finalNodes;
        this.nodes = productAutomatonConstructor.productAutomatonGraph.nodes;
        this.currentProcessedInitialNode = new ProductAutomatonNode();

        // for testing only:
        // productAutomatonConstructor.productAutomatonGraph.printGraph();

    }

    /**
     * calculates the answerSet for the chosen Data.
     * queries over every initialNode and calls reachability with said node.
     */
    public void calculate() {

        for (ProductAutomatonNode initialNode : initialNodes) {

                currentProcessedInitialNode = initialNode;
                System.out.print("Calling reachability with initial node: ");
                System.out.println(initialNode);
                reachability(currentProcessedInitialNode);

        }
        System.out.println("-----");
        System.out.print("The answerSet is: ");
        System.out.println(answerSet);
    }

    private void reachability(ProductAutomatonNode node) {

        boolean searchCompleted = false;
        System.out.println(node);
        while (!searchCompleted) {

            // there is no node. We have finished our search.
            /*
            if (node == null) {
                break;
            }

             */
            // node is a final Node. We add the answer to our answerSet.
            if (node.finalState) {
                System.out.println("inside the node is finalNode thingy");
                concreteAnswers = new Pair<>(currentProcessedInitialNode.identifier.getValue1().identifier, node.identifier.getValue1().identifier);
                answerSet.add(concreteAnswers);
            } else {
                System.out.println("i am in the else part... ");
                System.out.println(node.edges);
            }

            // We're looking for a neighborNode. If there is one, we call reachability on that node.
            // bug: we only get edges of initial nodes
            for (ProductAutomatonEdge edges : node.edges) {
                System.out.print("Calling reachability again with target: ");
                System.out.print(edges.target);
                System.out.print(" and edges: ");
                System.out.println(edges);
                initialNodes.add(edges.target);
                reachability(edges.target);

            }


            searchCompleted = true;


        }

    }

    private ProductAutomatonNode findChild(ProductAutomatonNode parent) {
        ProductAutomatonNode child = null;

        System.out.print("parent node: ");
        System.out.println(parent);
        System.out.print("edges from the parent node: ");
        System.out.println(parent.edges);

        for (ProductAutomatonEdge edges : parent.edges) {
            child = edges.target;
        }
        return child;
    }
}
