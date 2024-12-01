package UnionFind.Algos;

enum Variant {
    OnePass,
    TwoPass    
};

public class WeightedQuickUnionUF {
    private int[] id;
    private int[] sz;
    private Variant v;

    public WeightedQuickUnionUF(int N, Variant variant) {
        v = variant;

        id = new int[N];
        
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    private int root(int i) {
        while (id[i] != i) {
            id[i] = switch (v) {
                case OnePass -> id[id[i]]; // One-pass variant path compression
                case TwoPass -> root(id[i]); // Two-pass variant path compression
            };

            i = id[i];
        }

        return i;
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);

        if (i == j) return;

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }   
}
