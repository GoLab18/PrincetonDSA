import java.util.ArrayList;
import java.util.PriorityQueue;

public class BottleneckMST {
    private double bottleneckWeight;
    private final PriorityQueue<Edge> mst;

    public BottleneckMST(EdgeWeightedGraph G) {
        bottleneckWeight = Double.NEGATIVE_INFINITY;
        mst = new PriorityQueue<>();

        PriorityQueue<Edge> edges = new PriorityQueue<>();
        for (Edge e : G.edges()) edges.add(e);

        UnionFind uf = new UnionFind(G.V());

        for (Edge e : edges) {
            int v = e.either(), w = e.other(v);

            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.add(e);

                bottleneckWeight = Math.max(bottleneckWeight, e.weight());
            }

            if (mst.size() == G.V() - 1) break;
        }
    }

    public double bottleneckWeight() {
        return bottleneckWeight;
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public record Edge(int v, int w, double weight) implements Comparable<Edge> {
        public int either() {
            return v;
        }

        public int other(int vertex) {
            if (vertex == v) return w;
            else return v;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(this.weight, o.weight);
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
    }

    public static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];

            for (int i = 0; i < n; i++) parent[i] = i;
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }

            return p;
        }

        public void union(int p, int q) {
            int rootP = find(p), rootQ = find(q);

            if (rootP == rootQ) return;

            if (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
            else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
            else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(4);

        G.addEdge(new Edge(0, 1, 1.0));
        G.addEdge(new Edge(1, 2, 2.0));
        G.addEdge(new Edge(2, 3, 3.0));
        G.addEdge(new Edge(0, 3, 4.0));

        BottleneckMST mst = new BottleneckMST(G);

        System.out.println("Bottleneck Weight -> " + mst.bottleneckWeight());

        System.out.println("Edges in MST:");
        for (Edge e : mst.edges()) System.out.println(e);
    }
}
