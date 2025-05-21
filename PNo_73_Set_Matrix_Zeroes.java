/*
Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.

You must do it in place.



Example 1:


Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
Output: [[1,0,1],[0,0,0],[1,0,1]]
Example 2:


Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]


Constraints:

m == matrix.length
n == matrix[0].length
1 <= m, n <= 200
-231 <= matrix[i][j] <= 231 - 1
*/

class Solution {
    public void setZeroes(int[][] grid) {
        int rows = grid.length, cols = grid[0].length, firstColHasZero = 1;

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                if (grid[r][c] == 0) {
                    grid[r][0] = 0;
                    if (c != 0) {
                        grid[0][c] = 0;
                    } else {
                        firstColHasZero = 0;
                    }
                }
            }
        }

        for (int r = 1; r < rows; ++r) {
            for (int c = 1; c < cols; ++c) {
                if (grid[r][0] == 0 || grid[0][c] == 0) {
                    grid[r][c] = 0;
                }
            }
        }

        if (grid[0][0] == 0) {
            for (int c = 0; c < cols; ++c) {
                grid[0][c] = 0;
            }
        }

        if (firstColHasZero == 0) {
            for (int r = 0; r < rows; ++r) {
                grid[r][0] = 0;
            }
        }
    }
}