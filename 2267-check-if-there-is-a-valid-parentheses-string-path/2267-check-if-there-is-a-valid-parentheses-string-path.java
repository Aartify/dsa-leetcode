class Solution {
    public boolean hasValidPath(char[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // Valid parentheses string must have even length
        if ((m + n - 1) % 2 == 1) {
            return false;
        }

        // dp[i][j][balance]
        boolean[][][] dp = new boolean[m][n][m + n];

        // Starting cell must be '('
        if (grid[0][0] == ')') {
            return false;
        }

        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Skip starting cell
                if (i == 0 && j == 0) {
                    continue;
                }

                for (int balance = 0; balance < m + n; balance++) {

                    // Get balance from current cell
                    int newBalance;

                    if (grid[i][j] == '(') {
                        newBalance = balance + 1;
                    } else {
                        newBalance = balance - 1;
                    }

                    // Balance cannot be negative
                    if (newBalance < 0 || newBalance >= m + n) {
                        continue;
                    }

                    // Come from above
                    if (i > 0 && dp[i - 1][j][balance]) {
                        dp[i][j][newBalance] = true;
                    }

                    // Come from left
                    if (j > 0 && dp[i][j - 1][balance]) {
                        dp[i][j][newBalance] = true;
                    }
                }
            }
        }

        // Valid string must finish with balance 0
        return dp[m - 1][n - 1][0];
    }
}