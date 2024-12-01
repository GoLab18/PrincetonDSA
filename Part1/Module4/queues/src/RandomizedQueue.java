import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] dynamicArray;

    private int frontIndex;
    private int endIndex;

    private int size;

    // construct an empty randomized queue with default capacity of 4
    public RandomizedQueue() {
        dynamicArray = (Item[]) new Object[4];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (size == dynamicArray.length || endIndex == dynamicArray.length - 1) {
            resizeArray(2 * dynamicArray.length);
        }

        dynamicArray[endIndex++ + 1] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = null;
        int itemIndex = 0;

        while (item == null) {
            item = dynamicArray[StdRandom.uniformInt(frontIndex, endIndex + 1)];
        }

        dynamicArray[itemIndex] = null;
        size--;

        if (size > 0) {
            // Adjusting dynamic array capacity if needed to keep it optimized
            if (size == dynamicArray.length / 4) {
                resizeArray(dynamicArray.length / 2);

            // Adjusting frontIndex if needed
            } else if (frontIndex == itemIndex) {
                while (dynamicArray[frontIndex] == null) frontIndex++;

            // Adjusting endIndex if needed
            } else if (endIndex == itemIndex) {
                do {
                    endIndex--;
                } while (dynamicArray[endIndex] == null);
            }
        } else if (size == 0) {
            frontIndex = 0;
            endIndex = 0;
        }

        return item;
    }

    // Resizes the dynamic array to the given capacity
    private void resizeArray(int capacity) {
        Item[] cp = (Item[]) new Object[capacity];

        int j = 0;

        for (int i = 0; i < size; i++) {
            if (dynamicArray[i] != null) {
                cp[j] = dynamicArray[i];

                j++;
            }
        }

        frontIndex = 0;
        endIndex = size - 1;
        dynamicArray = cp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = null;

        while (item == null) {
            item = dynamicArray[StdRandom.uniformInt(frontIndex, endIndex + 1)];
        }

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int currIndex;
        private final Item[] shuffledArray;

        public RandomizedQueueIterator() {
            shuffledArray = (Item[]) new Object[size];

            int j = 0;

            for (int i = 0; i < size; i++) {
                if (dynamicArray[i] != null) {
                    shuffledArray[j] = dynamicArray[i];

                    j++;
                }
            }

            arrayShuffle(shuffledArray);
        }

        // Fisher-Yates Shuffle Implementation
        private void arrayShuffle(Item[] arr) {
            for (int i = arr.length - 1; i > 0; i--) {
                int index = StdRandom.uniformInt(i + 1);

                // Swapping
                Item temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
            }
        }

        @Override
        public boolean hasNext() {
            return currIndex < shuffledArray.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return shuffledArray[currIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        var rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String vr = StdIn.readString();

            switch (vr) {

                // Enqueues
                case "-a":
                    String p1 = StdIn.readString();
                    rq.enqueue(p1);

                    break;

                // Returns a random sample
                case "-df":
                    if (!rq.isEmpty())
                        System.out.println(rq.sample());

                    break;

                // Dequeues
                case "-al":
                    if (!rq.isEmpty())
                        System.out.println(rq.dequeue());

                    break;

                default:
                    break;
            }
        }

        System.out.println("Randomized queue has" + rq.size() + " items left:\n");

        for (String v : rq) {
            System.out.println(v);
        }
    }
}