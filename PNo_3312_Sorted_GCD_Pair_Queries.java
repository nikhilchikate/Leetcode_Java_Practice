/*
You are given an integer array nums of length n and an integer array queries.

Let gcdPairs denote an array obtained by calculating the GCD of all possible pairs (nums[i], nums[j]), where 0 <= i < j < n, and then sorting these values in ascending order.

For each query queries[i], you need to find the element at index queries[i] in gcdPairs.

Return an integer array answer, where answer[i] is the value at gcdPairs[queries[i]] for each query.

The term gcd(a, b) denotes the greatest common divisor of a and b.

 

Example 1:

Input: nums = [2,3,4], queries = [0,2,2]

Output: [1,2,2]

Explanation:

gcdPairs = [gcd(nums[0], nums[1]), gcd(nums[0], nums[2]), gcd(nums[1], nums[2])] = [1, 2, 1].

After sorting in ascending order, gcdPairs = [1, 1, 2].

So, the answer is [gcdPairs[queries[0]], gcdPairs[queries[1]], gcdPairs[queries[2]]] = [1, 2, 2].

Example 2:

Input: nums = [4,4,2,1], queries = [5,3,1,0]

Output: [4,2,1,1]

Explanation:

gcdPairs sorted in ascending order is [1, 1, 1, 2, 2, 4].

Example 3:

Input: nums = [2,2], queries = [0,0]

Output: [2,2]

Explanation:

gcdPairs = [2].

 

Constraints:

2 <= n == nums.length <= 105
1 <= nums[i] <= 5 * 104
1 <= queries.length <= 105
0 <= queries[i] < n * (n - 1) / 2
*/

class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums)
            max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums)
            freq[x]++;

        long[] multiples = new long[max + 1];
        for (int g = max; g >= 1; g--) {
            long cnt = 0;
            for (int m = g; m <= max; m += g) {
                cnt += freq[m];
            }
            multiples[g] = cnt;
        }

        long[] exact = new long[max + 1];
        for (int g = max; g >= 1; g--) {
            long total = multiples[g] * (multiples[g] - 1) / 2;
            for (int m = 2 * g; m <= max; m += g) {
                total -= exact[m];
            }
            exact[g] = total;
        }

        long[] pref = new long[max + 1];
        for (int g = 1; g <= max; g++) {
            pref[g] = pref[g - 1] + exact[g];
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long k = queries[i] + 1;
            int lo = 1, hi = max;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (pref[mid] >= k)
                    hi = mid;
                else
                    lo = mid + 1;
            }
            ans[i] = lo;
        }
        return ans;
    }
}