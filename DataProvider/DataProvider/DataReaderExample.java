package DataProvider;

import Approximations.ProductAutomatonConstructor;
import Approximations.ProductAutomatonReachabilityAnalysis;
import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

import java.io.PrintStream;

public class DataReaderExample {

    public static void main (String[] args) throws Exception {
        DataReader dataReader = new DataReader();
        dataReader.readFile();

        QueryGraph queryGraph = dataReader.dataProvider.getQueryGraph();
        TransducerGraph transducerGraph = dataReader.dataProvider.getTransducerGraph();
        DatabaseGraph databaseGraph = dataReader.dataProvider.getDatabaseGraph();

        PrintStream fileStream = new PrintStream("output/graphs.txt");
        PrintStream stdout = System.out;
        System.setOut(fileStream);


        System.out.println("query graph: ");
        queryGraph.printGraph();
        printSpace();

        System.out.println("transducer graph: ");
        transducerGraph.printGraph();
        printSpace();

        System.out.println("database graph: ");
        databaseGraph.printGraph();
        printSpace();

        System.setOut(stdout);

       ProductAutomatonConstructor productAutomatonConstructor = new ProductAutomatonConstructor(queryGraph, transducerGraph, databaseGraph);
       productAutomatonConstructor.construct();

       ProductAutomatonReachabilityAnalysis productAutomatonReachabilityAnalysis = new ProductAutomatonReachabilityAnalysis(productAutomatonConstructor);
       productAutomatonReachabilityAnalysis.processDijkstraOverAllInitialNodes();
    }

    public static void printSpace(){
        System.out.println("---");
    }

}
