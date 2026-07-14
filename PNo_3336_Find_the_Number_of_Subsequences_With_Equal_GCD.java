/*
You are given an integer array nums.

Your task is to find the number of pairs of non-empty subsequences (seq1, seq2) of nums that satisfy the following conditions:

The subsequences seq1 and seq2 are disjoint, meaning no index of nums is common between them.
The GCD of the elements of seq1 is equal to the GCD of the elements of seq2.
Return the total number of such pairs.

Since the answer may be very large, return it modulo 109 + 7.

 

Example 1:

Input: nums = [1,2,3,4]

Output: 10

Explanation:

The subsequence pairs which have the GCD of their elements equal to 1 are:

([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
([1, 2, 3, 4], [1, 2, 3, 4])
Example 2:

Input: nums = [10,20,30]

Output: 2

Explanation:

The subsequence pairs which have the GCD of their elements equal to 10 are:

([10, 20, 30], [10, 20, 30])
([10, 20, 30], [10, 20, 30])
Example 3:

Input: nums = [1,1,1,1]

Output: 50

 

Constraints:

1 <= nums.length <= 200
1 <= nums[i] <= 200
*/

class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int MX = 201;

    private static final int[][] lcms = new int[MX][MX];
    private static final int[] pow2 = new int[MX];
    private static final int[] pow3 = new int[MX];
    private static final int[] mu = new int[MX];
    private static boolean initialized = false;

    public Solution() {
        if (initialized) {
            return;
        }
        initialized = true;

        for (int i = 1; i < MX; i++) {
            for (int j = 1; j < MX; j++) {
                lcms[i][j] = lcm(i, j);
            }
        }

        pow2[0] = pow3[0] = 1;
        for (int i = 1; i < MX; i++) {
            pow2[i] = pow2[i - 1] * 2 % MOD;
            pow3[i] = (int) ((long) pow3[i - 1] * 3 % MOD);
        }

        mu[1] = 1;
        for (int i = 1; i < MX; i++) {
            for (int j = i * 2; j < MX; j += i) {
                mu[j] -= mu[i];
            }
        }
    }

    public int subsequencePairCount(int[] nums) {
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }

        int[] cnt = new int[m + 1];
        for (int x : nums) {
            cnt[x]++;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = i * 2; j <= m; j += i) {
                cnt[i] += cnt[j]; 
            }
        }

        int[][] f = new int[m + 1][m + 1];
        for (int g1 = 1; g1 <= m; g1++) {
            for (int g2 = 1; g2 <= m; g2++) {
                int l = lcms[g1][g2];
                int c = l <= m ? cnt[l] : 0;
                int c1 = cnt[g1];
                int c2 = cnt[g2];
                f[g1][g2] = (int) (((long) pow3[c] * pow2[c1 + c2 - c * 2] - pow2[c1] - pow2[c2] + 1) % MOD);
            }
        }

        long ans = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m / i; j++) {
                for (int k = 1; k <= m / i; k++) {
                    ans += mu[j] * mu[k] * f[j * i][k * i];
                }
            }
        }
        return (int) ((ans % MOD + MOD) % MOD); 
    }

    private static int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

    private static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }
}