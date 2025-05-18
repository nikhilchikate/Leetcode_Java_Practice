/*
You are given two integers m and n. Consider an m x n grid where each cell is initially white. You can paint each cell red, green, or blue. All cells must be painted.

Return the number of ways to color the grid with no two adjacent cells having the same color. Since the answer can be very large, return it modulo 109 + 7.



Example 1:


Input: m = 1, n = 1
Output: 3
Explanation: The three possible colorings are shown in the image above.
Example 2:


Input: m = 1, n = 2
Output: 6
Explanation: The six possible colorings are shown in the image above.
Example 3:

Input: m = 5, n = 5
Output: 580986


Constraints:

1 <= m <= 5
1 <= n <= 1000
*/

class Solution {
    static int modulo = (int) 1e9 + 7;
    static long[][][] transitionMatrices = {
            { { 2 } },
            { { 3 } },
            { { 3, 2 }, { 2, 2 } },
            {
                    { 3, 2, 1, 2 },
                    { 2, 2, 1, 2 },
                    { 1, 1, 2, 1 },
                    { 2, 2, 1, 2 },
            },
            {
                    { 3, 2, 2, 1, 0, 1, 2, 2 },
                    { 2, 2, 2, 1, 1, 1, 1, 1 },
                    { 2, 2, 2, 1, 0, 1, 2, 2 },
                    { 1, 1, 1, 2, 1, 1, 1, 1 },
                    { 0, 1, 0, 1, 2, 1, 0, 1 },
                    { 1, 1, 1, 1, 1, 2, 1, 1 },
                    { 2, 1, 2, 1, 0, 1, 2, 1 },
                    { 2, 1, 2, 1, 1, 1, 1, 2 },
            },
    };

    public int colorTheGrid(int m, int n) {
        int stateCount = m == 1 ? 1 : (int) (Math.pow(2, m - 2));
        long[][] dp = new long[stateCount][1];

        for (long[] row : dp) {
            row[0] = m == 1 ? 3 : 6;
        }

        long[][] matrix = transitionMatrices[m - 1];
        dp = multiplyMatrices(matrixPower(matrix, n - 1, modulo), dp, modulo);

        long result = 0L;
        for (long[] row : dp) {
            result = (result + row[0]) % modulo;
        }

        return (int) result;
    }

    private long[][] multiplyMatrices(long[][] a, long[][] b, long mod) {
        int rowsA = a.length;
        int colsB = b[0].length;
        int colsA = b.length;
        long[][] result = new long[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] = (result[i][j] + a[i][k] * b[k][j]) % mod;
                }
            }
        }
        return result;
    }

    private long[][] matrixPower(long[][] base, long exponent, long mod) {
        int size = base.length;
        long[][] result = new long[size][size];
        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }

        while (exponent != 0) {
            if ((exponent & 1) != 0) {
                result = multiplyMatrices(result, base, mod);
            }
            base = multiplyMatrices(base, base, mod);
            exponent >>= 1;
        }
        return result;
    }
}