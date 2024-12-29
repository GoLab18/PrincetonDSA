import java.util.ArrayList;

public class MSTEdgeCheck {
    public record Edge(int v, int w, double weight) {
        public int either() {
            return v;
        }

        public int other(int vertex) {
            if (vertex == v) return w;
            else return v;
        }

        public double weight() {
            return weight;
        }

        @Override
        public String toString() {
            return v + "-" + w + " (w: " + weight + ")";
        }
    }

    public static class EdgeWeightedGraph {
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

        public int V() {
            return V;
        }

        public Iterable<Edge> adj(int v) {
            return adj[v];
        }

        public void removeEdge(Edge e) {
            adj[e.either()].remove(e);
            adj[e.other(e.either())].remove(e);
        }
    }

    public static boolean isEdgeInMST(EdgeWeightedGraph graph, Edge e) {
        int u = e.either();
        int v = e.other(u);

        graph.removeEdge(e);

        boolean[] visited = new boolean[graph.V()];

        dfs(graph, u, visited, e.weight());

        return !visited[v];
    }

    private static void dfs(EdgeWeightedGraph graph, int u, boolean[] visited, double edgeWeightLimit) {
        visited[u] = true;

        for (Edge e : graph.adj(u)) {
            int v = e.other(u);

            if (!visited[v] && e.weight() < edgeWeightLimit) dfs(graph, v, visited, edgeWeightLimit);
        }
    }

    // Test
    public static void main(String[] args) {
        EdgeWeightedGraph graph = new EdgeWeightedGraph(5);

        Edge e1 = new Edge(0, 1, 2.0);
        Edge e2 = new Edge(1, 2, 3.0);
        Edge e3 = new Edge(2, 3, 1.0);
        Edge e4 = new Edge(3, 4, 4.0);
        Edge e5 = new Edge(4, 0, 5.0);

        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);

        System.out.println("Is " + e3 + " apart of some MST? " + isEdgeInMST(graph, e3));
    }
}
