package DataProvider;

import Database.DatabaseGraph;
import Database.DatabaseNode;
import Query.QueryGraph;
import Query.QueryNode;
import Transducer.TransducerGraph;
import Transducer.TransducerNode;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class DataReader {
    private String path;
    private boolean transducerAutoGeneration;
    private Set<String> alphabet;
    private DataProvider dataProvider;
    private HashMap<String, Integer> amountOfNodesMap;

    public DataReader(String inputFile, boolean transducerAutoGeneration) {
        this.path = "src/main/resources/input/" + inputFile;
        this.transducerAutoGeneration = transducerAutoGeneration;
        alphabet = new HashSet<>();
        dataProvider = new DataProvider();
        amountOfNodesMap = new HashMap<>();
    }

    public void readFile() throws Exception {

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

                    if (transducerAutoGeneration) {
                        System.out.println("Transducer info skipped. Initializing auto generation of transducer ...");
                        createTransducerPreservingClassicalAnswers();


                        do {
                            st = br.readLine();

                        }
                        while (!st.contains("databaseGraph:"));

                        System.out.println(" ... done. Proceeding with database data.");

                    } else {

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

        File stats = new File("src/main/resources/output/computationStats.txt");
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
        for (int i = 0; i < amountOfNodes; i++) {

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

            alphabet.add(label);
            dataProvider.queryGraph.addQueryObjectEdge(source, target, label);
        }
    }


    private void processTransducerGraphData(ArrayList<String> transducerData, int amountOfNodes) {
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
            if (strArray[2].equals("ε")) {
                incoming = "";
            } else incoming = strArray[2];

            // replace ε with empty string ""
            if (strArray[3].equals("ε")) {
                outgoing = "";
            } else outgoing = strArray[3];

            cost = Integer.parseInt(strArray[4]);

            dataProvider.transducerGraph.addTransducerObjectEdge(source, target, incoming, outgoing, cost);
        }

    }

    private void processDatabaseGraphData(ArrayList<String> databaseData, int amountOfNodes) {
        HashMap<String, DatabaseNode> databaseNodes = new HashMap<>();  // map containing the databaseNodes
        DatabaseNode source;
        DatabaseNode target;
        DatabaseNode node;
        String label;

        String[] strArray; // working array ...
        amountOfNodesMap.put("database", amountOfNodes);
        // looping over all nodes
        for (int i = 0; i < amountOfNodes; i++) {

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

    /**
     * auto-generation of a transducer. this transducer will only preserve classical answers.
     * design choice: only one source and target node with n edges between them (alphabet size = n)
     * other variation could be: having n source and target nodes with only one edge between them.
     * other variation could be: having one node (initial and final) and n self-loops (alphabet size = n)
     */
    private void createTransducerPreservingClassicalAnswers() {

        TransducerNode source = new TransducerNode("s0", true, false);
        TransducerNode target = new TransducerNode("s1", false, true);
        String word; // working String
        String incoming;
        String outgoing;
        int cost = 0;

        for (String s : alphabet) {
            word = s;
            incoming = word;
            outgoing = word;

            dataProvider.transducerGraph.addTransducerObjectEdge(source, target, incoming, outgoing, cost);
        }

        amountOfNodesMap.put("transducer", 2);

    }

    private String[] removeWhiteSpace(String[] words) {
        String[] wordsWithoutWhitespace = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            wordsWithoutWhitespace[i] = words[i].trim();
        }
        return wordsWithoutWhitespace;
    }

    public void printData() throws FileNotFoundException {


        try {

            QueryGraph queryGraph = dataProvider.getQueryGraph();
            TransducerGraph transducerGraph = dataProvider.getTransducerGraph();
            DatabaseGraph databaseGraph = dataProvider.getDatabaseGraph();

            PrintStream fileStream = new PrintStream("src/main/resources/output/parsedInputData.txt");
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

        } catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }

    }

    public static void printSpace() {
        System.out.println("---");
    }


    public Set<String> getAlphabet() {
        return alphabet;
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public HashMap<String, Integer> getAmountOfNodesMap() {
        return amountOfNodesMap;
    }
}
