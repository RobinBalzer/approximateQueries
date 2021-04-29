package StatsTrackers;

import Algorithms.DijkstraTopK;
import Application.Settings;
import ProductAutomatonSpecification.ProductAutomatonConstructor;
import DataProvider.DataProvider;
import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;
import org.javatuples.Pair;

import java.io.*;
import java.util.HashMap;

public class StatsTrackerTopK implements StatsTracker {

    QueryGraph queryGraph;
    TransducerGraph transducerGraph;
    DatabaseGraph databaseGraph;
    ProductAutomatonConstructor productAutomatonConstructor;
    HashMap<Pair<String, String>, Double> answerMap;

    DijkstraTopK dijkstraTopK;

    int topK;

    String outputDirectory = Settings.outputFileDirectory;


    public StatsTrackerTopK(DataProvider dataProvider, int topK) {
        this.queryGraph = dataProvider.getQueryGraph();
        this.transducerGraph = dataProvider.getTransducerGraph();
        this.databaseGraph = dataProvider.getDatabaseGraph();

        this.topK = topK;

        this.productAutomatonConstructor = new ProductAutomatonConstructor(queryGraph, transducerGraph, databaseGraph);
        this.dijkstraTopK = new DijkstraTopK(productAutomatonConstructor, topK);
        this.answerMap = new HashMap<>();

    }

    @Override
    public void runDijkstra() throws FileNotFoundException {

        // todo: working intellij: replace the next two lines with the commented line
        // PrintStream fileStream = new PrintStream(new FileOutputStream("src/main/resources/output/graphs.txt", false));
        PrintStream fileStream = new PrintStream(new FileOutputStream(outputDirectory + "graphs.txt", false));

        PrintStream stdout = System.out;
        System.setOut(fileStream);

        System.out.println("query graph: ");
        queryGraph.printGraph();
        System.out.println("---");

        System.out.println("transducer graph: ");
        transducerGraph.printGraph();
        System.out.println("---");

        System.out.println("database graph: ");
        databaseGraph.printGraph();
        System.out.println("---");

        System.setOut(stdout);

        long startCombinedComputation = System.currentTimeMillis();

        // start of preprocessing
        long startPreprocessing = System.currentTimeMillis();

        productAutomatonConstructor.construct();

        long elapsedTimeMillisPreprocessing = System.currentTimeMillis() - startPreprocessing;

        // start of the computation
        long start = System.currentTimeMillis();

        answerMap = dijkstraTopK.processDijkstraOverAllInitialNodes();

        // Get elapsed time in milliseconds
        long elapsedTimeMillis = System.currentTimeMillis() - start;

        long elapsedTimeTotalMillis = System.currentTimeMillis() - startCombinedComputation;

        writeTimeToFile(elapsedTimeMillis, elapsedTimeMillisPreprocessing, elapsedTimeTotalMillis);

        printEndResult();

    }

    @Override
    public void writeTimeToFile(long milli, long milliPreprocessing, long milliTotal) {
        // todo: working intellij: replace the next two lines with the commented line
        // File stats = new File("src/main/resources/output/computationStats.txt");
        File stats = new File( outputDirectory + "computationStats.txt");
        FileWriter out;


        float compTimeMillisPreprocessing = milliPreprocessing;
        float compTimeSecPreprocessing = milliPreprocessing / 1000F;
        float compTimeMinPreprocessing = milliPreprocessing / (60 * 1000F);

        float compTimeMillis = milli;
        float compTimeSec = milli / 1000F;
        float compTimeMin = milli / (60 * 1000F);

        float compTimeMillisTotal = milliTotal;
        float compTimeSecTotal = milli / 1000F;
        float compTimeMinTotal = milli / (60 * 1000F);

        try {
            int amountOfNodes = productAutomatonConstructor.productAutomatonGraph.nodes.size();
            out = new FileWriter(stats, true);

            out.write("amount of actual nodes in the product automaton: " + amountOfNodes + ". \n");
            out.write("note that we used the lazy construction. \n ");
            out.write("\n");
            out.write("some computation time stats. \n");
            out.write("(1) preprocessing (productAutomaton construction \n");
            out.write("   time needed (milliseconds): " + compTimeMillisPreprocessing + " \n");
            out.write("   time needed (seconds): " + compTimeSecPreprocessing + " \n");

            if (compTimeSecPreprocessing > 60.0) {
                out.write("   time needed (minutes): " + compTimeMinPreprocessing + " \n");
            }

            out.write("(2) dijkstra processing \n");
            out.write("   time needed (milliseconds): " + compTimeMillis + " \n");
            out.write("   time needed (seconds): " + compTimeSec + " \n");
            if (compTimeSec > 60.0) {
                out.write("   time needed (minutes): " + compTimeMin + " \n");
            }
            out.write("(3) combined (preprocessing and dijkstra) \n");
            out.write("   time needed (milliseconds): " + compTimeMillisTotal + " \n");
            out.write("   time needed (seconds): " + compTimeSecTotal + " \n");
            if (compTimeSecTotal > 60.0) {
                out.write("   time needed (minutes): " + compTimeMinTotal + " \n");
            }

            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printEndResult() throws FileNotFoundException {

        writeResultToFile();

        System.out.println("------------------");
        System.out.println("end result: ");
        for (Pair pair : answerMap.keySet()) {
            System.out.println("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair));
        }
        System.out.println("computation completed.");
    }

    @Override
    public void writeResultToFile() throws FileNotFoundException {

        // todo: working intellij: replace the next two lines with the commented line
        // File queryAnswers = new File("src/main/resources/output/queryResults.txt");
        File queryAnswers = new File(outputDirectory + "queryResults.txt");
        FileWriter out;

        try {
            out = new FileWriter(queryAnswers, false);
            out.write("query processed. \n");

            for (Pair pair : answerMap.keySet()) {

                out.write("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair) + "\n");

            }

            out.write("total answers: " + answerMap.size() + "\n");
            if (answerMap.size() > topK) {
                int diff = answerMap.size() - topK;
                out.write("The topK search is not fully optimized, as more results are found than needed. \n" +
                        "This happens because we only 'locally optimized' (see wiki) the topK search. \n" +
                        "We wanted to receive " + topK + " results but have gotten " + diff + " extra results. \n");
            }
            out.close();
            System.out.println("successfully wrote to file.");

        } catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }

        // todo: working intellij: replace the next two lines with the commented line
        // PrintStream fileStream = new PrintStream(new FileOutputStream("src/main/resources/output/graphs.txt", true));
        PrintStream fileStream = new PrintStream(new FileOutputStream(outputDirectory + "graphs.txt", true));
        PrintStream stdout = System.out;
        System.setOut(fileStream);
        System.out.println("product automaton: ");
        productAutomatonConstructor.productAutomatonGraph.printGraph();
        System.setOut(stdout);

    }
}
