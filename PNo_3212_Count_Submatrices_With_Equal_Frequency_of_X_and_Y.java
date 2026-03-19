/*
Given a 2D character matrix grid, where grid[i][j] is either 'X', 'Y', or '.', return the number of submatrices that contain:

grid[0][0]
an equal frequency of 'X' and 'Y'.
at least one 'X'.


Example 1:

Input: grid = [["X","Y","."],["Y",".","."]]

Output: 3

Explanation:



Example 2:

Input: grid = [["X","X"],["X","Y"]]

Output: 0

Explanation:

No submatrix has an equal frequency of 'X' and 'Y'.

Example 3:

Input: grid = [[".","."],[".","."]]

Output: 0

Explanation:

No submatrix has at least one 'X'.



Constraints:

1 <= grid.length, grid[i].length <= 1000
grid[i][j] is either 'X', 'Y', or '.'.
*/

class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] colX = new int[n];
        int[] colY = new int[n];
        int ans = 0;

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 'X')
                    colX[j]++;
                if (grid[i][j] == 'Y')
                    colY[j]++;
            }

            int x = 0;
            int y = 0;
            for (int k = 0; k < n; ++k) {
                x += colX[k];
                y += colY[k];

                if (x == y && x > 0)
                    ans++;
            }
        }

        return ans;
    }
}