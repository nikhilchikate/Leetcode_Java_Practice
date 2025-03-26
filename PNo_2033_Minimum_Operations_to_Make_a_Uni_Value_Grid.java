/*
You are given a 2D integer grid of size m x n and an integer x. In one operation, you can add x to or subtract x from any element in the grid.

A uni-value grid is a grid where all the elements of it are equal.

Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.



Example 1:


Input: grid = [[2,4],[6,8]], x = 2
Output: 4
Explanation: We can make every element equal to 4 by doing the following:
- Add x to 2 once.
- Subtract x from 6 once.
- Subtract x from 8 twice.
A total of 4 operations were used.
Example 2:


Input: grid = [[1,5],[2,3]], x = 1
Output: 5
Explanation: We can make every element equal to 3.
Example 3:


Input: grid = [[1,2],[3,4]], x = 2
Output: -1
Explanation: It is impossible to make every element equal.


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 105
1 <= m * n <= 105
1 <= x, grid[i][j] <= 104
*/

class Solution {
    public int minOperations(int[][] grid, int x) {
        int rows = grid.length, cols = grid[0].length;
        int size = rows * cols;
        if (size < 2) return 0;

        int[] cnt = new int[10001];
        int rem = grid[0][0] % x;

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                int val = grid[i][j];
                if (val % x != rem) return -1;
                cnt[val]++;
            }
        }

        size = (size + 1) / 2;
        int tot = 0;
        int mid = 0;

        for (int val = 0; val < cnt.length; ++val) {
            if (cnt[val] == 0) continue;
            tot += cnt[val];
            if (tot >= size) {
                mid = val;
                break;
            }
        }

        int res = 0;
        for (int val = 0; val <= mid; ++val) {
            if (cnt[val] == 0) continue;
            res += cnt[val] * (mid - val) / x;
        }

        for (int val = mid + 1; val < cnt.length; ++val) {
            if (cnt[val] == 0) continue;
            res += cnt[val] * (val - mid) / x;
        }
        return res;
    }
}