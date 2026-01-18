/*
A k x k magic square is a k x k grid filled with integers such that every row sum, every column sum, and both diagonal sums are all equal. The integers in the magic square do not have to be distinct. Every 1 x 1 grid is trivially a magic square.

Given an m x n integer grid, return the size (i.e., the side length k) of the largest magic square that can be found within this grid.



Example 1:


Input: grid = [[7,1,4,5,6],[2,5,1,6,4],[1,5,4,3,2],[1,2,7,3,4]]
Output: 3
Explanation: The largest magic square has a size of 3.
Every row sum, column sum, and diagonal sum of this magic square is equal to 12.
- Row sums: 5+1+6 = 5+4+3 = 2+7+3 = 12
- Column sums: 5+5+2 = 1+4+7 = 6+3+3 = 12
- Diagonal sums: 5+4+3 = 6+4+2 = 12
Example 2:


Input: grid = [[5,1,3,1],[9,3,3,1],[1,3,3,8]]
Output: 2


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 50
1 <= grid[i][j] <= 106
*/

class Solution {
    boolean check(int[][] mat, int[][] rPref, int[][] cPref, int len) {
        int rCnt = mat.length;
        int cCnt = mat[0].length;

        int rLim = rCnt - len;
        int cLim = cCnt - len;

        for (int r = 0; r <= rLim; ++r) {
            for (int c = 0; c <= cLim; ++c) {
                int target = rPref[r][c + len] - rPref[r][c];
                boolean ok = true;

                for (int k = 0; ok && k < len; ++k) {
                    int rowVal = rPref[r + k][c + len] - rPref[r + k][c];
                    int colVal = cPref[r + len][c + k] - cPref[r][c + k];
                    ok = rowVal == target && colVal == target;
                }

                if (ok) {
                    int d1 = 0, d2 = 0;
                    for (int k = 0; k < len; ++k) {
                        d1 += mat[r + k][c + k];
                        d2 += mat[r + k][c + len - 1 - k];
                    }
                    if (d1 == target && d2 == target)
                        return true;
                }
            }
        }
        return false;
    }

    public int largestMagicSquare(int[][] mat) {
        int rCnt = mat.length;
        int cCnt = mat[0].length;

        int[][] rPref = new int[rCnt][cCnt + 1];
        int[][] cPref = new int[rCnt + 1][cCnt];

        for (int r = 0; r < rCnt; ++r) {
            for (int c = 0; c < cCnt; ++c) {
                rPref[r][c + 1] = rPref[r][c] + mat[r][c];
                cPref[r + 1][c] = cPref[r][c] + mat[r][c];
            }
        }

        for (int len = Math.min(rCnt, cCnt); len > 1; len--) {
            if (check(mat, rPref, cPref, len))
                return len;
        }
        return 1;
    }
}