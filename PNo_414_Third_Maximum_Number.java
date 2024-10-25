/*
Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not exist, return the maximum number.



Example 1:

Input: nums = [3,2,1]
Output: 1
Explanation:
The first distinct maximum is 3.
The second distinct maximum is 2.
The third distinct maximum is 1.
Example 2:

Input: nums = [1,2]
Output: 2
Explanation:
The first distinct maximum is 2.
The second distinct maximum is 1.
The third distinct maximum does not exist, so the maximum (2) is returned instead.
Example 3:

Input: nums = [2,2,3,1]
Output: 1
Explanation:
The first distinct maximum is 3.
The second distinct maximum is 2 (both 2's are counted together since they have the same value).
The third distinct maximum is 1.


Constraints:

1 <= nums.length <= 104
-231 <= nums[i] <= 231 - 1


Follow up: Can you find an O(n) solution?
*/

class Solution {
    public int thirdMax(int[] nums) {
        long n1 = Long.MIN_VALUE;
        long n2 = Long.MIN_VALUE;
        long n3 = Long.MIN_VALUE;

        for (int num : nums) {
            if (num == n1 || num == n2 || num == n3)
                continue;
            if (num > n1) {
                n3 = n2;
                n2 = n1;
                n1 = num;
            } else if (num > n2) {
                n3 = n2;
                n2 = num;
            } else if (num > n3)
                n3 = num;
        }

        return (int) (n3 != Long.MIN_VALUE ? n3 : n1);
    }
}