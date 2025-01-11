import java.util.ArrayList;
import java.util.PriorityQueue;

public class FattestPath {
    public record DirectedEdge(int from, int to, double weight) implements Comparable<DirectedEdge> {
        @Override
        public int compareTo(DirectedEdge o) {
            return Double.compare(o.weight, this.weight);
        }
    }

    public static double fattestPath(ArrayList<DirectedEdge>[] g, int s, int t) {
        int n = g.length;
        boolean[] marked = new boolean[n];
        PriorityQueue<DirectedEdge> pq = new PriorityQueue<>();
        double bottleneck = Double.POSITIVE_INFINITY;

        for (DirectedEdge e : g[s]) pq.offer(e);

        marked[s] = true;

        while (!pq.isEmpty()) {
            DirectedEdge e = pq.poll();

            int w = e.to;

            if (marked[w]) continue;

            bottleneck = Math.min(bottleneck, e.weight);
            marked[w] = true;

            if (w == t) return bottleneck;

            for (DirectedEdge next : g[w]) if (!marked[next.to]) pq.offer(next);
        }

        return 0.0;
    }

    // Test
    public static void main(String[] args) {
        int n = 5;
        ArrayList<DirectedEdge>[] graph = (ArrayList<DirectedEdge>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        graph[0].add(new DirectedEdge(0, 1, 5));
        graph[0].add(new DirectedEdge(0, 2, 3));
        graph[1].add(new DirectedEdge(1, 3, 6));
        graph[2].add(new DirectedEdge(2, 3, 7));
        graph[3].add(new DirectedEdge(3, 4, 4));

        System.out.println("Fattest path capacity -> " + fattestPath(graph, 0, 4));
    }
}
