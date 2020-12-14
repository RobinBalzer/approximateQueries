package DataProvider;

import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

// class for mainly debugging purpose. We test whether we overwrite the objects correctly.
// we can check our data here aswell since we print out every graph.
public class DataProviderExample {


    public static void main(String[] args) {
        QueryGraph queryGraph = new QueryGraph();
        TransducerGraph transducerGraph = new TransducerGraph();
        DatabaseGraph databaseGraph = new DatabaseGraph();
        String choice = "eXaMPlE_2020";

        DataProvider dataProvider = new DataProvider(choice);

        queryGraph = dataProvider.queryGraph;
        transducerGraph = dataProvider.transducerGraph;
        databaseGraph = dataProvider.databaseGraph;

        System.out.println("This is the queryGraph for " + choice.toLowerCase());
        queryGraph.printGraph();
        System.out.println("--------------------------");

        System.out.println("This is the transducerGraph for " + choice.toLowerCase());
        transducerGraph.printGraph();
        System.out.println("--------------------------");

        System.out.println("This is the databaseGraph for " + choice.toLowerCase());
        databaseGraph.printGraph();
        System.out.println("--------------------------");



    }


}
