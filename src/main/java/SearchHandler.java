import Approximations.ProductAutomatonConstructor;
import Approximations.ProductAutomatonReachabilityAnalysis;
import Approximations.StatsTracker;
import DataProvider.DataProvider;
import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

import java.io.FileNotFoundException;

public class SearchHandler {
    private StatsTracker statsTracker;

    public SearchHandler(DataProvider dataProvider) {

    }

    public void searchAllAnswers(DataProvider dataProvider) throws FileNotFoundException {
        this.statsTracker = new StatsTracker(dataProvider);
        statsTracker.runDijkstraComplete();
    }

    public void searchTopKAnswers(DataProvider dataProvider, int k) throws FileNotFoundException {
        this.statsTracker = new StatsTracker(dataProvider, k);
        statsTracker.runDijkstraComplete();

    }

}
