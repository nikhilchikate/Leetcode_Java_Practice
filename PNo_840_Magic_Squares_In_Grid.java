/*
A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row, column, and both diagonals all have the same sum.

Given a row x col grid of integers, how many 3 x 3 magic square subgrids are there?

Note: while a magic square can only contain numbers from 1 to 9, grid may contain numbers up to 15.



Example 1:


Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
Output: 1
Explanation:
The following subgrid is a 3 x 3 magic square:

while this one is not:

In total, there is only one magic square inside the given grid.
Example 2:

Input: grid = [[8]]
Output: 0


Constraints:

row == grid.length
col == grid[i].length
1 <= row, col <= 10
0 <= grid[i][j] <= 15
*/

class Solution {

    public int numMagicSquaresInside(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int count = 0;

        for (int i = 0; i + 2 < m; ++i) {
            for (int j = 0; j + 2 < n; ++j) {
                if (isMagic(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isMagic(int[][] g, int r, int c) {
        // Center must be 5 for a 1–9 magic square
        if (g[r + 1][c + 1] != 5)
            return false;

        boolean[] seen = new boolean[10];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                int v = g[r + i][c + j];
                if (v < 1 || v > 9 || seen[v])
                    return false;
                seen[v] = true;
            }
        }

        int s = g[r][c] + g[r][c + 1] + g[r][c + 2];

        return g[r + 1][c] + g[r + 1][c + 1] + g[r + 1][c + 2] == s &&
                g[r + 2][c] + g[r + 2][c + 1] + g[r + 2][c + 2] == s &&
                g[r][c] + g[r + 1][c] + g[r + 2][c] == s &&
                g[r][c + 1] + g[r + 1][c + 1] + g[r + 2][c + 1] == s &&
                g[r][c + 2] + g[r + 1][c + 2] + g[r + 2][c + 2] == s &&
                g[r][c] + g[r + 1][c + 1] + g[r + 2][c + 2] == s &&
                g[r][c + 2] + g[r + 1][c + 1] + g[r + 2][c] == s;
    }
}