import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestDirectedCycle {
    public static int findShortestCycle(Util.Digraph G) {
        int shortest = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            int cl = bfs(G, i);
            if (cl != -1) shortest = Math.min(shortest, cl);
        }

        return (shortest == Integer.MAX_VALUE) ? -1 : shortest;
    }

    private static int bfs(Util.Digraph G, int stNode) {
        int[] parent = new int[G.V()];
        Arrays.fill(parent, -1);

        int[] dist = new int[G.V()];
        Arrays.fill(dist, -1);

        Queue<Integer> q = new LinkedList<>();
        q.offer(stNode);

        dist[stNode] = 0;

        while (!q.isEmpty()) {
            int node = q.poll();

            for (int nd : G.adj(node)) {
                if (dist[nd] == -1) {
                    dist[nd] = dist[node] + 1;
                    parent[nd] = node;
                    q.offer(nd);
                }

                else if (parent[node] != nd) return dist[node] + 1 + dist[nd];
            }
        }
        return -1;
    }

    // Test
    public static void main(String[] args) {
        Util.Digraph G = new Util.Digraph(6);

        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 0);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(3, 5);

        int r = findShortestCycle(G);

        System.out.println((r == -1) ? "DAG" : "Shortest directed cycle -> " + r);
    }
}
