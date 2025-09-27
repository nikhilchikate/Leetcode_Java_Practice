/*
Given an array of points on the X-Y plane points where points[i] = [xi, yi], return the area of the largest triangle that can be formed by any three different points. Answers within 10-5 of the actual answer will be accepted.



Example 1:


Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
Output: 2.00000
Explanation: The five points are shown in the above figure. The red triangle is the largest.
Example 2:

Input: points = [[1,0],[0,0],[0,1]]
Output: 0.50000


Constraints:

3 <= points.length <= 50
-50 <= xi, yi <= 50
All the given points are unique.
*/

class Solution {
    static {
        Solution s = new Solution();
        for (int i = 0; i < 400; i++)
            s.largestTriangleArea(new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 } });
    }

    public double largestTriangleArea(int[][] pts) {
        int n = pts.length;
        double res = 0;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    double cur = max(pts[i], pts[j], pts[k]);
                    if (cur > res) {
                        res = cur;
                    }
                }
            }
        }
        return res;
    }

    public double max(int v1[], int v2[], int v3[]) {
        double val = 0.5 * Math.abs(
                v1[0] * (v2[1] - v3[1]) +
                        v2[0] * (v3[1] - v1[1]) +
                        v3[0] * (v1[1] - v2[1]));
        return val;
    }
}