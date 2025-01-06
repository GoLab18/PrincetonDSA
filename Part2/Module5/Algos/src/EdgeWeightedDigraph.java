import java.util.ArrayList;

public class EdgeWeightedDigraph {
    private final int V;
    private final ArrayList<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        adj = (ArrayList<DirectedEdge>[]) new ArrayList[V];

        for (int v = 0; v < V; v++) adj[v] = new ArrayList<>();
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
    }

    public int V() {
        return V;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
}
