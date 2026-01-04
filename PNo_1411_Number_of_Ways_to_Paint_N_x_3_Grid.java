/*
You have a grid of size n x 3 and you want to paint each cell of the grid with exactly one of the three colors: Red, Yellow, or Green while making sure that no two adjacent cells have the same color (i.e., no two cells that share vertical or horizontal sides have the same color).

Given n the number of rows of the grid, return the number of ways you can paint this grid. As the answer may grow large, the answer must be computed modulo 109 + 7.



Example 1:


Input: n = 1
Output: 12
Explanation: There are 12 possible way to paint the grid as shown.
Example 2:

Input: n = 5000
Output: 30228214


Constraints:

n == grid.length
1 <= n <= 5000
*/

class Solution {
    public int numOfWays(int n) {
        final int MOD = 1_000_000_007;
        long color2 = 6; // 121, 131, 212, 232, 313, 323
        long color3 = 6; // 123, 132, 213, 231, 312, 321

        for (int i = 1; i < n; ++i) {
            final long nextColor2 = color2 * 3 + color3 * 2;
            final long nextColor3 = color2 * 2 + color3 * 2;
            color2 = nextColor2 % MOD;
            color3 = nextColor3 % MOD;
        }

        return (int) ((color2 + color3) % MOD);

    }
}