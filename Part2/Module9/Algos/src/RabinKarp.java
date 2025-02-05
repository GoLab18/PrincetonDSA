import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {
    private String pat;
    private long Q, RM, patHash;
    private int M, R = 256;

    public RabinKarp(String pat) {
        this.pat = pat;
        this.M = pat.length();
        Q = longRandomPrime();

        RM = 1;
        for (int i = 1; i <= M-1; i++) RM = (R * RM) % Q;

        patHash = hash(pat, M);
    }

    private boolean lasVegasCheck(String txt, int i) {
        for (int j = 0; j < M; j++) {
            if (txt.charAt(i + j) != pat.charAt(j)) return false;
        }

        return true;
    }

    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++) h = (R * h + key.charAt(j)) % Q;

        return h;
    }

    public int search(String txt, boolean lasVegasMethod) {
        int n = txt.length();
        long txtHash = hash(txt, M);

        if (patHash == txtHash) return 0;

        for (int i = M; i < n; i++) {
            txtHash = (txtHash + Q - RM*txt.charAt(i-M) % Q) % Q;
            txtHash = (txtHash*R + txt.charAt(i)) % Q;

            if (patHash == txtHash) {
                if (!lasVegasMethod) return i - M + 1; // Monte Carlo Version
                if (lasVegasCheck(txt, i - M + 1)) return i - M + 1;
            }
        }
        return n;
    }

    private long longRandomPrime() {
        return BigInteger.probablePrime(31, new Random()).longValue();
    }

    public static void main(String[] args) {
        System.out.println("Pattern start index -> "
                + new RabinKarp("abc").search("accdeabce", true));
    }
}
