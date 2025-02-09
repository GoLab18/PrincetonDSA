public class CircularSuffixArray {
    private final int[] index;
    private final String text;
    private final int n;

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("Input string can't be null");

        this.text = s;
        this.n = s.length();
        this.index = new int[n];

        for (int i = 0; i < n; i++) index[i] = i;

        radixQuickSort(index, 0, n - 1, 0);
    }

    public int length() {
        return n;
    }

    public int index(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException("Index out of bounds");
        return index[i];
    }

    private void radixQuickSort(int[] a, int lo, int hi, int d) {
        if (hi <= lo) return;

        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;

        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exchange(a, lt++, i++);
            else if (t > v) exchange(a, i, gt--);
            else i++;
        }

        radixQuickSort(a, lo, lt - 1, d);
        if (v >= 0) radixQuickSort(a, lt, gt, d + 1);
        radixQuickSort(a, gt + 1, hi, d);
    }

    private int charAt(int suffixIndex, int d) {
        if (d < n) return text.charAt((suffixIndex + d) % n);
        return -1;
    }

    private void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Test
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);

        System.out.printf("%-2s  %-23s  %-23s  %-2s  %s\n", "i", "Original Suffixes", "Sorted Suffixes", "t", "index[i]");
        System.out.println("--  ---------------  ---------------  --  --------");

        String[] ogSuff = new String[s.length()];
        String[] sortedSuff = new String[s.length()];

        for (int i = 0; i < s.length(); i++) ogSuff[i] = s.substring(i) + s.substring(0, i);
        for (int i = 0; i < s.length(); i++) sortedSuff[i] = ogSuff[csa.index(i)];

        for (int i = 0; i < s.length(); i++) {
            System.out.printf("%-2d  %-23s  %-23s  %-2d  %d\n", i, ogSuff[i], sortedSuff[i], i, csa.index(i));
        }
    }
}
