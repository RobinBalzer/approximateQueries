package StatsTrackers;

import Algorithms.DijkstraClassic;
import Application.Settings;
import DataProvider.DataProvider;
import Database.DatabaseGraph;
import ProductAutomatonSpecification.ProductAutomatonConstructor;
import Query.QueryGraph;
import Transducer.TransducerGraph;
import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;

public class StatsTrackerLargestWeight implements StatsTracker {

    QueryGraph queryGraph;
    TransducerGraph transducerGraph;
    DatabaseGraph databaseGraph;
    ProductAutomatonConstructor productAutomatonConstructor;
    HashMap<Pair<String, String>, Double> answerMap;

    DijkstraClassic dijkstraClassic;

    public StatsTrackerLargestWeight(DataProvider dataProvider) {
        this.queryGraph = dataProvider.getQueryGraph();
        this.transducerGraph = dataProvider.getTransducerGraph();
        this.databaseGraph = dataProvider.getDatabaseGraph();

        this.productAutomatonConstructor = new ProductAutomatonConstructor(queryGraph, transducerGraph, databaseGraph);
        this.dijkstraClassic = new DijkstraClassic(productAutomatonConstructor);
        this.answerMap = new HashMap<>();
    }

    @Override
    public void runDijkstra() {

        productAutomatonConstructor.construct();
        answerMap = dijkstraClassic.processDijkstraOverAllInitialNodes();
        if (answerMap.isEmpty()) {
            Settings.setLargestWeight(0.0);
        } else {
            Settings.setLargestWeight(Collections.max(answerMap.values()));
        }
    }

    @Override
    public void writeTimeToFile(long milli, long milliPreprocessing, long milliTotal) {

    }

    @Override
    public void printEndResult() throws FileNotFoundException {

    }

    @Override
    public void writeResultToFile() throws FileNotFoundException {

    }

}
