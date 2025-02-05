import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;

public class BoggleSolver {
    private static final int[][] DIRECTIONS = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    private final Trie trie = new Trie();


    public BoggleSolver(String[] dictionary) {
        for (String s : dictionary) {
            if (s.length() < 3) continue;

            if (s.length() == 3 || s.length() == 4) trie.put(s, 1);
            else if (s.length() == 5) trie.put(s, 2);
            else if (s.length() == 6) trie.put(s, 3);
            else if (s.length() == 7) trie.put(s, 5);
            else trie.put(s, 11);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> valWords = new HashSet<>();
        int rw = board.rows(), cl = board.cols();
        boolean[][] visited = new boolean[rw][cl];

        char[] strArray = new char[rw*cl*2];

        for (int i = 0; i < rw; i++) {
            for (int j = 0; j < cl; j++) dfs(i, j, visited, board, valWords, strArray, 0);
        }

        return valWords;
    }

    private void dfs(int i, int j, boolean[][] vst, BoggleBoard bd, HashSet<String> vw, char[] strArr, int currLen) {
        if (i < 0 || j < 0 || i >= bd.rows() || j >= bd.cols() || vst[i][j]) return;
        if (currLen >= 3 && !trie.hasPrefix(strArr, currLen)) return;

        char c = bd.getLetter(i, j);
        if (c != 'Q') strArr[currLen++] = c;
        else {
            strArr[currLen++] = 'Q';
            strArr[currLen++] = 'U';
        }

        if (currLen >= 3 && trie.contains(strArr, currLen)) vw.add(new String(strArr, 0, currLen));
        vst[i][j] = true;

        for (int[] dir : DIRECTIONS) dfs(i+dir[0], j+dir[1], vst, bd, vw, strArr, currLen);
        vst[i][j] = false;
    }

    public int scoreOf(String word) {
        return trie.get(word.toCharArray(), word.length());
    }

    // Test
    public static void main(String[] args) {
        String[] dict = new In(args[0]).readAllStrings();
        BoggleSolver sv = new BoggleSolver(dict);
        BoggleBoard bd = new BoggleBoard(args[1]);

        int sc = 0;
        for (String s : sv.getAllValidWords(bd)) {
            StdOut.println(s);
            sc += sv.scoreOf(s);
        }

        StdOut.println("Score = " + sc);
    }
}
