import java.util.ArrayList;

public class Digraph {
    private final int V;
    private final ArrayList<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.adj = (ArrayList<Integer>[]) new ArrayList[V];

        for (int v = 0; v < V; v++) adj[v] = new ArrayList<>();
    }

    public int V() {
        return V;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse() {
        Digraph R = new Digraph(V);

        for (int v = 0; v < V; v++) for (int w : adj(v)) R.addEdge(w, v);

        return R;
    }
}
