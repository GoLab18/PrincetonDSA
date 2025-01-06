import java.util.ArrayList;

public class EdgeWeightedGraph {
    private final int V;
    private final ArrayList<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = (ArrayList<Edge>[]) new ArrayList[V];

        for (int v = 0; v < V; v++) adj[v] = new ArrayList<>();
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);

        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<Edge> edges() {
        ArrayList<Edge> b = new ArrayList<>();

        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) if (e.other(v) > v) b.add(e);
        }

        return b;
    }

    public int V() {
        return V;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }
}
