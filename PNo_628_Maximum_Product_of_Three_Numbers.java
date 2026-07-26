/*
Given an integer array nums, find three numbers whose product is maximum and return the maximum product.

 

Example 1:

Input: nums = [1,2,3]
Output: 6
Example 2:

Input: nums = [1,2,3,4]
Output: 24
Example 3:

Input: nums = [-1,-2,-3]
Output: -6
 

Constraints:

3 <= nums.length <= 104
-1000 <= nums[i] <= 1000
*/

class Solution {
    public int maximumProduct(int[] nums) {
        int l = nums.length;
        int ma1 = Integer.MIN_VALUE;
        int ma2 = Integer.MIN_VALUE;
        int ma3 = Integer.MIN_VALUE;
        int mi1 = Integer.MAX_VALUE;
        int mi2 = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n > ma1) {
                ma3 = ma2;
                ma2 = ma1;
                ma1 = n;
            } else if (n > ma2) {
                ma3 = ma2;
                ma2 = n;
            } else if (n > ma3) {
                ma3 = n;
            }
            if (n < mi1) {
                mi2 = mi1;
                mi1 = n;
            } else if (n < mi2) {
                mi2 = n;
            }
        }
        return Math.max(Math.max(mi1 * mi2 * ma1, mi1 * ma2 * ma1), ma1 * ma2 * ma3);
    }
}