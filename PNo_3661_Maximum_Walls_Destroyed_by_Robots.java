/*
There is an endless straight line populated with some robots and walls. You are given integer arrays robots, distance, and walls:
robots[i] is the position of the ith robot.
distance[i] is the maximum distance the ith robot's bullet can travel.
walls[j] is the position of the jth wall.
Every robot has one bullet that can either fire to the left or the right at most distance[i] meters.

A bullet destroys every wall in its path that lies within its range. Robots are fixed obstacles: if a bullet hits another robot before reaching a wall, it immediately stops at that robot and cannot continue.

Return the maximum number of unique walls that can be destroyed by the robots.

Notes:

A wall and a robot may share the same position; the wall can be destroyed by the robot at that position.
Robots are not destroyed by bullets.


Example 1:

Input: robots = [4], distance = [3], walls = [1,10]

Output: 1

Explanation:

robots[0] = 4 fires left with distance[0] = 3, covering [1, 4] and destroys walls[0] = 1.
Thus, the answer is 1.
Example 2:

Input: robots = [10,2], distance = [5,1], walls = [5,2,7]

Output: 3

Explanation:

robots[0] = 10 fires left with distance[0] = 5, covering [5, 10] and destroys walls[0] = 5 and walls[2] = 7.
robots[1] = 2 fires left with distance[1] = 1, covering [1, 2] and destroys walls[1] = 2.
Thus, the answer is 3.
Example 3:
Input: robots = [1,2], distance = [100,1], walls = [10]

Output: 0

Explanation:

In this example, only robots[0] can reach the wall, but its shot to the right is blocked by robots[1]; thus the answer is 0.



Constraints:

1 <= robots.length == distance.length <= 105
1 <= walls.length <= 105
1 <= robots[i], walls[j] <= 109
1 <= distance[i] <= 105
All values in robots are unique
All values in walls are unique
*/

class Solution {
    public static final int INF = Integer.MAX_VALUE;
    public static final int NEG_INF = Integer.MIN_VALUE;

    record Robot(int position, int distance) {
    }

    public int maxWalls(int[] a, int[] b, int[] c) {
        int m = a.length;
        Robot[] r = new Robot[m];

        for (int i = 0; i < m; i++) {
            r[i] = new Robot(a[i], b[i]);
        }

        Arrays.sort(r, Comparator.comparingInt(o -> o.position));
        Arrays.sort(c);
        return maxWalls(r, m, c);
    }

    private int maxWalls(Robot[] r, int m, int[] c) {
        int[] dl = new int[m];
        int[] dr = new int[m];
        int[] lw = prepareLeftWall(r, m, c);
        int[] rw = prepareRightWall(r, m, c);
        int[] cm = prepareCommon(r, m, c);

        dl[0] = lw[0];
        dr[0] = rw[0];

        for (int i = 1; i < m; i++) {
            dl[i] = lw[i] + Math.max(dl[i - 1], dr[i - 1] - cm[i]);
            dr[i] = rw[i] + Math.max(dl[i - 1], dr[i - 1]);
        }

        return Math.max(dl[m - 1], dr[m - 1]);
    }

    private int[] prepareLeftWall(Robot[] r, int m, int[] w) {
        int[] lw = new int[m];
        int ws = w.length;
        int lo = 0;
        int hi = -1;

        for (int i = 0; i < m; i++) {
            Robot rb = r[i];
            int pp = (i == 0) ? NEG_INF : r[i - 1].position;
            int ss = Math.max(pp + 1, rb.position - rb.distance);
            int se = rb.position;

            while (hi + 1 < ws && w[hi + 1] <= se)
                ++hi;
            while (lo < ws && w[lo] < ss)
                ++lo;

            if (lo <= hi)
                lw[i] = hi - lo + 1;
        }
        return lw;
    }

    private int[] prepareRightWall(Robot[] r, int m, int[] w) {
        int[] rw = new int[m];
        int ws = w.length;
        int lo = 0;
        int hi = -1;

        for (int i = 0; i < m; i++) {
            Robot rb = r[i];
            int np = (i == m - 1) ? INF : r[i + 1].position;
            int ss = rb.position;
            int se = Math.min(np - 1, rb.position + rb.distance);

            while (hi + 1 < ws && w[hi + 1] <= se)
                ++hi;
            while (lo < ws && w[lo] < ss)
                ++lo;

            if (lo <= hi)
                rw[i] = hi - lo + 1;
        }
        return rw;
    }

    private int[] prepareCommon(Robot[] r, int m, int[] w) {
        int[] cm = new int[m];
        int ws = w.length;
        int lo = 0;
        int hi = -1;

        for (int i = 1; i < m; i++) {
            Robot pv = r[i - 1];
            Robot cr = r[i];
            int ss = Math.max(pv.position + 1, cr.position - cr.distance);
            int se = Math.min(cr.position - 1, pv.position + pv.distance);

            if (ss > se)
                continue;

            while (hi + 1 < ws && w[hi + 1] <= se)
                ++hi;
            while (lo < ws && w[lo] < ss)
                ++lo;

            if (lo <= hi)
                cm[i] = hi - lo + 1;
        }
        return cm;
    }
}