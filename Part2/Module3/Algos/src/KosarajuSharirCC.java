public class KosarajuSharirCC {
    private final boolean[] marked;
    private final int[] id;
    private int uid;

    public KosarajuSharirCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder dfo = new DepthFirstOrder(G.reverse());

        for (int v : dfo.reversePost()) {
            if (!marked[v]) {
                dfs(G, v);

                uid++;
            }
        }

    }

    // BFS can be used also for example
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = uid;

        for (int w : G.adj(v)) if (!marked[w]) dfs(G, w);
    }

    public boolean stronlyConnected(int v, int w) {
        return id[v] == id[w];
    }
}
