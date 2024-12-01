import java.util.Iterator;

public class ArrayImplStack<T> implements Iterable<T> {
    private T[] s;
    private int size = 0;

    public ArrayImplStack(int capacity) {
        s = (T[]) new Object[4];
    }

    public void push(T item) {
        if (size == s.length) resize(2 * s.length);

        s[size++] = item;
    }

    private void resize(int capacity) {
        T[] n = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            n[i] = s[i];
        }

        s = n;
    }

    public T pop() {
        T item = s[--size];
        s[size] = null;

        if (size > 0 && size == s.length/4) resize(s.length/2);

        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {
        private int i = size;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the stack");
            }

            return s[--i];
        }
    }
}
