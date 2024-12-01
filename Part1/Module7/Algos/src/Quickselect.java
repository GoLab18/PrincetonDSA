import edu.princeton.cs.algs4.StdRandom;

public class Quickselect {

    public static <T extends Comparable<T>> T sort(T[] a, int k) {
        StdRandom.shuffle(a);

        int lo = 0, hi = a.length - 1;

        while (hi > lo) {
            int j = partition(a, lo, hi);

            if (k > j) lo = j + 1;
            else if (k < j) hi = j + 1;
            else return a[k];
        }

        return a[k];
    }

    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
        int i = lo, j = hi + 1;

        while (true) {
            while (less(a[++i], a[lo])) {
                if (i == hi) break;
            }

            while (less(a[lo], a[--j])) {
                if (j == lo) break; // Redundant (?)
            }

            if (i >= j) break;

            exch(a, i, j);
        }

        exch(a, lo, j);

        return j;
    }

    private static <T extends Comparable<T>> boolean less(T l, T r) {
        return l.compareTo(r) < 0;
    }

    private static <T extends Comparable<T>> void exch(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
