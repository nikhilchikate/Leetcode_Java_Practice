/*
You are given 3 positive integers zero, one, and limit.

A binary array arr is called stable if:

The number of occurrences of 0 in arr is exactly zero.
The number of occurrences of 1 in arr is exactly one.
Each subarray of arr with a size greater than limit must contain both 0 and 1.
Return the total number of stable binary arrays.

Since the answer may be very large, return it modulo 109 + 7.



Example 1:

Input: zero = 1, one = 1, limit = 2

Output: 2

Explanation:

The two possible stable binary arrays are [1,0] and [0,1].

Example 2:

Input: zero = 1, one = 2, limit = 1

Output: 1

Explanation:

The only possible stable binary array is [1,0,1].

Example 3:

Input: zero = 3, one = 3, limit = 2

Output: 14

Explanation:

All the possible stable binary arrays are [0,0,1,0,1,1], [0,0,1,1,0,1], [0,1,0,0,1,1], [0,1,0,1,0,1], [0,1,0,1,1,0], [0,1,1,0,0,1], [0,1,1,0,1,0], [1,0,0,1,0,1], [1,0,0,1,1,0], [1,0,1,0,0,1], [1,0,1,0,1,0], [1,0,1,1,0,0], [1,1,0,0,1,0], and [1,1,0,1,0,0].



Constraints:

1 <= zero, one, limit <= 1000
*/

class Solution {
    private static final int mod = (int) 1e9 + 7;
    private static final long modLong = mod;

    private static int add(int a, int b) {
        return (a + b) % mod;
    }

    private static int sub(int a, int b) {
        return (a + mod - b) % mod;
    }

    private static int mul(int a, int b) {
        return (int) ((long) a * b % modLong);
    }

    public static int numberOfStableArrays(int zero, int one, int limit) {
        int m = Math.max(zero, one);
        int[][] sum = new int[m + 1][m + 1];
        int[] pre = new int[m + 1];
        int[] cur = sum[0];
        cur[0] = 1;

        for (int c = 1; c <= m; c++) {
            int end = Math.min(c * limit, m);
            int s = 0;

            for (int i = 0; i <= end; i++) {
                pre[i] = s;
                s = add(s, cur[i]);
            }

            cur = sum[c];

            for (int i = c; i <= end; i++)
                cur[i] = i < limit ? pre[i] : sub(pre[i], pre[i - limit]);
        }

        int res = m <= limit ? 2 : 0;
        int c0 = sum[1][zero];
        int c1 = sum[1][one];

        for (int i = 2; i <= m; i++) {
            int p0 = c0;
            int p1 = c1;

            c0 = sum[i][zero];
            c1 = sum[i][one];

            res = add(res, mul(c0, p1));
            res = add(res, mul(c1, p0));

            int t = mul(c0, c1);
            res = add(res, add(t, t));
        }

        return res;
    }
}