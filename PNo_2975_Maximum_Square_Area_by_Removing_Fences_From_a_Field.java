/*
There is a large (m - 1) x (n - 1) rectangular field with corners at (1, 1) and (m, n) containing some horizontal and vertical fences given in arrays hFences and vFences respectively.

Horizontal fences are from the coordinates (hFences[i], 1) to (hFences[i], n) and vertical fences are from the coordinates (1, vFences[i]) to (m, vFences[i]).

Return the maximum area of a square field that can be formed by removing some fences (possibly none) or -1 if it is impossible to make a square field.

Since the answer may be large, return it modulo 109 + 7.

Note: The field is surrounded by two horizontal fences from the coordinates (1, 1) to (1, n) and (m, 1) to (m, n) and two vertical fences from the coordinates (1, 1) to (m, 1) and (1, n) to (m, n). These fences cannot be removed.



Example 1:



Input: m = 4, n = 3, hFences = [2,3], vFences = [2]
Output: 4
Explanation: Removing the horizontal fence at 2 and the vertical fence at 2 will give a square field of area 4.
Example 2:



Input: m = 6, n = 7, hFences = [2], vFences = [4]
Output: -1
Explanation: It can be proved that there is no way to create a square field by removing fences.


Constraints:

3 <= m, n <= 109
1 <= hFences.length, vFences.length <= 600
1 < hFences[i] < m
1 < vFences[i] < n
hFences and vFences are unique.
*/

class Solution {
    public int maximizeSquareArea(int height, int width, int[] hCuts, int[] vCuts) {
        long MOD = 1000000007L;
        long bestArea = 0L;
        long candidate = 0L;

        int ySize = 2 + hCuts.length;
        int xSize = 2 + vCuts.length;

        int[] yCoords = new int[ySize];
        int[] xCoords = new int[xSize];
        yCoords[0] = 1;
        yCoords[ySize - 1] = height;
        xCoords[0] = 1;
        xCoords[xSize - 1] = width;

        Set<Long> xDiffs = new HashSet<>();

        int hLen = hCuts.length;
        int vLen = vCuts.length;

        for (int i = 1; i <= hLen; ++i) {
            yCoords[i] = hCuts[i - 1];
        }
        for (int j = 1; j <= vLen; ++j) {
            xCoords[j] = vCuts[j - 1];
        }

        Arrays.sort(yCoords);
        Arrays.sort(xCoords);

        int xLen = xCoords.length;
        int yLen = yCoords.length;

        for (int i = 0; i < xLen; ++i) {
            for (int j = i; j < xLen; ++j) {
                long dx = (long) xCoords[i] - xCoords[j];
                xDiffs.add(dx);
            }
        }

        for (int i = 0; i < yLen; ++i) {
            for (int j = i; j < yLen; ++j) {
                long dy = (long) yCoords[i] - yCoords[j];
                if (dy * dy <= bestArea)
                    continue;
                if (xDiffs.contains(dy)) {
                    candidate = dy * dy;
                    bestArea = Math.max(bestArea, candidate);
                }
            }
        }

        if (bestArea == 0) {
            return -1;
        }
        return (int) (bestArea % MOD);
    }
}