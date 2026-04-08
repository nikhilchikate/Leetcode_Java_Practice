/*
You are given an integer array nums of length n and a 2D integer array queries of size q, where queries[i] = [li, ri, ki, vi].

For each query, you must apply the following operations in order:

Set idx = li.
While idx <= ri:
Update: nums[idx] = (nums[idx] * vi) % (109 + 7)
Set idx += ki.
Return the bitwise XOR of all elements in nums after processing all queries.



Example 1:

Input: nums = [1,1,1], queries = [[0,2,1,4]]

Output: 4

Explanation:

A single query [0, 2, 1, 4] multiplies every element from index 0 through index 2 by 4.
The array changes from [1, 1, 1] to [4, 4, 4].
The XOR of all elements is 4 ^ 4 ^ 4 = 4.
Example 2:

Input: nums = [2,3,1,5,4], queries = [[1,4,2,3],[0,2,1,2]]

Output: 31

Explanation:

The first query [1, 4, 2, 3] multiplies the elements at indices 1 and 3 by 3, transforming the array to [2, 9, 1, 15, 4].
The second query [0, 2, 1, 2] multiplies the elements at indices 0, 1, and 2 by 2, resulting in [4, 18, 2, 15, 4].
Finally, the XOR of all elements is 4 ^ 18 ^ 2 ^ 15 ^ 4 = 31.​​​​​​​​​​​​​​


Constraints:

1 <= n == nums.length <= 103
1 <= nums[i] <= 109
1 <= q == queries.length <= 103
queries[i] = [li, ri, ki, vi]
0 <= li <= ri < n
1 <= ki <= n
1 <= vi <= 105
*/

class Solution {
    public int xorAfterQueries(int[] nums, int[][] queries) {
        final long MOD = (long) 1e9 + 7;
        int n = nums.length;
        long[] mult = new long[n];
        Arrays.fill(mult, 1L);

        long[] diff = new long[n + 1];
        Arrays.fill(diff, 1L);

        for (var q : queries) {
            int l = q[0], r = q[1], k = q[2];
            long v = q[3];
            if (k == 1) {
                diff[l] = diff[l] * v % MOD;
                diff[r + 1] = diff[r + 1] * modInverse(v, MOD) % MOD;
            } else {
                for (int idx = l; idx <= r; idx += k)
                    mult[idx] = mult[idx] * v % MOD;
            }
        }

        long cur = 1L;
        for (int i = 0; i < n; i++) {
            cur = cur * diff[i] % MOD;
            mult[i] = mult[i] * cur % MOD;
        }

        int ans = 0;
        for (int i = 0; i < n; i++)
            ans ^= (int) (nums[i] * mult[i] % MOD);

        return ans;
    }

    private long modInverse(long a, long mod) {
        return modPow(a, mod - 2, mod);
    }

    private long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}