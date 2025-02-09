import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    public static void transform() {
        String text = BinaryStdIn.readString();
        int n = text.length();

        CircularSuffixArray csa = new CircularSuffixArray(text);
        int first = findOgRow(csa);

        BinaryStdOut.write(first);

        for (int i = 0; i < n; i++) {
            int suffIdx = csa.index(i);
            BinaryStdOut.write(text.charAt(suffIdx == 0 ? n-1 : suffIdx-1));
        }

        BinaryStdOut.close();
    }

    private static int findOgRow(CircularSuffixArray csa) {
        for (int i = 0; i < csa.length(); i++) if (csa.index(i) == 0) return i;
        return -1;
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        int n = t.length();

        int R = 256;
        int[] count = new int[R+1];

        // Key-indexed counting sort approach
        for (int i = 0; i < n; i++) count[t.charAt(i) + 1]++;

        for (int r = 0; r < R; r++) count[r+1] += count[r];

        int[] next = new int[n];
        char[] firstCol = new char[n];
        for (int i = 0; i < n; i++) {
            char c = t.charAt(i);
            next[count[c]] = i;
            firstCol[count[c]++] = c;
        }

        int row = first;
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(firstCol[row]);
            row = next[row];
        }

        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) BurrowsWheeler.transform();
        else if (args[0].equals("+")) BurrowsWheeler.inverseTransform();
    }
}