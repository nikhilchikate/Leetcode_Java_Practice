/*
Given an integer array nums and an integer k, find three non-overlapping subarrays of length k with maximum sum and return them.

Return the result as a list of indices representing the starting position of each interval (0-indexed). If there are multiple answers, return the lexicographically smallest one.



Example 1:

Input: nums = [1,2,1,2,6,7,5,1], k = 2
Output: [0,3,5]
Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
Example 2:

Input: nums = [1,2,1,2,1,2,1,2,1], k = 2
Output: [0,2,4]


Constraints:

1 <= nums.length <= 2 * 104
1 <= nums[i] < 216
1 <= k <= floor(nums.length / 3)
*/

class Solution {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        // Initialize best start indices for single, double, and triple subarrays
        int bss = 0;
        int[] bds = { 0, k };
        int[] bts = { 0, k, k * 2 };

        // Calculate the initial sums for the first three subarrays
        int s1 = 0;
        for (int i = 0; i < k; i++) {
            s1 += nums[i];
        }

        int s2 = 0; // Sum of the second subarray
        for (int i = k; i < k * 2; i++) {
            s2 += nums[i];
        }

        int s3 = 0; // Sum of the third subarray
        for (int i = k * 2; i < k * 3; i++) {
            s3 += nums[i];
        }

        // Initialize best sums for single, double, and triple subarrays
        int bs1 = s1;
        int bs2 = s1 + s2;
        int bs3 = s1 + s2 + s3;

        // Sliding window start indices
        int si = 1;
        int di = k + 1;
        int ti = k * 2 + 1;

        // Slide the windows across the array
        while (ti <= nums.length - k) {
            // Update the sums by sliding the windows
            s1 = s1 - nums[si - 1] + nums[si + k - 1];
            s2 = s2 - nums[di - 1] + nums[di + k - 1];
            s3 = s3 - nums[ti - 1] + nums[ti + k - 1];

            // Update the best single subarray if a better sum is found
            if (s1 > bs1) {
                bss = si;
                bs1 = s1;
            }

            // Update the best double subarray if a better sum is found
            if (s2 + bs1 > bs2) {
                bds[0] = bss;
                bds[1] = di;
                bs2 = s2 + bs1;
            }

            // Update the best triple subarray if a better sum is found
            if (s3 + bs2 > bs3) {
                bts[0] = bds[0];
                bts[1] = bds[1];
                bts[2] = ti;
                bs3 = s3 + bs2;
            }

            // Move the sliding window indices forward
            si++;
            di++;
            ti++;
        }

        // Return the best start indices for the three subarrays with the maximum sum
        return bts;
    }
}