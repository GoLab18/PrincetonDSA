public class TwoSortedArraysSelection {

    // Binary search approach
    public static int findKthElement(int[] a, int[] b, int k) {

        // Ensuring a is the smaller array
        if (a.length > b.length) {
            return findKthElement(b, a, k);
        }

        int n1 = a.length, n2 = b.length;
        int low = 0, high = n1;

        while (low <= high) {
            int partitionA = low + (high - low) / 2;
            int partitionB = k - partitionA;

            int maxLeftA = (partitionA == 0) ? Integer.MIN_VALUE : a[partitionA - 1];
            int minRightA = (partitionA == n1) ? Integer.MAX_VALUE : a[partitionA];

            int maxLeftB = (partitionB == 0) ? Integer.MIN_VALUE : b[partitionB - 1];
            int minRightB = (partitionB == n2) ? Integer.MAX_VALUE : b[partitionB];

            if (maxLeftA <= minRightB && maxLeftB <= minRightA) {
                // Found the correct partition
                return Math.max(maxLeftA, maxLeftB);
            } else if (maxLeftA > minRightB) {
                // PartitionA moved to the left
                high = partitionA - 1;
            } else {
                // PartitionA moved to the right
                low = partitionA + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 8};
        int[] b = {7, 9, 10, 11};

        int k = 5;
        System.out.println("The " + k + "-th smallest element is: " + findKthElement(a, b, k));
    }
}
