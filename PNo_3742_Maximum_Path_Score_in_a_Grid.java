/*
You are given an m x n grid where each cell contains one of the values 0, 1, or 2. You are also given an integer k.

You start from the top-left corner (0, 0) and want to reach the bottom-right corner (m - 1, n - 1) by moving only right or down.

Each cell contributes a specific score and incurs an associated cost, according to their cell values:

0: adds 0 to your score and costs 0.
1: adds 1 to your score and costs 1.
2: adds 2 to your score and costs 1. ​​​​​​​
Return the maximum score achievable without exceeding a total cost of k, or -1 if no valid path exists.

Note: If you reach the last cell but the total cost exceeds k, the path is invalid.



Example 1:

Input: grid = [[0, 1],[2, 0]], k = 1

Output: 2

Explanation:​​​​​​​

The optimal path is:

Cell	grid[i][j]	Score	Total
Score	Cost	Total
Cost
(0, 0)	0	0	0	0	0
(1, 0)	2	2	2	1	1
(1, 1)	0	0	2	0	1
Thus, the maximum possible score is 2.

Example 2:

Input: grid = [[0, 1],[1, 2]], k = 1

Output: -1

Explanation:

There is no path that reaches cell (1, 1)​​​​​​​ without exceeding cost k. Thus, the answer is -1.



Constraints:

1 <= m, n <= 200
0 <= k <= 103​​​​​​​
​​​​​​​grid[0][0] == 0
0 <= grid[i][j] <= 2
*/

class Solution {
    public int maxPathScore(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int INF = 1 << 29;

        int[][] dp = new int[n][k + 1];

        for (int j = 0; j < n; j++) {
            for (int r = 0; r <= k; r++) {
                dp[j][r] = -INF;
            }
        }

        int startVal = grid[0][0];
        int startK = (startVal > 0) ? k - 1 : k;
        if (startK >= 0) {
            dp[0][startK] = 0;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                boolean costs = val > 0;

                for (int r = 0; r <= k; r++) {
                    if (dp[j][r] <= -INF && (j == 0 || dp[j - 1][r] <= -INF))
                        continue;

                    int prevMax = dp[j][r];
                    if (j > 0)
                        prevMax = Math.max(prevMax, dp[j - 1][r]);

                    if (costs) {
                        if (r > 0) {
                            dp[j][r - 1] = prevMax + val;
                        }
                        dp[j][r] = -INF;
                    } else {
                        dp[j][r] = prevMax + val;
                    }
                }
            }
        }

        int ans = -INF;
        for (int r = 0; r <= k; r++) {
            ans = Math.max(ans, dp[n - 1][r]);
        }

        return ans < 0 ? -1 : ans;
    }
}