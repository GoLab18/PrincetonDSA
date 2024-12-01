import java.util.LinkedList;
import java.util.Queue;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private final Key key;
        private Value val;
        private int count; // Nodes num in the subtree
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    public Value get(Key key) {
        Node x = root;

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
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);

        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;

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

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;

        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    // Traverses left until the smallest node
    private Node min(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    // Hibbard deletion
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);

        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            // Replacing with successor
            Node n = x;
            x = min(n.right);
            x.right = deleteMin(n.right);
            x.left = n.left;
        }

        x.count = size(x.left) + size(x.right) + 1;

        return x;
    }

    // Morris inorder traversal test
    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<>();
        bst.put(50, "V1");
        bst.put(30, "V2");
        bst.put(70, "V3");
        bst.put(20, "V4");
        bst.put(40, "V5");
        bst.put(60, "V6");
        bst.put(80, "V7");

        System.out.println("Inorder Traversal:");
        bst.inorderMorrisTraversal();
    }
}