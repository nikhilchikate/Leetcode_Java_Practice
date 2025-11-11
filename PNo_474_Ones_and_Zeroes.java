/*
You are given an array of binary strings strs and two integers m and n.

Return the size of the largest subset of strs such that there are at most m 0's and n 1's in the subset.

A set x is a subset of a set y if all elements of x are also elements of y.



Example 1:

Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
Output: 4
Explanation: The largest subset with at most 5 0's and 3 1's is {"10", "0001", "1", "0"}, so the answer is 4.
Other valid but smaller subsets include {"0001", "1"} and {"10", "1", "0"}.
{"111001"} is an invalid subset because it contains 4 1's, greater than the maximum of 3.
Example 2:

Input: strs = ["10","0","1"], m = 1, n = 1
Output: 2
Explanation: The largest subset is {"0", "1"}, so the answer is 2.


Constraints:

1 <= strs.length <= 600
1 <= strs[i].length <= 100
strs[i] consists only of digits '0' and '1'.
1 <= m, n <= 100
*/

class Solution {
    public int findMaxForm(String[] strings, int maxZeros, int maxOnes) {
        int numStrings = strings.length;
        int[][] memo = new int[maxZeros + 1][maxOnes + 1];
        memo[0][0] = 1;

        for (int i = 1; i <= numStrings; ++i) {
            int currentOnes = 0;
            int currentZeros = 0;
            String currentStr = strings[i - 1];

            for (char ch : currentStr.toCharArray()) {
                if (ch == '0') {
                    currentZeros++;
                }
            }
            currentOnes = currentStr.length() - currentZeros;

            for (int j = maxZeros; j >= currentZeros; --j) {
                for (int k = maxOnes; k >= currentOnes; --k) {
                    if (memo[j - currentZeros][k - currentOnes] > 0) {
                        memo[j][k] = Math.max(memo[j][k], 1 + memo[j - currentZeros][k - currentOnes]);
                    }
                }
            }
        }

        int maximumCount = 1;
        for (int j = 1; j <= maxZeros; ++j) {
            for (int k = 1; k <= maxOnes; ++k) {
                maximumCount = Math.max(maximumCount, memo[j][k]);
            }
        }

        return maximumCount - 1;
    }
}