import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class SAP {
    private final Digraph G;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Graph can't be null");
        this.G = new Digraph(G);
    }

    public int length(int v, int w) {
        validateVertices(v, w);
        return bfs(v, w)[0];
    }

    public int ancestor(int v, int w) {
        validateVertices(v, w);
        return bfs(v, w)[1];
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateIterVertices(v, w);
        return bfsSet(v, w)[0];
    }


    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateIterVertices(v, w);
        return bfsSet(v, w)[1];
    }

    private int[] bfs(int v, int w) {
        Queue<Integer> queueV = new Queue<>();
        Queue<Integer> queueW = new Queue<>();

        int[] distV = new int[G.V()];
        int[] distW = new int[G.V()];

        boolean[] markedV = new boolean[G.V()];
        boolean[] markedW = new boolean[G.V()];

        // SP length and ancestor
        int[] lenAns = {-1, -1};

        queueV.enqueue(v);
        distV[v] = 0;
        markedV[v] = true;

        queueW.enqueue(w);
        distW[w] = 0;
        markedW[w] = true;

        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) processVertex(queueV, distV, markedV, distW, markedW, lenAns);
            if (!queueW.isEmpty()) processVertex(queueW, distW, markedW, distV, markedV, lenAns);
        }

        return lenAns;
    }

    private int[] bfsSet(Iterable<Integer> v, Iterable<Integer> w) {
        Queue<Integer> queueV = new Queue<>();
        Queue<Integer> queueW = new Queue<>();

        int[] distV = new int[G.V()];
        int[] distW = new int[G.V()];

        boolean[] markedV = new boolean[G.V()];
        boolean[] markedW = new boolean[G.V()];

        int[] lenAns = {-1, -1};

        for (int vert : v) {
            queueV.enqueue(vert);
            distV[vert] = 0;
            markedV[vert] = true;
        }

        for (int vert : w) {
            queueW.enqueue(vert);
            distW[vert] = 0;
            markedW[vert] = true;
        }

        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) processVertex(queueV, distV, markedV, distW, markedW, lenAns);
            if (!queueW.isEmpty()) processVertex(queueW, distW, markedW, distV, markedV, lenAns);
        }

        return lenAns;
    }

    private void processVertex(Queue<Integer> q, int[] dist, boolean[] marked,
                               int[] distOther, boolean[] markedOther, int[] lenAns) {
        int curr = q.dequeue();

        for (int next : G.adj(curr)) {
            if (!marked[next]) {
                dist[next] = dist[curr] + 1;
                marked[next] = true;
                q.enqueue(next);
            }

            if (markedOther[next]) {
                int len = dist[next] + distOther[next];

                if (lenAns[0] == -1 || len < lenAns[0]) {
                    lenAns[0] = len;
                    lenAns[1] = next;
                }
            }
        }
    }

    private void validateVertices(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException("Vertices outside of prescribed range");
    }

    private void validateIterVertices(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("Iterable arguments can't be null");
        for (Integer vertex : v)
            if (vertex == null || vertex < 0 || vertex >= G.V())
                throw new IllegalArgumentException("Invalid vertex inside iterable");

        for (Integer vertex : w)
            if (vertex == null || vertex < 0 || vertex >= G.V())
                throw new IllegalArgumentException("Invalid vertex inside iterable");
    }

    // Test
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}