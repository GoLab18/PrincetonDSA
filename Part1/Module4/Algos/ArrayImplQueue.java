import java.util.Iterator;

/// Based on circular action to use the underlying array to full extent.
public class ArrayImplQueue<T> implements Iterable<T> {
    private T[] q;
    private int front = 0;
    private int rear = 0;
    private int size = 0;

    public ArrayImplQueue() {
        q = (T[]) new Object[4];
    }

    public void enqueue(T item) {
        if (size == q.length) resize(2 * q.length);

        q[rear] = item;

        // Moving rear to the next index and wrapping around if necessary
        rear = (rear + 1) % q.length;

        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");

        T item = q[front];

        // Avoiding loitering
        q[front] = null;

        // Moving front to the next index, wrapping around if necessary
        front = (front + 1) % q.length;

        size--;

        // Resizing the queue if it's too sparse
        if (size > 0 && size == q.length / 4) resize(q.length / 2);

        return item;
    }

    // Resize the array to the given capacity
    private void resize(int capacity) {
        T[] newQueue = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            newQueue[i] = q[(front + i) % q.length];
        }

        q = newQueue;
        front = 0;
        rear = size;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T> {
        private int curr = front;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the queue");
            }

            T item = q[curr];

            curr = (curr + 1) % q.length;
            count++;

            return item;
        }
    }
}

