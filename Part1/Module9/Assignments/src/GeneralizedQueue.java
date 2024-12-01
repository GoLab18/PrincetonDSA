import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class GeneralizedQueue<Value> {
    private TreeMap<Integer, Value> rbTree;
    private int nextIndex = 0;

    public GeneralizedQueue() {
        rbTree = new TreeMap<>();
    }

    // Returns the previous value associated
    public Value append(Value val) {
        return rbTree.put(nextIndex++, val);
    }

    public Value removeFront() {
        if (rbTree.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        int firstKey = rbTree.firstKey();
        Value firstItem = rbTree.get(firstKey);

        rbTree.remove(firstKey);

        return firstItem;
    }

    public Value getIthItem(int i) {
        if (i <= 0 || i > rbTree.size()) {
            throw new IndexOutOfBoundsException("Invalid ith key");
        }

        // For this method to work we'd need a Red Black BST with a size field
        // for each node to retrieve the proper key with the value for fetching.
    }

    public void removeIthItem(int i) {
        if (i <= 0 || i > rbTree.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        // For this method to work we'd need a Red Black BST with a size field
        // for each node to retrieve the proper key with the value for deletion.
    }

    // Debug helper method
    public void printQueue() {
        for (Map.Entry<Integer, Value> entry : rbTree.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}