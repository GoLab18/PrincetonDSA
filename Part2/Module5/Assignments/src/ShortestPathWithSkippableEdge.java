import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class ShortestPathWithSkippableEdge {
    public static EdgeWeightedDigraph reverse(EdgeWeightedDigraph G) {
        EdgeWeightedDigraph r = new EdgeWeightedDigraph(G.V());

        for (DirectedEdge e : G.edges()) {
            int from = e.from();
            int to = e.to();
            double weight = e.weight();
            r.addEdge(new DirectedEdge(to, from, weight));
        }

        return r;
    }

    public static double shortestPathWithSkippableEdge(EdgeWeightedDigraph G, int s, int t) {
        DijkstraSP dijkstraS = new DijkstraSP(G, s);

        EdgeWeightedDigraph revG = reverse(G);

        DijkstraSP dijkstraT = new DijkstraSP(revG, t);

        double minDist = dijkstraS.distTo(t);
        for (DirectedEdge e : G.edges()) {
            int u = e.from(), v = e.to();
            double pathDist = dijkstraS.distTo(u) + dijkstraT.distTo(v);

            minDist = Math.min(minDist, pathDist);
        }

        return minDist;
    }

    // Test
    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(6);

        G.addEdge(new DirectedEdge(0, 1, 2.0));
        G.addEdge(new DirectedEdge(0, 2, 4.0));
        G.addEdge(new DirectedEdge(1, 2, 1.0));
        G.addEdge(new DirectedEdge(1, 3, 7.0));
        G.addEdge(new DirectedEdge(2, 4, 3.0));
        G.addEdge(new DirectedEdge(3, 4, 2.0));
        G.addEdge(new DirectedEdge(3, 5, 1.0));
        G.addEdge(new DirectedEdge(4, 5, 5.0));

        int s = 0, t = 5;

        double r = shortestPathWithSkippableEdge(G, s, t);
        System.out.printf("Shortest path from %d to %d with one skippable edge -> %.2f\n", s, t, r);
    }
}
