/*
You are given a positive integer n, representing an n x n city. You are also given a 2D grid buildings, where buildings[i] = [x, y] denotes a unique building located at coordinates [x, y].

A building is covered if there is at least one building in all four directions: left, right, above, and below.

Return the number of covered buildings.



Example 1:



Input: n = 3, buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]

Output: 1

Explanation:

Only building [2,2] is covered as it has at least one building:
above ([1,2])
below ([3,2])
left ([2,1])
right ([2,3])
Thus, the count of covered buildings is 1.
Example 2:



Input: n = 3, buildings = [[1,1],[1,2],[2,1],[2,2]]

Output: 0

Explanation:

No building has at least one building in all four directions.
Example 3:



Input: n = 5, buildings = [[1,3],[3,2],[3,3],[3,5],[5,3]]

Output: 1

Explanation:

Only building [3,3] is covered as it has at least one building:
above ([1,3])
below ([5,3])
left ([3,2])
right ([3,5])
Thus, the count of covered buildings is 1.


Constraints:

2 <= n <= 105
1 <= buildings.length <= 105
buildings[i] = [x, y]
1 <= x, y <= n
All coordinates of buildings are unique.
*/

class Solution {

    public int countCoveredBuildings(int m, int[][] pts) {
        int[] hiR = new int[m + 1];
        int[] loR = new int[m + 1];
        int[] hiC = new int[m + 1];
        int[] loC = new int[m + 1];

        Arrays.fill(loR, m + 1);
        Arrays.fill(loC, m + 1);

        for (int[] q : pts) {
            int a = q[0];
            int b = q[1];
            hiR[b] = Math.max(hiR[b], a);
            loR[b] = Math.min(loR[b], a);
            hiC[a] = Math.max(hiC[a], b);
            loC[a] = Math.min(loC[a], b);
        }

        int out = 0;
        for (int[] q : pts) {
            int a = q[0];
            int b = q[1];
            if (a > loR[b] && a < hiR[b] && b > loC[a] && b < hiC[a]) {
                out++;
            }
        }

        return out;
    }
}