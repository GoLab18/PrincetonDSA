import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());

        int k = Integer.parseInt(args[0]);

        if (k < 0 || k > rq.size())
            throw new IllegalArgumentException();

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}
