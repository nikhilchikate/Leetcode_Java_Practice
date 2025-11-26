/*
You are given a 0-indexed m x n integer matrix grid and an integer k. You are currently at position (0, 0) and you want to reach position (m - 1, n - 1) moving only down or right.

Return the number of paths where the sum of the elements on the path is divisible by k. Since the answer may be very large, return it modulo 109 + 7.



Example 1:


Input: grid = [[5,2,4],[3,0,5],[0,7,2]], k = 3
Output: 2
Explanation: There are two paths where the sum of the elements on the path is divisible by k.
The first path highlighted in red has a sum of 5 + 2 + 4 + 5 + 2 = 18 which is divisible by 3.
The second path highlighted in blue has a sum of 5 + 3 + 0 + 5 + 2 = 15 which is divisible by 3.
Example 2:


Input: grid = [[0,0]], k = 5
Output: 1
Explanation: The path highlighted in red has a sum of 0 + 0 = 0 which is divisible by 5.
Example 3:


Input: grid = [[7,3,4,9],[2,3,6,2],[2,3,7,0]], k = 1
Output: 10
Explanation: Every integer is divisible by 1 so the sum of the elements on every possible path is divisible by k.


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 5 * 104
1 <= m * n <= 5 * 104
0 <= grid[i][j] <= 100
1 <= k <= 50
*/

class Solution {
    private static final int MOD = (int) 1e9 + 7;

    public int numberOfPaths(int[][] grid, int k) {
        return run(grid, k);
    }

    private int run(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] prev = new int[cols][k];
        int[][] next = new int[cols][k];

        int acc = 0;
        for (int c = 0; c < cols; c++) {
            acc += grid[0][c];
            prev[c][acc % k] = 1;
        }

        acc = grid[0][0];

        for (int r = 1; r < rows; r++) {
            acc += grid[r][0];

            Arrays.fill(next[0], 0);
            next[0][acc % k] = 1;

            for (int c = 1; c < cols; c++) {
                int val = grid[r][c];

                for (int rem = 0; rem < k; rem++) {
                    int newRem = (rem + val) % k;
                    next[c][newRem] = (int) (((long) prev[c][rem] + next[c - 1][rem]) % MOD);
                }
            }

            int[][] swap = prev;
            prev = next;
            next = swap;
        }

        return prev[cols - 1][0];
    }

    private int tab(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] dp = new int[rows * cols][k];

        int s = 0;
        for (int c = 0; c < cols; c++) {
            s += grid[0][c];
            dp[c][s % k] = 1;
        }

        s = grid[0][0];
        for (int r = 1; r < rows; r++) {
            s += grid[r][0];
            dp[r * cols][s % k] = 1;
        }

        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                int v = grid[r][c];
                int pos = r * cols + c;

                for (int rem = 0; rem < k; rem++) {
                    int newRem = (rem + v) % k;
                    dp[pos][newRem] = (int) (((long) dp[(r - 1) * cols + c][rem] +
                            dp[r * cols + c - 1][rem]) % MOD);
                }
            }
        }

        return dp[(rows - 1) * cols + cols - 1][0];
    }

    private int memo(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] memo = new int[rows * cols][k];
        for (int[] row : memo)
            Arrays.fill(row, -1);

        return dfs(grid, k, 0, 0, 0, memo);
    }

    private int dfs(int[][] grid, int k, int r, int c, int rem, int[][] memo) {
        int cols = grid[0].length;
        int pos = r * cols + c;

        int newRem = (rem + grid[r][c]) % k;

        if (memo[pos][newRem] != -1) {
            return memo[pos][newRem];
        }

        if (r == grid.length - 1 && c == cols - 1) {
            return newRem == 0 ? 1 : 0;
        }

        int down = r + 1 < grid.length ? dfs(grid, k, r + 1, c, newRem, memo) : 0;
        int right = c + 1 < cols ? dfs(grid, k, r, c + 1, newRem, memo) : 0;

        return memo[pos][newRem] = (down + right) % MOD;
    }
}