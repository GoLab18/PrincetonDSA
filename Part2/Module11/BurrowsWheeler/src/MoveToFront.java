import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

// Doubly-linked list is probably the simplest implementation and will do for small alphabets, but
// for more general purpose and with better average time complexity in theory, we can use AVL, red black trees or skip lists.
public class MoveToFront {
    private static class Node {
        private int ch;
        private Node prev, next;
    }

    public static void encode() {
        Node startNode = new Node(), nd = startNode;

        for (int i = 0; i < 256; i++, nd = nd.next) {
            nd.ch = i;

            if (i != 255) {
                nd.next = new Node();
                nd.next.prev = nd;
            }
        }

        nd = startNode;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int i = 0;

            while (nd.ch != c) {
                nd = nd.next;
                i++;
            }

            BinaryStdOut.write(i, 8);

            if (nd == startNode) continue;

            if (nd.next != null) nd.next.prev = nd.prev;
            nd.prev.next = nd.next;

            nd.prev = null;
            nd.next = startNode;

            startNode.prev = nd;
            startNode = nd;
        }

        BinaryStdOut.close();
    }

    public static void decode() {
        Node startNode = new Node(), nd = startNode;

        for (int i = 0; i < 256; i++, nd = nd.next) {
            nd.ch = i;

            if (i != 255) {
                nd.next = new Node();
                nd.next.prev = nd;
            }
        }

        nd = startNode;
        while (!BinaryStdIn.isEmpty()) {
            int ri = BinaryStdIn.readChar();
            for (int i = 0; i < ri; i++) nd = nd.next;

            BinaryStdOut.write(nd.ch, 8);

            if (nd == startNode) continue;

            if (nd.next != null) nd.next.prev = nd.prev;
            nd.prev.next = nd.next;

            nd.prev = null;
            nd.next = startNode;

            startNode.prev = nd;
            startNode = nd;
        }

        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) MoveToFront.encode();
        else if (args[0].equals("+")) MoveToFront.decode();
    }
}