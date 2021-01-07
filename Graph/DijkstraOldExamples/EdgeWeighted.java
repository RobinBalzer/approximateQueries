package DijkstraOldExamples;
public class EdgeWeighted implements Comparable<EdgeWeighted> {

    NodeWeighted source;
    String label;
    NodeWeighted destination;
    double weight;

    EdgeWeighted(NodeWeighted s, NodeWeighted d, String l, double w) {
        source = s;
        destination = d;
        label = l;
        weight = w;
    }

    public String toString() {
        return String.format("(%s -[%s]> %s, %f)", source.name, label, destination.name, weight);
    }

    public int compareTo(EdgeWeighted otherEdge) {
        if (this.weight > otherEdge.weight) {
            return 1;
        } else return -1;
    }
}
