/*
You are given an m x n binary matrix matrix.

You can choose any number of columns in the matrix and flip every cell in that column (i.e., Change the value of the cell from 0 to 1 or vice versa).

Return the maximum number of rows that have all values equal after some number of flips.



Example 1:

Input: matrix = [[0,1],[1,1]]
Output: 1
Explanation: After flipping no values, 1 row has all values equal.
Example 2:

Input: matrix = [[0,1],[1,0]]
Output: 2
Explanation: After flipping values in the first column, both rows have equal values.
Example 3:

Input: matrix = [[0,0,0],[0,0,1],[1,1,0]]
Output: 2
Explanation: After flipping values in the first two columns, the last two rows have equal values.


Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 300
matrix[i][j] is either 0 or 1.
*/

class Solution {
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int ans = 0, n = matrix.length, m = matrix[0].length;

        for (int i = 0; i < n; i++) {
            if (matrix[i][m - 1] == 1)
                for (int j = 0; j < m; j++) {
                    matrix[i][j] ^= matrix[i][m - 1]; // flip all element in a row
                }
            ans = Math.max(ans, cnt.merge(Arrays.hashCode(matrix[i]), 1, Integer::sum));
        }

        return ans;
    }
}