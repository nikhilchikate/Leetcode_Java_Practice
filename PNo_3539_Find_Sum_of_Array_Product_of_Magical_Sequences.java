/*
You are given two integers, m and k, and an integer array nums.

A sequence of integers seq is called magical if:
seq has a size of m.
0 <= seq[i] < nums.length
The binary representation of 2seq[0] + 2seq[1] + ... + 2seq[m - 1] has k set bits.
The array product of this sequence is defined as prod(seq) = (nums[seq[0]] * nums[seq[1]] * ... * nums[seq[m - 1]]).

Return the sum of the array products for all valid magical sequences.

Since the answer may be large, return it modulo 109 + 7.

A set bit refers to a bit in the binary representation of a number that has a value of 1.



Example 1:

Input: m = 5, k = 5, nums = [1,10,100,10000,1000000]

Output: 991600007

Explanation:

All permutations of [0, 1, 2, 3, 4] are magical sequences, each with an array product of 1013.

Example 2:

Input: m = 2, k = 2, nums = [5,4,3,2,1]

Output: 170

Explanation:

The magical sequences are [0, 1], [0, 2], [0, 3], [0, 4], [1, 0], [1, 2], [1, 3], [1, 4], [2, 0], [2, 1], [2, 3], [2, 4], [3, 0], [3, 1], [3, 2], [3, 4], [4, 0], [4, 1], [4, 2], and [4, 3].

Example 3:

Input: m = 1, k = 1, nums = [28]

Output: 28

Explanation:

The only magical sequence is [0].



Constraints:

1 <= k <= m <= 30
1 <= nums.length <= 50
1 <= nums[i] <= 108
*/

class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int MX = 31;

    private static final long[] F = new long[MX];
    private static final long[] IF = new long[MX];

    static {
        F[0] = 1;
        for (int i = 1; i < MX; ++i) {
            F[i] = F[i - 1] * i % MOD;
        }

        IF[MX - 1] = pow(F[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; --i) {
            IF[i - 1] = IF[i] * i % MOD;
        }
    }

    private static long pow(long x, int e) {
        long r = 1;
        for (; e > 0; e /= 2) {
            if (e % 2 > 0) {
                r = r * x % MOD;
            }
            x = x * x % MOD;
        }
        return r;
    }

    public int magicalSum(int M, int K, int[] A) {
        int N = A.length;
        int[][] PV = new int[N][M + 1];

        for (int i = 0; i < N; i++) {
            PV[i][0] = 1;
            for (int j = 1; j <= M; ++j) {
                PV[i][j] = (int) ((long) PV[i][j - 1] * A[i] % MOD);
            }
        }

        int[][][][] D = new int[N][M + 1][M / 2 + 1][K + 1];
        for (int[][][] a : D) {
            for (int[][] b : a) {
                for (int[] c : b) {
                    Arrays.fill(c, -1);
                }
            }
        }

        return (int) (dfs(0, M, 0, K, PV, D) * F[M] % MOD);
    }

    private long dfs(int i, int lM, int Cr, int lK, int[][] PV, int[][][][] D) {
        int bc = Integer.bitCount(Cr);

        if (bc + lM < lK) {
            return 0;
        }
        if (i == PV.length) {
            return lM == 0 && bc == lK ? 1 : 0;
        }
        if (D[i][lM][Cr][lK] != -1) {
            return D[i][lM][Cr][lK];
        }

        long R = 0;

        for (int j = 0; j <= lM; ++j) {
            int b = (Cr + j) & 1;

            if (b <= lK) {
                long r = dfs(i + 1, lM - j, (Cr + j) >> 1, lK - b, PV, D);
                R = (R + r * PV[i][j] % MOD * IF[j]) % MOD;
            }
        }

        return D[i][lM][Cr][lK] = (int) R;
    }
}