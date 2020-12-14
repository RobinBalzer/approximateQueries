package ClassicalAnswers;

import java.util.Iterator;

public class ProductAutomatonExample {

    /**
     * main function mainly for testing the productAutomatonConstruction.
     * does not add functionality despite fancy printing (yay!)
     *
     * @param args
     */
    public static void main(String[] args) {
        String choice = "example_2006";
        ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(choice);

        System.out.println("------------------------");
        System.out.println("Edges of the product automaton: ");

        // construct a productAutomaton and print it
        productAutomatonConstructor.construct();
        productAutomatonConstructor.productAutomatonGraph.printGraph();

        productAutomatonConstructor.productAutomatonGraph.updateInitialNodes();
        productAutomatonConstructor.productAutomatonGraph.updateFinalNodes();

        System.out.println("------------------------");
        System.out.println("Initial states: ");
        Iterator<ProductAutomatonNode> nodeInitial = productAutomatonConstructor.productAutomatonGraph.initialNodes.iterator();
        while (nodeInitial.hasNext()){
            System.out.println(printNode("initial", nodeInitial.next()));
        }

        System.out.println("------------------------");
        System.out.println("Final states: ");
        Iterator<ProductAutomatonNode> nodeFinal = productAutomatonConstructor.productAutomatonGraph.finalNodes.iterator();
        while(nodeFinal.hasNext()){
            System.out.println(printNode("final", nodeFinal.next()));
        }
    }

    public static String printNode(String property, ProductAutomatonNode node){
        if(property.equalsIgnoreCase("initial")){
            return String.format("((%s, %s))", node.identifier.getValue0().identifier, node.identifier.getValue1().identifier);
        } else if (property.equalsIgnoreCase("final")){
            return String.format("[[%s, %s]]", node.identifier.getValue0().identifier, node.identifier.getValue1().identifier);
        } else return null;
    }

}
