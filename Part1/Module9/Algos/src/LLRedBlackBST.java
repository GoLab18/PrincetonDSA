import java.util.LinkedList;
import java.util.Queue;

public class LLRedBlackBST<Key extends Comparable<Key>, Value> {
    private Node root;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private Key key;
        private Value val;
        private int count; // Nodes num in the subtree
        private Node left, right;
        boolean color;

        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    public Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }

        return null;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, RED);
        int cmp = key.compareTo(x.key);

        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;

        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);

        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);

        Node t = floor(x.right, key);
        return t != null ? t : x;
    }

    // Returns the amount of keys < the specified key
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;

        int cmp = key.compareTo(x.key);

        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    private boolean isRed(Node x) {
        if (x == null) return false; // Null links -> black
        return x.color == RED;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;

        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;

        return x;
    }

    // Keys traversal, O(n) time and 0(keys amount) space
    public Iterable<Key> keys() {
        Queue<Key> q = new LinkedList<Key>();
        inorder(q, root);
        return q;
    }

    // Morris Inorder Traversal, O(n) time and 0(1) space
    public void inorderMorrisTraversal() {
        Node curr = root;

        while (curr != null) {
            if (curr.left == null) {
                System.out.print(curr.key + " ");
                curr = curr.right;
            } else {
                // Finding the inorder predecessor
                Node predecessor = curr.left;

                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                // Thread to the current node if it doesn't already exist
                if (predecessor.right == null) {
                    predecessor.right = curr; // Thread creation
                    curr = curr.left;
                } else {
                    // Thread already exists -> left subtree already visited
                    predecessor.right = null; // Removing the thread
                    System.out.print(curr.key + " ");
                    curr = curr.right;
                }
            }
        }
    }

    private void inorder(Queue<Key> q, Node x) {
        if (x == null) return;
        inorder(q, x.left);
        q.add(x.key);
        inorder(q, x.right);
    }

    private Node moveRedLeft(Node x) {
        flipColors(x);

        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
        }

        return x;
    }

    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return null;
        if (!isRed(x.left) && !isRed(x.left.left)) x = moveRedLeft(x);
        x.left = deleteMin(x.left);

        return balance(x);
    }

    private Node moveRedRight(Node x) {
        flipColors(x);
        if (!isRed(x.left.left)) x = rotateRight(x);
        return x;
    }

    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node x) {
        if (isRed(x.left)) x = rotateRight(x);
        if (x.right == null) return null;
        if (!isRed(x.right) && !isRed(x.right.left)) x = moveRedRight(x);
        x.right = deleteMax(x.right);

        return balance(x);
    }

    // Traverses left until the smallest node
    private Node min(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    private boolean isEmpty() {
        return root == null;
    }

    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node x, Key key) {
        if (key.compareTo(x.key) < 0) {
            if (!isRed(x.left) && !isRed(x.left.left)) x = moveRedLeft(x);
            x.left = delete(x.left, key);
        } else {
            if (isRed(x.left)) x = rotateRight(x);

            if (key.compareTo(x.key) == 0 && (x.right == null)) return null;
            if (!isRed(x.right) && !isRed(x.right.left)) x = moveRedRight(x);

            if (key.compareTo(x.key) == 0) {
                x.val = get(x.right, min(x.right).key);
                x.key = min(x.right).key;
                x.right = deleteMin(x.right);
            }

            else x.right = delete(x.right, key);
        }

        return balance(x);
    }
}