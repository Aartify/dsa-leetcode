class Solution {
    public int distinctSubseqII(String s) {

        int MOD = 1000000007;
        int n = s.length();

        // dp[i] = number of distinct subsequences
        // using first i characters
        long[] dp = new long[n + 1];

        dp[0] = 1; // empty subsequence

        // Last occurrence of each character
        int[] last = new int[26];

        // 0 means character has never appeared
        for (int i = 0; i < 26; i++) {
            last[i] = 0;
        }

        for (int i = 1; i <= n; i++) {

            char c = s.charAt(i - 1);
            int index = c - 'a';

            // Initially double the number of subsequences
            dp[i] = (2 * dp[i - 1]) % MOD;

            // If this character appeared before,
            // remove duplicate subsequences
            if (last[index] != 0) {
                dp[i] = (dp[i] - dp[last[index] - 1] + MOD) % MOD;
            }

            // Store current position
            last[index] = i;
        }

        // Remove empty subsequence
        return (int) ((dp[n] - 1 + MOD) % MOD);
    }
}