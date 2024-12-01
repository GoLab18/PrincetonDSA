import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private final int[][] board;
    private int blankTileX;
    private int blankTileY;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles.length;
        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankTileX = i;
                    blankTileY = j;
                }

                board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        int n = this.dimension();

        StringBuilder s = new StringBuilder();

        s.append(n).append("\n"); // First line with board size

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        int n = this.dimension();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != i*n + j + 1 && (i+1 != n || j+1 != n)) hammingDistance++;
            }
        }

        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        int n = this.dimension();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = board[i][j];

                if (tile != 0) {
                    int expectedRow = (tile - 1) / n;
                    int expectedCol = (tile - 1) % n;

                    // Adding manhattan distance for this tile
                    manhattanDistance += Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
                }
            }
        }

        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;

        if (getClass() != y.getClass()) return false;
        Board that = (Board) y;

        int n = this.dimension();
        if (n != that.dimension()) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new NeighborsIterable();
    }

    private class NeighborsIterable implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new NeighborsIterator();
        }

        private class NeighborsIterator implements Iterator<Board> {
            private final int[][] directions = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                while (currentIndex < directions.length) {
                    int newBlankX = blankTileX + directions[currentIndex][0];
                    int newBlankY = blankTileY + directions[currentIndex][1];

                    if (isWithinBounds(newBlankX, newBlankY)) return true;

                    currentIndex++; // Skipping invalid directions
                }

                return false;
            }

            @Override
            public Board next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                // Compute the next neighbor
                int newBlankX = blankTileX + directions[currentIndex][0];
                int newBlankY = blankTileY + directions[currentIndex][1];
                currentIndex++;

                // Create a new board configuration
                int[][] newBoard = copyBoard();
                newBoard[blankTileX][blankTileY] = newBoard[newBlankX][newBlankY];
                newBoard[newBlankX][newBlankY] = 0;

                return new Board(newBoard);
            }

            // Helper method that checks if a position is within bounds
            private boolean isWithinBounds(int x, int y) {
                int n = Board.this.dimension();
                return x >= 0 && x < n && y >= 0 && y < n;
            }
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int n = this.dimension();
        int[][] newBoard = copyBoard();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (newBoard[i][j] != 0 && newBoard[i][j + 1] != 0) {
                    // Swap operation on the two tiles
                    int temp = newBoard[i][j];
                    newBoard[i][j] = newBoard[i][j + 1];
                    newBoard[i][j + 1] = temp;

                    return new Board(newBoard);
                }
            }
        }

        // Should never reach here since a twin must always exist
        throw new IllegalStateException("Board twin cannot be created");
    }

    private int[][] copyBoard() {
        int n = Board.this.dimension();
        int[][] newBoard = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, n);
        }

        return newBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        System.out.println("Test 1: Board Creation and Properties");
        int[][] tiles = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board = new Board(tiles);
        System.out.println("The board:");
        System.out.println(board);
        System.out.println("Dimension: " + board.dimension());
        System.out.println("Hamming distance: " + board.hamming());
        System.out.println("Manhattan distance: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println();

        System.out.println("Test 2: Neighbors");
        System.out.println("Neighbors:");
        for (Board neighborBoard : board.neighbors()) {
            System.out.println(neighborBoard);
        }
        System.out.println();

        System.out.println("Test 3: Twin Board");
        System.out.println("Twin:");
        System.out.println(board.twin());
        System.out.println();

        System.out.println("Test 4: Equality Check");
        int[][] tiles2 = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board boardCopy = new Board(tiles2);
        System.out.println("Are boards equal? " + board.equals(boardCopy));

        int[][] tiles3 = {
                {0, 1},
                {3, 2}
        };
        Board board3 = new Board(tiles3);
        int[][] tiles4 = {
                {0, 1, 7},
                {3, 2, 6},
                {5, 8, 4}
        };
        Board board4 = new Board(tiles4);
        System.out.println("Are boards equal? " + board3.equals(board4));
        System.out.println();
    }
}