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
    // set S of nodes (vertices) whose final shortest-path weights from the source have already been determined.
    HashSet<ProductAutomatonNode> setOfNodes;
    // min-priority queue Q (note that by default a priority queue in java is a min queue!)
    PriorityQueue<ProductAutomatonNode> queue;
    // answerSet
    HashMap<Pair<String, String>, Double> answerMap;
    // int for the user input top_k search
    int topK;
    int localTopK;
    // boolean describing the search state we are in. false => "searchALl", true => "searchTopK"
    boolean topKSearchActive;

    public ProductAutomatonReachabilityAnalysis(ProductAutomatonConstructor productAutomatonConstructor) {
        predecessor = new HashMap<>();
        setOfNodes = new HashSet<>();
        queue = new PriorityQueue<>();
        answerMap = new HashMap<>();
        this.productAutomatonConstructor = productAutomatonConstructor;
        this.topKSearchActive = false;


    }

    public ProductAutomatonReachabilityAnalysis(ProductAutomatonConstructor productAutomatonConstructor, int topK) {
        predecessor = new HashMap<>();
        setOfNodes = new HashSet<>();
        queue = new PriorityQueue<>();
        answerMap = new HashMap<>();
        this.productAutomatonConstructor = productAutomatonConstructor;
        this.topKSearchActive = true;
        this.topK = topK;
        localTopK = topK;
        System.out.println("topK: " + topK);
        System.out.println("localTopK: " + localTopK);
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
                setOfNodes.add(edge.target);
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

    public HashMap<Pair<String, String>, Double> processDijkstraOverAllInitialNodes() {


        // for all initial nodes...
        for (ProductAutomatonNode initialNode : productAutomatonConstructor.productAutomatonGraph.initialNodes) {

            // clean up from previous runs...
            predecessor.clear();
            queue.clear();

            // run single-source dijkstra
            algo_dijkstra(initialNode);
            // put the new shortest-paths into the answer set
            retrieveResultForOneInitialNode(initialNode);

        }

        return answerMap;

    }

    private void retrieveResultForOneInitialNode(ProductAutomatonNode initialNode) {

        // for all nodes in set S
        for (ProductAutomatonNode node : setOfNodes) {
            // if you are a final state and your weight is not infinite (that means there is a path from the source to you)
            if (node.finalState && !node.getWeight().isInfinite()) {
                // I add you to the final answerSet in the form of ((source, target), weight)
                Pair<String, String> answerPair = new Pair(initialNode.identifier.getValue2().identifier, node.identifier.getValue2().identifier);
                answerMap.put(answerPair, node.getWeight());
            }
        }
    }
}
