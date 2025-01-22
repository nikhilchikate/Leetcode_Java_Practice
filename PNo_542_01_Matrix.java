/*
Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.

The distance between two cells sharing a common edge is 1.



Example 1:


Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
Output: [[0,0,0],[0,1,0],[0,0,0]]
Example 2:


Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
Output: [[0,0,0],[0,1,0],[1,2,1]]


Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 104
1 <= m * n <= 104
mat[i][j] is either 0 or 1.
There is at least one 0 in mat.


Note: This question is the same as 1765: https://leetcode.com/problems/map-of-highest-peak/
*/

class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int mx = m * n;

        // First pass: Top-left to bottom-right
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (mat[r][c] != 0) {
                    int tp = mx;
                    int lf = mx;
                    if (r > 0)
                        tp = mat[r - 1][c];
                    if (c > 0)
                        lf = mat[r][c - 1];
                    mat[r][c] = Math.min(tp, lf) + 1;
                }
            }
        }

        // Second pass: Bottom-right to top-left
        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                if (mat[r][c] != 0) {
                    int bt = mx;
                    int rt = mx;
                    if (r < m - 1)
                        bt = mat[r + 1][c];
                    if (c < n - 1)
                        rt = mat[r][c + 1];
                    mat[r][c] = Math.min(mat[r][c], Math.min(bt, rt) + 1);
                }
            }
        }

        return mat;
    }
}