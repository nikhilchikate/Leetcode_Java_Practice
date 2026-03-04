/*
Given an m x n binary matrix mat, return the number of special positions in mat.

A position (i, j) is called special if mat[i][j] == 1 and all other elements in row i and column j are 0 (rows and columns are 0-indexed).



Example 1:


Input: mat = [[1,0,0],[0,0,1],[1,0,0]]
Output: 1
Explanation: (1, 2) is a special position because mat[1][2] == 1 and all other elements in row 1 and column 2 are 0.
Example 2:


Input: mat = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3
Explanation: (0, 0), (1, 1) and (2, 2) are special positions.


Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 100
mat[i][j] is either 0 or 1.
*/

class Solution {
    public int numSpecial(int[][] matrix) {
        int specialCount = 0;

        for (int rowIndex = 0; rowIndex < matrix.length; ++rowIndex) {
            int colIndex = findSingleOneInRow(matrix, rowIndex);
            if (colIndex >= 0 && isSingleOneInColumn(matrix, rowIndex, colIndex)) {
                specialCount++;
            }
        }

        return specialCount;
    }

    private int findSingleOneInRow(int[][] matrix, int rowIndex) {
        int foundCol = -1;
        for (int colIndex = 0; colIndex < matrix[0].length; ++colIndex) {
            if (matrix[rowIndex][colIndex] == 1) {
                if (foundCol >= 0) {
                    return -1;
                }
                foundCol = colIndex;
            }
        }
        return foundCol;
    }

    private boolean isSingleOneInColumn(int[][] matrix, int rowIndex, int colIndex) {
        for (int currentRow = 0; currentRow < matrix.length; ++currentRow) {
            if (matrix[currentRow][colIndex] == 1 && currentRow != rowIndex) {
                return false;
            }
        }
        return true;
    }
}