//Search in a bitonic array. An array is bitonic if it is composed of an increasing sequence of integers
// followed immediately by a decreasing sequence of integers.
// Write a program that, given a bitonic array of n distinct integer values,
// determines whether a given integer is in the array.
//
//Standard version: Use ∼3lg(n) compares in the worst case.
//Signing bonus: Use ∼2lg(n) compares in the worst case
// (and prove that no algorithm can guarantee to perform fewer than ∼ 2lg(n)
//∼2lgn compares in the worst case).

public class BitonicArraySearch {

    public static int findPeak(int[] arr) {
        int left = 0, right = arr.length - 1;

        while (left < right) {
            // Safer version rather than classic (right + left) / 2 (memory variable bounds)
            int mid = left + (right - left) / 2;

            if (arr[mid] > arr[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        // Peak returned
        return left;
    }

    public static int searchBitonicArray(int[] arr, int target) {
        int peak = findPeak(arr);

        // Searching in the increasing part
        int index = binarySearch(arr, 0, peak, target, true);
        if (index != -1) return index;

        // Searching in the decreasing part
        return binarySearch(arr, peak + 1, arr.length - 1, target, false);
    }

    public static int binarySearch(int[] arr, int left, int right, int target, boolean increasing) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) return mid;

            if (increasing) {
                if (arr[mid] < target) left = mid + 1;
                else right = mid - 1;
            } else {
                if (arr[mid] > target) left = mid + 1;
                else right = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] bitonicArray = {1, 3, 8, 12, 4, 2};

        int target = 4;

        int result = searchBitonicArray(bitonicArray, target);

        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}
