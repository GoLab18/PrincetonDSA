import java.util.AbstractMap;

// Hashset approach easier but not as interesting =)
public class RadixTwoSum {
    private static final int MSB = 64;

    public static AbstractMap.SimpleImmutableEntry<Long, Long> twoSum(long[] a, long T) {
        radixSort(a, MSB, 0, a.length - 1);

        int i = 0, j = a.length - 1;
        while (i < j) {
            long t = a[i] + a[j];

            if (t < T) i++;
            else if (t > T) j--;
            else return new AbstractMap.SimpleImmutableEntry<>(a[i], a[j]);
        }

        return null;
    }

    private static void radixSort(long[] a, int bt, int lo, int hi) {
        if (hi <= lo || bt < 1) return;

        int left = lo - 1, right = hi + 1;

        int i = lo;
        while (i < right) {
            if (isBitSetSigned(a[i], bt)) exch(a, i, --right);
            else {
                left++;
                i++;
            }
        }

        radixSort(a, bt - 1, lo, left);
        radixSort(a, bt - 1, right, hi);
    }

    private static boolean isBitSetSigned(long val, int bt) {
        return (val & 1L << (bt - 1)) != 0 ^ (bt == MSB);
    }

    private static void exch(long[] a, int i, int j) {
        long tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        long[] a = {10, 30, 50, 40, 80, 120, 90};
        long T = 70;

        var r = twoSum(a, T);

        if (r != null) System.out.println("Solution: " + r.getKey() + " + " + r.getValue() + " = " + T);
        else System.out.println("Solution not found");
    }
}
