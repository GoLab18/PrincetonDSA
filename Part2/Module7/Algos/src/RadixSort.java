// Ascii version, obviously very inefficient for Unicode (for ascii also not the best considering
// how many count arrays are there to be made). Useless for small arrays (just use Insertion Sort lol).
public class RadixSort {
    public static void SortLSD(String[] a, int w) {
        int r = 256; int n = a.length;
        String[] aux = new String[n];

        for (int d = w-1; d >= 0; d--) {
            int[] count = new int[r+1];

            // Key-indexed counting sort
            for (String s : a) count[s.charAt(d) + 1]++;

            for (int i = 0; i < r; i++) count[i+1] += count[i];

            for (String s : a) aux[count[s.charAt(d)]++] = s;

            System.arraycopy(aux, 0, a, 0, n);
        }
    }

    public static void sortMSD(String[] a, int w) {
        String[] aux = new String[a.length];
        sortMSD(a, aux, 0, a.length-1, 0);
    }

    private static void sortMSD(String[] a, String[] aux, int lo, int hi, int d) {
        if (hi <= lo) return;

        int r = 256;
        int[] count = new int[r+2];

        // Key-indexed counting sort approach
        for (int i = lo; i <= hi; i++) count[charAt(a[i], d) + 2]++;

        for (int i = 0; i < r+1; i++) count[i+1] += count[i];

        for (int i = lo; i <= hi; i++) aux[count[charAt(a[i], d) + 1]++] = a[i];

        for (int i = lo; i <= hi; i++) a[i] = aux[i - lo];

        for (int i = 0; i < r; i++) sortMSD(a, aux, lo + count[i], lo + count[i+1] - 1, d+1);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }
}
