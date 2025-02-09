import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

// Stores counts as 8-bit values
public class RunLengthEncoding {
    public static void expand() {
        boolean bit = false;
        while (!BinaryStdIn.isEmpty()) {
            char cnt = BinaryStdIn.readChar();
            for (int i = 0; i < cnt; i++) BinaryStdOut.write(bit);
            bit = !bit;
        }

        BinaryStdOut.close();
    }

    public static void compress() {
        char cnt = 0;
        boolean bit, old = false;

        while (!BinaryStdIn.isEmpty()) {
            bit = BinaryStdIn.readBoolean();
            if (bit != old) {
                BinaryStdOut.write(cnt);
                cnt = 0;
                old = !old;
            }

            // If the 8-bit buffer gets exceeded, write 0 as a padding between consecutive same bit counts
            else if (cnt == 255) {
                BinaryStdOut.write(cnt);
                cnt = 0;
                BinaryStdOut.write(cnt);
            }

            cnt++;
        }

        BinaryStdOut.write(cnt);
        BinaryStdOut.close();
    }
}
