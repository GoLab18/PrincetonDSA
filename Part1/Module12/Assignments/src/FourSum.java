import java.util.ArrayList;
import java.util.HashMap;

public class FourSum {

    public static boolean fourSum(int[] arr) {
        int n = arr.length;

        // HashMap to store sums of pairs and their indices
        HashMap<Integer, ArrayList<int[]>> sumMap = new HashMap<>();

        // Iterating over all pairs of (i, j)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = arr[i] + arr[j];

                if (sumMap.containsKey(sum)) {
                    // Checking all previous pairs (k, l) for this sum
                    for (int[] pair : sumMap.get(sum)) {
                        int k = pair[0];
                        int l = pair[1];

                        // Ensuring that indices i, j, k, l are distinct
                        if (i != k && i != l && j != k && j != l) {
                            System.out.println("Quadruple found: ("
                                    + arr[i] + ", " + arr[j] + ", " + arr[k] + ", " + arr[l] + ")");

                            return true;
                        }
                    }
                }

                sumMap.putIfAbsent(sum, new ArrayList<>());
                sumMap.get(sum).add(new int[]{i, j});
            }
        }

        return false;
    }

    // Test
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6};
        if (!fourSum(a)) {
            System.out.println("No quadruple found.");
        }
    }
}
