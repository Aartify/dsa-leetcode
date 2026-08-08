import java.util.Arrays;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // last[j] stores the maximum index in word1 such that
        // the suffix word2[j...m-1] can be formed validly.
        int[] last = new int[m];
        Arrays.fill(last, -1);

        int i = n - 1;
        int j = m - 1;

        // Precompute rightmost possible indices for matching word2 suffix
        while (i >= 0 && j >= 0) {
            if (word1.charAt(i) == word2.charAt(j)) {
                last[j] = i;
                j--;
            }
            i--;
        }

        int[] result = new int[m];
        boolean canSkip = true;
        j = 0;

        // Left-to-right greedy match to ensure lexicographically smallest indices
        for (i = 0; i < n; i++) {
            if (j == m) break;

            if (word1.charAt(i) == word2.charAt(j)) {
                result[j++] = i;
            } else if (canSkip && (j == m - 1 || i < last[j + 1])) {
                // Take mismatch greedily at the earliest possible index
                canSkip = false;
                result[j++] = i;
            }
        }

        return j == m ? result : new int[0];
    }
}