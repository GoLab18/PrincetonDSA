import java.util.ArrayList;

public class FlowNetwork {
    private final int V;
    private ArrayList<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        this.V = V;

        adj = (ArrayList<FlowEdge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) adj[v] = new ArrayList<>();
    }

    public void addEdge(FlowEdge e) {
        adj[e.from()].add(e);
        adj[e.to()].add(e);
    }

    public int V() {
        return V;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }
}
