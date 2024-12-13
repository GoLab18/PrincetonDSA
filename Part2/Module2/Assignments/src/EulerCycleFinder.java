import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class EulerCycleFinder {
    private final boolean[] visited;
    private final List<Integer> eulerCycle;

    public EulerCycleFinder(Graph g, int start) {
        visited = new boolean[g.size()];
        eulerCycle = new LinkedList<>();

        if (hasEulerCycle(g)) findEulerCycle(g, start);
        else {
            System.out.println("The graph does not have an Euler cycle.");
            System.exit(0);
        }
    }

    private boolean hasEulerCycle(Graph g) {
        // Even degrees check
        for (int v = 0; v < g.size(); v++) if (g.adj(v).size() % 2 != 0) {
            return false;
        }

        // Graph connection check
        dfsCheck(g, 0);

        for (int v = 0; v < g.size(); v++) if (!g.adj(v).isEmpty() && !visited[v]) {
            return false;
        }

        return true;
    }

    private void dfsCheck(Graph g, int v) {
        visited[v] = true;
        for (int w : g.adj(v)) if (!visited[w]) dfsCheck(g, w);
    }

    private void findEulerCycle(Graph g, int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int v = stack.peek();

            if (g.adj(v).isEmpty()) eulerCycle.add(stack.pop());
            else {
                int w = g.adj(v).remove(0);

                g.adj(w).remove((Integer) v);
                stack.push(w);
            }
        }
    }

    public List<Integer> getEulerCycle() {
        return eulerCycle;
    }

    public static class Graph {
        private final int vertexAmount;
        private final ArrayList<Integer>[] adj;

        public Graph(int v) {
            this.vertexAmount = v;
            adj = (ArrayList<Integer>[]) new ArrayList[v];
            for (int i = 0; i < v; i++) adj[i] = new ArrayList<>();
        }

        public void addEdge(int l, int r) {
            adj[l].add(r);
            adj[r].add(l);
        }

        public int size() {
            return vertexAmount;
        }

        public ArrayList<Integer> adj(int v) {
            return adj[v];
        }
    }

    // Test
    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);

        EulerCycleFinder finder = new EulerCycleFinder(graph, 0);

        System.out.println("Euler Cycle: " + finder.getEulerCycle());
    }
}
