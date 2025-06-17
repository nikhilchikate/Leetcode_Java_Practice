/*
You are given three integers n, m, k. A good array arr of size n is defined as follows:

Each element in arr is in the inclusive range [1, m].
Exactly k indices i (where 1 <= i < n) satisfy the condition arr[i - 1] == arr[i].
Return the number of good arrays that can be formed.

Since the answer may be very large, return it modulo 109 + 7.



Example 1:

Input: n = 3, m = 2, k = 1

Output: 4

Explanation:

There are 4 good arrays. They are [1, 1, 2], [1, 2, 2], [2, 1, 1] and [2, 2, 1].
Hence, the answer is 4.
Example 2:

Input: n = 4, m = 2, k = 2

Output: 6

Explanation:

The good arrays are [1, 1, 1, 2], [1, 1, 2, 2], [1, 2, 2, 2], [2, 1, 1, 1], [2, 2, 1, 1] and [2, 2, 2, 1].
Hence, the answer is 6.
Example 3:

Input: n = 5, m = 2, k = 0

Output: 2

Explanation:

The good arrays are [1, 2, 1, 2, 1] and [2, 1, 2, 1, 2]. Hence, the answer is 2.


Constraints:

1 <= n <= 105
1 <= m <= 105
0 <= k <= n - 1
*/

class Solution {
    public int countGoodArrays(int len, int valRng, int maxSim) {
        if (factVals[0] == 0)
            factVals[0] = 1;
        long result = valRng * power(valRng - 1, len - 1 - maxSim) % MOD * combinations(len - 1, len - 1 - maxSim)
                % MOD;
        return (int) result;
    }

    int MOD = 1_000_000_007;
    static long[] invVals = new long[100001];
    static int[] factVals = new int[100001];

    public long power(int baseVal, int expVal) {
        long res = 1;
        long base = baseVal;
        while (expVal > 0) {
            if ((expVal & 1) == 1)
                res = res * base % MOD;
            base = base * base % MOD;
            expVal /= 2;
        }
        return res;
    }

    public long combinations(int nVal, int kVal) {
        return (long) getFact(nVal) * getInv(getFact(nVal - kVal)) % MOD * getInv(getFact(kVal)) % MOD;
    }

    public long getFact(int num) {
        if (factVals[num] != 0)
            return factVals[num];
        return factVals[num] = (int) (getFact(num - 1) * num % MOD);
    }

    public long getInv(long val) {
        if (val == 1)
            return val;
        return MOD - MOD / val * getInv(MOD % val) % MOD;
    }
}