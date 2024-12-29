import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.ArrayList;

public class EagerPrimMST {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    public EagerPrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int v = 0; v < G.V(); v++) distTo[v] = Double.POSITIVE_INFINITY;

        distTo[0] = 0.0;
        pq.insert(0, 0.0);

        while (!pq.isEmpty()) visit(G, pq.delMin());
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;

        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;

            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();

                if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        ArrayList<Edge> mst = new ArrayList<Edge>();

        for (int v = 1; v < edgeTo.length; v++) mst.add(edgeTo[v]);

        return mst;
    }
}