/*
You are given an integer array nums and an integer k.

You are allowed to perform the following operation on each element of the array at most once:

Add an integer in the range [-k, k] to the element.
Return the maximum possible number of distinct elements in nums after performing the operations.



Example 1:

Input: nums = [1,2,2,3,3,4], k = 2

Output: 6

Explanation:

nums changes to [-1, 0, 1, 2, 3, 4] after performing operations on the first four elements.

Example 2:

Input: nums = [4,4,4,4], k = 1

Output: 3

Explanation:

By adding -1 to nums[0] and 1 to nums[1], nums changes to [3, 5, 4, 4].



Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
0 <= k <= 109
*/

class Solution {
    public int maxDistinctElements(int[] N, int K) {
        if (N.length <= (K << 1) + 1)
            return N.length;
        Arrays.sort(N);
        int D = 0;
        int L = Integer.MIN_VALUE;
        for (int i = 0; i < N.length; ++i) {
            int M = Math.max(L + 1, N[i] - K);
            if (M <= N[i] + K) {
                D++;
                L = M;
            }
        }
        return D;
    }
}