package DataProvider;

import Database.DatabaseNode;
import Query.QueryEdge;
import Query.QueryGraph;
import Query.QueryNode;
import Transducer.TransducerNode;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class DataReader {

    DataProvider dataProvider = new DataProvider();
    HashMap<String, Integer> amountOfNodesMap = new HashMap<>();


    public void readFile() throws Exception {


        String fileName;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file name you want to process ");
        fileName = scanner.nextLine();

        String path = "input/" + fileName;

        try {
            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            String[] words;
            int amountOfNodes;

            boolean queryEdgeFinder = false;
            boolean transducerEdgeFinder = false;
            boolean databaseEdgeFinder = false;

            while ((st = br.readLine()) != null) {

                // start with finding out about the name of the data set
                if (st.contains("name:")) {
                    System.out.println("Found 'name:' ");
                    words = st.split("name: ");
                    dataProvider.setDatasetIdentifier(words[1].trim());
                    st = br.readLine();
                }

                // read all the data for the queryGraph...
                if (st.contains("queryGraph:")) {
                    System.out.println("Found 'queryGraph:' ");
                    ArrayList<String> queryGraphData = new ArrayList<>();
                    amountOfNodes = 0;
                    st = br.readLine();
                    if (st.contains("nodes:")) {
                        st = br.readLine();
                    }
                    do {
                        if (!st.contains("edges:") && !queryEdgeFinder) {
                            amountOfNodes++;
                        } else queryEdgeFinder = true;

                        queryGraphData.add(st);
                        st = br.readLine();

                    } while (!st.contains("transducerGraph:"));
                    processQueryGraphData(queryGraphData, amountOfNodes);

                }

                // read all the data for the transducer graph.
                if (st.contains("transducerGraph:")) {
                    System.out.println("Found 'transducerGraph:' ");
                    ArrayList<String> transducerGraphData = new ArrayList<>();
                    amountOfNodes = 0;
                    st = br.readLine();

                    if (st.contains("nodes:")) {
                        st = br.readLine();
                    }
                    do {
                        if (!st.contains("edges:") && !transducerEdgeFinder) {
                            amountOfNodes++;
                        } else transducerEdgeFinder = true;

                        transducerGraphData.add(st);
                        st = br.readLine();

                    } while (!st.contains("databaseGraph:"));
                    processTransducerGraphData(transducerGraphData, amountOfNodes);
                }

                // read all the data for the database graph.
                if (st.contains("databaseGraph:")) {
                    System.out.println("Found 'databaseGraph:' ");
                    ArrayList<String> databaseGraphData = new ArrayList<>();
                    amountOfNodes = 0;
                    st = br.readLine();

                    if (st.contains("nodes:")) {
                        st = br.readLine();
                    }
                    do {
                        if (!st.contains("edges:") && !databaseEdgeFinder) {
                            amountOfNodes++;
                        } else databaseEdgeFinder = true;

                        databaseGraphData.add(st);
                       // st = br.readLine();

                    } while ((st = br.readLine()) != null);
                    processDatabaseGraphData(databaseGraphData, amountOfNodes);
                }



            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        File stats = new File("output/computationStats.txt");
        FileWriter out;

        int maxNodeAmountTotal = amountOfNodesMap.get("query") * amountOfNodesMap.get("transducer") * amountOfNodesMap.get("database");

        try {
            out = new FileWriter(stats, false);
            out.write("max amount of possible nodes in the product automaton: " + maxNodeAmountTotal + ". \n");
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processQueryGraphData(ArrayList<String> queryData, int amountOfNodes) {

        HashMap<String, QueryNode> queryNodes = new HashMap<>();  // map containing the QueryNodes
        QueryNode source;
        QueryNode target;
        QueryNode node;
        String label;
        String[] strArray; // working array ...

        amountOfNodesMap.put("query", amountOfNodes);
        // looping over all nodes
        for (int i = 0; i < amountOfNodes ; i++) {

            strArray = queryData.get(i).split(",");
            strArray = removeWhiteSpace(strArray);

            node = new QueryNode(strArray[0], Boolean.parseBoolean(strArray[1]), Boolean.parseBoolean(strArray[2]));
            queryNodes.put(strArray[0], node);

        }


        // looping over all edges
        for (int i = amountOfNodes + 1; i < queryData.size(); i++) {
            strArray = queryData.get(i).split(",");
            strArray = removeWhiteSpace(strArray);

            source = queryNodes.get(strArray[0]);
            target = queryNodes.get(strArray[1]);
            label = strArray[2];

            dataProvider.queryGraph.addQueryObjectEdge(source, target, label);
        }
    }


    private void processTransducerGraphData(ArrayList<String> transducerData, int amountOfNodes){
        HashMap<String, TransducerNode> transducerNodes = new HashMap<>();  // map containing the transducerNodes
        TransducerNode source;
        TransducerNode target;
        TransducerNode node;
        String incoming;
        String outgoing;
        int cost;

        amountOfNodesMap.put("transducer", amountOfNodes);
        String[] strArray; // working array ...

        // looping over all nodes
        for (int i = 0; i < amountOfNodes; i++) {
            strArray = transducerData.get(i).split(",");
            strArray = removeWhiteSpace(strArray);

            node = new TransducerNode(strArray[0], Boolean.parseBoolean(strArray[1]), Boolean.parseBoolean(strArray[2]));
            transducerNodes.put(strArray[0], node);
        }

        // looping over all edges
        for (int i = amountOfNodes + 1; i < transducerData.size(); i++) {
            strArray = transducerData.get(i).split(",");
            strArray = removeWhiteSpace(strArray);

            source = transducerNodes.get(strArray[0]);
            target = transducerNodes.get(strArray[1]);

            // replace ε with empty string ""
            if (strArray[2].equals("ε")){
                incoming = "";
            } else incoming = strArray[2];

            // replace ε with empty string ""
            if (strArray[3].equals("ε")){
                outgoing = "";
            } else outgoing = strArray[3];

            cost = Integer.parseInt(strArray[4]);

            dataProvider.transducerGraph.addTransducerObjectEdge(source, target, incoming, outgoing, cost );
        }

    }

    private void processDatabaseGraphData(ArrayList<String> databaseData, int amountOfNodes){
        HashMap<String, DatabaseNode> databaseNodes = new HashMap<>();  // map containing the databaseNodes
        DatabaseNode source;
        DatabaseNode target;
        DatabaseNode node;
        String label;

        String[] strArray; // working array ...
        amountOfNodesMap.put("database", amountOfNodes);
        // looping over all nodes
        for (int i = 0; i < amountOfNodes ; i++) {

            strArray = databaseData.get(i).split(",");
            strArray = removeWhiteSpace(strArray);

            node = new DatabaseNode(strArray[0]);
            databaseNodes.put(strArray[0], node);

        }

        // looping over all edges
        for (int i = amountOfNodes + 1; i < databaseData.size(); i++) {
            strArray = databaseData.get(i).split(",");
            strArray = removeWhiteSpace(strArray);

            source = databaseNodes.get(strArray[0]);
            target = databaseNodes.get(strArray[1]);
            label = strArray[2];

            dataProvider.databaseGraph.addDatabaseObjectEdge(source, target, label);
        }
    }

    private String[] removeWhiteSpace(String[] words) {
        String[] wordsWithoutWhitespace = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            wordsWithoutWhitespace[i] = words[i].trim();
        }
        return wordsWithoutWhitespace;
    }
}
