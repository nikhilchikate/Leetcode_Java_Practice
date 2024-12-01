/*
You are given a 0-indexed array of integers nums.

A prefix nums[0..i] is sequential if, for all 1 <= j <= i, nums[j] = nums[j - 1] + 1. In particular, the prefix consisting only of nums[0] is sequential.

Return the smallest integer x missing from nums such that x is greater than or equal to the sum of the longest sequential prefix.



Example 1:

Input: nums = [1,2,3,2,5]
Output: 6
Explanation: The longest sequential prefix of nums is [1,2,3] with a sum of 6. 6 is not in the array, therefore 6 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
Example 2:

Input: nums = [3,4,5,1,12,14,13]
Output: 15
Explanation: The longest sequential prefix of nums is [3,4,5] with a sum of 12. 12, 13, and 14 belong to the array while 15 does not. Therefore 15 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.


Constraints:

1 <= nums.length <= 50
1 <= nums[i] <= 50
*/

class Solution {
    public int missingInteger(int[] nums) {
        int currentSum = nums[0], result = 0;
        if (nums.length == 1)
            result = nums[0] + 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                currentSum = currentSum + nums[i];
            } else {
                break;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != currentSum) {
                result = currentSum;
            } else {
                i = 0;
                currentSum++;
            }
        }

        return result;
    }
}