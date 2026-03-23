/*
You are given a m x n matrix grid. Initially, you are located at the top-left corner (0, 0), and in each step, you can only move right or down in the matrix.

Among all possible paths starting from the top-left corner (0, 0) and ending in the bottom-right corner (m - 1, n - 1), find the path with the maximum non-negative product. The product of a path is the product of all integers in the grid cells visited along the path.

Return the maximum non-negative product modulo 109 + 7. If the maximum product is negative, return -1.

Notice that the modulo is performed after getting the maximum product.



Example 1:


Input: grid = [[-1,-2,-3],[-2,-3,-3],[-3,-3,-2]]
Output: -1
Explanation: It is not possible to get non-negative product in the path from (0, 0) to (2, 2), so return -1.
Example 2:


Input: grid = [[1,-2,1],[1,-2,1],[3,-4,1]]
Output: 8
Explanation: Maximum non-negative product is shown (1 * 1 * -2 * -4 * 1 = 8).
Example 3:


Input: grid = [[1,3],[0,-4]]
Output: 0
Explanation: Maximum non-negative product is shown (1 * 0 * -4 = 0).


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 15
-4 <= grid[i][j] <= 4
*/

class Solution {
    public int maxProductPath(int[][] grid) {
        final int MOD = 1_000_000_007;
        final int m = grid.length, n = grid[0].length;

        long[] dpMin = new long[n];
        long[] dpMax = new long[n];

        dpMin[0] = dpMax[0] = grid[0][0];
        for (int j = 1; j < n; j++)
            dpMin[j] = dpMax[j] = dpMin[j - 1] * grid[0][j];

        for (int i = 1; i < m; i++) {
            long[] newMin = new long[n];
            long[] newMax = new long[n];

            newMin[0] = newMax[0] = dpMin[0] * grid[i][0];

            for (int j = 1; j < n; j++) {
                int g = grid[i][j];
                long cMax = Math.max(dpMax[j], newMax[j - 1]);
                long cMin = Math.min(dpMin[j], newMin[j - 1]);

                if (g < 0) {
                    newMin[j] = cMax * g;
                    newMax[j] = cMin * g;
                } else {
                    newMin[j] = cMin * g;
                    newMax[j] = cMax * g;
                }
            }

            dpMin = newMin;
            dpMax = newMax;
        }

        long mx = Math.max(dpMin[n - 1], dpMax[n - 1]);
        return mx < 0 ? -1 : (int) (mx % MOD);
    }
}