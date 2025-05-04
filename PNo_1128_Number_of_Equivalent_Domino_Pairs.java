/*
Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d] if and only if either (a == c and b == d), or (a == d and b == c) - that is, one domino can be rotated to be equal to another domino.

Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is equivalent to dominoes[j].



Example 1:

Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
Output: 1
Example 2:

Input: dominoes = [[1,2],[1,2],[1,1],[1,2],[2,2]]
Output: 3


Constraints:

1 <= dominoes.length <= 4 * 104
dominoes[i].length == 2
1 <= dominoes[i][j] <= 9
*/

class Solution {
    public int numEquivDominoPairs(int[][] dominoes) {
        int[][] dp = new int[10][10];
        for (int[] domino : dominoes) {
            dp[domino[0]][domino[1]]++;
        }
        int res = 0;
        int r = dp.length;
        for (int i = 1; i < r; i++) {
            for (int j = i; j < dp[i].length; j++) {
                int count = dp[i][j];
                if (i != j) {
                    count += dp[j][i];
                }
                res += count * (count - 1) / 2;
            }
        }
        return res;
    }
}