// KMP approach based on LPS -> less memory and initialization heavy
public class TandemRepeat {
    private final String base;
    private final int[] lps;

    public TandemRepeat(String base) {
        this.base = base;
        lps = new int[base.length()];

        int prefixLenPtr = 0;
        for (int i = 1; i < base.length(); i++) {
            char c = base.charAt(i);

            while (prefixLenPtr > 0 && base.charAt(prefixLenPtr) != c) prefixLenPtr = lps[prefixLenPtr - 1]; // Rollback

            if (base.charAt(prefixLenPtr) == c) lps[i] = ++prefixLenPtr;
        }
    }

    public String findMaxTanRep(String s) {
        int tanRepStartIdx = 0, maxLen = 0;

        for (int i = 0, baseIdx = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            while (baseIdx > 0 && base.charAt(baseIdx) != c) baseIdx = lps[baseIdx - 1];

            if (base.charAt(baseIdx) == c) {
                if (baseIdx == base.length() - 1) {
                    int start = i - baseIdx;

                    int repCount = 1;
                    while (i + 1 < s.length() && s.startsWith(base, i + 1)) {
                        repCount++;
                        i += base.length();
                    }

                    int currLen = repCount * base.length();
                    if (currLen > maxLen) {
                        tanRepStartIdx = start;
                        maxLen = currLen;
                    }

                    baseIdx = lps[baseIdx];
                }

                else baseIdx++;
            }
        }

        return s.substring(tanRepStartIdx, tanRepStartIdx + maxLen);
    }

    // Test
    public static void main(String[] args) {
        String b = "abcab";
        String s1 = "abcabcababcaba", s2 = "abcabcaabbcababcababcababcababccaabac";

        TandemRepeat tr = new TandemRepeat(b);
        System.out.println("Longest tandem repeat for `" + s1 + "` -> " + tr.findMaxTanRep(s1));
        System.out.println("Longest tandem repeat for `" + s2 + "` -> " + tr.findMaxTanRep(s2));
    }
}
