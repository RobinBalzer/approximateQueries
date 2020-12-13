package DataProvider;

// here we provide data from the paper
// Answering Regular Path Queries Under Approximate Semantics in Lightweight Description Logics
// by Gil and Turhan (year: 2020)
// data is taken from example 1 of the paper.

import Database.DatabaseGraph;
import Database.DatabaseNode;
import Query.QueryGraph;
import Query.QueryNode;
import Transducer.TransducerGraph;
import Transducer.TransducerNode;

public class Example_2020 implements InternalDataBuilder {

    @Override
    public QueryGraph fillQueryGraph() {
        QueryGraph queryGraph = new QueryGraph();

        QueryNode s0 = new QueryNode("s0", true, false);
        QueryNode s1 = new QueryNode("s1", false, true);
        QueryNode s2 = new QueryNode("s2", false, false);

        queryGraph.addQueryObjectEdge(s0, s1, "u1");
        queryGraph.addQueryObjectEdge(s1, s1, "u2");
        queryGraph.addQueryObjectEdge(s0, s2, "v1");
        queryGraph.addQueryObjectEdge(s2, s1, "u1");

        return queryGraph;
    }

    @Override
    public TransducerGraph fillTransducerGraph() {
        TransducerGraph transducerGraph = new TransducerGraph();

        TransducerNode t0 = new TransducerNode("t0", true, false);
        TransducerNode t1 = new TransducerNode("t1", false, false);
        TransducerNode t2 = new TransducerNode("t2", false, false);
        TransducerNode t3 = new TransducerNode("t3", false, true);

        transducerGraph.addTransducerObjectEdge(t0, t0, "u1", "u1", 0);
        transducerGraph.addTransducerObjectEdge(t0, t1, "u1", "v2", 3);
        transducerGraph.addTransducerObjectEdge(t0, t2, "v1", "v2", 1);
        transducerGraph.addTransducerObjectEdge(t1, t3, "", "v1", 3);
        transducerGraph.addTransducerObjectEdge(t1, t3, "u2", "", 1);
        transducerGraph.addTransducerObjectEdge(t2, t2, "u1", "u1", 0);

        return transducerGraph;
    }

    @Override
    public DatabaseGraph fillDatabaseGraph() {
        DatabaseGraph databaseGraph = new DatabaseGraph();

        DatabaseNode a = new DatabaseNode("a");
        DatabaseNode b = new DatabaseNode("b");
        DatabaseNode c = new DatabaseNode("c");
        DatabaseNode d = new DatabaseNode("d");

        databaseGraph.addDatabaseObjectEdge(a, b, "v2");
        databaseGraph.addDatabaseObjectEdge(a, c, "v2");
        databaseGraph.addDatabaseObjectEdge(b, d, "v1");
        databaseGraph.addDatabaseObjectEdge(c, d, "u1");

        return databaseGraph;
    }
}
