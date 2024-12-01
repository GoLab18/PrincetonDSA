import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int virtualTop;
    private final int virtualBottom;
    private final boolean[][] sitesGrid; // Equals false when site blocked
    private int openSitesAmount; // Tracks opened sites

    // Weighted quick unions for handling percolation
    // Separation of uf algorithms is for handling the backwash problem
    //
    // These algorithms work with 1D arrays,
    // but we can treat the 2D array as a folded 1D array
    private final WeightedQuickUnionUF ufTop;
    private final WeightedQuickUnionUF ufTopBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        sitesGrid = new boolean[n][n]; // Set to false

        // Set to be outside of grid range
        virtualTop = n * n;
        virtualBottom = n * n + 1;

        ufTop = new WeightedQuickUnionUF(n * n + 1);
        ufTopBottom = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 0; i < n; i++) {
            ufTop.union(i, virtualTop);
            ufTopBottom.union(i, virtualTop);
            ufTopBottom.union(n * (n - 1) + i, virtualBottom);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        indicesValidation(row, col);

        if (!isOpen(row, col)) {

            sitesGrid[row - 1][col - 1] = true;

            int current = (row - 1) * n + col - 1;
            int right = (row - 1) * n + col;
            int top = (row - 2) * n + col - 1;
            int left = (row - 1) * n + col - 2;
            int bottom = (row) * n + col - 1;

            if (col < n && isOpen(row, col + 1)) {
                ufTop.union(current, right);
                ufTopBottom.union(current, right);
            }

            if (row - 2 >= 0 && isOpen(row - 1, col)) {
                ufTop.union(current, top);
                ufTopBottom.union(current, top);
            }

            if (col - 2 >= 0 && isOpen(row, col - 1)) {
                ufTop.union(current, left);
                ufTopBottom.union(current, left);
            }

            if (row < n && isOpen(row + 1, col)) {
                ufTop.union(current, bottom);
                ufTopBottom.union(current, bottom);
            }

            openSitesAmount++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sitesGrid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        indicesValidation(row, col);

        if (isOpen(row, col)) {
            return ufTop.connected(n * (row - 1) + (col - 1), virtualTop);
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesAmount;
    }

    // Returns true if the system percolates.
    public boolean percolates() {
        if (n != 1) {
            return ufTopBottom.connected(virtualTop, virtualBottom);
        }
        else {
            return sitesGrid[0][0];
        }
    }

    // Checks if row and column indices are ints between 1 and n,
    // by convention they should be.
    // If not, throws.
    private void indicesValidation(int r, int c) {
        if (r > n || c > n || r < 1 || c < 1) throw new IllegalArgumentException();
    }
}
