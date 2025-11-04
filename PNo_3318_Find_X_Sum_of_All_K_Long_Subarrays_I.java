/*
You are given an array nums of n integers and two integers k and x.

The x-sum of an array is calculated by the following procedure:

Count the occurrences of all elements in the array.
Keep only the occurrences of the top x most frequent elements. If two elements have the same number of occurrences, the element with the bigger value is considered more frequent.
Calculate the sum of the resulting array.
Note that if an array has less than x distinct elements, its x-sum is the sum of the array.

Return an integer array answer of length n - k + 1 where answer[i] is the x-sum of the subarray nums[i..i + k - 1].



Example 1:

Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

Output: [6,10,12]

Explanation:

For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
Example 2:

Input: nums = [3,8,7,8,7,5], k = 2, x = 2

Output: [11,15,15,15,12]

Explanation:

Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].



Constraints:

1 <= n == nums.length <= 50
1 <= nums[i] <= 50
1 <= x <= k <= nums.length
*/

class Solution {
    public int[] findXSum(int[] dataArray, int windowSize, int xTop) {
        int[] finalSums = new int[dataArray.length - windowSize + 1];

        for (int i = 0; i < finalSums.length; i++) {
            int startIdx = i, endIdx = i + windowSize - 1;
            finalSums[i] = computeXSumForWindow(dataArray, startIdx, endIdx, xTop);
        }

        return finalSums;
    }

    private int computeXSumForWindow(int[] dataArray, int startIdx, int endIdx, int xTop) {
        int currentSum = 0, numDistinct = 0;
        int[] valueFrequencies = new int[51];

        for (int i = startIdx; i <= endIdx; i++) {
            currentSum += dataArray[i];
            if (valueFrequencies[dataArray[i]] == 0) {
                numDistinct++;
            }
            valueFrequencies[dataArray[i]]++;
        }

        if (numDistinct < xTop) {
            return currentSum;
        }

        currentSum = 0;
        for (int elementPicked = 0; elementPicked < xTop; elementPicked++) {
            int maxFrequency = -1;
            int bestValue = -1;

            for (int value = 50; value >= 1; value--) {
                if (valueFrequencies[value] > maxFrequency) {
                    maxFrequency = valueFrequencies[value];
                    bestValue = value;
                }
            }

            if (bestValue != -1) {
                currentSum += bestValue * maxFrequency;
                valueFrequencies[bestValue] = 0;
            }
        }

        return currentSum;
    }
}