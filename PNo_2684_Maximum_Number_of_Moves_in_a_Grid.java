//You are given a 0-indexed m x n matrix grid consisting of positive integers.
//
//You can start at any cell in the first column of the matrix, and traverse the grid in the following way:
//
//From a cell (row, col), you can move to any of the cells: (row - 1, col + 1), (row, col + 1) and (row + 1, col + 1) such that the value of the cell you move to, should be strictly bigger than the value of the current cell.
//Return the maximum number of moves that you can perform.
//
//
//
//Example 1:
//
//
//Input: grid = [[2,4,3,5],[5,4,9,3],[3,4,2,11],[10,9,13,15]]
//Output: 3
//Explanation: We can start at the cell (0, 0) and make the following moves:
//        - (0, 0) -> (0, 1).
//        - (0, 1) -> (1, 2).
//        - (1, 2) -> (2, 3).
//It can be shown that it is the maximum number of moves that can be made.
//Example 2:
//
//
//Input: grid = [[3,2,4],[2,1,9],[1,1,7]]
//Output: 0
//Explanation: Starting from any cell in the first column we cannot perform any moves.
//
//
//        Constraints:
//
//m == grid.length
//n == grid[i].length
//2 <= m, n <= 1000
//        4 <= m * n <= 105
//        1 <= grid[i][j] <= 106

class Solution {
    private int dfs(int r, int c, int rows, int cols, int[][] grid, int[][] memo) {
        // Base case: check for out-of-bounds
        if (r < 0 || c < 0 || r >= rows || c >= cols) {
            return 0;
        }

        // Return cached result if already computed
        if (memo[r][c] != -1) {
            return memo[r][c];
        }

        int maxMoves = 0; // Initialize maximum moves from current position

        // Check possible diagonal move up-right
        if (r - 1 >= 0 && c + 1 < cols && grid[r][c] < grid[r - 1][c + 1]) {
            maxMoves = Math.max(maxMoves, 1 + dfs(r - 1, c + 1, rows, cols, grid, memo));
        }

        // Check possible right move
        if (c + 1 < cols && grid[r][c] < grid[r][c + 1]) {
            maxMoves = Math.max(maxMoves, 1 + dfs(r, c + 1, rows, cols, grid, memo));
        }

        // Check possible diagonal move down-right
        if (r + 1 < rows && c + 1 < cols && grid[r][c] < grid[r + 1][c + 1]) {
            maxMoves = Math.max(maxMoves, 1 + dfs(r + 1, c + 1, rows, cols, grid, memo));
        }

        // Store the computed result in memoization table
        memo[r][c] = maxMoves;
        return memo[r][c];
    }

    public int maxMoves(int[][] grid) {
        int rows = grid.length; // Number of rows in the grid
        int cols = grid[0].length; // Number of columns in the grid
        int[][] memo = new int[rows][cols]; // Memoization table

        // Initialize memo table with -1
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        int maxResult = 0; // Track the maximum moves across all starting positions

        // Explore all starting positions in the first column
        for (int r = 0; r < rows; r++) {
            maxResult = Math.max(maxResult, dfs(r, 0, rows, cols, grid, memo));
        }

        return maxResult; // Return the overall maximum moves
    }
}