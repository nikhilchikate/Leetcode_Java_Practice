/*
You are given an integer array nums. A subsequence of nums is called a square streak if:

The length of the subsequence is at least 2, and
after sorting the subsequence, each element (except the first element) is the square of the previous number.
Return the length of the longest square streak in nums, or return -1 if there is no square streak.

A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.



Example 1:

Input: nums = [4,3,6,16,8,2]
Output: 3
Explanation: Choose the subsequence [4,16,2]. After sorting it, it becomes [2,4,16].
- 4 = 2 * 2.
- 16 = 4 * 4.
Therefore, [4,16,2] is a square streak.
It can be shown that every subsequence of length 4 is not a square streak.
Example 2:

Input: nums = [2,3,5,6,7]
Output: -1
Explanation: There is no square streak in nums so return -1.


Constraints:

2 <= nums.length <= 105
2 <= nums[i] <= 105
*/

public class Solution {
    public int longestSquareStreak(int[] nums) {
        int maxStreak = -1; // Track the maximum streak
        final int MAX_NUM = (int) Math.pow(10, 5); // Maximum value for nums
        boolean[] exists = new boolean[MAX_NUM + 1]; // Track existence of numbers
        boolean[] visited = new boolean[MAX_NUM + 1]; // Track visited numbers

        // Mark existing numbers
        for (int num : nums) {
            exists[num] = true;
        }

        // Check for square streaks
        for (int i = 2; i * i <= MAX_NUM; i++) {
            if (!exists[i] || visited[i]) {
                continue; // Skip if number doesn't exist or is already visited
            }
            visited[i] = true; // Mark as visited
            int streakLength = 1; // Start streak length
            long j = (long) i * i; // Calculate square (use long to avoid overflow)

            // Continue while j is valid and exists
            while (j >= 0 && j <= MAX_NUM && exists[(int) j]) {
                visited[(int) j] = true; // Mark as visited
                streakLength++; // Increase streak length
                j = j * j; // Move to next square
            }

            // Update maximum streak if valid
            if (streakLength > 1) {
                maxStreak = Math.max(maxStreak, streakLength);
            }
        }
        return maxStreak; // Return the maximum streak found
    }
}