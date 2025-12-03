/*
You are given a 2D integer array points where points[i] = [xi, yi] represents the coordinates of the ith point on the Cartesian plane.

Return the number of unique trapezoids that can be formed by choosing any four distinct points from points.

A trapezoid is a convex quadrilateral with at least one pair of parallel sides. Two lines are parallel if and only if they have the same slope.



Example 1:

Input: points = [[-3,2],[3,0],[2,3],[3,2],[2,-3]]

Output: 2

Explanation:



There are two distinct ways to pick four points that form a trapezoid:

The points [-3,2], [2,3], [3,2], [2,-3] form one trapezoid.
The points [2,3], [3,2], [3,0], [2,-3] form another trapezoid.
Example 2:

Input: points = [[0,0],[1,0],[0,1],[2,1]]

Output: 1

Explanation:



There is only one trapezoid which can be formed.



Constraints:

4 <= points.length <= 500
–1000 <= xi, yi <= 1000
All points are pairwise distinct.
*/

class Solution {
    public int countTrapezoids(int[][] pts) {
        int n = pts.length;

        Map<Double, Map<Double, Integer>> slopeMap = new HashMap<>(n * n);
        Map<Integer, Map<Double, Integer>> midMap = new HashMap<>(n * n);

        for (int i = 0; i < n; i++) {
            int x1 = pts[i][0], y1 = pts[i][1];
            for (int j = 0; j < i; j++) {

                int x2 = pts[j][0], y2 = pts[j][1];
                int dx = x2 - x1, dy = y2 - y1;

                double k = (dx == 0) ? Double.MAX_VALUE : (double) dy / dx;
                double b = (dx == 0) ? x1 : (double) (y1 * dx - x1 * dy) / dx;

                if (k == -0.0)
                    k = 0.0;
                if (b == -0.0)
                    b = 0.0;

                slopeMap.computeIfAbsent(k, a -> new HashMap<>())
                        .merge(b, 1, Integer::sum);

                int mid = (x1 + x2 + 2000) * 4096 + (y1 + y2 + 2000);

                midMap.computeIfAbsent(mid, a -> new HashMap<>())
                        .merge(k, 1, Integer::sum);
            }
        }

        int res = 0;

        for (var group : slopeMap.values()) {
            int acc = 0;
            for (int c : group.values()) {
                res += acc * c;
                acc += c;
            }
        }

        for (var group : midMap.values()) {
            int acc = 0;
            for (int c : group.values()) {
                res -= acc * c;
                acc += c;
            }
        }

        return res;
    }
}