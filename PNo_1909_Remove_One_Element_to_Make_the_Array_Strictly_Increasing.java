/*
Given a 0-indexed integer array nums, return true if it can be made strictly increasing after removing exactly one element, or false otherwise. If the array is already strictly increasing, return true.

The array nums is strictly increasing if nums[i - 1] < nums[i] for each index (1 <= i < nums.length).



Example 1:

Input: nums = [1,2,10,5,7]
Output: true
Explanation: By removing 10 at index 2 from nums, it becomes [1,2,5,7].
[1,2,5,7] is strictly increasing, so return true.
Example 2:

Input: nums = [2,3,1,2]
Output: false
Explanation:
[3,1,2] is the result of removing the element at index 0.
[2,1,2] is the result of removing the element at index 1.
[2,3,2] is the result of removing the element at index 2.
[2,3,1] is the result of removing the element at index 3.
No resulting array is strictly increasing, so return false.
Example 3:

Input: nums = [1,1,1]
Output: false
Explanation: The result of removing any element is [1,1].
[1,1] is not strictly increasing, so return false.


Constraints:

2 <= nums.length <= 1000
1 <= nums[i] <= 1000
*/

class Solution {
    public boolean canBeIncreasing(int[] nums) {
        int idx = 1, n = nums.length;
        // Find first violation of increasing order
        for (; idx < n && nums[idx - 1] < nums[idx]; ++idx)
            ;
        // Check if removing nums[idx-1] or nums[idx] fixes the order
        return isStrictlyIncreasing(nums, idx - 1) || isStrictlyIncreasing(nums, idx);
    }

    // Check if the array is strictly increasing after removing one element
    private boolean isStrictlyIncreasing(int[] nums, int removeIdx) {
        int prev = Integer.MIN_VALUE;
        for (int j = 0; j < nums.length; ++j) {
            if (j == removeIdx) continue; // Skip the element
            if (prev >= nums[j]) return false; // Not strictly increasing
            prev = nums[j];
        }
        return true;
    }
}