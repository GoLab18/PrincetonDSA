public class KMP {
    private final String pattern;
    private final int M, R = 256;
    private final int[][] dfa;

    public KMP(String pattern) {
        this.pattern = pattern;
        M = pattern.length();
        dfa = new int[R][M];

        dfa[pattern.charAt(0)][0] = 1;

        for (int x = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++) dfa[c][j] = dfa[c][x]; // Copying mismatched cases
            dfa[pattern.charAt(j)][j] = j+1; // Setting matching case
            x = dfa[pattern.charAt(j)][x]; // Updating restart state
        }
    }

    public int search(String txt) {
        int i, j, n = txt.length();

        for (i = 0, j = 0; i < n && j < M; i++) j = dfa[txt.charAt(i)][j];

        if (j == M) return i - M;
        else return n;
    }
}
