import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;

    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    private class Node {
        private Node next;
        private Node previous;

        private Item item;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node n = new Node();
        n.item = item;
        n.previous = null;
        n.next = first;

        first = n;

        if (last == null) last = first;

        if (first.next != null) first.next.previous = first;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node n = new Node();
        n.item = item;
        n.next = null;
        n.previous = last;

        last = n;

        if (first == null) first = last;

        if (last.previous != null) last.previous.next = last;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;

        if (first.next == null) {
            last = null;
        } else first.next.previous = null;

        first = first.next;
        size--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;

        if (last.previous == null) {
            first = null;
        } else last.previous.next = null;

        last = last.previous;
        size--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<Item> {

        private Node curr = first;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = curr.item;
            curr = curr.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        var dq = new Deque<String>();

        while (!StdIn.isEmpty()) {
            String vr = StdIn.readString();

            switch (vr) {

                // Adds to the front
                case "-af":
                    String p1 = StdIn.readString();
                    dq.addFirst(p1);

                    break;

                // Removes from the front
                case "-df":
                    if (!dq.isEmpty()) {
                        System.out.println(dq.removeFirst());
                    }

                    break;

                // Adds to the end
                case "-al":
                    String p2 = StdIn.readString();
                    dq.addLast(p2);

                    break;

                // Removes from the end
                case "-dl":
                    if (!dq.isEmpty()) {
                        System.out.println(dq.removeLast());
                    }

                    break;

                default:
                    break;
            }
        }

        System.out.println("Deque has" + dq.size() + " items left:\n");

        for (String v : dq) {
            System.out.println(v);
        }
    }
}