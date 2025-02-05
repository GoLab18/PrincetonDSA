public class BoyerMoore {
    private int[] right;
    private String pat;

    public BoyerMoore(String pat) {
        this.pat = pat;
        int R = 256;
        right = new int[R];

        for (int c = 0; c < R; c++) right[c] = -1;

        for (int j = 0; j < pat.length(); j++) right[pat.charAt(j)] = j;
    }

    public int search(String txt) {
        int n = txt.length(), m = pat.length();
        int skip;

        for (int i = 0; i <= n-m; i += skip) {
            skip = 0;

            for (int j = m-1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                     break;
                }
            }

            if (skip == 0) return i;
        }

        return n; // Not found
    }
}