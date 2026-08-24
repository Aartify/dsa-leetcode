class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        long[] prefix = new long[n];

        // Calculate prefix sums
        prefix[0] = stones[0];

        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }

        // Alice can take all stones as the first move
        long ans = prefix[n - 1];

        // Try every possible point where the first move can end
        for (int i = n - 2; i >= 1; i--) {
            ans = Math.max(ans, prefix[i] - ans);
        }

        return (int) ans;
    }
}