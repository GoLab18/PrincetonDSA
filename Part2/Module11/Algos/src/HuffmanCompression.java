import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class HuffmanCompression {
    private static final int R = 256; // ASCII

    private record Node(char ch, int freq, Node left, Node right) implements Comparable<Node> {
        public boolean isLeaf() {
            return left == null && right == null;
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
            for (int j = 0; j < code.length(); j++) BinaryStdOut.write(code.charAt(j) == '1');
        }

        BinaryStdOut.close();
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }

        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    public static void expand() {
        Node root = readTrie();
        int n = BinaryStdIn.readInt(); // Reads in chars num

        for (int i = 0; i < n; i++) {
            Node x = root;

            while (!x.isLeaf()) {
                if (BinaryStdIn.readBoolean()) x = x.right;
                else x = x.left;
            }

            BinaryStdOut.write(x.ch);
        }

        BinaryStdOut.close();
    }

    private static Node readTrie() {
        return (BinaryStdIn.readBoolean())
                ? new Node(BinaryStdIn.readChar(), 0, null, null)
                : new Node('\0', 0, readTrie(), readTrie());
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }

        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    // Huffman Algorithm for building the most optimal code representation tree.
    // There is also Shannon-Fano Algorithm, but it does not guarantee the most optimal code base.
    // Both provide prefix-free code bases.
    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();

        for (char i = 0; i < R; i++) if (freq[i] > 0) pq.insert(new Node(i, freq[i], null, null));

        while (pq.size() > 1) {
            Node x = pq.delMin(), y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);

            pq.insert(parent);
        }

        return pq.delMin(); // Root
    }
}
