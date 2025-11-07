/*
You are given a 0-indexed integer array stations of length n, where stations[i] represents the number of power stations in the ith city.

Each power station can provide power to every city in a fixed range. In other words, if the range is denoted by r, then a power station at city i can provide power to all cities j such that |i - j| <= r and 0 <= i, j <= n - 1.

Note that |x| denotes absolute value. For example, |7 - 5| = 2 and |3 - 10| = 7.
The power of a city is the total number of power stations it is being provided power from.

The government has sanctioned building k more power stations, each of which can be built in any city, and have the same range as the pre-existing ones.

Given the two integers r and k, return the maximum possible minimum power of a city, if the additional power stations are built optimally.

Note that you can build the k power stations in multiple cities.



Example 1:

Input: stations = [1,2,4,5,0], r = 1, k = 2
Output: 5
Explanation:
One of the optimal ways is to install both the power stations at city 1.
So stations will become [1,4,4,5,0].
- City 0 is provided by 1 + 4 = 5 power stations.
- City 1 is provided by 1 + 4 + 4 = 9 power stations.
- City 2 is provided by 4 + 4 + 5 = 13 power stations.
- City 3 is provided by 5 + 4 = 9 power stations.
- City 4 is provided by 5 + 0 = 5 power stations.
So the minimum power of a city is 5.
Since it is not possible to obtain a larger power, we return 5.
Example 2:

Input: stations = [4,4,4,4], r = 0, k = 3
Output: 4
Explanation:
It can be proved that we cannot make the minimum power of a city greater than 4.


Constraints:

n == stations.length
1 <= n <= 105
0 <= stations[i] <= 105
0 <= r <= n - 1
0 <= k <= 109
*/

class Solution {
    public long maxPower(int[] stationValues, int radius, long budget) {
        int arrayLen = stationValues.length;

        // 1) compute initial power for each city using sliding window
        long[] initialPower = new long[arrayLen];
        long currentWindowSum = 0;
        int windowSpan = 2 * radius + 1;

        // initial window from 0 to min(n-1, r)
        for (int j = 0; j <= Math.min(arrayLen - 1, radius); j++) currentWindowSum += stationValues[j];

        for (int i = 0; i < arrayLen; i++) {
            // currentWindowSum covers [i-r, i+r] clipped to [0, n-1]
            initialPower[i] = currentWindowSum;

            // slide window: remove left element (i-r) and add right element (i+r+1)
            int removeIndex = i - radius;
            if (removeIndex >= 0) currentWindowSum -= stationValues[removeIndex];

            int addIndex = i + radius + 1;
            if (addIndex < arrayLen) currentWindowSum += stationValues[addIndex];
        }

        // 2) binary search answer
        long lowerBound = 0;
        long upperBound = Arrays.stream(initialPower).max().orElse(0L) + budget;
        long maxAchievablePower = 0;

        while (lowerBound <= upperBound) {
            long middleTarget = lowerBound + (upperBound - lowerBound) / 2;
            if (isReachable(initialPower, radius, budget, middleTarget)) {
                maxAchievablePower = middleTarget;
                lowerBound = middleTarget + 1;
            } else {
                upperBound = middleTarget - 1;
            }
        }
        return maxAchievablePower;
    }

    // Check if we can make every city's power >= target using <= k extra stations
    private boolean isReachable(long[] initialPower, int radius, long budget, long targetPower) {
        int arrayLen = initialPower.length;
        long totalUsedBudget = 0L;
        long[] differenceArray = new long[arrayLen + 1];
        long currentAddition = 0L;

        for (int i = 0; i < arrayLen; i++) {
            currentAddition += differenceArray[i];
            long combinedPower = initialPower[i] + currentAddition;

            if (combinedPower < targetPower) {
                long deficit = targetPower - combinedPower;
                totalUsedBudget += deficit;

                if (totalUsedBudget > budget) return false;

                currentAddition += deficit;

                int endEffectIndex = Math.min(arrayLen, i + 2 * radius + 1);
                differenceArray[endEffectIndex] -= deficit;
            }
        }
        return true;
    }
}