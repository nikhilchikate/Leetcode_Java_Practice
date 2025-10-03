/*
Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return the volume of water it can trap after raining.



Example 1:


Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
Output: 4
Explanation: After the rain, water is trapped between the blocks.
We have two small ponds 1 and 3 units trapped.
The total volume of water trapped is 4.
Example 2:


Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
Output: 10


Constraints:

m == heightMap.length
n == heightMap[i].length
1 <= m, n <= 200
0 <= heightMap[i][j] <= 2 * 104
*/

class Solution {
    public int trapRainWater(int[][] h) {
        int n = h[0].length;
        int m = h.length;

        int[][] w = new int[m][n];
        for (int r = 0; r < m; ++r) {
            for (int c = 0; c < n; ++c) {
                w[r][c] = h[r][c];
            }
        }

        boolean init = true;
        boolean upd = true;
        while (upd) {
            upd = false;

            for (int r = 1; r < m - 1; ++r) {
                for (int c = 1; c < n - 1; ++c) {
                    int minH = Math.min(w[r - 1][c], w[r][c - 1]);
                    int newW = Math.max(h[r][c], minH);
                    if (init || w[r][c] > newW) {
                        w[r][c] = newW;
                        upd = true;
                    }
                }
            }
            init = false;

            for (int r = m - 2; r >= 1; --r) {
                for (int c = n - 2; c >= 1; --c) {
                    int minH = Math.min(w[r + 1][c], w[r][c + 1]);
                    int newW = Math.max(h[r][c], minH);
                    if (w[r][c] > newW) {
                        w[r][c] = newW;
                        upd = true;
                    }
                }
            }
        }

        int res = 0;
        for (int r = 1; r < m - 1; ++r) {
            for (int c = 1; c < n - 1; ++c) {
                if (w[r][c] > h[r][c])
                    res += w[r][c] - h[r][c];
            }
        }
        return res;
    }
}