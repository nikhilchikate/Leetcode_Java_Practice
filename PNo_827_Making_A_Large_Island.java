/*
You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1.

Return the size of the largest island in grid after applying this operation.

An island is a 4-directionally connected group of 1s.



Example 1:

Input: grid = [[1,0],[0,1]]
Output: 3
Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
Example 2:

Input: grid = [[1,1],[1,0]]
Output: 4
Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
Example 3:

Input: grid = [[1,1],[1,1]]
Output: 4
Explanation: Can't change any 0 to 1, only one island with area = 4.


Constraints:

n == grid.length
n == grid[i].length
1 <= n <= 500
grid[i][j] is either 0 or 1.
*/

class Solution {
    // Function to find the largest island after changing water cells to land
    public int largestIsland(int[][] grid) {
        // Array to store the size of each island, index corresponds to the island number
        int[] sz = new int[2 + grid.length * grid.length];
        int nComp = 2;  // Island label starts from 2 (1 is for water)
        int maxSz = 0;   // Variable to keep track of the largest island size

        // First pass: mark each island and calculate its size using DFS
        for (int r = 0; r < grid.length; ++r) {
            for (int c = 0; c < grid[r].length; ++c) {
                // If the cell is land (1), it's part of an unvisited island
                if (grid[r][c] == 1) {
                    // Perform DFS to calculate island size and label the island
                    sz[nComp] = dfs(grid, r, c, nComp);
                    // Update max size if this island is larger
                    maxSz = Math.max(maxSz, sz[nComp]);
                    ++nComp;  // Increment island label
                }
            }
        }

        // Second pass: try converting water cells to land and see if we can form a larger island
        for (int r = 0; r < grid.length; ++r) {
            for (int c = 0; c < grid[r].length; ++c) {
                // Skip cells that are already land
                if (grid[r][c] != 0) {
                    continue;
                }
                int mSz = 1;  // Start with 1 (current water cell converted to land)
                int a = 0, b = 0, c1 = 0;  // To store neighboring island labels

                // Check the neighboring cells and add their island sizes if they are different islands
                if (r > 0) {
                    a = grid[r - 1][c];
                    mSz += sz[a];  // Add size of the island above
                }
                if (c + 1 < grid[r].length && grid[r][c + 1] != a) {
                    b = grid[r][c + 1];
                    mSz += sz[b];  // Add size of the island to the right
                }
                if (r + 1 < grid.length && grid[r + 1][c] != a && grid[r + 1][c] != b) {
                    c1 = grid[r + 1][c];
                    mSz += sz[c1];  // Add size of the island below
                }
                if (c > 0 && grid[r][c - 1] != a && grid[r][c - 1] != b && grid[r][c - 1] != c1) {
                    mSz += sz[grid[r][c - 1]];  // Add size of the island to the left
                }

                // Update max island size after considering current water cell
                maxSz = Math.max(maxSz, mSz);
            }
        }
        return maxSz;  // Return the largest island size
    }

    // Helper DFS function to calculate the size of an island
    private int dfs(int[][] grid, int r, int c, int comp) {
        grid[r][c] = comp;  // Mark the current cell as part of the island
        int sz = 1;  // Initial size of the island

        // Explore all 4 directions (up, down, left, right) and add size for connected land
        if (r > 0 && grid[r - 1][c] == 1) {
            sz += dfs(grid, r - 1, c, comp);
        }
        if (r + 1 < grid.length && grid[r + 1][c] == 1) {
            sz += dfs(grid, r + 1, c, comp);
        }
        if (c > 0 && grid[r][c - 1] == 1) {
            sz += dfs(grid, r, c - 1, comp);
        }
        if (c + 1 < grid.length && grid[r][c + 1] == 1) {
            sz += dfs(grid, r, c + 1, comp);
        }
        return sz;  // Return the size of the current island
    }
}