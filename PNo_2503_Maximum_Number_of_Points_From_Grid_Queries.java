/*
You are given an m x n integer matrix grid and an array queries of size k.

Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process:

If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right.
Otherwise, you do not get any points, and you end this process.
After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times.

Return the resulting array answer.



Example 1:


Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
Output: [5,8,1]
Explanation: The diagrams above show which cells we visit to get points for each query.
Example 2:


Input: grid = [[5,2,1],[1,1,2]], queries = [3]
Output: [0]
Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.


Constraints:

m == grid.length
n == grid[i].length
2 <= m, n <= 1000
4 <= m * n <= 105
k == queries.length
1 <= k <= 104
1 <= grid[i][j], queries[i] <= 106
*/

class Solution {
    int totalCount = 0;

    public void traverseGrid(int[][] matrix, int threshold, int row, int col, PriorityQueue<int[]> queue) {
        if (row < 0 || col < 0 || row == matrix.length || col == matrix[0].length) {
            return;
        }
        if (matrix[row][col] > 0) {
            if (matrix[row][col] < threshold) {
                totalCount++;
                matrix[row][col] = -1; // visited
                traverseGrid(matrix, threshold, row + 1, col, queue);
                traverseGrid(matrix, threshold, row - 1, col, queue);
                traverseGrid(matrix, threshold, row, col + 1, queue);
                traverseGrid(matrix, threshold, row, col - 1, queue);
            } else {
                queue.add(new int[] { row, col, matrix[row][col] });
                matrix[row][col] = 0;
            }
        }
        while (!queue.isEmpty()) {
            int[] topElement = queue.peek();
            if (topElement[2] < threshold) {
                queue.remove();
                matrix[topElement[0]][topElement[1]] = topElement[2];
                traverseGrid(matrix, threshold, topElement[0], topElement[1], queue);
            } else {
                break;
            }
        }
    }

    public int[] maxPoints(int[][] matrix, int[] queryValues) {
        int[] result = new int[queryValues.length];
        List<int[]> indexedQueries = new ArrayList<>();
        for (int i = 0; i < queryValues.length; i++) {
            indexedQueries.add(new int[] { i, queryValues[i] });
        }
        Collections.sort(indexedQueries, (a, b) -> a[1] - b[1]);
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        for (int[] query : indexedQueries) {
            traverseGrid(matrix, query[1], 0, 0, queue);
            result[query[0]] = totalCount;
        }
        return result;
    }
}