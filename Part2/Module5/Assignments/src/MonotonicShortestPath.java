import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.Comparator;

// NOTE: missing one edge case, for most graphs the code will be fine but for ones like the one
// in the main test function (in which 2 is used for two seperate monotonic shortest paths),
// the shortest path length will be correct but the path will most likely be wrong =(

public class MonotonicShortestPath {
    private final DirectedEdge[] edgeToInc;
    private final double[] distToInc;
    private final DirectedEdge[] edgeToDec;
    private final double[] distToDec;

    public MonotonicShortestPath(EdgeWeightedDigraph G, int s) {
        edgeToInc = new DirectedEdge[G.V()];
        distToInc = new double[G.V()];
        edgeToDec = new DirectedEdge[G.V()];
        distToDec = new double[G.V()];

        for (int v = 0; v < G.V(); v++) {
            distToInc[v] = Double.POSITIVE_INFINITY;
            distToDec[v] = Double.POSITIVE_INFINITY;
        }

        distToInc[s] = 0.0;
        distToDec[s] = 0.0;

        ArrayList<DirectedEdge> edges = getAllEdges(G);
        edges.sort(Comparator.comparingDouble(DirectedEdge::weight));
        relaxEdges(edges, distToInc, edgeToInc, true);

        edges.sort((e1, e2) -> Double.compare(e2.weight(), e1.weight()));
        relaxEdges(edges, distToDec, edgeToDec, false);
    }

    private void relaxEdges(ArrayList<DirectedEdge> edges, double[] distTo, DirectedEdge[] edgeTo, boolean isInc) {
        for (DirectedEdge e : edges) {
            int v = e.from(), w = e.to();

            if (isInc) System.out.println(e);

            if (distTo[w] > distTo[v] + e.weight()) {
                if (
                    edgeTo[v] == null || (isInc && e.weight() > edgeTo[v].weight())
                    || (!isInc && e.weight() < edgeTo[v].weight())
                ) {
                    distTo[w] = distTo[v] + e.weight();
                    edgeTo[w] = e;
                }
            }
        }
    }

    public double distToInc(int v) {
        return distToInc[v];
    }

    public double distToDec(int v) {
        return distToDec[v];
    }

    public Iterable<DirectedEdge> pathToInc(int v) {
        return pathTo(v, edgeToInc);
    }

    public Iterable<DirectedEdge> pathToDec(int v) {
        return pathTo(v, edgeToDec);
    }

    private Iterable<DirectedEdge> pathTo(int v, DirectedEdge[] edgeTo) {
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) path.push(e);
        return path;
    }

    private ArrayList<DirectedEdge> getAllEdges(EdgeWeightedDigraph G) {
        ArrayList<DirectedEdge> edges = new ArrayList<>();
        for (int v = 0; v < G.V(); v++) for (DirectedEdge e : G.adj(v)) edges.add(e);
        return edges;
    }

    // Test
    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(5);

        G.addEdge(new DirectedEdge(0, 1, 3));
        G.addEdge(new DirectedEdge(0, 2, 7));
        G.addEdge(new DirectedEdge(1, 2, 5));
        G.addEdge(new DirectedEdge(1, 3, 7));
        G.addEdge(new DirectedEdge(2, 3, 2));
        G.addEdge(new DirectedEdge(2, 4, 6));
        G.addEdge(new DirectedEdge(3, 4, 3));

        int s = 0;

        MonotonicShortestPath sp = new MonotonicShortestPath(G, s);

        for (int t = 0; t < G.V(); t++) {
            System.out.print(s + " to " + t + "  ----  ");
            System.out.printf(" Increasing (%4.2f): ", sp.distToInc(t));
            if (sp.distToInc(t) < Double.POSITIVE_INFINITY) for (DirectedEdge e : sp.pathToInc(t)) System.out.print(e + " ");

            System.out.print("  ----  ");

            System.out.printf(" Decreasing (%4.2f): ", sp.distToDec(t));
            if (sp.distToDec(t) < Double.POSITIVE_INFINITY) for (DirectedEdge e : sp.pathToDec(t)) System.out.print(e + " ");
            System.out.println();
        }
    }
}
