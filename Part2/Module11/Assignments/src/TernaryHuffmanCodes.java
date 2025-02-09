import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

// The trie is built in the same way as with the binary implementation, thus will provide the most optimal codes.
public class TernaryHuffmanCodes {
    private static final int R = 256; // ASCII

    private record Node(char ch, int freq, Node left, Node mid, Node right) implements Comparable<Node> {
        public boolean isLeaf() {
            return left == null && mid == null && right == null;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        int[] freq = new int[R];
        for (char c : input) freq[c]++;

        Node root = buildTrie(freq);

        String[] st = new String[R];

        buildCode(st, root, "");

        writeTrie(root);

        BinaryStdOut.write(input.length);

        for (char c : input) {
            String code = st[c];
            for (int j = 0; j < code.length(); j++) BinaryStdOut.write(code.charAt(j) - '0', 2);
        }

        BinaryStdOut.close();
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }

        if (x.left != null) buildCode(st, x.left, s + '0');
        if (x.mid != null) buildCode(st, x.mid, s + '1');
        if (x.right != null) buildCode(st, x.right, s + '2');
    }

    public static void expand() {
        Node root = readTrie();
        int n = BinaryStdIn.readInt(); // Reads in chars num

        for (int i = 0; i < n; i++) {
            Node x = root;

            while (!x.isLeaf()) {
                int num = BinaryStdIn.readInt(2);

                if (num == 0) x = x.left;
                else if (num == 1) x = x.mid;
                else x = x.right;
            }

            BinaryStdOut.write(x.ch);
        }

        BinaryStdOut.close();
    }

    private static Node readTrie() {
        return (BinaryStdIn.readBoolean())
                ? new Node(BinaryStdIn.readChar(), 0, null, null, null)
                : new Node('\0', 0, readTrie(), readTrie(), readTrie());
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }

        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.mid);
        writeTrie(x.right);
    }

    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();

        for (char i = 0; i < R; i++) if (freq[i] > 0) pq.insert(new Node(i, freq[i], null, null, null));

        // The placeholder node is added if nodes num is even, thus we always end up on pq.size() == 1 eventually
        if (pq.size() % 2 == 0) pq.insert(new Node('\0', 0, null, null, null));

        while (pq.size() > 1) {
            Node x = pq.delMin(); Node y = pq.delMin(); Node z = pq.delMin();

            Node parent = new Node('\0', x.freq + y.freq + z.freq, x, y, z);

            pq.insert(parent);
        }

        return pq.delMin();
    }
}
