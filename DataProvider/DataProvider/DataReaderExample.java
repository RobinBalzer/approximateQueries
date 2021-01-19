package DataProvider;

import Approximations.ProductAutomatonConstructor;
import Approximations.ProductAutomatonReachabilityAnalysis;
import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

public class DataReaderExample {

    public static void main (String[] args) throws Exception {
        DataReader dataReader = new DataReader();
        dataReader.readFile();

        QueryGraph queryGraph = dataReader.dataProvider.getQueryGraph();
        TransducerGraph transducerGraph = dataReader.dataProvider.getTransducerGraph();
        DatabaseGraph databaseGraph = dataReader.dataProvider.getDatabaseGraph();

        printSpace();
        queryGraph.printGraph();
        printSpace();
        transducerGraph.printGraph();
        printSpace();
        databaseGraph.printGraph();
        printSpace();

       ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(queryGraph, transducerGraph, databaseGraph);
       productAutomatonConstructor.construct();

       ProductAutomatonReachabilityAnalysis productAutomatonReachabilityAnalysis = new ProductAutomatonReachabilityAnalysis(productAutomatonConstructor);
       productAutomatonReachabilityAnalysis.processDijkstraOverAllInitialNodes();
    }

    public static void printSpace(){
        System.out.println("---");
    }

}
