/*
Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.



Example 1:


Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 6
Explanation: The maximal rectangle is shown in the above picture.
Example 2:

Input: matrix = [["0"]]
Output: 0
Example 3:

Input: matrix = [["1"]]
Output: 1


Constraints:

rows == matrix.length
cols == matrix[i].length
1 <= rows, cols <= 200
matrix[i][j] is '0' or '1'.
*/

class Solution {
    public int maximalRectangle(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        int[] heightArr = new int[cols];
        int[] leftArr = new int[cols];
        int[] rightArr = new int[cols];
        Arrays.fill(rightArr, cols);

        int bestArea = 0;

        for (int r = 0; r < rows; ++r) {
            int leftPtr = 0;
            int rightPtr = cols;

            updateHeightAndLeft(grid[r], heightArr, leftArr, leftPtr);
            updateRight(grid[r], rightArr, rightPtr);
            bestArea = computeArea(heightArr, leftArr, rightArr, bestArea);
        }

        return bestArea;
    }

    private void updateHeightAndLeft(char[] line, int[] heightArr, int[] leftArr, int leftPtr) {
        for (int c = 0; c < heightArr.length; ++c) {
            if (line[c] == '1') {
                heightArr[c]++;
                leftArr[c] = Math.max(leftArr[c], leftPtr);
            } else {
                heightArr[c] = 0;
                leftArr[c] = 0;
                leftPtr = c + 1;
            }
        }
    }

    private void updateRight(char[] line, int[] rightArr, int rightPtr) {
        for (int c = rightArr.length - 1; c >= 0; --c) {
            if (line[c] == '1') {
                rightArr[c] = Math.min(rightArr[c], rightPtr);
            } else {
                rightArr[c] = rightPtr;
                rightPtr = c;
            }
        }
    }

    private int computeArea(int[] heightArr, int[] leftArr, int[] rightArr, int bestArea) {
        for (int c = 0; c < heightArr.length; ++c) {
            int width = rightArr[c] - leftArr[c];
            int area = heightArr[c] * width;
            bestArea = Math.max(bestArea, area);
        }
        return bestArea;
    }
}