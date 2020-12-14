package ClassicalAnswers;

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
    }

}
