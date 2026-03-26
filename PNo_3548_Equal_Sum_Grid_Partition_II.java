/*
You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

Each of the two resulting sections formed by the cut is non-empty.
The sum of elements in both sections is equal, or can be made equal by discounting at most one single cell in total (from either section).
If a cell is discounted, the rest of the section must remain connected.
Return true if such a partition exists; otherwise, return false.

Note: A section is connected if every cell in it can be reached from any other cell by moving up, down, left, or right through other cells in the section.



Example 1:

Input: grid = [[1,4],[2,3]]

Output: true

Explanation:



A horizontal cut after the first row gives sums 1 + 4 = 5 and 2 + 3 = 5, which are equal. Thus, the answer is true.
Example 2:

Input: grid = [[1,2],[3,4]]

Output: true

Explanation:



A vertical cut after the first column gives sums 1 + 3 = 4 and 2 + 4 = 6.
By discounting 2 from the right section (6 - 2 = 4), both sections have equal sums and remain connected. Thus, the answer is true.
Example 3:

Input: grid = [[1,2,4],[2,3,5]]

Output: false

Explanation:



A horizontal cut after the first row gives 1 + 2 + 4 = 7 and 2 + 3 + 5 = 10.
By discounting 3 from the bottom section (10 - 3 = 7), both sections have equal sums, but they do not remain connected as it splits the bottom section into two parts ([2] and [5]). Thus, the answer is false.
Example 4:

Input: grid = [[4,1,8],[3,2,6]]

Output: false

Explanation:

No valid cut exists, so the answer is false.



Constraints:

1 <= m == grid.length <= 105
1 <= n == grid[i].length <= 105
2 <= m * n <= 105
1 <= grid[i][j] <= 105
*/

class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long[] rSum = new long[m];
        long total = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rSum[i] += grid[i][j];
                total += grid[i][j];
            }
        }
        long[] rPre = new long[m + 1];
        for (int i = 1; i <= m; i++)
            rPre[i] = rPre[i - 1] + rSum[i - 1];

        long[] cSum = new long[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                cSum[j] += grid[i][j];

        long[] cPre = new long[n + 1];
        for (int j = 1; j <= n; j++)
            cPre[j] = cPre[j - 1] + cSum[j - 1];

        for (int k = 1; k < m; k++) {
            long top = rPre[k];
            if (top == total - top)
                return true;
        }
        for (int k = 1; k < n; k++) {
            long left = cPre[k];
            if (left == total - left)
                return true;
        }

        final int MAX = 100000;

        if (m >= 2) {
            int[] freq = new int[MAX + 1];
            for (int k = 1; k < m; k++) {
                for (int j = 0; j < n; j++) {
                    int v = grid[k - 1][j];
                    if (v <= MAX)
                        freq[v]++;
                }
                long top = rPre[k];
                long bot = total - top;
                if (top <= bot)
                    continue;
                long diff = top - bot;
                if (diff > MAX)
                    continue;
                int d = (int) diff;
                int h = k, w = n;
                int r0 = 0, r1 = k - 1, c0 = 0, c1 = n - 1;
                boolean ok = false;
                if (h == 1 && w == 1) {
                } else if (h >= 2 && w >= 2) {
                    if (freq[d] > 0)
                        ok = true;
                } else {
                    if (grid[r0][c0] == d || grid[r0][c1] == d || grid[r1][c0] == d || grid[r1][c1] == d)
                        ok = true;
                }
                if (ok)
                    return true;
            }
        }

        if (m >= 2) {
            int[] freq = new int[MAX + 1];
            for (int bh = 1; bh < m; bh++) {
                int row = m - bh;
                for (int j = 0; j < n; j++) {
                    int v = grid[row][j];
                    if (v <= MAX)
                        freq[v]++;
                }
                long top = rPre[m - bh];
                long bot = total - top;
                if (bot <= top)
                    continue;
                long diff = bot - top;
                if (diff > MAX)
                    continue;
                int d = (int) diff;
                int h = bh, w = n;
                int r0 = m - bh, r1 = m - 1, c0 = 0, c1 = n - 1;
                boolean ok = false;
                if (h == 1 && w == 1) {
                } else if (h >= 2 && w >= 2) {
                    if (freq[d] > 0)
                        ok = true;
                } else {
                    if (grid[r0][c0] == d || grid[r0][c1] == d || grid[r1][c0] == d || grid[r1][c1] == d)
                        ok = true;
                }
                if (ok)
                    return true;
            }
        }

        if (n >= 2) {
            int[] freq = new int[MAX + 1];
            for (int k = 1; k < n; k++) {
                int col = k - 1;
                for (int i = 0; i < m; i++) {
                    int v = grid[i][col];
                    if (v <= MAX)
                        freq[v]++;
                }
                long left = cPre[k];
                long right = total - left;
                if (left <= right)
                    continue;
                long diff = left - right;
                if (diff > MAX)
                    continue;
                int d = (int) diff;
                int h = m, w = k;
                int r0 = 0, r1 = m - 1, c0 = 0, c1 = k - 1;
                boolean ok = false;
                if (h == 1 && w == 1) {
                } else if (h >= 2 && w >= 2) {
                    if (freq[d] > 0)
                        ok = true;
                } else {
                    if (grid[r0][c0] == d || grid[r0][c1] == d || grid[r1][c0] == d || grid[r1][c1] == d)
                        ok = true;
                }
                if (ok)
                    return true;
            }
        }

        if (n >= 2) {
            int[] freq = new int[MAX + 1];
            for (int bw = 1; bw < n; bw++) {
                int col = n - bw;
                for (int i = 0; i < m; i++) {
                    int v = grid[i][col];
                    if (v <= MAX)
                        freq[v]++;
                }
                long left = cPre[n - bw];
                long right = total - left;
                if (right <= left)
                    continue;
                long diff = right - left;
                if (diff > MAX)
                    continue;
                int d = (int) diff;
                int h = m, w = bw;
                int r0 = 0, r1 = m - 1, c0 = n - bw, c1 = n - 1;
                boolean ok = false;
                if (h == 1 && w == 1) {
                } else if (h >= 2 && w >= 2) {
                    if (freq[d] > 0)
                        ok = true;
                } else {
                    if (grid[r0][c0] == d || grid[r0][c1] == d || grid[r1][c0] == d || grid[r1][c1] == d)
                        ok = true;
                }
                if (ok)
                    return true;
            }
        }

        return false;
    }
}