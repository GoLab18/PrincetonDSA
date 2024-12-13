import java.util.ArrayList;
import java.util.Stack;

public class StackBasedDFS {
    private final boolean[] marked;
    private final int[] edgeTo;
    private final int s;

    public StackBasedDFS(Graph g, int s) {
        marked = new boolean[g.size()];
        edgeTo = new int[g.size()];
        this.s = s;

        dfs(g, s);
    }

    private void dfs(Graph g, int start) {
        Stack<Integer> st = new Stack<>();

        st.push(start);
        marked[start] = true;

        while (!st.isEmpty()) {
            int v = st.pop();

            for (int w : g.adj(v)) if (!marked[w]) {
                st.push(w);
                marked[w] = true;
                edgeTo[w] = v;
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        Stack<Integer> path = new Stack<>();

        for (int i = v; i != s; i = edgeTo[i]) path.push(i);
        path.push(s);

        return path;
    }

    public static class Graph {
        private final int vertexAmount;
        private final ArrayList<Integer>[] adj;

        public Graph(int v) {
            this.vertexAmount = v;
            adj = (ArrayList<Integer>[]) new ArrayList[v];
            for (int i = 0; i < v; i++) adj[i] = new ArrayList<>();
        }

        // Creates an edge between given values
        public void addEdge(int l, int r) {
            adj[l].add(r);
            adj[r].add(l);
        }

        public int size() {
            return vertexAmount;
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

    // Test
    public static void main(String[] args) {
        StackBasedDFS.Graph graph = new StackBasedDFS.Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        StackBasedDFS dfs = new StackBasedDFS(graph, 2);

        for (int v = 0; v < graph.size(); v++) {
            if (dfs.hasPathTo(v)) System.out.println("Path to " + v + ": " + dfs.pathTo(v));
            else System.out.println("No path to " + v);
        }
    }
}
