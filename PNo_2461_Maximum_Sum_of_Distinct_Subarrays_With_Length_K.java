/*
You are given an integer array nums and an integer k. Find the maximum subarray sum of all the subarrays of nums that meet the following conditions:

The length of the subarray is k, and
All the elements of the subarray are distinct.
Return the maximum subarray sum of all the subarrays that meet the conditions. If no subarray meets the conditions, return 0.

A subarray is a contiguous non-empty sequence of elements within an array.



Example 1:

Input: nums = [1,5,4,2,9,9,9], k = 3
Output: 15
Explanation: The subarrays of nums with length 3 are:
- [1,5,4] which meets the requirements and has a sum of 10.
- [5,4,2] which meets the requirements and has a sum of 11.
- [4,2,9] which meets the requirements and has a sum of 15.
- [2,9,9] which does not meet the requirements because the element 9 is repeated.
- [9,9,9] which does not meet the requirements because the element 9 is repeated.
We return 15 because it is the maximum subarray sum of all the subarrays that meet the conditions
Example 2:

Input: nums = [4,4,4], k = 3
Output: 0
Explanation: The subarrays of nums with length 3 are:
- [4,4,4] which does not meet the requirements because the element 4 is repeated.
We return 0 because no subarrays meet the conditions.


Constraints:

1 <= k <= nums.length <= 105
1 <= nums[i] <= 105
*/

class Solution {
    public long maximumSubarraySum(int[] nums, int k) {
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        int[] freq = new int[maxVal + 1];
        int duplicateCount = 0;
        long currentSum = 0;
        long maxSum = 0;

        // Initialize the first window of size k
        for (int i = 0; i < k; i++) {
            if (freq[nums[i]] > 0) {
                duplicateCount++; // Increment duplicate count if element is repeated
            }
            freq[nums[i]]++;
            currentSum += nums[i];
        }

        // If no duplicates in the first window, store the current sum
        if (duplicateCount == 0) {
            maxSum = currentSum;
        }

        // Slide the window across the array
        for (int i = k; i < nums.length; i++) {
            // Include the current element in the window
            if (freq[nums[i]] > 0) {
                duplicateCount++;
            }
            freq[nums[i]]++;
            currentSum += nums[i];

            // Remove the element going out of the window
            if (freq[nums[i - k]] > 1) {
                duplicateCount--;
            }
            freq[nums[i - k]]--;
            currentSum -= nums[i - k];

            // Update maxSum if no duplicates in the current window
            if (duplicateCount == 0) {
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }
}