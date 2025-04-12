/*
You are given two positive integers n and k.

An integer x is called k-palindromic if:

x is a palindrome.
x is divisible by k.
An integer is called good if its digits can be rearranged to form a k-palindromic integer. For example, for k = 2, 2020 can be rearranged to form the k-palindromic integer 2002, whereas 1010 cannot be rearranged to form a k-palindromic integer.

Return the count of good integers containing n digits.

Note that any integer must not have leading zeros, neither before nor after rearrangement. For example, 1010 cannot be rearranged to form 101.



Example 1:

Input: n = 3, k = 5

Output: 27

Explanation:

Some of the good integers are:

551 because it can be rearranged to form 515.
525 because it is already k-palindromic.
Example 2:

Input: n = 1, k = 4

Output: 2

Explanation:

The two good integers are 4 and 8.

Example 3:

Input: n = 5, k = 6

Output: 2468



Constraints:

1 <= n <= 10
1 <= k <= 9
*/

class Solution {
    private static final int[][] MOD10 = new int[][] {
            { 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
            { 1, 3, 2, 6, 4, 5, 1, 3, 2, 6, 4 }, { 1, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };

    private static final int[] FACT = new int[] { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800 };

    public long countGoodIntegers(int n, int k) {
        if (n == 1)
            return 9 / k;

        return bktk(0, 0, n, k, new int[(n + 1) / 2]);
    }

    private long bktk(int i, int ld, int len, int mod, int[] d) {
        if (i == d.length) {
            if (d[i - 1] == 0)
                return 0;
            if (len % 2 == 1) {
                if (d[i - 2] == 0)
                    return hasPalPerm(d, len, mod, 0, 1 << (i - 2), 0) ? permCnt(d, len, 0) : 0;
                long ans = 0;
                for (int c = 0; c < d.length; c++) {
                    if (c > 0 && d[c] == d[c - 1])
                        continue;
                    if (hasPalPerm(d, len, mod, (int) ((long) d[c] * MOD10[mod][len / 2] % mod), 1 << c, 0)) {
                        ans += permCnt(d, len, d[c]);
                    }
                }
                return ans;
            }

            return hasPalPerm(d, len, mod, 0, 0, 0) ? permCnt(d, len, -1) : 0;
        }
        long ans = 0;
        for (int dig = ld; dig <= 9; dig++) {
            d[i] = dig;
            ans += bktk(i + 1, dig, len, mod, d);
        }

        return ans;
    }

    private boolean hasPalPerm(int[] d, int len, int mod, int rem, int mask, int cnt) {
        if (cnt == len / 2)
            return rem == 0;
        for (int i = d.length - 1; i >= 0; i--) {
            if (cnt == 0 && d[i] == 0)
                break;
            if ((mask & (1 << i)) != 0)
                continue;
            long term = (long) d[i] * (MOD10[mod][len - cnt - 1] + MOD10[mod][cnt]) % mod;
            if (hasPalPerm(d, len, mod, (int) ((rem + term) % mod), mask | (1 << i), cnt + 1))
                return true;
        }

        return false;
    }

    private int permCnt(int[] d, int len, int center) {
        int nzIdx = 0;
        while (d[nzIdx] == 0)
            nzIdx++;
        int zCnt = nzIdx * 2 - (center == 0 ? 1 : 0);
        long perms = (long) FACT[len] / FACT[zCnt];
        int stk = 1;
        for (int i = nzIdx + 1; i < d.length; i++) {
            if (d[i] == d[i - 1]) {
                stk++;
            } else {
                perms /= FACT[2 * stk - (d[i - 1] == center ? 1 : 0)];
                stk = 1;
            }
        }
        perms /= FACT[2 * stk - (d[d.length - 1] == center ? 1 : 0)];

        return (int) (perms * (len - zCnt) / len);
    }
}