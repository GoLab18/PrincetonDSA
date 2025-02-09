import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

public class LZWCompression {
    private static final int R = 256; // number of input chars
    private static final int L = 4096; // number of different codewords = 2^W
    private static final int W = 12; // codeword width

    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>(); // Ternary Search Trie symbol table

        for (int i = 0; i < R; i++) st.put(String.valueOf((char) i), i);

        int code = R+1;

        while (!input.isEmpty()) {
            String s = st.longestPrefixOf(input);

            BinaryStdOut.write(st.get(s), W);

            int t = s.length();
            if (t < input.length() && code < L) st.put(input.substring(0, t+1), code++);
            input = input.substring(t);
        }

        BinaryStdOut.write(R, W); // Write EOF codeword which is R
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L];

        int i;
        for (i = 0; i < R; i++) st[i] = String.valueOf((char) i);

        st[i++] = " "; // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);

            codeword = BinaryStdIn.readInt(W);

            if (codeword == R) break;

            String s = st[codeword];

            if (i == codeword) s = val + val.charAt(0); // If lookahead is invalid, codeword from last one

            if (i < L) st[i++] = val + s.charAt(0);

            val = s;
        }

        BinaryStdOut.close();
    }
}