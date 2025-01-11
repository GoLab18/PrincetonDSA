import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PerfectMatching {
    static ArrayList<Integer>[] graph;
    static int[] pairings, dist;

    static void addManWomanEdge(int u, int v) {
        graph[u].add(v);
        graph[v].add(u);
    }

    static boolean hopcroftKarp(int n, int k) {
        pairings = new int[2*n+1];
        dist = new int[n+1];

        Arrays.fill(pairings, 0);

        int matchingPairs = 0;

        while (bfs(n)) {
            for (int u = 1; u <= n; u++) if (pairings[u] == 0 && dfs(u)) matchingPairs++;
        }

        return matchingPairs == n;
    }

    static boolean bfs(int n) {
        Queue<Integer> q = new LinkedList<>();

        for (int u = 1; u <= n; u++) {
            if (pairings[u] == 0) {
                dist[u] = 0;
                q.add(u);
            }

            else dist[u] = Integer.MAX_VALUE;
        }

        dist[0] = Integer.MAX_VALUE;

        while (!q.isEmpty()) {
            int u = q.poll();

            if (dist[u] < dist[0]) {
                for (int v : graph[u]) {
                    if (dist[pairings[v]] == Integer.MAX_VALUE) {
                        dist[pairings[v]] = dist[u] + 1;
                        q.add(pairings[v]);
                    }
                }
            }
        }

        return dist[0] != Integer.MAX_VALUE;
    }

    static boolean dfs(int u) {
        if (u == 0) return true;

        for (int v : graph[u]) {
            if (dist[pairings[v]] == dist[u] + 1 && dfs(pairings[v])) {
                pairings[v] = u;
                pairings[u] = v;

                return true;
            }
        }

        dist[u] = Integer.MAX_VALUE;

        return false;
    }

    public static void main(String[] args) {
        int n = 4, k = 2;

        graph = (ArrayList<Integer>[]) new ArrayList[2*n+1];
        for (int i = 0; i < 2*n+1; i++) graph[i] = new ArrayList<>();

        addManWomanEdge(1, 5);
        addManWomanEdge(1, 6);
        addManWomanEdge(2, 5);
        addManWomanEdge(2, 7);
        addManWomanEdge(3, 6);
        addManWomanEdge(3, 8);
        addManWomanEdge(4, 7);
        addManWomanEdge(4, 8);

        System.out.println("Perfect Matching Exists -> " + hopcroftKarp(n, k));
    }
}
