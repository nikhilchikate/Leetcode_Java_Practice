/*
Given an m x n grid. Each cell of the grid has a sign pointing to the next cell you should visit if you are currently in this cell. The sign of grid[i][j] can be:

1 which means go to the cell to the right. (i.e go from grid[i][j] to grid[i][j + 1])
2 which means go to the cell to the left. (i.e go from grid[i][j] to grid[i][j - 1])
3 which means go to the lower cell. (i.e go from grid[i][j] to grid[i + 1][j])
4 which means go to the upper cell. (i.e go from grid[i][j] to grid[i - 1][j])
Notice that there could be some signs on the cells of the grid that point outside the grid.

You will initially start at the upper left cell (0, 0). A valid path in the grid is a path that starts from the upper left cell (0, 0) and ends at the bottom-right cell (m - 1, n - 1) following the signs on the grid. The valid path does not have to be the shortest.

You can modify the sign on a cell with cost = 1. You can modify the sign on a cell one time only.

Return the minimum cost to make the grid have at least one valid path.



Example 1:


Input: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
Output: 3
Explanation: You will start at point (0, 0).
The path to (3, 3) is as follows. (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) change the arrow to down with cost = 1 --> (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) change the arrow to down with cost = 1 --> (2, 0) --> (2, 1) --> (2, 2) --> (2, 3) change the arrow to down with cost = 1 --> (3, 3)
The total cost = 3.
Example 2:


Input: grid = [[1,1,3],[3,2,2],[1,1,4]]
Output: 0
Explanation: You can follow the path from (0, 0) to (2, 2).
Example 3:


Input: grid = [[1,2],[4,3]]
Output: 1


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 100
1 <= grid[i][j] <= 4
*/

class Solution {
    int[] dc = new int[] { 1, -1, 0, 0 };
    int[] dr = new int[] { 0, 0, 1, -1 };
    Deque<int[]> dq = new LinkedList();
    boolean[][] seen;
    int cols;
    int rows;

    private void runAll(int[][] grid, int r, int c) {
        while (r >= 0 && r < rows && c >= 0 && c < cols && !seen[r][c]) {
            seen[r][c] = true;
            int dir = grid[r][c] - 1;
            dq.addLast(new int[] { r, c });
            c += dc[dir];
            r += dr[dir];
        }
    }

    public int minCost(int[][] grid) {
        cols = grid[0].length;
        rows = grid.length;
        seen = new boolean[rows][cols];
        int cost = 0;

        runAll(grid, 0, 0);

        while (!dq.isEmpty()) {
            for (int round = dq.size(); round > 0; round--) {
                int[] cur = dq.pollFirst();
                int r = cur[0];
                int c = cur[1];

                if (r == rows - 1 && c == cols - 1)
                    return cost;

                runAll(grid, r, c);

                for (int k = 0; k < 4; k++) {
                    runAll(grid, r + dr[k], c + dc[k]);
                }
            }
            cost++;
        }
        return -1;
    }
}