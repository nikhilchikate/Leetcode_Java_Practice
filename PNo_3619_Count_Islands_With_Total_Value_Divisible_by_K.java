/*
You are given an m x n matrix grid and a positive integer k. An island is a group of positive integers (representing land) that are 4-directionally connected (horizontally or vertically).

The total value of an island is the sum of the values of all cells in the island.

Return the number of islands with a total value divisible by k.

 

Example 1:


Input: grid = [[0,2,1,0,0],[0,5,0,0,5],[0,0,1,0,0],[0,1,4,7,0],[0,2,0,0,8]], k = 5

Output: 2

Explanation:

The grid contains four islands. The islands highlighted in blue have a total value that is divisible by 5, while the islands highlighted in red do not.

Example 2:


Input: grid = [[3,0,3,0], [0,3,0,3], [3,0,3,0]], k = 3

Output: 6

Explanation:

The grid contains six islands, each with a total value that is divisible by 3.

 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 1000
1 <= m * n <= 105
0 <= grid[i][j] <= 106
1 <= k <= 106
*/

class Solution {
    int ans = 0;

    public int countIslands(int[][] grid, int k) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > 0) {
                    long val = solve(grid, i, j);
                    if (val % k == 0)
                        ans++;
                }
            }
        }
        return ans;
    }

    private long solve(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i == grid.length || j == grid[0].length) 
            return 0;

        long sum = grid[i][j];
        grid[i][j] = -1;

        if (i > 0 && grid[i - 1][j] > 0) 
            sum += solve(grid, i - 1, j);
        

        if (j > 0 && grid[i][j - 1] > 0) 
            sum += solve(grid, i, j - 1);
        

        if (i < grid.length - 1 && grid[i + 1][j] > 0) 
            sum += solve(grid, i + 1, j);
        

        if (j < grid[0].length - 1 && grid[i][j + 1] > 0) 
            sum += solve(grid, i, j + 1);

        return sum;
    }
}