//Stack with max. Create a data structure that efficiently supports the stack operations (push and pop)
// and also a return-the-maximum operation. Assume the elements are real numbers so that you can compare them.

import java.util.Stack;

class MaxStack {
    private Stack<Double> stack;      // Main stack
    private Stack<Double> maxStack;   // Stack that stores the maximums at each level

    public MaxStack() {
        stack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(double x) {
        stack.push(x);

        if (maxStack.isEmpty()) {
            maxStack.push(x);
        } else {
            maxStack.push(Math.max(x, maxStack.peek()));
        }
    }

    // Pop an element from the stack
    public double pop() {
        if (stack.isEmpty()) {
            throw new IndexOutOfBoundsException("Pop invoked for an empty stack");
        }

        maxStack.pop();
        return stack.pop();
    }

    public double getMax() {
        if (maxStack.isEmpty()) {
            throw new IndexOutOfBoundsException("Invoked getMax() on an empty stack");
        }

        return maxStack.peek();
    }

    // Test
    public static void main(String[] args) {
        MaxStack stack = new MaxStack();

        stack.push(3);
        stack.push(1);
        stack.push(5);

        // Output: 5.0
        System.out.println("Current max: " + stack.getMax());

        stack.pop();

        // Output: 3.0
        System.out.println("Max after pop: " + stack.getMax());

        stack.push(4);

        // Expected output: 4.0
        System.out.println("Max after pushing 4: " + stack.getMax());
    }
}

