/*
2918. Minimum Equal Sum of Two Arrays After Replacing Zeros
Medium
Topics
Companies
Hint
You are given two arrays nums1 and nums2 consisting of positive integers.

You have to replace all the 0's in both arrays with strictly positive integers such that the sum of elements of both arrays becomes equal.

Return the minimum equal sum you can obtain, or -1 if it is impossible.



Example 1:

Input: nums1 = [3,2,0,1,0], nums2 = [6,5,0]
Output: 12
Explanation: We can replace 0's in the following way:
- Replace the two 0's in nums1 with the values 2 and 4. The resulting array is nums1 = [3,2,2,1,4].
- Replace the 0 in nums2 with the value 1. The resulting array is nums2 = [6,5,1].
Both arrays have an equal sum of 12. It can be shown that it is the minimum sum we can obtain.
Example 2:

Input: nums1 = [2,0,2,0], nums2 = [1,4]
Output: -1
Explanation: It is impossible to make the sum of both arrays equal.


Constraints:

1 <= nums1.length, nums2.length <= 105
0 <= nums1[i], nums2[i] <= 106
*/

class Solution {
    public long minSum(int[] nums1, int[] nums2) {
        long sum1 = 0, sum2 = 0, zeroCount1 = 0, zeroCount2 = 0, minSum1 = 0, minSum2 = 0;

        for (int num: nums1) {
            sum1 += (long) num;
            zeroCount1 += num != 0 ? 0 : 1;
        }

        minSum1 = sum1+zeroCount1;

        for (int num: nums2) {
            sum2 += (long) num;
            zeroCount2 += num != 0 ? 0 : 1;
        }

        minSum2 = sum2+zeroCount2;

        if (minSum1<minSum2 && zeroCount1 == 0)
            return -1;

        if (minSum1>minSum2 && zeroCount2 == 0)
            return -1;

        return Math.max(minSum1, minSum2);
    }
}