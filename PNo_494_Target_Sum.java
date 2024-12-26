/*
You are given an integer array nums and an integer target.

You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums and then concatenate all the integers.

For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 and concatenate them to build the expression "+2-1".
Return the number of different expressions that you can build, which evaluates to target.



Example 1:

Input: nums = [1,1,1,1,1], target = 3
Output: 5
Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
-1 + 1 + 1 + 1 + 1 = 3
+1 - 1 + 1 + 1 + 1 = 3
+1 + 1 - 1 + 1 + 1 = 3
+1 + 1 + 1 - 1 + 1 = 3
+1 + 1 + 1 + 1 - 1 = 3
Example 2:

Input: nums = [1], target = 1
Output: 1


Constraints:

1 <= nums.length <= 20
0 <= nums[i] <= 1000
0 <= sum(nums[i]) <= 1000
-1000 <= target <= 1000
*/

class Solution {
    int[] dp;

    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (target > sum || target + sum < 0 || (target + sum) % 2 != 0) {
            return 0;
        }

        int t = (target + sum) / 2;
        dp = new int[t + 1];
        dp[0] = 1;

        dfs(nums, 0, t);
        return dp[t];
    }

    private void dfs(int[] nums, int i, int t) {
        if (i == nums.length) {
            return;
        }

        for (int j = t; j >= nums[i]; j--) {
            dp[j] += dp[j - nums[i]];
        }

        dfs(nums, i + 1, t);
    }
}