public class AmericanFlagSort {
    public static void sort(int[] a, int R) {
        int[] count = new int[R];

        for (int j : a) count[j]++;

        for (int i = 0, r = 0; i < a.length; r++) while (count[r]-- > 0) a[i++] = r;
    }

    public static void main(String[] args) {
        int[] arr = {3, 0, 2, 1, 4, 2, 1, 3};

        sort(arr, 5);

        System.out.print("Sorted array:");
        for (int v : arr) System.out.print(" " + v);
    }
}
