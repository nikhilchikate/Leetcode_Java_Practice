/*
Given a m x n matrix mat and an integer threshold, return the maximum side-length of a square with a sum less than or equal to threshold or return 0 if there is no such square.



Example 1:


Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
Output: 2
Explanation: The maximum side length of square with sum less than 4 is 2 as shown.
Example 2:

Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
Output: 0


Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 300
0 <= mat[i][j] <= 104
0 <= threshold <= 105
*/

class Solution {
    public int maxSideLength(int[][] grid, int limit) {
        int rCount = grid.length, cCount = grid[0].length;

        for (int r = 0; r < rCount; ++r) {
            for (int c = 1; c < cCount; ++c) {
                grid[r][c] += grid[r][c - 1];
            }
        }
        for (int r = 1; r < rCount; ++r) {
            for (int c = 0; c < cCount; ++c) {
                grid[r][c] += grid[r - 1][c];
            }
        }

        int bestLen = 0;
        for (int r = 0; r < rCount; ++r) {
            for (int c = 0; c < cCount; ++c) {
                for (int size = bestLen + 1; r + 1 - size >= 0 && c + 1 - size >= 0; size++) {
                    int rPrev = r - size, cPrev = c - size;

                    int corner = rPrev >= 0 && cPrev >= 0 ? grid[rPrev][cPrev] : 0;
                    int colSum = cPrev >= 0 ? grid[r][cPrev] : 0;
                    int rowSum = rPrev >= 0 ? grid[rPrev][c] : 0;

                    int total = grid[r][c] + corner - rowSum - colSum;
                    if (total <= limit) {
                        bestLen = size;
                    } else {
                        break;
                    }
                }
            }
        }

        return bestLen;
    }
}