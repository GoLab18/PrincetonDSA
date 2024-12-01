public class Sort {

    // ~N^2/2
    public static void selectionSort(Comparable[] a) {
        int n = a.length;

        for (int i = 0; i < n; i++) {
            int min = i;

            for (int j = i + 1; j < n; j++) {
                if (less(a[j], a[min])) min = j;
            }

            exch(a, i, min);
        }
    }

    // ~N^2/4 compares + ~N^2/4 exchanges on average
    // ~N^2/2 compares + ~N^2/2 exchanges when input array sorted in descending order (worst case)
    // Only N - 1 compares if input array already sorted (best case)
    public static void insertionSort(Comparable[] a) {
        int n = a.length;

        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) exch(a, j, j - 1);
                else break;
            }
        }
    }

    // Fast unless array size is huge
    // Kinda a variation of an insertion sort
    public static void shellSort(Comparable[] a) {
        int n = a.length;

        int h = 1;
        while (h < n/3) h = 3*h + 1; // 3x + 1 sequence

        // H-sorting
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }

            h = h/3;
        }
    }

    private static boolean less(Comparable l, Comparable r) {
        return l.compareTo(r) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}