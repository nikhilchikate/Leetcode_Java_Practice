/*
Given an n x n binary grid, in one step you can choose two adjacent rows of the grid and swap them.

A grid is said to be valid if all the cells above the main diagonal are zeros.

Return the minimum number of steps needed to make the grid valid, or -1 if the grid cannot be valid.

The main diagonal of a grid is the diagonal that starts at cell (1, 1) and ends at cell (n, n).



Example 1:


Input: grid = [[0,0,1],[1,1,0],[1,0,0]]
Output: 3
Example 2:


Input: grid = [[0,1,1,0],[0,1,1,0],[0,1,1,0],[0,1,1,0]]
Output: -1
Explanation: All rows are similar, swaps have no effect on the grid.
Example 3:


Input: grid = [[1,0,0],[1,1,0],[1,1,1]]
Output: 0


Constraints:

n == grid.length == grid[i].length
1 <= n <= 200
grid[i][j] is either 0 or 1
*/

class Solution {
    public int minSwaps(int[][] matrix) {
        final int size = matrix.length;
        int swapCount = 0;
        int[] trailingZeroCounts = new int[size];

        for (int rowIndex = 0; rowIndex < size; ++rowIndex)
            trailingZeroCounts[rowIndex] = countTrailingZeros(matrix[rowIndex]);

        for (int position = 0; position < size; ++position) {
            final int requiredZeros = size - 1 - position;
            final int candidateRow = findFirstRowWithEnoughZeros(trailingZeroCounts, position, requiredZeros);
            if (candidateRow == -1)
                return -1;

            for (int moveIndex = candidateRow; moveIndex > position; --moveIndex)
                trailingZeroCounts[moveIndex] = trailingZeroCounts[moveIndex - 1];

            swapCount += candidateRow - position;
        }

        return swapCount;
    }

    private int countTrailingZeros(int[] row) {
        for (int index = row.length - 1; index >= 0; --index)
            if (row[index] == 1)
                return row.length - index - 1;
        return row.length;
    }

    private int findFirstRowWithEnoughZeros(
            int[] trailingZeroCounts, int startIndex, int requiredZeros) {
        for (int rowIndex = startIndex; rowIndex < trailingZeroCounts.length; ++rowIndex)
            if (trailingZeroCounts[rowIndex] >= requiredZeros)
                return rowIndex;
        return -1;
    }
}