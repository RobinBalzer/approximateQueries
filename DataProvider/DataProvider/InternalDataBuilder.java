package DataProvider;

import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

// the interface for our data sources.
// If you want to add other data, you have to implement this and override the functions.
// You can have a look at Example_2006 and Example_2020 for details.

public interface InternalDataBuilder {

    QueryGraph fillQueryGraph();
    TransducerGraph fillTransducerGraph();
    DatabaseGraph fillDatabaseGraph();


}
