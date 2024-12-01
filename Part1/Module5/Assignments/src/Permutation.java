//Question 2
//Permutation. Given two integer arrays of size n, design a sub-quadratic algorithm to determine
// whether one is a permutation of the other. That is, do they contain exactly the same entries but,
// possibly, in a different order.

import java.util.HashMap;

public class Permutation {
    public static boolean arePermutations(int[] a, int[] b) {
        // If lengths not the same, they can't be permutations
        if (a.length != b.length) {
            return false;
        }

        HashMap<Integer, Integer> freq = new HashMap<>();

        for (int v : a) {
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }

        // Checking the second array against the freq HashMap
        for (int v : b) {
            if (!freq.containsKey(v)) {
                return false;
            }

            freq.put(v, freq.get(v) - 1);

            if (freq.get(v) == 0) freq.remove(v);
        }

        return freq.isEmpty();
    }

    // Test
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 2};
        int[] b = {4, 3, 2, 1};

        System.out.println("Are a and b permutations of each other? "
                + (arePermutations(a, b) ? "Yes" : "No"));

        int[] c = {1, 2, 3, 4};
        int[] d = {4, 3, 2, 1};

        System.out.println("Are c and d permutations of each other? "
                + (arePermutations(c, d) ? "Yes" : "No"));
    }
}
