import java.util.Arrays;

public class ThreeSum {

    public static void findThreeSum(int[] arr) {
        Arrays.sort(arr); // Sorting the array first, O(n log n)

        int n = arr.length;

        for (int i = 0; i < n - 2; i++) {
            // Avoiding duplicates
            if (i > 0 && arr[i] == arr[i - 1]) continue;

            int target = -arr[i];

            // Leftmost (after i) index
            int left = i + 1;

            // Rightmost (before n) index
            int right = n - 1;

            while (left < right) {
                int sum = arr[left] + arr[right];
                if (sum == target) {
                    System.out.println(arr[i] + ", " + arr[left] + ", " + arr[right]);
                    left++;
                    right--;

                    // Skipping duplicates
                    while (left < right && arr[left] == arr[left - 1]) left++;
                    while (left < right && arr[right] == arr[right + 1]) right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {-1, 0, 1, 2, -1, -4};

        findThreeSum(arr);
    }
}

