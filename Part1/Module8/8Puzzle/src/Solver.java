import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.Stack;

public class Solver {
    private SearchNode searchNode;
    private boolean isSolvable;

    // Solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> mainPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        mainPQ.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        searchNode = mainPQ.delMin();
        SearchNode twinNode = twinPQ.delMin();

        isSolvable = searchNode.board.isGoal();

        while (!isSolvable) {
            if (twinNode.board.isGoal()) break;

            for (Board b : searchNode.board.neighbors()) {
                if (searchNode.prevNode == null || !searchNode.prevNode.board.equals(b)) {
                    mainPQ.insert(new SearchNode(b, searchNode.moves + 1, searchNode));
                }
            }

            searchNode = mainPQ.delMin();

            for (Board b : twinNode.board.neighbors()) {
                if (twinNode.prevNode == null || !twinNode.prevNode.board.equals(b)) {
                    twinPQ.insert(new SearchNode(b, twinNode.moves + 1, twinNode));
                }
            }

            twinNode = twinPQ.delMin();

            isSolvable = searchNode.board.isGoal();
        }

        if (!isSolvable) searchNode = null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable ? searchNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;

        Stack<Board> solutionStack = new Stack<>();

        // Backtracking from the goal node to the initial node
        for (SearchNode n = searchNode; n != null; n = n.prevNode) {
            solutionStack.push(n.board);
        }

        return solutionStack;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final int moves;
        private final Board board;
        private final SearchNode prevNode;
        private final int distanceCache;

        public SearchNode(Board board, int moves, SearchNode prevNode) {
            this.board = board;
            this.moves = moves;
            this.prevNode = prevNode;
            this.distanceCache = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            // Prioritizing by Manhattan distance + moves number (A* heuristic)
            return Integer.compare(this.distanceCache + this.moves, that.distanceCache + that.moves);
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}