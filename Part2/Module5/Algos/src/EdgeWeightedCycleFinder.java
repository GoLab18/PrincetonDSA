import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

import java.util.ArrayList;

public class EdgeWeightedCycleFinder {
    private final boolean[] marked;
    private final DirectedEdge[] edgeTo;
    private final boolean[] onCurrRecStack;
    private ArrayList<DirectedEdge> cycle;

    public EdgeWeightedCycleFinder(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onCurrRecStack = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) if (!marked[v]) dfs(G, v);
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        onCurrRecStack[v] = true;
        marked[v] = true;

        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();

            if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }

            else if (onCurrRecStack[w]) {
                cycle = new ArrayList<>();
                DirectedEdge f = e;

                while (f.from() != w) {
                    cycle.add(f);
                    f = edgeTo[f.from()];
                }

                cycle.add(f);

                return;
            }
        }

        onCurrRecStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }
}