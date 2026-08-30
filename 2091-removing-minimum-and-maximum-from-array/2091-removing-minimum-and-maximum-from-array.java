class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIndex = 0;
        int maxIndex = 0;

        // Step 1: Find min and max element indices
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Step 2: Ensure left is the smaller index and right is the larger index
        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        // Step 3: Compute min deletions across 3 strategies
        int frontOnly = right + 1;
        int backOnly = n - left;
        int bothEnds = (left + 1) + (n - right);

        return Math.min(frontOnly, Math.min(backOnly, bothEnds));
    }
}