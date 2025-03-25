/*
You are given an integer n representing the dimensions of an n x n grid, with the origin at the bottom-left corner of the grid. You are also given a 2D array of coordinates rectangles, where rectangles[i] is in the form [startx, starty, endx, endy], representing a rectangle on the grid. Each rectangle is defined as follows:

(startx, starty): The bottom-left corner of the rectangle.
(endx, endy): The top-right corner of the rectangle.
Note that the rectangles do not overlap. Your task is to determine if it is possible to make either two horizontal or two vertical cuts on the grid such that:

Each of the three resulting sections formed by the cuts contains at least one rectangle.
Every rectangle belongs to exactly one section.
Return true if such cuts can be made; otherwise, return false.



Example 1:

Input: n = 5, rectangles = [[1,0,5,2],[0,2,2,4],[3,2,5,3],[0,4,4,5]]

Output: true

Explanation:



The grid is shown in the diagram. We can make horizontal cuts at y = 2 and y = 4. Hence, output is true.

Example 2:

Input: n = 4, rectangles = [[0,0,1,1],[2,0,3,4],[0,2,2,3],[3,0,4,3]]

Output: true

Explanation:



We can make vertical cuts at x = 2 and x = 3. Hence, output is true.

Example 3:

Input: n = 4, rectangles = [[0,2,2,4],[1,0,3,2],[2,2,3,4],[3,0,4,2],[3,2,4,4]]

Output: false

Explanation:

We cannot make two horizontal or two vertical cuts that satisfy the conditions. Hence, output is false.



Constraints:

3 <= n <= 109
3 <= rectangles.length <= 105
0 <= rectangles[i][0] < rectangles[i][2] <= n
0 <= rectangles[i][1] < rectangles[i][3] <= n
No two rectangles overlap.
*/

public class Solution {
    private static final int BITMASK = (1 << 30) - 1;

    public boolean checkValidCuts(int m, int[][] rects) {
        int rectCnt = rects.length;
        long[] yVals = new long[rectCnt];
        for (int i = 0; i < rectCnt; i++) {
            yVals[i] = ((long) rects[i][1] << 32) + rects[i][3];
        }
        Arrays.sort(yVals);
        if (checkCuts(yVals)) {
            return true;
        }
        long[] xVals = new long[rectCnt];
        for (int i = 0; i < rectCnt; i++) {
            xVals[i] = ((long) rects[i][0] << 32) + rects[i][2];
        }
        Arrays.sort(xVals);
        return checkCuts(xVals);
    }

    private boolean checkCuts(long[] vals) {
        int cutCnt = 0;
        int valCnt = vals.length;
        int maxEnd = (int) vals[0] & BITMASK;
        for (int i = 0; i < valCnt; i++) {
            int startVal = (int) (vals[i] >> 32);
            if (startVal >= maxEnd && ++cutCnt == 2) {
                return true;
            }
            maxEnd = Math.max(maxEnd, (int) (vals[i] & BITMASK));
        }
        return false;
    }
}