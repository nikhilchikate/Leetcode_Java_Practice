/*
You are given a 2D integer array grid of size m * n.

You start at the top-left cell (0, 0) and want to reach the bottom-right cell (m - 1, n - 1).

At each step, you may move either right or down.

The cost of a path is defined as the bitwise XOR of all the values in the cells along that path, including the start and end cells.

Return the minimum possible XOR value among all valid paths from (0, 0) to (m - 1, n - 1).

 

Example 1:

Input: grid = [[1,2],[3,4]]

Output: 6

Explanation:

There are two valid paths:

(0, 0) → (0, 1) → (1, 1) with XOR: 1 XOR 2 XOR 4 = 7
(0, 0) → (1, 0) → (1, 1) with XOR: 1 XOR 3 XOR 4 = 6
The minimum XOR value among all valid paths is 6.

Example 2:

Input: grid = [[6,7],[5,8]]

Output: 9

Explanation:

There are two valid paths:

(0, 0) → (0, 1) → (1, 1) with XOR: 6 XOR 7 XOR 8 = 9
(0, 0) → (1, 0) → (1, 1) with XOR: 6 XOR 5 XOR 8 = 11
The minimum XOR value among all valid paths is 9.

Example 3:

Input: grid = [[2,7,5]]

Output: 0

Explanation:

There is only one valid path:

(0, 0) → (0, 1) → (0, 2) with XOR: 2 XOR 7 XOR 5 = 0
The XOR value of this path is 0, which is the minimum possible.

 

Constraints:

1 <= m == grid.length <= 1000
1 <= n == grid[i].length <= 1000
m * n <= 1000
0 <= grid[i][j] <= 1023​
*/

class Solution {
    public int minCost(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] reachable = new boolean[cols][1024];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean[] current = new boolean[1024];
                int value = grid[row][col];

                if (row == 0 && col == 0) {
                    current[value] = true;
                } else {
                    if (row > 0) {
                        addXors(reachable[col], current, value);
                    }
                    if (col > 0) {
                        addXors(reachable[col - 1], current, value);
                    }
                }

                reachable[col] = current;
            }
        }

        for (int xor = 0; xor < 1024; xor++) {
            if (reachable[cols - 1][xor]) {
                return xor;
            }
        }
        return -1;
    }

    private void addXors(boolean[] previous, boolean[] current, int value) {
        for (int xor = 0; xor < 1024; xor++) {
            if (previous[xor]) {
                current[xor ^ value] = true;
            }
        }
    }
}
