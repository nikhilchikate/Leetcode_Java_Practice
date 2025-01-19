/*
Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return the volume of water it can trap after raining.



Example 1:


Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
Output: 4
Explanation: After the rain, water is trapped between the blocks.
We have two small ponds 1 and 3 units trapped.
The total volume of water trapped is 4.
Example 2:


Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
Output: 10


Constraints:

m == heightMap.length
n == heightMap[i].length
1 <= m, n <= 200
0 <= heightMap[i][j] <= 2 * 104
*/

class Solution {
    public int trapRainWater(int[][] heightMap) {
        int cols = heightMap[0].length;
        int rows = heightMap.length;

        // initialization
        int[][] waterLevels = new int[rows][cols];
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                waterLevels[row][col] = heightMap[row][col];
            }
        }

        // spread water levels
        boolean isFirstPass = true;
        boolean isUpdated = true;
        while (isUpdated) {
            isUpdated = false;

            // propagate from top-left
            for (int row = 1; row < rows - 1; ++row) {
                for (int col = 1; col < cols - 1; ++col) {
                    int minNeighborHeight = Math.min(waterLevels[row - 1][col], waterLevels[row][col - 1]);
                    int newWaterLevel = Math.max(heightMap[row][col], minNeighborHeight);
                    if (isFirstPass || waterLevels[row][col] > newWaterLevel) {
                        waterLevels[row][col] = newWaterLevel;
                        isUpdated = true;
                    }
                }
            }
            isFirstPass = false;

            // propagate from bottom-right
            for (int row = rows - 2; row >= 1; --row) {
                for (int col = cols - 2; col >= 1; --col) {
                    int minNeighborHeight = Math.min(waterLevels[row + 1][col], waterLevels[row][col + 1]);
                    int newWaterLevel = Math.max(heightMap[row][col], minNeighborHeight);
                    if (waterLevels[row][col] > newWaterLevel) {
                        waterLevels[row][col] = newWaterLevel;
                        isUpdated = true;
                    }
                }
            }
        }

        // calculate the trapped water
        int totalWater = 0;
        for (int row = 1; row < rows - 1; ++row) {
            for (int col = 1; col < cols - 1; ++col) {
                if (waterLevels[row][col] > heightMap[row][col])
                    totalWater += waterLevels[row][col] - heightMap[row][col];
            }
        }
        return totalWater;
    }
}