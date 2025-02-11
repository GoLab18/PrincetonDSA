public class Simplex {
    private final double[][] a; // Tableaux
    private final int m, n; // Constraints and variables nums

    public Simplex(double[][] A, double[] b, double[] c) {
        m = b.length; n = c.length;
        a = new double[m+1][m+n+1];

        for (int i = 0; i < m; i++) System.arraycopy(A[i], 0, a[i], 0, n);
        for (int j = n; j < m + n; j++) a[j-n][j] = 1.0;
        System.arraycopy(c, 0, a[m], 0, n);
        for (int i = 0; i < m; i++) a[i][m+n] = b[i];
    }

    // Selects the entering variable using Bland's Rule (first positive coefficient)
    // Guarantees termination by preventing cycling
    private int blandRule() {
        for (int q = 0; q < m+n; q++) if (a[m][q] > 0) return q;

        return -1;
    }

    // Selects the entering variable using Dantzig’s Rule (largest positive coefficient)
    // Usually converges faster but may cause cycling in rare cases
    private int dantzigRule() {
        int q = -1;

        for (int j = 0; j < m + n; j++) {
            if (a[m][j] > 0 && (q == -1 || a[m][j] > a[m][q])) q = j;
        }

        return q;
    }

    // Selects the leaving variable using the Minimum Ratio Rule
    // Ensures feasibility by choosing the row that limits the increase of entering variable
    private int minRatioRule(int q) {
        int p = -1;

        for (int i = 0; i < m; i++) {
            if (a[i][q] <= 0) continue;
            else if (p == -1) p = i;
            else if (a[i][m+n] / a[i][q] < a[p][m+n] / a[p][q]) p = i;
        }

        return p;
    }

    public void pivot(int p, int q) {
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m+n; j++) if (i != p && j != q) a[i][j] -= a[p][j]*a[i][q]/a[p][q];
        }

        for (int i = 0; i <= m; i++) if (i != p) a[i][q] = 0.0;

        for (int j = 0; j < m+n; j++) if (j != q) a[p][j] /= a[p][q];

        a[p][q] = 1.0;
    }

    public void solve() {
        while (true) {
            int q = blandRule(); // Can be exchanged with dantzigRule()
            if (q == -1) break;

            int p = minRatioRule(q);
            if (p == -1) break;

            pivot(p, q);
        }
    }
}
