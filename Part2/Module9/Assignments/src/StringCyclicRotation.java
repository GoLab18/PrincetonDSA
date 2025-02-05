// KMP approach based on DFA
public class StringCyclicRotation {
    private final String cyclicRotPat;
    private final int[][] dfa;

    public StringCyclicRotation(String pat) {
        cyclicRotPat = pat;
        dfa = new int[256][pat.length()];

        for (int x = 0, i = 0; i < pat.length(); i++) {
            for (int c = 0; c < 256; c++) dfa[c][i] = dfa[c][x];
            dfa[pat.charAt(i)][i] = i+1;
            x = dfa[pat.charAt(i)][x];
        }
    }

    public boolean isCyclicRotation(String t) {
        if (cyclicRotPat.length() != t.length()) return false;
        return kmpSearch(t+t);
    }

    private boolean kmpSearch(String txt) {
        int i, j, n = txt.length(), m = cyclicRotPat.length();
        for (i = 0, j = 0; i < n && j < m; i++) j = dfa[txt.charAt(i)][j];

        return j == m;
    }

    // Test
    public static void main(String[] args) {
        String s1 = "winterbreak", s2 = "breakwinter", s3 = "breakwinner";

        var scr = new StringCyclicRotation(s1);
        System.out.println(s1 + " is a cycle rotation of " + s2 + " -> " + scr.isCyclicRotation(s2));
        System.out.println(s1 + " is a cycle rotation of " + s3 + " -> " + scr.isCyclicRotation(s3));
    }
}
