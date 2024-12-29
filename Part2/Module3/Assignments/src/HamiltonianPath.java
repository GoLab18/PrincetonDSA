import java.util.*;
import java.util.function.Consumer;

public class HamiltonianPath {
    public static class DepthFirstOrder {
        private final boolean[] marked;
        private final Stack<Integer> reversePost;

        public DepthFirstOrder(Util.Digraph G) {
            marked = new boolean[G.V()];
            reversePost = new Stack<Integer>();

            for (int v = 0; v < G.V(); v++) if (!marked[v]) dfs(G, v);
        }

        private void dfs(Util.Digraph G, int v) {
            marked[v] = true;

            for (int w : G.adj(v)) if (!marked[w]) dfs(G, w);

            reversePost.push(v);
        }

        public Iterable<Integer> reversePost() {
            return reversePost;
        }
    }

    public static boolean hasHamiltonianPath(Util.Digraph G) {
        DepthFirstOrder dfo = new DepthFirstOrder(G);
        Iterable<Integer> tsRes = dfo.reversePost();

        Iterator<Integer> iter = tsRes.iterator();
        int prev = iter.next();

        while (iter.hasNext()) {
            int curr = iter.next();

            if (!isDirectlyConnected(G, curr, prev)) return false;

            prev = curr;
        }

        return true;
    }

    private static boolean isDirectlyConnected(Util.Digraph G, int v, int w) {
        for (int ng : G.adj(v)) if (ng == w) return true;

        return false;
    }

    // Test
    public static void main(String[] args) {
        Consumer<Boolean> output = (t) ->
                System.out.println(t ? "Hamiltonian Path exists" : "Hamiltonian Path doesn't exist");

        Util.Digraph G1 = new Util.Digraph(6);

        G1.addEdge(0, 1);
        G1.addEdge(1, 2);
        G1.addEdge(2, 3);
        G1.addEdge(3, 4);
        G1.addEdge(4, 5);

        output.accept(hasHamiltonianPath(G1));

        Util.Digraph G2 = new Util.Digraph(5);

        G2.addEdge(0, 1);
        G2.addEdge(1, 2);
        G2.addEdge(2, 3);
        G2.addEdge(4, 2);
        G2.addEdge(4, 3);

        output.accept(hasHamiltonianPath(G2));
    }
}
