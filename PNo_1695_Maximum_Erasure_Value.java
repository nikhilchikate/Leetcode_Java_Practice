/*
You are given an array of positive integers nums and want to erase a subarray containing unique elements. The score you get by erasing the subarray is equal to the sum of its elements.

Return the maximum score you can get by erasing exactly one subarray.

An array b is called to be a subarray of a if it forms a contiguous subsequence of a, that is, if it is equal to a[l],a[l+1],...,a[r] for some (l,r).



Example 1:

Input: nums = [4,2,4,5,6]
Output: 17
Explanation: The optimal subarray here is [2,4,5,6].
Example 2:

Input: nums = [5,2,1,2,5,2,1,2,5]
Output: 8
Explanation: The optimal subarray here is [5,2,1] or [1,2,5].


Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 104
*/

class Solution {
    public int maximumUniqueSubarray(int[] arr) {
        int[] lastIdx = new int[10001];
        for (int i = 0; i < lastIdx.length; i++) {
            lastIdx[i] = -1;
        }
        int left = -1, sum = 0;
        int[] prefSum = new int[arr.length + 1];

        for (int r = 0; r < arr.length; r++) {
            prefSum[r + 1] = arr[r] + prefSum[r];
            if (lastIdx[arr[r]] >= 0) {
                left = Math.max(left, lastIdx[arr[r]]);
            }
            sum = Math.max(sum, prefSum[r + 1] - prefSum[left + 1]);
            lastIdx[arr[r]] = r;
        }
        return sum;
    }
}