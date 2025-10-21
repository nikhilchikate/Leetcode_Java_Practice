/*
You are given an integer array nums and two integers k and numOperations.

You must perform an operation numOperations times on nums, where in each operation you:

Select an index i that was not selected in any previous operations.
Add an integer in the range [-k, k] to nums[i].
Return the maximum possible frequency of any element in nums after performing the operations.



Example 1:

Input: nums = [1,4,5], k = 1, numOperations = 2

Output: 2

Explanation:

We can achieve a maximum frequency of two by:

Adding 0 to nums[1]. nums becomes [1, 4, 5].
Adding -1 to nums[2]. nums becomes [1, 4, 4].
Example 2:

Input: nums = [5,11,20,20], k = 5, numOperations = 1

Output: 2

Explanation:

We can achieve a maximum frequency of two by:

Adding 0 to nums[1].


Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 105
0 <= k <= 105
0 <= numOperations <= nums.length
*/

class Solution {
    public int maxFrequency(int[] Nums, int Diff, int Ops) {
        int MaxVal = 0, MinVal = Integer.MAX_VALUE;

        for (int num : Nums) {
            MaxVal = Math.max(MaxVal, num);
            MinVal = Math.min(MinVal, num);
        }

        int[] Freq = new int[MaxVal + 1];
        int[] PSum = new int[MaxVal + 1];

        for (int num : Nums) {
            Freq[num]++;
        }

        for (int i = MinVal; i <= MaxVal; i++) {
            PSum[i] = PSum[i - 1] + Freq[i];
        }

        int Result = 0;

        for (int i = MinVal; i <= MaxVal; i++) {
            int LBoundCount = 0;
            if (i - Diff - 1 > 0) {
                LBoundCount = PSum[i - Diff - 1];
            }

            int UBoundCount = 0;
            if (i + Diff <= MaxVal) {
                UBoundCount = PSum[i + Diff];
            } else {
                UBoundCount = PSum[MaxVal];
            }

            int AdjCount = UBoundCount - LBoundCount - Freq[i];

            Result = Math.max(Result, Freq[i] + Math.min(Ops, AdjCount));
        }
        return Result;
    }
}