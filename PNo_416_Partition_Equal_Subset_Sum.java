/*
Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise.



Example 1:

Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
Example 2:

Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.


Constraints:

1 <= nums.length <= 200
1 <= nums[i] <= 100
*/

class Solution {

    Boolean canPartitionSubsets(Boolean[] dp, int targetSum, int currentIndex, int[] numbers) {
        if (targetSum == 0)
            return true;

        if (targetSum < 0)
            return false;

        if (currentIndex == 0)
            return targetSum == numbers[0];

        if (dp[targetSum] != null)
            return dp[targetSum];

        return dp[targetSum] = canPartitionSubsets(dp, targetSum - numbers[currentIndex], currentIndex - 1, numbers)
                || canPartitionSubsets(dp, targetSum, currentIndex - 1, numbers);
    }

    public boolean canPartition(int[] numbers) {

        int totalSum = 0;

        for (int num : numbers)
            totalSum += num;

        if (totalSum % 2 != 0)
            return false;

        int n = numbers.length;

        int targetSum = totalSum / 2;

        Boolean[] dp = new Boolean[targetSum + 1];

        return canPartitionSubsets(dp, targetSum, n - 1, numbers);
    }
}