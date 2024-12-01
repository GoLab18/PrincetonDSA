public class Mergesort {

    private static final int CUTOFF = 7;

    // Both halves of the array a should be sorted
    private static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {

        // Not needed copy by including a role switch of a and aux in each recursive call
        // for (int k = lo; k <= hi; k++) aux[k] = a[k];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) aux[k] = a[j++];
            else if (j > hi) aux[k] = a[i++];
            else if (less(a[j], a[i])) aux[k] = a[j++];
            else aux[k] = a[i++];
        }
    }

    private static <T extends Comparable<T>> void sort(T[] a, T[] aux, int lo, int hi) {
        // Using insertion sort for small sub-arrays (more efficient)
        if (hi <= lo + CUTOFF - 1) {
            insertionSort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;

        sort(aux, a, lo, mid);
        sort(aux, a, mid+1, hi);

        // Stop if biggest item in the first half <= smallest item in the second half,
        // helps for partially-ordered arrays
        if (!less(a[mid+1], a[mid])) return;

        merge(a, aux, lo, mid, hi);
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

    public static <T extends Comparable<T>> void sort(T[] a) {
        T[] aux = (T[]) new Object[a.length];

        sort(a, aux, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> boolean less(T p, T q) {
        return p.compareTo(q) < 0;
    }
}
