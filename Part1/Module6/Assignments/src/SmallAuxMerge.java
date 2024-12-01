//Merging with smaller auxiliary array.
//Suppose that the sub-array a[0] to a[n-1] is sorted and the sub-array a[n] to a[2*n-1] is sorted.
//How can you merge the two sub-arrays so that a[0] to a[2*n-1] is sorted
//using an auxiliary array of size n (instead of 2n)?

import java.util.Arrays;

public class SmallAuxMerge {
    public static void merge(Comparable[] a, Comparable[] aux, int n) {
        for (int i = 0; i < n; i++) aux[i] = a[i];

        // Indices of aux and right side of a respectively
        int i = 0, j = n;

        // For loop over merged array (final version of a)
        for (int k = 0; k < a.length; k++) {
            // If the auxiliary array has been exhausted, copying from the right half of a
            if (i >= n) {
                a[k] = a[j++];
            }

            // If the right half of a has been exhausted, copying from the auxiliary array
            else if (j >= a.length) {
                a[k] = aux[i++];
            }

            // If the current element in aux is smaller, copying it to a
            else if (aux[i].compareTo(a[j]) < 0) {
                a[k] = aux[i++];
            }

            // Otherwise, copying the element from the right part of a
            else a[k] = a[j++];
        }
    }

    public static void main(String[] args) {
        Comparable[] a = {40, 61, 70, 71, 99, 20, 51, 55, 75, 100};
        int n = a.length / 2;
        Comparable[] aux = new Comparable[n];

        SmallAuxMerge.merge(a, aux, n);

        System.out.println("Merge result:");
        Arrays.stream(a).forEach((c) -> System.out.print(c + ","));
    }
}
