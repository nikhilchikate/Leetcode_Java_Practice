/*
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.

Note that you must do this in-place without making a copy of the array.



Example 1:

Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
Example 2:

Input: nums = [0]
Output: [0]


Constraints:

1 <= nums.length <= 104
-231 <= nums[i] <= 231 - 1


Follow up: Could you minimize the total number of operations done?
*/

class Solution {
    // Moves all zeros in the array to the end
    public void moveZeroes(int[] nums) {
        int zeroCount = 0; // Count of zeros encountered
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                shiftZeros(nums, i, zeroCount);
                break; // Stop after finding the first zero
            }
        }
    }

    // Shifts non-zero elements left and counts zeros
    private static void shiftZeros(int[] arr, int index, int zeroCount) {
        zeroCount++; // Increment zero count
        for (int j = index + 1; j < arr.length; j++) {
            if (arr[j] == 0) {
                shiftZeros(arr, j, zeroCount); // Recursive call for next zero
                return;
            }
            arr[j - zeroCount] = arr[j]; // Shift non-zero elements left
        }
        // Fill remaining elements with zeros at the end
        for (int k = 1; k <= zeroCount; k++) {
            arr[arr.length - k] = 0;
        }
    }
}