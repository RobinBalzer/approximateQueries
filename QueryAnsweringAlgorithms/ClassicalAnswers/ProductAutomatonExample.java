package ClassicalAnswers;

import java.util.Iterator;

public class ProductAutomatonExample {

    /**
     * main function mainly for testing the productAutomatonConstruction.
     * does not add functionality.
     *
     * @param args
     */
    public static void main(String[] args) {
        String choice = "example_2020";
        ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(choice);

        // construct a productAutomaton and print it
        productAutomatonConstructor.construct();
        productAutomatonConstructor.productAutomatonGraph.printGraph();

        productAutomatonConstructor.productAutomatonGraph.updateInitialNodes();
        productAutomatonConstructor.productAutomatonGraph.updateFinalNodes();

        Iterator<ProductAutomatonNode> nodeInitial = productAutomatonConstructor.productAutomatonGraph.initialNodes.iterator();
        while (nodeInitial.hasNext()){
            System.out.println(printNode("initial", nodeInitial.next()));
        }

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
