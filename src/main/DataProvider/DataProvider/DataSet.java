package DataProvider;

import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

public class DataSet {

    public String identifier;
    public QueryGraph queryGraph;
    public TransducerGraph transducerGraph;
    public DatabaseGraph databaseGraph;

    public DataSet(String identifier, QueryGraph queryGraph, TransducerGraph transducerGraph, DatabaseGraph databaseGraph){
        this.identifier = identifier;
        this.queryGraph = queryGraph;
        this.transducerGraph = transducerGraph;
        this.databaseGraph = databaseGraph;
    }


}
