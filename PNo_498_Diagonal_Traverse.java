/*
Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.



Example 1:


Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,4,7,5,3,6,8,9]
Example 2:

Input: mat = [[1,2],[3,4]]
Output: [1,2,3,4]


Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 104
1 <= m * n <= 104
-105 <= mat[i][j] <= 105
*/

class Solution {
    int r = 0, c = 0, pos = 0;
    int[] res;
    int[][] matCopy;

    public int[] findDiagonalOrder(int[][] mat) {
        matCopy = mat;
        r = mat.length;
        c = mat[0].length;

        res = new int[r * c];

        diagonalTraversal(0, 0, true); // diagonal traversal from the top-left corner of the matCopy

        return res;
    }

    public void diagonalTraversal(int row, int col, boolean moveUp) {
        if (row == r - 1 && col == c - 1) { // Base case: when we reach the bottom-right corner, then return
            res[pos++] = matCopy[row][col];
            return;
        }

        res[pos++] = matCopy[row][col];

        // move upwards
        if (moveUp) {
            if (col == c - 1) {
                diagonalTraversal(row + 1, col, false);
            } else if (row == 0) {
                diagonalTraversal(row, col + 1, false);
            } else {
                diagonalTraversal(row - 1, col + 1, true);
            }
        }
        // move downwards
        else {
            if (row == r - 1) {
                diagonalTraversal(row, col + 1, true);
            } else if (col == 0) {
                diagonalTraversal(row + 1, col, true);
            } else {
                diagonalTraversal(row + 1, col - 1, false);
            }
        }
    }
}