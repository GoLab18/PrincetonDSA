import java.util.Arrays;

public class PrefixFreeCodes {
    private static final int R = 2;
    private final Node root = new Node();

    private static class Node {
        private String val;
        private final Node[] next = new Node[R];
    }

    public boolean isPrefixFree(String[] binStrings) {
        for (String s : binStrings) {

            boolean noNewNodesCreated = true;
            Node curr = root;
            int d = 1;
            for (char c : s.toCharArray()) {
                int binVal = (c == '0') ? 0 : 1;

                boolean exists = curr.next[binVal] != null;

                if (!exists) {
                    curr.next[binVal] = new Node();
                    noNewNodesCreated = false;
                }

                if (noNewNodesCreated && (d == s.length() || curr.next[binVal].val != null)) return false;

                if (d == s.length()) curr.next[binVal].val = s;

                curr = curr.next[binVal];
                d++;
            }
        }

        return true;
    }

    // Test
    public static void main(String[] args) {
        var pc1 = new PrefixFreeCodes();
        String[] binStrSet1 = {"01", "10", "0010", "1111"};

        var pc2 = new PrefixFreeCodes();
        String[] binStrSet2 = {"01", "10", "0010", "10100"};

        System.out.println("Set: " + Arrays.toString(binStrSet1));
        System.out.println("Is prefix-free? -> " + pc1.isPrefixFree(binStrSet1));

        System.out.println("Set: " + Arrays.toString(binStrSet2));
        System.out.println("Is prefix-free? -> " + pc2.isPrefixFree(binStrSet2));
    }
}
