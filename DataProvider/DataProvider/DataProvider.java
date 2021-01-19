package DataProvider;

import Database.DatabaseGraph;
import Query.QueryGraph;
import Transducer.TransducerGraph;

// this class provides us with data for the query, transducer and database.
// initially we provide 2 examples. see the respective classes for their sources.
public class DataProvider {

    public String datasetIdentifier;
    public QueryGraph queryGraph = new QueryGraph();
    public TransducerGraph transducerGraph = new TransducerGraph();
    public DatabaseGraph databaseGraph = new DatabaseGraph();
    InternalDataBuilder internalDataBuilderExample_2006 = new Example_2006();
    InternalDataBuilder internalDataBuilderExample_2020 = new Example_2020();

    /**
     * @param choice the data source we want to get the data from.
     */
    public DataProvider(String choice) {

        choice = choice.toLowerCase();
        switch (choice) {
            case "example_2006":
                queryGraph = internalDataBuilderExample_2006.fillQueryGraph();
                transducerGraph = internalDataBuilderExample_2006.fillTransducerGraph();
                databaseGraph = internalDataBuilderExample_2006.fillDatabaseGraph();
                break;
            case "example_2020":
                queryGraph = internalDataBuilderExample_2020.fillQueryGraph();
                transducerGraph = internalDataBuilderExample_2020.fillTransducerGraph();
                databaseGraph = internalDataBuilderExample_2020.fillDatabaseGraph();
                break;
            default:
                break;


        }
    }

    public DataProvider() {
        QueryGraph queryGraph = new QueryGraph();
        TransducerGraph transducerGraph = new TransducerGraph();
        DatabaseGraph databaseGraph = new DatabaseGraph();
    }

    public QueryGraph getQueryGraph() {
        return queryGraph;
    }

    public TransducerGraph getTransducerGraph() {
        return transducerGraph;
    }

    public DatabaseGraph getDatabaseGraph() {
        return databaseGraph;
    }

    public void setDatasetIdentifier(String datasetIdentifier) {
        this.datasetIdentifier = datasetIdentifier;
    }

    public String getDatasetIdentifier() {
        return datasetIdentifier;
    }
}


