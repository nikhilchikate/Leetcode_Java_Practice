/*
You are given an integer array nums.

The low score of nums is the minimum absolute difference between any two integers.
The high score of nums is the maximum absolute difference between any two integers.
The score of nums is the sum of the high and low scores.
Return the minimum score after changing two elements of nums.

 

Example 1:

Input: nums = [1,4,7,8,5]

Output: 3

Explanation:

Change nums[0] and nums[1] to be 6 so that nums becomes [6,6,7,8,5].
The low score is the minimum absolute difference: |6 - 6| = 0.
The high score is the maximum absolute difference: |8 - 5| = 3.
The sum of high and low score is 3.
Example 2:

Input: nums = [1,4,3]

Output: 0

Explanation:

Change nums[1] and nums[2] to 1 so that nums becomes [1,1,1].
The sum of maximum absolute difference and minimum absolute difference is 0.
 

Constraints:

3 <= nums.length <= 105
1 <= nums[i] <= 109
*/

class Solution {

    public int minimizeSum(int[] nums) {
        int mn1 = Integer.MAX_VALUE,
            mn2 = Integer.MAX_VALUE,
            mn3 = Integer.MAX_VALUE;
        int mx1 = Integer.MIN_VALUE,
            mx2 = Integer.MIN_VALUE,
            mx3 = Integer.MIN_VALUE;

        for (int x : nums) {
            if (x < mn1) {
                mn3 = mn2;
                mn2 = mn1;
                mn1 = x;
            } else if (x < mn2) {
                mn3 = mn2;
                mn2 = x;
            } else if (x < mn3) 
                mn3 = x;
            
            if (x > mx1) {
                mx3 = mx2;
                mx2 = mx1;
                mx1 = x;
            } else if (x > mx2) {
                mx3 = mx2;
                mx2 = x;
            } else if (x > mx3) 
                mx3 = x;
        }

        return Math.min(mx1 - mn3, Math.min(mx2 - mn2, mx3 - mn1));
    }
}