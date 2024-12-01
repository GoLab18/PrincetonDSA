import java.util.Iterator;

public class LinkedListImplStack<T> implements Iterable<T> {
    private Node main = null;

    private class Node {
        T item;
        Node next;
    }

    public void push(T item) {
        Node oldMain = main;

        main = new Node();
        main.item = item;
        main.next = oldMain;
    }

    public T pop() {
        T item = main.item;
        main = main.next;
        return item;
    }

    public boolean isEmpty() {
        return main == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {
        private Node curr = main;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the stack");
            }

            T item = curr.item;
            curr = curr.next;

            return item;
        }
    }
}
