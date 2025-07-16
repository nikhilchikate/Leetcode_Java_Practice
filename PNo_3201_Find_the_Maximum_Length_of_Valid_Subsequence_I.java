/*
You are given an integer array nums.
A subsequence sub of nums with length x is called valid if it satisfies:

(sub[0] + sub[1]) % 2 == (sub[1] + sub[2]) % 2 == ... == (sub[x - 2] + sub[x - 1]) % 2.
Return the length of the longest valid subsequence of nums.

A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.



Example 1:

Input: nums = [1,2,3,4]

Output: 4

Explanation:

The longest valid subsequence is [1, 2, 3, 4].

Example 2:

Input: nums = [1,2,1,1,2,1,2]

Output: 6

Explanation:

The longest valid subsequence is [1, 2, 1, 2, 1, 2].

Example 3:

Input: nums = [1,3]

Output: 2

Explanation:

The longest valid subsequence is [1, 3].



Constraints:

2 <= nums.length <= 2 * 105
1 <= nums[i] <= 107
*/

class Solution {
    public int maximumLength(int[] arr) {
        int[] cnts = new int[2];
        int[] altLens = new int[2];

        for (int val : arr) {
            int rem = val % 2;
            cnts[rem]++;
            altLens[rem] = altLens[1 - rem] + 1;
        }

        return Math.max(
                Math.max(cnts[0], cnts[1]),
                Math.max(altLens[0], altLens[1])
        );
    }
}