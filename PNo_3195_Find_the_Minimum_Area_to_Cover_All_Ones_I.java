/*
3195. Find the Minimum Area to Cover All Ones I
Solved
Medium
Topics
premium lock icon
Companies
Hint
You are given a 2D binary array grid. Find a rectangle with horizontal and vertical sides with the smallest area, such that all the 1's in grid lie inside this rectangle.

Return the minimum possible area of the rectangle.



Example 1:

Input: grid = [[0,1,0],[1,0,1]]

Output: 6

Explanation:



The smallest rectangle has a height of 2 and a width of 3, so it has an area of 2 * 3 = 6.

Example 2:

Input: grid = [[1,0],[0,0]]

Output: 1

Explanation:



The smallest rectangle has both height and width 1, so its area is 1 * 1 = 1.



Constraints:

1 <= grid.length, grid[i].length <= 1000
grid[i][j] is either 0 or 1.
The input is generated such that there is at least one 1 in grid.
*/

class Solution {
    public int minimumArea(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        int minR = 0;
        int maxR = 0;
        int minC = 0;
        int maxC = 0;
        int found = 0;

        for (int i = 0; i < r; ++i) {
            for (int j = 0; j < c; ++j) {
                if (grid[i][j] == 1) {
                    minR = i;
                    found = 1;
                    break;
                }
            }
            if (found == 1)
                break;
        }
        found = 0;

        for (int i = r - 1; i >= 0; --i) {
            for (int j = 0; j < c; ++j) {
                if (grid[i][j] == 1) {
                    maxR = i;
                    found = 1;
                    break;
                }
            }
            if (found == 1)
                break;
        }
        found = 0;

        for (int i = 0; i < c; ++i) {
            for (int j = 0; j < r; ++j) {
                if (grid[j][i] == 1) {
                    minC = i;
                    found = 1;
                    break;
                }
            }
            if (found == 1)
                break;
        }
        found = 0;

        for (int i = c - 1; i >= 0; --i) {
            for (int j = 0; j < r; ++j) {
                if (grid[j][i] == 1) {
                    maxC = i;
                    found = 1;
                    break;
                }
            }
            if (found == 1)
                break;
        }

        return (maxC - minC + 1) * (maxR - minR + 1);
    }
}