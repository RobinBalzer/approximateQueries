package DataProvider;

import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

public interface InternalDataBuilder {

    QueryGraph fillQueryGraph();
    TransducerGraph fillTransducerGraph();
    DatabaseGraph fillDatabaseGraph();


}
