/*
Given an array of integers arr, and three integers a, b and c. You need to find the number of good triplets.

A triplet (arr[i], arr[j], arr[k]) is good if the following conditions are true:

0 <= i < j < k < arr.length
|arr[i] - arr[j]| <= a
|arr[j] - arr[k]| <= b
|arr[i] - arr[k]| <= c
Where |x| denotes the absolute value of x.

Return the number of good triplets.



Example 1:

Input: arr = [3,0,1,1,9,7], a = 7, b = 2, c = 3
Output: 4
Explanation: There are 4 good triplets: [(3,0,1), (3,0,1), (3,1,1), (0,1,1)].
Example 2:

Input: arr = [1,1,2,2,3], a = 0, b = 0, c = 1
Output: 0
Explanation: No triplet satisfies all conditions.


Constraints:

3 <= arr.length <= 100
0 <= arr[i] <= 1000
0 <= a, b, c <= 1000
*/

class Solution {

    public int countGoodTriplets(int[] nums, int valA, int valB, int valC) {
        int count = 0, n = nums.length;
        int[] prefixSum = new int[1001];
        for (int j = 0; j < n; ++j) {
            for (int k = j + 1; k < n; ++k) {
                if (Math.abs(nums[j] - nums[k]) <= valB) {
                    int lowerBoundJ = nums[j] - valA, upperBoundJ = nums[j] + valA;
                    int lowerBoundK = nums[k] - valC, upperBoundK = nums[k] + valC;
                    int lowerBoundI = Math.max(0, Math.max(lowerBoundJ, lowerBoundK));
                    int upperBoundI = Math.min(1000, Math.min(upperBoundJ, upperBoundK));
                    if (lowerBoundI <= upperBoundI) {
                        if (lowerBoundI == 0) {
                            count += prefixSum[upperBoundI];
                        } else {
                            count += prefixSum[upperBoundI] - prefixSum[lowerBoundI - 1];
                        }
                    }
                }
            }
            for (int k = nums[j]; k <= 1000; ++k) {
                ++prefixSum[k];
            }
        }
        return count;
    }
}