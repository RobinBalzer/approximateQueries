package DataProvider;

// here we provide data from the paper
// Regular path queries under approximate semantics
// by Grahne and Thomo (year: 2006)
// data is taken from chapter 3 (page 171)

import Database.DatabaseGraph;
import Database.DatabaseNode;
import Query.QueryGraph;
import Query.QueryNode;
import Transducer.TransducerGraph;
import Transducer.TransducerNode;

public class Example_2006 implements InternalDataBuilder {

    @Override
    public QueryGraph fillQueryGraph() {
        QueryGraph queryGraph = new QueryGraph();

        QueryNode s0 = new QueryNode("s0", true, false);
        QueryNode s1 = new QueryNode("s1", false, true);
        QueryNode s2 = new QueryNode("s2", false, false);

        queryGraph.addQueryObjectEdge(s0, s2, "S");
        queryGraph.addQueryObjectEdge(s2, s1, "R");
        queryGraph.addQueryObjectEdge(s0, s1, "T");

        return queryGraph;
    }

    @Override
    public TransducerGraph fillTransducerGraph() {
        TransducerGraph transducerGraph = new TransducerGraph();

        TransducerNode t0 = new TransducerNode("t0", true, false);
        TransducerNode t1 = new TransducerNode("t1", false, false);
        TransducerNode t2 = new TransducerNode("t2", false, false);
        TransducerNode t3 = new TransducerNode("t3", false, true);

        transducerGraph.addTransducerObjectEdge(t0, t1, "R", "R", 0);
        transducerGraph.addTransducerObjectEdge(t1, t2, "T", "S", 2);
        transducerGraph.addTransducerObjectEdge(t2, t3, "T", "R", 0);
        transducerGraph.addTransducerObjectEdge(t2, t3, "T", "S", 1);

        return transducerGraph;
    }

    @Override
    public DatabaseGraph fillDatabaseGraph() {
        DatabaseGraph databaseGraph = new DatabaseGraph();

        DatabaseNode a = new DatabaseNode("a");
        DatabaseNode b = new DatabaseNode("b");
        DatabaseNode c = new DatabaseNode("c");
        DatabaseNode d = new DatabaseNode("d");

        databaseGraph.addDatabaseObjectEdge(a, b, "R");
        databaseGraph.addDatabaseObjectEdge(b, d, "S");
        databaseGraph.addDatabaseObjectEdge(d, c, "R");
        databaseGraph.addDatabaseObjectEdge(d, a, "T");
        databaseGraph.addDatabaseObjectEdge(c, a, "S");

        return databaseGraph;
    }
}
