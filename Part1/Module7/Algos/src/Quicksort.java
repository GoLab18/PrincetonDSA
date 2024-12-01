import edu.princeton.cs.algs4.StdRandom;

public class Quicksort {

    private static final int CUTOFF = 7;


    // When isThreeWay == true, quicksort uses three-way partitioning (optimized for high amount of duplicates),
    // otherwise two-way partitioning (can be ~N^2 in the worst case with duplicates)
    public static <T extends Comparable<T>> void sort(T[] a, boolean isThreeWay) {
        StdRandom.shuffle(a);

        if (isThreeWay) threeWaySort(a, 0, a.length - 1);
        else twoWaySort(a, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> void twoWaySort(T[] a, int lo, int hi) {

        // Efficiency improvement for smaller sub-arrays
        if (hi <= lo + CUTOFF - 1) {
            insertionSort(a, lo, hi);

            return;
        }

        int j = partition(a, lo, hi);

        twoWaySort(a, lo, j - 1);
        twoWaySort(a, j + 1, hi);
    }

    private static <T extends Comparable<T>> void threeWaySort(T[] a, int lo, int hi) {

        // Efficiency improvement for smaller sub-arrays
        if (hi <= lo + CUTOFF - 1) {
            insertionSort(a, lo, hi);

            return;
        }

        int lt = lo, gt = hi;

        // Partitioning item
        T v = a[lo];

        int i = lo;

        while (i <= gt) {
            int cmp = a[i].compareTo(v);

            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }

        threeWaySort(a, lo, lt - 1);
        threeWaySort(a, gt + 1, hi);
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

    private static <T extends Comparable<T>> void insertionSort(T[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            T key = a[i];
            int j = i - 1;

            while (j >= lo && less(key, a[j])) {
                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = key;
        }
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