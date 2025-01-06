import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Topological;

// NOTE same as AcyclicSP but only requires changing distTo initialization to negative infinity and
// switching '>' to '<' inside the if statement in relax method

public class AcyclicLP {
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    public AcyclicLP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v = 0; v < G.V(); v++) distTo[v] = Double.NEGATIVE_INFINITY;

        distTo[s] = 0.0;

        Topological tpl = new Topological(new EdgeWeightedDigraph(G));

        for (int v : tpl.order()) for (DirectedEdge e : G.adj(v)) relax(e);
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();

        if (distTo[w] < distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }
}
