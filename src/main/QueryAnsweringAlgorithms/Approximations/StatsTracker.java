package Approximations;

import DataProvider.DataProvider;
import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;
import org.javatuples.Pair;

import java.io.*;
import java.util.HashMap;

public class StatsTracker {
    QueryGraph queryGraph;
    TransducerGraph transducerGraph;
    DatabaseGraph databaseGraph;
    ProductAutomatonConstructor productAutomatonConstructor;
    ProductAutomatonReachabilityAnalysis productAutomatonReachabilityAnalysis;
    HashMap<Pair<String, String>, Double> answerMap;
    int topK;

    public StatsTracker(DataProvider dataProvider) {
        this.queryGraph = dataProvider.getQueryGraph();
        this.transducerGraph = dataProvider.getTransducerGraph();
        this.databaseGraph = dataProvider.getDatabaseGraph();

        this.productAutomatonConstructor = new ProductAutomatonConstructor(queryGraph, transducerGraph, databaseGraph);
        this.productAutomatonReachabilityAnalysis = new ProductAutomatonReachabilityAnalysis(productAutomatonConstructor);
        this.answerMap = new HashMap<>();
    }

    public StatsTracker(DataProvider dataProvider, int topK){
        this.queryGraph = dataProvider.getQueryGraph();
        this.transducerGraph = dataProvider.getTransducerGraph();
        this.databaseGraph = dataProvider.getDatabaseGraph();

        this.topK = topK;

        this.productAutomatonConstructor = new ProductAutomatonConstructor(queryGraph, transducerGraph, databaseGraph);
        this.productAutomatonReachabilityAnalysis = new ProductAutomatonReachabilityAnalysis(productAutomatonConstructor, topK);
        this.answerMap = new HashMap<>();


    }

    public void runDijkstraComplete() throws FileNotFoundException {

         productAutomatonConstructor.construct();


        // start of the computation
        long start = System.currentTimeMillis();

        answerMap = productAutomatonReachabilityAnalysis.processDijkstraOverAllInitialNodes();

        // Get elapsed time in milliseconds
        long elapsedTimeMillis = System.currentTimeMillis() - start;

        // Get elapsed time in seconds
        float elapsedTimeSec = elapsedTimeMillis / 1000F;

        // Get elapsed time in minutes
        float elapsedTimeMin = elapsedTimeMillis / (60 * 1000F);

        writeTimeToFile(elapsedTimeMillis, elapsedTimeSec, elapsedTimeMin);

        printEndResult();
    }

    private void writeTimeToFile(long milli, float sec, float min) {
        File stats = new File("src/main/resources/output/computationStats.txt");
        FileWriter out;

        try {
            int amountOfNodes = productAutomatonConstructor.productAutomatonGraph.nodes.size();
            out = new FileWriter(stats, true);

            out.write("amount of actual nodes in the product automaton: " + amountOfNodes + ". \n");
            out.write("note that we used the lazy construction. \n ");
            out.write("\n");
            out.write("some stats for the dijkstra processing time. \n");
            out.write("computation time in milliseconds: " + milli + ". \n");
            out.write("computation time in seconds: " + sec + ". \n");
            // out.write("computation time in minutes: " + min + ". \n");


            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printEndResult() throws FileNotFoundException {

        writeToFile();

        System.out.println("------------------");
        System.out.println("end result: ");
        for (Pair pair : answerMap.keySet()) {
            System.out.println("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair));
        }
        System.out.println("computation completed.");
    }

    private void writeToFile() throws FileNotFoundException {

        File queryAnswers = new File ("src/main/resources/output/queryResults.txt");
        FileWriter out;
        int count = 0;
        try {
            out = new FileWriter(queryAnswers, false);
            out.write("query processed. \n");

            // TODO: this doesn't really work correctly, right? We print every connection with weight 0.0 as a classical,
            // TODO: but we should verify this with the path or the product automaton edges... (new attribute "classical"?)
            for (Pair pair : answerMap.keySet()) {
                if (answerMap.get(pair) == 0.0) {
                    count++;
                    out.write("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair) + " (classical answer) \n");
                } else
                    out.write("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair) + "\n");
            }
            int classicalAnswers = count;
            int approxAnswers = answerMap.size() - count;
            out.write("total answers: " + answerMap.size());
            out.write(". (" + classicalAnswers + " classical answer(s) and " + approxAnswers + " approximate answer(s).) \n");
            out.close();
            System.out.println("successfully wrote to file.");

        } catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }

        PrintStream fileStream = new PrintStream( new FileOutputStream("src/main/resources/output/graphs.txt", false));
        PrintStream stdout = System.out;
        System.setOut(fileStream);
        System.out.println("the product automaton graph for this computation: \n");
        productAutomatonConstructor.productAutomatonGraph.printGraph();
        System.out.println();
        System.out.println("note that '' (emptyString) means we read/write an epsilon. \n");
        System.setOut(stdout);
    }


}
