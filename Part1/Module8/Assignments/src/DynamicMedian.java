import java.util.PriorityQueue;
import java.util.Comparator;

public class DynamicMedian {
    private final PriorityQueue<Integer> minHeap; // Upper half
    private final PriorityQueue<Integer> maxHeap; // Lower half

    public DynamicMedian() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }

    public void insert(int num) {
        // Adding to the max-heap
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) maxHeap.offer(num);

        // Adding to the min-heap
        else minHeap.offer(num);

        // Rebalancing
        if (maxHeap.size() > minHeap.size() + 1) minHeap.offer(maxHeap.poll());
        else if (maxHeap.size() < minHeap.size()) maxHeap.offer(minHeap.poll());
    }

    // The median is always at the root of the max-heap
    public Integer findMedian() {
        return maxHeap.peek();
    }

    public void removeMedian() {
        if (!maxHeap.isEmpty()) {
            // Removing the median
            maxHeap.poll();

            // Rebalancing
            if (maxHeap.size() < minHeap.size()) maxHeap.offer(minHeap.poll());
        }
    }

    // Test
    public static void main(String[] args) {
        DynamicMedian dm = new DynamicMedian();

        dm.insert(5);
        System.out.println("Median: " + dm.findMedian()); // Median: 5
        dm.insert(2);
        System.out.println("Median: " + dm.findMedian()); // Median: 2
        dm.insert(8);
        System.out.println("Median: " + dm.findMedian()); // Median: 5
        dm.insert(10);
        System.out.println("Median: " + dm.findMedian()); // Median: 5
        dm.removeMedian();
        System.out.println("Median: " + dm.findMedian()); // Median: 8
    }
}
