/*
You have a convex n-sided polygon where each vertex has an integer value. You are given an integer array values where values[i] is the value of the ith vertex in clockwise order.

Polygon triangulation is a process where you divide a polygon into a set of triangles and the vertices of each triangle must also be vertices of the original polygon. Note that no other shapes other than triangles are allowed in the division. This process will result in n - 2 triangles.

You will triangulate the polygon. For each triangle, the weight of that triangle is the product of the values at its vertices. The total score of the triangulation is the sum of these weights over all n - 2 triangles.

Return the minimum possible score that you can achieve with some triangulation of the polygon.



Example 1:



Input: values = [1,2,3]

Output: 6

Explanation: The polygon is already triangulated, and the score of the only triangle is 6.

Example 2:



Input: values = [3,7,4,5]

Output: 144

Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.
The minimum score is 144.

Example 3:



Input: values = [1,3,1,4,1,5]

Output: 13

Explanation: The minimum score triangulation is 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.



Constraints:

n == values.length
3 <= n <= 50
1 <= values[i] <= 100
*/

class Solution {
    int[][] memo;

    public int minScoreTriangulation(int[] v) {
        memo = new int[v.length][v.length];
        return solve(v, 0, v.length - 1);
    }

    int solve(int[] v, int i, int j) {
        if (j - i < 2) {
            return 0;
        }
        if (memo[i][j] != 0)
            return memo[i][j];

        int minCost = Integer.MAX_VALUE;

        for (int k = i + 1; k < j; k++) {
            int currentCost = v[i] * v[k] * v[j] + solve(v, i, k) + solve(v, k, j);
            minCost = Math.min(minCost, currentCost);
        }

        return memo[i][j] = minCost;
    }
}