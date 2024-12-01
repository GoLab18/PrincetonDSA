import java.util.Arrays;

public class ArrayInversions {

    private static <T extends Comparable<T>> long merge(T[] a, T[] aux, int lo, int mid, int hi) {
        long inversions = 0;

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) aux[k] = a[j++];
            else if (j > hi) aux[k] = a[i++];
            else if (less(a[j], a[i])) {
                aux[k] = a[j++];
                inversions += (mid - i + 1);
            } else aux[k] = a[i++];
        }

        return inversions;
    }

    private static <T extends Comparable<T>> long sort(T[] a, T[] aux, int lo, int hi) {
        if (hi <= lo) return 0;

        int mid = lo + (hi - lo) / 2;

        long inversions = 0;

        inversions += sort(aux, a, lo, mid); // Inversions in the left half
        inversions += sort(aux, a, mid + 1, hi); // Inversions in the right half

        if (!less(a[mid + 1], a[mid])) return inversions;

        inversions += merge(a, aux, lo, mid, hi);

        return inversions;
    }

    public static <T extends Comparable<T>> long countInversions(T[] a) {
        T[] aux = Arrays.copyOf(a, a.length);

        return sort(a, aux, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> boolean less(T p, T q) {
        return p.compareTo(q) < 0;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 20, 6, 4, 5};
        long inversions = countInversions(array);
        System.out.println("Inversions num: " + inversions);
    }
}
