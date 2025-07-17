/*
You are given an integer array nums and a positive integer k.
A subsequence sub of nums with length x is called valid if it satisfies:

(sub[0] + sub[1]) % k == (sub[1] + sub[2]) % k == ... == (sub[x - 2] + sub[x - 1]) % k.
Return the length of the longest valid subsequence of nums.


Example 1:

Input: nums = [1,2,3,4,5], k = 2

Output: 5

Explanation:

The longest valid subsequence is [1, 2, 3, 4, 5].

Example 2:

Input: nums = [1,4,2,3,1,4], k = 3

Output: 4

Explanation:

The longest valid subsequence is [1, 4, 1, 4].



Constraints:

2 <= nums.length <= 103
1 <= nums[i] <= 107
1 <= k <= 103
*/

class Solution {
    public int maximumLength(int[] vals, int mod) {
        int len = vals.length;
        if (mod == 1) {
            return len;
        }
        int maxLen = 2;
        int[] modVals = new int[len];
        for (int i = 0; i < len; i++) {
            modVals[i] = vals[i] % mod;
        }
        for (int i = 0; i < mod; i++) {
            int[] dpArr = new int[mod];
            for (int j = 0; j < len; j++) {
                dpArr[modVals[j]] = dpArr[(i - modVals[j] + mod) % mod] + 1;
                maxLen = Math.max(maxLen, dpArr[modVals[j]]);
            }
        }
        return maxLen;
    }
}