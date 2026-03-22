/*
Given two n x n binary matrices mat and target, return true if it is possible to make mat equal to target by rotating mat in 90-degree increments, or false otherwise.



Example 1:


Input: mat = [[0,1],[1,0]], target = [[1,0],[0,1]]
Output: true
Explanation: We can rotate mat 90 degrees clockwise to make mat equal target.
Example 2:


Input: mat = [[0,1],[1,1]], target = [[1,0],[0,1]]
Output: false
Explanation: It is impossible to make mat equal to target by rotating mat.
Example 3:


Input: mat = [[0,0,0],[0,1,0],[1,1,1]], target = [[1,1,1],[0,1,0],[0,0,0]]
Output: true
Explanation: We can rotate mat 90 degrees clockwise two times to make mat equal target.


Constraints:

n == mat.length == target.length
n == mat[i].length == target[i].length
1 <= n <= 10
mat[i][j] and target[i][j] are either 0 or 1.
*/

class Solution {
    public boolean findRotation(int[][] mat, int[][] target) {
        int n = mat.length;
        // Track which rotations (0°,90°,180°,270°) are still valid
        boolean[] valid = {true, true, true, true};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int src = mat[i][j];
                // 0°:   mat[i][j]     == target[i][j]
                // 90°:  mat[i][j]     == target[j][n-1-i]
                // 180°: mat[i][j]     == target[n-1-i][n-1-j]
                // 270°: mat[i][j]     == target[n-1-j][i]
                if (valid[0] && src != target[i][j])         valid[0] = false;
                if (valid[1] && src != target[j][n-1-i])     valid[1] = false;
                if (valid[2] && src != target[n-1-i][n-1-j]) valid[2] = false;
                if (valid[3] && src != target[n-1-j][i])     valid[3] = false;

                // Early exit if all rotations are eliminated
                if (!valid[0] && !valid[1] && !valid[2] && !valid[3])
                    return false;
            }
        }
        return true; // at least one rotation matched
    }
}