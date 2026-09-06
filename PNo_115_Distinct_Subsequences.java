/*
Given two strings s and t, return the number of distinct subsequences of s which equals t.

The test cases are generated so that the answer fits on a 32-bit signed integer.

 

Example 1:

Input: s = "rabbbit", t = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from s.
rabbbit
rabbbit
rabbbit
Example 2:

Input: s = "babgbag", t = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from s.
babgbag
babgbag
babgbag
babgbag
babgbag
 

Constraints:

1 <= s.length, t.length <= 1000
s and t consist of English letters.
*/

class Solution {
    public int numDistinct(String B, String A) {
        int lenA = A.length();
        int lenB = B.length();
        Integer[][] memo = new Integer[lenA][lenB];

        return solve(A, B, lenA - 1, lenB - 1, memo);
    }

    private int solve(String A, String B, int i, int j, Integer[][] memo) {
        if (i < 0)
            return 1;

        if (j < 0)
            return 0;

        if (j < i)
            return 0;

        if (memo[i][j] != null)
            return memo[i][j];

        if (A.charAt(i) == B.charAt(j)) {
            return memo[i][j] = solve(A, B, i - 1, j - 1, memo) + solve(A, B, i, j - 1, memo);
        }
        
        return memo[i][j] = solve(A, B, i, j - 1, memo);
    }
}