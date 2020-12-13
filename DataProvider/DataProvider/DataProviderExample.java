package DataProvider;

import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

public class DataProviderExample {


    public static void main(String[] args) {
        QueryGraph queryGraph = new QueryGraph();
        TransducerGraph transducerGraph = new TransducerGraph();
        DatabaseGraph databaseGraph = new DatabaseGraph();
        String choice = "eXaMPlE_2006";

        DataProvider dataProvider = new DataProvider();
        dataProvider.provideData("example_2006");

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
