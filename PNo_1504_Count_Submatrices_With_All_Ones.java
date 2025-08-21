/*
Given an m x n binary matrix mat, return the number of submatrices that have all ones.



Example 1:


Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
Output: 13
Explanation:
There are 6 rectangles of side 1x1.
There are 2 rectangles of side 1x2.
There are 3 rectangles of side 2x1.
There is 1 rectangle of side 2x2.
There is 1 rectangle of side 3x1.
Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
Example 2:


Input: mat = [[0,1,1,0],[0,1,1,1],[1,1,1,0]]
Output: 24
Explanation:
There are 8 rectangles of side 1x1.
There are 5 rectangles of side 1x2.
There are 2 rectangles of side 1x3.
There are 4 rectangles of side 2x1.
There are 2 rectangles of side 2x2.
There are 2 rectangles of side 3x1.
There is 1 rectangle of side 3x2.
Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.


Constraints:

1 <= m, n <= 150
mat[i][j] is either 0 or 1.
*/

class Solution {
    public int numSubmat(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int result = 0;
        int[] heights = new int[cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                heights[j] = (matrix[i][j] == 0) ? 0 : heights[j] + 1;
            }
            result += calculateRects(heights);
        }
        return result;
    }

    private int calculateRects(int[] h) {
        int m = h.length;
        int totalCount = 0;
        int[] stack = new int[m];
        int top = -1;
        int[] sums = new int[m];

        for (int j = 0; j < m; j++) {
            while (top >= 0 && h[stack[top]] >= h[j]) {
                top--;
            }

            if (top == -1) {
                sums[j] = h[j] * (j + 1);
            } else {
                sums[j] = sums[stack[top]] + h[j] * (j - stack[top]);
            }
            totalCount += sums[j];
            stack[++top] = j;
        }
        return totalCount;
    }
}