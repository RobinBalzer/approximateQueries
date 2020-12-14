package ClassicalAnswers;

public class ProductAutomatonExample {

    public static void main(String[] args){
        String choice = "example_2020";
        ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(choice);

        // construct a productAutomaton and print it
        productAutomatonConstructor.construct();
        productAutomatonConstructor.productAutomatonGraph.printGraph();
    }

}
