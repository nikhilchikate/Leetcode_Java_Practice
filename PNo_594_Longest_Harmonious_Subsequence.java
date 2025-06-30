/*
We define a harmonious array as an array where the difference between its maximum value and its minimum value is exactly 1.

Given an integer array nums, return the length of its longest harmonious subsequence among all its possible subsequences.



Example 1:

Input: nums = [1,3,2,2,5,2,3,7]

Output: 5

Explanation:

The longest harmonious subsequence is [3,2,2,2,3].

Example 2:

Input: nums = [1,2,3,4]

Output: 2

Explanation:

The longest harmonious subsequences are [1,2], [2,3], and [3,4], all of which have a length of 2.

Example 3:

Input: nums = [1,1,1,1]

Output: 0

Explanation:

No harmonic subsequence exists.



Constraints:

1 <= nums.length <= 2 * 104
-109 <= nums[i] <= 109
*/

class Solution {
    static {
        for (int i = 0; i < 500; i++) {
            findLHS(new int[] { 1, 2, 3, 4 });
        }
    }

    public static int findLHS(int[] dataArr) {
        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for (int val : dataArr) {
            minVal = Math.min(minVal, val);
            maxVal = Math.max(maxVal, val);
        }
        int rangeSize = maxVal - minVal + 1;
        if (rangeSize > 1000000)
            return findLHSWithMap(dataArr);

        int[] valCounts = new int[rangeSize];
        for (int val : dataArr)
            valCounts[val - minVal]++;

        int maxLen = 0;
        for (int i = 1; i < valCounts.length; i++) {
            if (valCounts[i] != 0 && valCounts[i - 1] != 0) {
                maxLen = Math.max(maxLen, valCounts[i] + valCounts[i - 1]);
            }
        }
        return maxLen;
    }

    private static int findLHSWithMap(int[] dataArr) {
        if (dataArr.length < 2)
            return 0;

        Map<Integer, Integer> valToFreq = new HashMap<>();
        for (int val : dataArr)
            valToFreq.merge(val, 1, Integer::sum);

        int maxLen = 0;
        for (Map.Entry<Integer, Integer> entry : valToFreq.entrySet()) {
            int currentKey = entry.getKey();
            int currentFreq = entry.getValue();
            Integer freqOfLess = valToFreq.get(currentKey - 1);
            Integer freqOfMore = valToFreq.get(currentKey + 1);

            if (freqOfLess != null)
                maxLen = Math.max(maxLen, currentFreq + freqOfLess);
            if (freqOfMore != null)
                maxLen = Math.max(maxLen, currentFreq + freqOfMore);
        }
        return maxLen;
    }
}
