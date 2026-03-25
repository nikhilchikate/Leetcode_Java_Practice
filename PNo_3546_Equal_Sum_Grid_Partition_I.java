/*
You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

Each of the two resulting sections formed by the cut is non-empty.
The sum of the elements in both sections is equal.
Return true if such a partition exists; otherwise return false.



Example 1:

Input: grid = [[1,4],[2,3]]

Output: true

Explanation:



A horizontal cut between row 0 and row 1 results in two non-empty sections, each with a sum of 5. Thus, the answer is true.

Example 2:

Input: grid = [[1,3],[2,4]]

Output: false

Explanation:

No horizontal or vertical cut results in two non-empty sections with equal sums. Thus, the answer is false.



Constraints:

1 <= m == grid.length <= 105
1 <= n == grid[i].length <= 105
2 <= m * n <= 105
1 <= grid[i][j] <= 105
*/

class Solution {
    public boolean canPartitionGrid(int[][] m) {
        final long[] rS = new long[m.length];
        long s = 0;
        for (int i = 0; i < rS.length; ++i) {
            for (final int v : m[i]) {
                rS[i] += v;
            }
            s += rS[i];
        }
        if ((s % 2) != 0) {
            return false;
        }
        s /= 2;
        long t = 0;
        for (int i = 0; i < rS.length - 1 && t < s; ++i) {
            t += rS[i];
        }
        if (t == s) {
            return true;
        }
        t = 0;
        for (int j = 0; j < m[0].length - 1 && t < s; ++j) {
            for (int i = 0; i < m.length; ++i) {
                t += m[i][j];
            }
        }
        return t == s;
    }
}