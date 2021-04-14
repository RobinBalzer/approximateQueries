import StatsTrackers.StatsTrackerClassic;
import StatsTrackers.StatsTrackerThreshold;
import StatsTrackers.StatsTrackerTopK;
import DataProvider.DataProvider;

import java.io.FileNotFoundException;

public class SearchHandler {
    private StatsTrackerClassic statsTrackerClassic;
    private StatsTrackerThreshold statsTrackerThreshold;
    private StatsTrackerTopK statsTrackerTopK;

    public SearchHandler(DataProvider dataProvider) {

    }

    public void searchAllAnswers(DataProvider dataProvider) throws FileNotFoundException {
        this.statsTrackerClassic = new StatsTrackerClassic(dataProvider);
        statsTrackerClassic.runDijkstra();
    }

    public void searchTopKAnswers(DataProvider dataProvider, int k) throws FileNotFoundException {
        this.statsTrackerTopK = new StatsTrackerTopK(dataProvider, k);
        statsTrackerTopK.runDijkstra();

    }

    public void searchThresholdAnswers(DataProvider dataProvider, Double threshold) throws FileNotFoundException {
        this.statsTrackerThreshold = new StatsTrackerThreshold(dataProvider, threshold);
        statsTrackerThreshold.runDijkstra();
    }

}
