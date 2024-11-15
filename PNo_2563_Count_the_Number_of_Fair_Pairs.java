/*
Given a 0-indexed integer array nums of size n and two integers lower and upper, return the number of fair pairs.

A pair (i, j) is fair if:

0 <= i < j < n, and
lower <= nums[i] + nums[j] <= upper


Example 1:

Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
Output: 6
Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
Example 2:

Input: nums = [1,7,9,2,5], lower = 11, upper = 11
Output: 1
Explanation: There is a single fair pair: (2,3).


Constraints:

1 <= nums.length <= 105
nums.length == n
-109 <= nums[i] <= 109
-109 <= lower <= upper <= 109
*/

class Solution {
    public long countFairPairs(int[] nums, int lower, int upper) {
        // Edge case: No pairs possible if array has fewer than 2 elements
        if (nums == null || nums.length < 2)
            return 0;

        // Sort the array to use two-pointer technique
        Arrays.sort(nums);

        // Count pairs with sum <= upper and sum <= lower - 1
        long upperCount = countPairs(nums, upper);
        long lowerCount = countPairs(nums, lower - 1);

        // Return the difference to get pairs in range [lower, upper]
        return upperCount - lowerCount;
    }

    // Count pairs where sum of nums[left] + nums[right] <= target
    private long countPairs(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        long count = 0;

        while (left < right) {
            if (nums[left] + nums[right] <= target) {
                // All pairs (left, left+1, ..., right-1) are valid
                count += (right - left);
                left++; // Move left pointer to the right
            } else {
                right--; // Move right pointer to the left
            }
        }

        return count;
    }
}