import java.util.LinkedList;
import java.util.Queue;

public class Trie<Value> {
    private static final int R = 256;
    private Node root = new Node();

    private static class Node {
        private Object val;
        private final Node[] next = new Node[R];
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();

        if (d == key.length()) {
            x.val = val;
            return x;
        }

        char c = key.charAt(d);

        x.next[c] = put(x.next[c], key, val, d+1);

        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);

        if (x== null) return null;

        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;

        if (d == key.length()) return x;

        return get(x.next[key.charAt(d)], key, d+1);
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new LinkedList<>();

        collect(get(root, pre, 0), pre, q);

        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;

        if (x.val != null) q.add(pre);

        for (char c = 0; c < R; c++) collect(x.next[c], pre + c, q);
    }

    public String longestPrefixOf(String s) {
        int len = search(root, s, 0, 0);
        return s.substring(0, len);
    }

    private int search(Node x, String s, int d, int len) {
        if (x == null) return len;

        if (x.val != null) len = d;

        if (d == s.length()) return len;

        char c = s.charAt(d);

        return search(x.next[c], s, d+1, len);
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;

        if (d == key.length()) x.val = null;

        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if (x.val != null) return x;

        for (char c = 0; c < R; c++) if (x.next[c] != null) return x;

        return null;
    }
}
