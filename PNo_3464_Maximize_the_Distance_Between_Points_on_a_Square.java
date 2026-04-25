/*
You are given an integer side, representing the edge length of a square with corners at (0, 0), (0, side), (side, 0), and (side, side) on a Cartesian plane.

You are also given a positive integer k and a 2D integer array points, where points[i] = [xi, yi] represents the coordinate of a point lying on the boundary of the square.

You need to select k elements among points such that the minimum Manhattan distance between any two points is maximized.

Return the maximum possible minimum Manhattan distance between the selected k points.

The Manhattan Distance between two cells (xi, yi) and (xj, yj) is |xi - xj| + |yi - yj|.



Example 1:

Input: side = 2, points = [[0,2],[2,0],[2,2],[0,0]], k = 4

Output: 2

Explanation:



Select all four points.

Example 2:

Input: side = 2, points = [[0,0],[1,2],[2,0],[2,2],[2,1]], k = 4

Output: 1

Explanation:



Select the points (0, 0), (2, 0), (2, 2), and (2, 1).

Example 3:

Input: side = 2, points = [[0,0],[0,1],[0,2],[1,2],[2,0],[2,2],[2,1]], k = 5

Output: 1

Explanation:



Select the points (0, 0), (0, 1), (0, 2), (1, 2), and (2, 2).



Constraints:

1 <= side <= 109
4 <= points.length <= min(4 * side, 15 * 103)
points[i] == [xi, yi]
The input is generated such that:
points[i] lies on the boundary of the square.
All points[i] are unique.
4 <= k <= min(25, points.length)
*/

class Solution {
    public int maxDistance(int limit, int[][] coords, int total) {
        long[] mapped = new long[coords.length];
        for (int i = 0; i < coords.length; i++) {
            int h = coords[i][0];
            int v = coords[i][1];
            if (h == 0) {
                mapped[i] = v;
            } else if (v == limit) {
                mapped[i] = limit + h;
            } else if (h == limit) {
                mapped[i] = limit * 3L - v;
            } else {
                mapped[i] = limit * 4L - h;
            }
        }
        Arrays.sort(mapped);

        int minVal = 1;
        int maxVal = limit + 1;
        while (minVal + 1 < maxVal) {
            int avg = (minVal + maxVal) >>> 1;
            if (verify(mapped, limit, total, avg)) {
                minVal = avg;
            } else {
                maxVal = avg;
            }
        }
        return minVal;
    }

    private boolean verify(long[] mapped, int limit, int total, int gap) {
        int[] trackers = new int[total];
        long anchor = mapped[0];
        for (int j = 1; j < total; j++) {
            int i = findNext(mapped, anchor + gap);
            if (i == mapped.length) {
                return false;
            }
            trackers[j] = i;
            anchor = mapped[i];
        }
        if (anchor - mapped[0] <= limit * 4L - gap) {
            return true;
        }

        int limitIdx = trackers[1];
        for (trackers[0] = 1; trackers[0] < limitIdx; trackers[0]++) {
            for (int j = 1; j < total; j++) {
                while (mapped[trackers[j]] < mapped[trackers[j - 1]] + gap) {
                    trackers[j]++;
                    if (trackers[j] == mapped.length) {
                        return false;
                    }
                }
            }
            if (mapped[trackers[total - 1]] - mapped[trackers[0]] <= limit * 4L - gap) {
                return true;
            }
        }
        return false;
    }

    private int findNext(long[] data, long goal) {
        int low = -1;
        int high = data.length;
        while (low + 1 < high) {
            int pivot = (low + high) >>> 1;
            if (data[pivot] >= goal) {
                high = pivot;
            } else {
                low = pivot;
            }
        }
        return high;
    }
}