/*
An integer x is a good if after rotating each digit individually by 180 degrees, we get a valid number that is different from x. Each digit must be rotated - we cannot choose to leave it alone.

A number is valid if each digit remains a digit after rotation. For example:

0, 1, and 8 rotate to themselves,
2 and 5 rotate to each other (in this case they are rotated in a different direction, in other words, 2 or 5 gets mirrored),
6 and 9 rotate to each other, and
the rest of the numbers do not rotate to any other number and become invalid.
Given an integer n, return the number of good integers in the range [1, n].

 

Example 1:

Input: n = 10
Output: 4
Explanation: There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
Example 2:

Input: n = 1
Output: 0
Example 3:

Input: n = 2
Output: 1
 

Constraints:

1 <= n <= 104
*/

class Solution {
    public int rotatedDigits(int n) {
        char[] arr = String.valueOf(n).toCharArray();
        Integer[][][] dp = new Integer[arr.length][2][2];
        return solve(0, 1, 0, arr, dp);
    }

    private int solve(int idx, int tight, int changed, char[] arr, Integer[][][] dp) {
        if (idx == arr.length) {
            return changed == 1 ? 1 : 0;
        }
        if (dp[idx][tight][changed] != null) {
            return dp[idx][tight][changed];
        }
        int limit = tight == 1 ? arr[idx] - '0' : 9;
        int res = 0;
        for (int d = 0; d <= limit; d++) {
            if (d == 3 || d == 4 || d == 7) {
                continue;
            }
            int nextTight = (tight == 1 && d == limit) ? 1 : 0;
            int nextChanged = changed;
            if (d == 2 || d == 5 || d == 6 || d == 9) {
                nextChanged = 1;
            }
            res += solve(idx + 1, nextTight, nextChanged, arr, dp);
        }
        dp[idx][tight][changed] = res;
        return res;
    }
}
