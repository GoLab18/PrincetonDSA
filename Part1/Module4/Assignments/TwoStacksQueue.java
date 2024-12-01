//Queue with two stacks. Implement a queue with two stacks so that each queue operation
// takes a constant amortized number of stack operations.

import java.util.Stack;

public class TwoStacksQueue {
    private Stack<Integer> s1; // Base stack for enqueue operations
    private Stack<Integer> s2; // Holds values in reverse for dequeue operations

    public TwoStacksQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    public void enqueue(int value) {
        s1.push(value);
    }

    // Pops from s2, transfers if needed
    public int dequeue() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }

        // If s2 still empty, the queue is empty
        if (s2.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        return s2.pop();
    }

    public boolean isEmpty() {
        return s1.isEmpty() && s2.isEmpty();
    }

    // Peeks at the front of the queue, transfers if needed
    public int peek() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }

        if (s2.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        return s2.peek();
    }

    public static void main(String[] args) {
        TwoStacksQueue queue = new TwoStacksQueue();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        System.out.println(queue.dequeue()); // 1
        System.out.println(queue.dequeue()); // 2

        queue.enqueue(4);

        System.out.println(queue.peek()); // 3
        System.out.println(queue.dequeue()); // 3
        System.out.println(queue.dequeue()); // 4
    }
}
