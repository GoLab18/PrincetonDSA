import java.util.Random;

public class RandPriorityQueue<Key extends Comparable<Key>> {
    private int N;
    private Key[] pq;
    private final Random rand;

    public RandPriorityQueue(int capacity) {
        // One more because it does not use position at index == 0
        pq = (Key[]) new Comparable[capacity + 1];
        rand = new Random();
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key key) {
        pq[++N] = key;
        swim(N);
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;

            if (j < N && less(j, j+1)) j++;
            if (!less(k, j)) break;

            exch(k, j);
            k = j;
        }
    }

    private Key sample() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");

        int randi = 1 + rand.nextInt(N);
        return pq[randi];
    }

    private Key delRandom() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");

        int randi = 1 + rand.nextInt(N);
        Key randKey = pq[randi];

        exch(randi, N--);

        // Restoring heap property
        if (randi <= N) {
            swim(randi);
            sink(randi);
        }

        // Loitering prevention
        pq[N+1] = null;

        return randKey;
    }

    public Key deleteMax() {
        Key max = pq[1];

        exch(1, N--);
        sink(1);

        // Loitering prevention
        pq[N+1] = null;

        return max;
    }

    // To implement min priority queue this helper function must become greater()
    private boolean less(int l, int r) {
        return pq[l].compareTo(pq[r]) < 0;
    }

    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }
}