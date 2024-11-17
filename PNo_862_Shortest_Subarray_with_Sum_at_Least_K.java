/*
Given an integer array nums and an integer k, return the length of the shortest non-empty subarray of nums with a sum of at least k. If there is no such subarray, return -1.

A subarray is a contiguous part of an array.



Example 1:

Input: nums = [1], k = 1
Output: 1
Example 2:

Input: nums = [1,2], k = 4
Output: -1
Example 3:

Input: nums = [2,-1,2], k = 3
Output: 3


Constraints:

1 <= nums.length <= 105
-105 <= nums[i] <= 105
1 <= k <= 109
*/

class Solution {
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i]; // Compute prefix sums
        }

        int[] q = new int[n + 1];
        int l = 0, r = 0;
        int res = n + 1;

        for (int i = 0; i < sum.length; i++) {
            // Try to find the shortest subarray with sum >= k
            while (r > l && sum[i] >= sum[q[l]] + k) {
                res = Math.min(res, i - q[l++]); // Update result
            }
            // Maintain queue in increasing order of prefix sums
            while (r > l && sum[i] <= sum[q[r - 1]]) {
                r--; // Remove larger prefix sums
            }
            q[r++] = i; // Add current index to the queue
        }

        return res <= n ? res : -1; // If no valid subarray found, return -1
    }
}