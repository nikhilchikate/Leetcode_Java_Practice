/*
You are given an m x n integer matrix grid​​​, where m and n are both even integers, and an integer k.

The matrix is composed of several layers, which is shown in the below image, where each color is its own layer:



A cyclic rotation of the matrix is done by cyclically rotating each layer in the matrix. To cyclically rotate a layer once, each element in the layer will take the place of the adjacent element in the counter-clockwise direction. An example rotation is shown below:


Return the matrix after applying k cyclic rotations to it.

 

Example 1:


Input: grid = [[40,10],[30,20]], k = 1
Output: [[10,20],[40,30]]
Explanation: The figures above represent the grid at every state.
Example 2:


Input: grid = [[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]], k = 2
Output: [[3,4,8,12],[2,11,10,16],[1,7,6,15],[5,9,13,14]]
Explanation: The figures above represent the grid at every state.
 

Constraints:

m == grid.length
n == grid[i].length
2 <= m, n <= 50
Both m and n are even integers.
1 <= grid[i][j] <= 5000
1 <= k <= 109
*/

class Solution {
    private int processLayer(int[][] matrix, int[] buffer, int rows, int cols, int layerIdx, boolean writeBack,
            int offset, int size) {
        int rTop = layerIdx, cLeft = layerIdx;
        int rBottom = rows - layerIdx - 1, cRight = cols - layerIdx - 1;

        int count = 0;
        for (int row = rTop; row <= rBottom; row++)
            if (writeBack)
                matrix[row][cLeft] = buffer[(count++ + offset) % size];
            else
                buffer[count++] = matrix[row][cLeft];

        for (int col = cLeft + 1; col <= cRight; col++)
            if (writeBack)
                matrix[rBottom][col] = buffer[(count++ + offset) % size];
            else
                buffer[count++] = matrix[rBottom][col];

        for (int row = rBottom - 1; row >= rTop; row--)
            if (writeBack)
                matrix[row][cRight] = buffer[(count++ + offset) % size];
            else
                buffer[count++] = matrix[row][cRight];

        for (int col = cRight - 1; col > cLeft; col--)
            if (writeBack)
                matrix[rTop][col] = buffer[(count++ + offset) % size];
            else
                buffer[count++] = matrix[rTop][col];

        return count;
    }

    public int[][] rotateGrid(int[][] grid, int k) {
        int height = grid.length, width = grid[0].length;
        int[] storage = new int[(height + width - 2) << 1];
        int layers = Math.min(height, width) >> 1;

        for (int i = 0; i < layers; i++) {
            int currentSize = processLayer(grid, storage, height, width, i, false, k, 0);
            processLayer(grid, storage, height, width, i, true, currentSize - (k % currentSize), currentSize);
        }
        return grid;
    }
}