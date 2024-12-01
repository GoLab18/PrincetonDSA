public class Heapsort {
    // Heapsort is done on 1-based indexing, although sorts arrays based on 0-based
    public static <T extends Comparable<T>> void sort(T[] pq) {
        int N = pq.length;

        // Arranging the heap array
        for (int k = N/2; k >= 1; k--) sink(pq, k, N);

        // Sorting in delete max style
        while (N > 1) {
            exch(pq, 1, N);
            sink(pq, 1, --N);
        }
    }

    private static <T extends Comparable<T>> void sink(T[] pq, int k, int N) {
        while (2*k <= N) {
            int j = 2*k;

            if (j < N && less(pq, j, j+1)) j++;
            if (!less(pq, k, j)) break;

            exch(pq, k, j);
            k = j;
        }
    }

    // Compares on actual items shifting indices one to the left
    // to account for 1-based indexing that heap based approach is based on
    private static <T extends Comparable<T>> boolean less(T[]a, int l, int r) {
        return a[l-1].compareTo(a[r-1]) < 0;
    }

    // Exchanges on actual indices shifting one to the left
    // to account for 1-based indexing that heap based approach is based on
    private static <T extends Comparable<T>> void exch(T[] a, int i, int j) {
        T temp = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = temp;
    }

    // Test
    public static void main(String[] args) {
        Integer[] array = {4, 12, 18, 5, 2, 9, 1};
        Heapsort.sort(array);

        for (int i = 1; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
