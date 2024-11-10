/*
You are given an array nums of non-negative integers and an integer k.

An array is called special if the bitwise OR of all of its elements is at least k.

Return the length of the shortest special non-empty
subarray
 of nums, or return -1 if no special subarray exists.



Example 1:

Input: nums = [1,2,3], k = 2

Output: 1

Explanation:

The subarray [3] has OR value of 3. Hence, we return 1.

Example 2:

Input: nums = [2,1,8], k = 10

Output: 3

Explanation:

The subarray [2,1,8] has OR value of 11. Hence, we return 3.

Example 3:

Input: nums = [1,2], k = 0

Output: 1

Explanation:

The subarray [1] has OR value of 1. Hence, we return 1.



Constraints:

1 <= nums.length <= 2 * 105
0 <= nums[i] <= 109
0 <= k <= 109
*/

class Solution {
    public int minimumSubarrayLength(int[] nums, int target) {
        int windowBits = 0, minLength = Integer.MAX_VALUE;
        int[] bitCounts = new int[32]; // Track counts of individual bits
        for (int left = 0, right = 0; right < nums.length; right++) {
            if (nums[right] >= target)
                return 1; // Single element >= target
            windowBits |= nums[right]; // OR the current element with the window bits
            for (int i = 0; i < bitCounts.length; i++) {
                bitCounts[i] += (nums[right] >> i) & 1; // Count bits in the current number
            }
            while (windowBits >= target) { // While the window meets the target condition
                minLength = Math.min(minLength, right - left + 1); // Update min length
                for (int i = 0; i < bitCounts.length; i++) {
                    bitCounts[i] -= (nums[left] >> i) & 1; // Remove bits of the left element
                    if (bitCounts[i] == 0)
                        windowBits &= ~(1 << i); // Reset bit if no bits left
                }
                left++; // Shrink the window by moving the left pointer
            }
        }
        return minLength == Integer.MAX_VALUE ? -1 : minLength; // No valid subarray found
    }

    private int prefixBinarySearch(int[] nums, int target) { // Binary search attempt (not working)
        int[] prefixOR = new int[nums.length + 1]; // Prefix OR array
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target)
                return 1; // Single element >= target
            prefixOR[i + 1] = prefixOR[i] | nums[i]; // Build prefix OR array
        }
        int minLength = Integer.MAX_VALUE;
        for (int i = 1; i < prefixOR.length; i++) {
            if (prefixOR[i] >= target) { // If valid subarray is found
                int left = 0, right = i;
                while (left < right) { // Perform binary search
                    int mid = left + (right - left) / 2;
                    int subarrayOR = prefixOR[i] & ~prefixOR[mid]; // Compute OR difference
                    if (subarrayOR < target)
                        right = mid; // Adjust bounds if OR is too small
                    else {
                        left = mid + 1;
                        minLength = Math.min(minLength, i - mid); // Update min length
                    }
                }
            }
        }
        return minLength;
    }
}