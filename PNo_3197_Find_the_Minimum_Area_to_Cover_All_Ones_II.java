/*
You are given a 2D binary array grid. You need to find 3 non-overlapping rectangles having non-zero areas with horizontal and vertical sides such that all the 1's in grid lie inside these rectangles.

Return the minimum possible sum of the area of these rectangles.

Note that the rectangles are allowed to touch.



Example 1:

Input: grid = [[1,0,1],[1,1,1]]

Output: 5

Explanation:



The 1's at (0, 0) and (1, 0) are covered by a rectangle of area 2.
The 1's at (0, 2) and (1, 2) are covered by a rectangle of area 2.
The 1 at (1, 1) is covered by a rectangle of area 1.
Example 2:

Input: grid = [[1,0,1,0],[0,1,0,1]]

Output: 5

Explanation:



The 1's at (0, 0) and (0, 2) are covered by a rectangle of area 3.
The 1 at (1, 1) is covered by a rectangle of area 1.
The 1 at (1, 3) is covered by a rectangle of area 1.


Constraints:

1 <= grid.length, grid[i].length <= 30
grid[i][j] is either 0 or 1.
The input is generated such that there are at least three 1's in grid.
*/

class Solution {
    public int minimumSum(int[][] grid) {
        return Math.min(calculateSum(grid), calculateSum(rotateGrid(grid)));
    }

    private int calculateSum(int[][] a) {
        int m = a.length;
        int n = a[0].length;

        int[][] topLeft = getMinimumArea(a);
        int[][] bottomLeft = rotateGrid(rotateGrid(rotateGrid(getMinimumArea(rotateGrid(a)))));
        int[][] bottomRight = rotateGrid(rotateGrid(getMinimumArea(rotateGrid(rotateGrid(a)))));
        int[][] topRight = rotateGrid(getMinimumArea(rotateGrid(rotateGrid(rotateGrid(a)))));

        int ans = Integer.MAX_VALUE;

        if (m >= 3) {
            for (int i = 1; i < m; i++) {
                int left = n;
                int right = 0;
                int top = m;
                int bottom = 0;

                int[][] lr = new int[m][2];
                for (int row = 0; row < m; row++) {
                    int l = -1;
                    int r = 0;
                    for (int col = 0; col < n; col++) {
                        if (a[row][col] > 0) {
                            if (l < 0) {
                                l = col;
                            }
                            r = col;
                        }
                    }
                    lr[row][0] = l;
                    lr[row][1] = r;
                }

                for (int j = i + 1; j < m; j++) {
                    int l = lr[j - 1][0];
                    if (l >= 0) {
                        left = Math.min(left, l);
                        right = Math.max(right, lr[j - 1][1]);
                        top = Math.min(top, j - 1);
                        bottom = j - 1;
                    }
                    ans = Math.min(ans, topLeft[i][n] + (right - left + 1) * (bottom - top + 1) + bottomLeft[j][n]);
                }
            }
        }

        if (m >= 2 && n >= 2) {
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    ans = Math.min(ans, topLeft[i][n] + bottomLeft[i][j] + bottomRight[i][j]);
                    ans = Math.min(ans, topLeft[i][j] + topRight[i][j] + bottomLeft[i][n]);
                }
            }
        }
        return ans;
    }

    private int[][] getMinimumArea(int[][] a) {
        int m = a.length;
        int n = a[0].length;
        int[][] result = new int[m + 1][n + 1];
        int[][] borders = new int[n][3];

        for (int j = 0; j < n; j++) {
            borders[j][0] = -1;
        }

        for (int i = 0; i < m; i++) {
            int left = -1;
            int right = 0;
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 1) {
                    if (left < 0) {
                        left = j;
                    }
                    right = j;
                }
                int[] preB = borders[j];
                if (left < 0) {
                    result[i + 1][j + 1] = result[i][j + 1];
                } else if (preB[0] < 0) {
                    result[i + 1][j + 1] = right - left + 1;
                    borders[j][0] = i;
                    borders[j][1] = left;
                    borders[j][2] = right;
                } else {
                    int l = Math.min(preB[1], left);
                    int r = Math.max(preB[2], right);
                    result[i + 1][j + 1] = (r - l + 1) * (i - preB[0] + 1);
                    borders[j][1] = l;
                    borders[j][2] = r;
                }
            }
        }
        return result;
    }

    private int[][] rotateGrid(int[][] a) {
        int m = a.length;
        int n = a[0].length;
        int[][] b = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[j][m - 1 - i] = a[i][j];
            }
        }
        return b;
    }
}