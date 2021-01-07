package ClassicalAnswers;

import org.javatuples.Pair;

import java.util.*;

// WIP!
// here we want to calculate the answer set.
// we take the productAutomaton and find all paths from an initial to a final state.
// then we return all databaseNodeIdentifiers that are contained in valid paths.
// these are all classical answers to the query.
// TODO: answerSet might not be the right return value...
// TODO: 15th december: implement reachability.
//  we have initialNodes and finalNodes.
//  Find all paths from an initialNode to all finalNodes using the edges in the graph.
//  return the pairs of database-source and database-target.
//  repeat until all initialNodes have been tested for reachability.

public class CalculatingClassicalAnswers {

    public Set<Pair> finalAnswerSet;
    String choice;
    ProductAutomatonConstructor productAutomatonConstructor;
    Set<ProductAutomatonNode> initialNodes;
    Set<ProductAutomatonNode> finalNodes;
    Set<ProductAutomatonNode> nodes;
    List<String> pathList;
    List<ProductAutomatonNode> nodeStack;
    ProductAutomatonNode currentNode;

    CalculatingClassicalAnswers(String choice) {
        this.choice = choice;
        this.productAutomatonConstructor = new ProductAutomatonConstructor(choice);

        productAutomatonConstructor.construct();

        this.nodes = productAutomatonConstructor.productAutomatonGraph.nodes;
        this.initialNodes = productAutomatonConstructor.productAutomatonGraph.initialNodes;
        this.finalNodes = productAutomatonConstructor.productAutomatonGraph.finalNodes;
        this.pathList = new ArrayList<>();
        this.nodeStack = new ArrayList<>();
        this.currentNode = new ProductAutomatonNode();
        this.finalAnswerSet = new HashSet<>();
    }

    public void reachability(ProductAutomatonNode start) {

        // add the start node to the node stack.
        nodeStack.add(start);
        pathList.add(start.identifier.getValue1().identifier);

        // start.visit(); // TODO: remove visit since we dont need it

        System.out.println("---");
        System.out.println(pathList);
        System.out.println(nodeStack);


        // while nodes to explore
        //  explore node at index 0,
        //  for every edge of node
        //      add target of edge (new node)
        //

        while(true){
            currentNode = findChild(start);

            if(currentNode == null){
                System.out.println("currentNode == null!");
                break; // make this a return eventually!
            }

            // if our currentNode is a final state, we want to add the databasePart to our answerSet!
            if (currentNode.finalState){
                System.out.println("We've found a final state!");
                Pair<String, String> answerTuple = new Pair<>(start.identifier.getValue1().identifier, currentNode.identifier.getValue1().identifier);
                finalAnswerSet.add(answerTuple);
            }

            // Note: we do not need visit() here, since we may visit a node more than once. -> Don't block it right away!
            // this may be a bit messy, however for approximations we will use the visit() boolean and prevent multiple visiting.

            // Now we go through all nodes our current node has an edge to.

            for (ProductAutomatonEdge edge : currentNode.edges){

            }
        }

        while (!nodeStack.isEmpty()) {

            ProductAutomatonNode neighbor = findChild(nodeStack.get(0));
            /*

            currentNode = nodeStack.get(0);
            nodeStack.remove(0);
            for (ProductAutomatonEdge edgeInner : currentNode.edges) {
                pathList.add(edgeInner.target.identifier.getValue1().identifier);
                nodeStack.add(edgeInner.target);


             */
            if (neighbor.finalState) {
                Pair<String, String> answerTuple = new Pair<>(start.identifier.getValue1().identifier, neighbor.identifier.getValue1().identifier);
                finalAnswerSet.add(answerTuple);
                System.out.println("updated answerSet!");
                System.out.println(finalAnswerSet);
                System.out.println(pathList);
            } // else  find another node... -> recursion
        }
    }

    public ProductAutomatonNode findChild(ProductAutomatonNode node) {

        ProductAutomatonNode child = null;
        currentNode = nodeStack.get(0);
        nodeStack.remove(0);
        for (ProductAutomatonEdge edgeInner : currentNode.edges) {
            pathList.add(edgeInner.target.identifier.getValue1().identifier);
            nodeStack.add(edgeInner.target);

            if (edgeInner.target.finalState) {
                Pair<String, String> answerTuple = new Pair<>(node.identifier.getValue1().identifier, edgeInner.target.identifier.getValue1().identifier);
                finalAnswerSet.add(answerTuple);
                System.out.println("updated answerSet!");
                System.out.println(finalAnswerSet);
                System.out.println(pathList);
            } // else  find another node... -> recursion
        }

        return child;
    }

    public void reachabilityAll() {
        ProductAutomatonNode start;

        // for all initial nodes
        // if initialNode is unvisited
        // call reachability with this.initialNode
        for (ProductAutomatonNode initialNode : initialNodes) {
            if (!initialNode.visited) {
                start = initialNode;
                reachability(start);
                initialNode.visit();
            }
        }

        System.out.print("Answer set for data set " + choice + " : ");
        System.out.println(Arrays.toString(finalAnswerSet.toArray()));
    }

}