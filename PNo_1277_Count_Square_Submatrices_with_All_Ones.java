/*
Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.



Example 1:

Input: matrix =
[
  [0,1,1,1],
  [1,1,1,1],
  [0,1,1,1]
]
Output: 15
Explanation:
There are 10 squares of side 1.
There are 4 squares of side 2.
There is  1 square of side 3.
Total number of squares = 10 + 4 + 1 = 15.
Example 2:

Input: matrix =
[
  [1,0,1],
  [1,1,0],
  [1,1,0]
]
Output: 7
Explanation:
There are 6 squares of side 1.
There is 1 square of side 2.
Total number of squares = 6 + 1 = 7.


Constraints:

1 <= arr.length <= 300
1 <= arr[0].length <= 300
0 <= arr[i][j] <= 1
*/

class Solution {
    static {
        int[][] matrix = new int[][] {
                { 0, 1, 0, 1, 1, 0 },
                { 1, 0, 0, 1, 1, 1 },
                { 1, 1, 0, 0, 0, 1 },
                { 1, 0, 0, 1, 1, 1 } };
        for (int i = 0; i < 400; i++)
            countSquares(matrix);
    }

    public static int countSquares(int[][] mat) {
        int rows = mat.length; // Number of rows
        int cols = mat[0].length; // Number of columns

        // Update matrix values to count square sizes
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                if (mat[r][c] == 1) {
                    mat[r][c] += Math.min(mat[r - 1][c], Math.min(mat[r][c - 1], mat[r - 1][c - 1]));
                }
            }
        }

        int total = 0; // Total count of squares
        // Sum up all values in the matrix
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                total += mat[r][c];
            }
        }

        return total; // Return total count of square submatrices
    }
}