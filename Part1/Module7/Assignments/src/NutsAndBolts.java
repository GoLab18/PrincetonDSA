import edu.princeton.cs.algs4.StdRandom;

// Using quicksort with two-way partitioning (unique nut and bolt pairs)
public class NutsAndBolts {
    public static <T extends Comparable<T>> void sort(T[] nuts, T[] bolts) {
        StdRandom.shuffle(nuts);
        StdRandom.shuffle(bolts);

        sort(nuts, bolts , 0, nuts.length - 1);
    }

    private static <T extends Comparable<T>> void sort(T[] nuts, T[] bolts, int lo, int hi) {
        if (lo >= hi) return;

        // Partitioning nuts using a bolt as a pivot
        int pivotIndex = partition(nuts, lo, hi, bolts[lo]);

        // Partitioning bolts using a nut as a pivot
        partition(bolts, lo, hi, nuts[pivotIndex]);

        sort(nuts, bolts, lo, pivotIndex - 1);
        sort(nuts, bolts, pivotIndex + 1, hi);
    }

    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi, T pivot) {
        int i = lo, j = hi;

        while (i <= j) {
            while (a[i].compareTo(pivot) < 0) i++;
            while (a[j].compareTo(pivot) > 0) j--;

            if (i <= j) {
                exch(a, i, j);
                i++;
                j--;
            }
        }

        // Returning the final index of the pivot
        return i - 1;
    }

    private static <T extends Comparable<T>> void exch(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Test
    public static void main(String[] args) {
        Integer[] nuts = {1, 4, 3, 2};
        Integer[] bolts = {4, 2, 1, 3};

        System.out.println("Before matching:");
        System.out.println("Nuts: " + java.util.Arrays.toString(nuts));
        System.out.println("Bolts: " + java.util.Arrays.toString(bolts));

        sort(nuts, bolts);

        System.out.println("\nAfter matching:");
        System.out.println("Nuts: " + java.util.Arrays.toString(nuts));
        System.out.println("Bolts: " + java.util.Arrays.toString(bolts));
    }
}