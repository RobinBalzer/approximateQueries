package Approximations;

import java.io.FileNotFoundException;

// this is the current main function that is used to show the algorithm.
public class ProductAutomatonExample {

    public static void main(String[] args) throws FileNotFoundException {

        ProductAutomatonReachabilityAnalysis productAutomatonReachabilityAnalysis = new ProductAutomatonReachabilityAnalysis("example_2020");

        productAutomatonReachabilityAnalysis.processDijkstraOverAllInitialNodes();




    }
}
