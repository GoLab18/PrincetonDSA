import java.util.Iterator;

public class LinkedListImplQueue<T> implements Iterable<T> {
    private Node first, last;

    private class Node {
        T item;
        Node next;
    }

    public void enqueue(T item) {
        Node oldLast = last;

        last = new Node();
        last.item = item;
        last.next = null;

        if(isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
    }

    public T dequeue() {
        T item = first.item;
        first = first.next;

        if (isEmpty()) last = null;

        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T> {
        private Node curr = first;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the queue");
            }

            T item = curr.item;
            curr = curr.next;

            return item;
        }
    }
}
