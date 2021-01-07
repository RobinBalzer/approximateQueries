package ClassicalAnswers;

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
        CalculatingClassicalAnswers calculatingClassicalAnswers = new CalculatingClassicalAnswers(choice);
        AnswerSet answerSet = new AnswerSet(choice);

        // productAutomatonConstructor.construct();

        // answerSet.calculate();

        // here we're currently
        // calculatingClassicalAnswers.reachabilityAll();


        System.out.println("------------------------");
        System.out.println("Edges of the product automaton: ");

        // construct a productAutomaton and print it
        productAutomatonConstructor.construct();
        productAutomatonConstructor.productAutomatonGraph.printGraph();

        productAutomatonConstructor.productAutomatonGraph.updateInitialNodes();
        productAutomatonConstructor.productAutomatonGraph.updateFinalNodes();

        System.out.println("------------------------");
        System.out.println("Initial states: ");
        for (ProductAutomatonNode productAutomatonNode : productAutomatonConstructor.productAutomatonGraph.initialNodes) {
            System.out.println(printNode("initial", productAutomatonNode));
        }

        System.out.println("------------------------");
        System.out.println("Final states: ");
        for (ProductAutomatonNode productAutomatonNode : productAutomatonConstructor.productAutomatonGraph.finalNodes) {
            System.out.println(printNode("final", productAutomatonNode));
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
