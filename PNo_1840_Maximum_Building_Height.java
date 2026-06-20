/*
You want to build n new buildings in a city. The new buildings will be built in a line and are labeled from 1 to n.

However, there are city restrictions on the heights of the new buildings:

The height of each building must be a non-negative integer.
The height of the first building must be 0.
The height difference between any two adjacent buildings cannot exceed 1.
Additionally, there are city restrictions on the maximum height of specific buildings. These restrictions are given as a 2D integer array restrictions where restrictions[i] = [idi, maxHeighti] indicates that building idi must have a height less than or equal to maxHeighti.

It is guaranteed that each building will appear at most once in restrictions, and building 1 will not be in restrictions.

Return the maximum possible height of the tallest building.

 

Example 1:


Input: n = 5, restrictions = [[2,1],[4,1]]
Output: 2
Explanation: The green area in the image indicates the maximum allowed height for each building.
We can build the buildings with heights [0,1,2,1,2], and the tallest building has a height of 2.
Example 2:


Input: n = 6, restrictions = []
Output: 5
Explanation: The green area in the image indicates the maximum allowed height for each building.
We can build the buildings with heights [0,1,2,3,4,5], and the tallest building has a height of 5.
Example 3:


Input: n = 10, restrictions = [[5,3],[2,5],[7,4],[10,3]]
Output: 5
Explanation: The green area in the image indicates the maximum allowed height for each building.
We can build the buildings with heights [0,1,2,3,3,4,4,5,4,3], and the tallest building has a height of 5.
 

Constraints:

2 <= n <= 109
0 <= restrictions.length <= min(n - 1, 105)
2 <= idi <= n
idi is unique.
0 <= maxHeighti <= 109
*/

class Solution {
    public int maxBuilding(int n, int[][] restrictions) {
        int len = restrictions.length;

        if (len == 0) {
            return n - 1;
        }

        Arrays.sort(restrictions, (a, b) -> Integer.compare(a[0], b[0]));

        restrictions[0][1] = Math.min(restrictions[0][1], restrictions[0][0] - 1);
        for (int i = 1; i < len; i++) {
            int fromLeft = restrictions[i - 1][1] + (restrictions[i][0] - restrictions[i - 1][0]);
            if (restrictions[i][1] > fromLeft) {
                restrictions[i][1] = fromLeft;
            }
        }

        for (int i = len - 2; i >= 0; i--) {
            int fromRight = restrictions[i + 1][1] + (restrictions[i + 1][0] - restrictions[i][0]);
            if (restrictions[i][1] > fromRight) {
                restrictions[i][1] = fromRight;
            }
        }

        int maxH = 0;

        maxH = Math.max(maxH, (0 + restrictions[0][1] + restrictions[0][0] - 1) / 2);

        for (int i = 1; i < len; i++) {
            int pA = restrictions[i - 1][0];
            int hA = restrictions[i - 1][1];
            int pB = restrictions[i][0];
            int hB = restrictions[i][1];

            int peak = (hA + hB + (pB - pA)) / 2;
            if (peak > maxH) {
                maxH = peak;
            }
        }

        int lastP = restrictions[len - 1][0];
        int lastH = restrictions[len - 1][1];
        int endPeak = lastH + (n - lastP);
        if (endPeak > maxH) {
            maxH = endPeak;
        }

        return maxH;
    }
}