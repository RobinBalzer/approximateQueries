import java.util.LinkedList;

// class for specifying a database node.
// it only has a name and a list of edges ...
public class DatabaseNode {

    String identifier;
    LinkedList<DatabaseEdge> edges;

    DatabaseNode(String id) {
        identifier = id;
        edges = new LinkedList<>();
    }

}
