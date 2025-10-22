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

Adding 0 to nums[1], after which nums becomes [1, 4, 5].
Adding -1 to nums[2], after which nums becomes [1, 4, 4].
Example 2:

Input: nums = [5,11,20,20], k = 5, numOperations = 1

Output: 2

Explanation:

We can achieve a maximum frequency of two by:

Adding 0 to nums[1].


Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
0 <= k <= 109
0 <= numOperations <= nums.length
*/

class Solution {
    public int maxFrequency(int[] Nums, int K, int Ops) {
        int MaxF = 0;
        Arrays.sort(Nums);
        int N = Nums.length;
        int Left = 0;
        int Right = 0;

        int i = 0;
        while (i < N) {
            int Target = Nums[i];
            int Freq = 0;
            while (i < N && Nums[i] == Target) {
                Freq++;
                i++;
            }

            while (Right < N && Nums[Right] <= Target + K) {
                Right++;
            }
            while (Left < N && Nums[Left] < Target - K) {
                Left++;
            }
            MaxF = Math.max(MaxF, Math.min(Freq + Ops, Right - Left));
        }

        Left = 0;
        Right = 0;
        while (Right < N) {
            while (Right < N && (long) Nums[Left] + K + K >= Nums[Right]) {
                Right++;
            }
            MaxF = Math.max(MaxF, Math.min(Right - Left, Ops));
            Left++;
        }

        return MaxF;
    }
}