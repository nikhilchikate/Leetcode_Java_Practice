/*
Given an m x n matrix, return all elements of the matrix in spiral order.



Example 1:


Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
Example 2:


Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]


Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 10
-100 <= matrix[i][j] <= 100
*/

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int row = matrix.length, col = matrix[0].length;
        int r1 = 0, c1 = 0, r2 = row - 1, c2 = col - 1;
        List<Integer> res = new ArrayList<>();
        while (r1 <= r2 && c1 <= c2) {
            for (int j = c1; j <= c2; j++) {
                res.add(matrix[r1][j]);
            }
            for (int i = r1 + 1; i <= r2; i++) {
                res.add(matrix[i][c2]);
            }
            if (r1 < r2 && c1 < c2) {
                for (int j = c2 - 1; j >= c1; j--) {
                    res.add(matrix[r2][j]);
                }
                for (int i = r2 - 1; i > r1; i--) {
                    res.add(matrix[i][c1]);
                }
            }
            ++r1;
            ++c1;
            --r2;
            --c2;
        }
        return res;
    }
}