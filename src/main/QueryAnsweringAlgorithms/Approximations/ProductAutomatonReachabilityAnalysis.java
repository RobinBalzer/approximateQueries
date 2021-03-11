package Approximations;

import org.javatuples.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

// note: we do not use the distance here since we store the weight in every ProductAutomatonNode.
public class ProductAutomatonReachabilityAnalysis {

    ProductAutomatonConstructor productAutomatonConstructor;



    // π[V] - predecessor of V: <V, predecessorOfV>
    HashMap<ProductAutomatonNode, ProductAutomatonNode> predecessor;
    // set S
    HashSet<ProductAutomatonNode> setOfNodes;
    // min-priority queue Q (note that by default a priority queue in java is a min queue!)
    PriorityQueue<ProductAutomatonNode> queue;
    // answerSet
    HashMap<Pair<String, String>, Double> answerMap;

    ProductAutomatonReachabilityAnalysis(String choice) {

        // String choice = "example_2006";
        predecessor = new HashMap<>();
        setOfNodes = new HashSet<>();
        queue = new PriorityQueue<>();
        answerMap = new HashMap<>();

        productAutomatonConstructor = new ProductAutomatonConstructor(choice);
        productAutomatonConstructor.construct();

    }


    public ProductAutomatonReachabilityAnalysis (ProductAutomatonConstructor productAutomatonConstructor) {
        predecessor = new HashMap<>();
        setOfNodes = new HashSet<>();
        queue = new PriorityQueue<>();
        answerMap = new HashMap<>();
        this.productAutomatonConstructor = productAutomatonConstructor;

    }



    /**
     * Dijkstra algorithm
     * explanations taken from "Introduction to algorithms" (Cormen, Leiserson, Rivest, Stein)
     * page 595
     * comments refer to the pseudo-code presented there
     *
     * @param sourceNode the source node
     */

    private void algo_dijkstra(ProductAutomatonNode sourceNode) {


        // line 1
        initialiseSingleSource(productAutomatonConstructor.productAutomatonGraph, sourceNode);
        // line 2
        setOfNodes.clear();
        // line 3
        queue.addAll(productAutomatonConstructor.productAutomatonGraph.nodes);

        // line 4
        while (!queue.isEmpty()) {
            // line 5
            ProductAutomatonNode p = queue.poll();
            // line 6
            setOfNodes.add(p);
            // line 7
            for (ProductAutomatonEdge edge : p.edges) {
                if (!setOfNodes.contains(edge.target)) {
                    setOfNodes.add(edge.target);
                }

                System.out.print(p.toStringWithWeight() + ", " + edge.target.toStringWithWeight() + ", edge: " );
                edge.print();
                System.out.println();

                // line 8
                relax(p, edge.target, edge);

                // this will keep the priorityQueue ordered.
                queue.remove(edge.target);
                queue.add(edge.target);

            }
        }
    }

    private void initialiseSingleSource(ProductAutomatonGraph graph, ProductAutomatonNode sourceNode) {

        // line 1.1
        for (ProductAutomatonNode node : graph.nodes) {

            // line 1.2
            node.setWeight(Double.POSITIVE_INFINITY);

            // line 1.3
            predecessor.put(node, null);
        }
        // line 1.4
        sourceNode.setWeight(0.0);
    }

    private void relax(ProductAutomatonNode u, ProductAutomatonNode v, ProductAutomatonEdge edge) {

        Double newCost = u.getWeight() + edge.cost;

        // line 8.1
        if (v.getWeight() >= newCost && !newCost.isInfinite()) {

            // line 8.2
            v.setWeight(newCost);

            // line 8.3
            // predecessor of v is u.
            predecessor.put(v, u);

        }

    }

    public void processDijkstraOverAllInitialNodes() throws FileNotFoundException {

        long start = System.currentTimeMillis();

        // for all initial nodes...
        for (ProductAutomatonNode initialNode : productAutomatonConstructor.productAutomatonGraph.initialNodes) {

            // clean up from previous runs...
            predecessor.clear();
            queue.clear();

            algo_dijkstra(initialNode);
            retrieveResultForOneInitialNode(initialNode);

            // System.out.println("set of predecessors " + predecessor);
            // System.out.println("setOfNodes (should contain every node " + setOfNodes);
            // System.out.println("queue (should be empty) " +  queue);


        }

        // Get elapsed time in milliseconds
        long elapsedTimeMillis = System.currentTimeMillis()-start;

        // Get elapsed time in seconds
        float elapsedTimeSec = elapsedTimeMillis/1000F;

        // Get elapsed time in minutes
        float elapsedTimeMin = elapsedTimeMillis/(60*1000F);

        writeTimeToFile(elapsedTimeMillis, elapsedTimeSec, elapsedTimeMin);


        printEndResult();

    }

    private void retrieveResultForOneInitialNode(ProductAutomatonNode initialNode) {

        System.out.println("------------------");
        System.out.println("reachable final nodes from initial node " + initialNode.toString() + " : ");


        for (ProductAutomatonNode node : setOfNodes) {
            // System.out.println(node.toString() + " " + node.finalState + " " + node.getWeight());
            System.out.println(node.toStringWithWeight());
            if (node.finalState && !node.getWeight().isInfinite()) {
                System.out.println(node.toString() + " with cost " + node.getWeight());
                Pair<String, String> answerPair = new Pair(initialNode.identifier.getValue2().identifier, node.identifier.getValue2().identifier);
                answerMap.put(answerPair, node.getWeight());
            }
        }

        System.out.println("------------------");

    }

    private void printEndResult() throws FileNotFoundException {

        writeToFile();

        System.out.println("------------------");
        System.out.println("end result: ");
        for (Pair pair : answerMap.keySet()) {
            System.out.println("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair));
        }
        System.out.println("computation completed.");
    }
    private void writeTimeToFile(long milli, float sec, float min){
        File stats = new File("src/main/resources/output/computationStats.txt");
        FileWriter out;

        try {
            int amountOfNodes = productAutomatonConstructor.productAutomatonGraph.nodes.size();
            out = new FileWriter(stats, true);

            out.write("amount of actual nodes in the product automaton: " + amountOfNodes + ". \n");
            out.write("note that we used the lazy construction. \n ");
            out.write("\n");
            out.write("some stats for the dijkstra processing time. \n");
            out.write("computation time in milliseconds: " + milli + ". \n");
            out.write("computation time in seconds: " + sec + ". \n");
            // out.write("computation time in minutes: " + min + ". \n");


            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() throws FileNotFoundException {

        File queryAnswers = new File ("src/main/resources/output/queryResults.txt");
        FileWriter out;
        int count = 0;
        try {
            out = new FileWriter(queryAnswers, false);
            out.write("query processed. \n");

            // TODO: this doesn't really work correctly, right? We print every connection with weight 0.0 as a classical,
            // TODO: but we should verify this with the path or the product automaton edges... (new attribute "classical"?)
            for (Pair pair : answerMap.keySet()) {
                if (answerMap.get(pair) == 0.0) {
                    count++;
                    out.write("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair) + " (classical answer) \n");
                } else
                out.write("(" + pair.getValue0().toString() + ", " + pair.getValue1().toString() + ") with cost " + answerMap.get(pair) + "\n");
            }
            int classicalAnswers = count;
            int approxAnswers = answerMap.size() - count;
            out.write("total answers: " + answerMap.size());
            out.write(". (" + classicalAnswers + " classical answer(s) and " + approxAnswers + " approximate answer(s).) \n");
            out.close();
            System.out.println("successfully wrote to file.");

        } catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }

        PrintStream fileStream = new PrintStream( new FileOutputStream("src/main/resources/output/graphs.txt", true));
        PrintStream stdout = System.out;
        System.setOut(fileStream);
        System.out.println("the product automaton graph for this computation: \n");
        productAutomatonConstructor.productAutomatonGraph.printGraph();
        System.out.println();
        System.out.println("note that '' (emptyString) means we read/write an epsilon. \n");
        System.setOut(stdout);
    }


}
