/*
There is a dungeon with n x m rooms arranged as a grid.

You are given a 2D array moveTime of size n x m, where moveTime[i][j] represents the minimum time in seconds when you can start moving to that room. You start from the room (0, 0) at time t = 0 and can move to an adjacent room. Moving between adjacent rooms takes exactly one second.

Return the minimum time to reach the room (n - 1, m - 1).

Two rooms are adjacent if they share a common wall, either horizontally or vertically.



Example 1:

Input: moveTime = [[0,4],[4,4]]

Output: 6

Explanation:

The minimum time required is 6 seconds.

At time t == 4, move from room (0, 0) to room (1, 0) in one second.
At time t == 5, move from room (1, 0) to room (1, 1) in one second.
Example 2:

Input: moveTime = [[0,0,0],[0,0,0]]

Output: 3

Explanation:

The minimum time required is 3 seconds.

At time t == 0, move from room (0, 0) to room (1, 0) in one second.
At time t == 1, move from room (1, 0) to room (1, 1) in one second.
At time t == 2, move from room (1, 1) to room (1, 2) in one second.
Example 3:

Input: moveTime = [[0,1],[1,2]]

Output: 3



Constraints:

2 <= n == moveTime.length <= 50
2 <= m == moveTime[i].length <= 50
0 <= moveTime[i][j] <= 109
*/

import java.util.*;

class Solution {
    private static class R implements Comparable<R> {
        final int t;
        R[] adj;
        R nxt;

        R() {
            t = Integer.MAX_VALUE;
        }

        R(int t) {
            this.t = t;
            nxt = this;
        }

        @Override
        public int compareTo(R o) {
            return t - o.t;
        }
    }

    private static final R DUMMY = new R();

    private static R init(int[][] mt) {
        int n = mt.length;
        int m = mt[0].length;
        R[][] rms = new R[n][m];
        for (int i = 0; i < n; i++) {
            int[] mr = mt[i];
            R[] rr = rms[i];
            for (int j = 0; j < m; j++)
                rr[j] = new R(mr[j]);
        }
        R[] dr = new R[m];
        Arrays.fill(dr, DUMMY);
        R[] prvR = dr;
        R[] curR = rms[0];
        n--;
        m--;
        for (int i = 0; i <= n; i++) {
            R[] nxtR = i < n ? rms[i + 1] : dr;
            R prv = DUMMY;
            R cur = curR[0];
            for (int j = 0; j <= m; j++) {
                R nxt = j < m ? curR[j + 1] : DUMMY;
                cur.adj = new R[] { prv, prvR[j], nxt, nxtR[j] };
                prv = cur;
                cur = nxt;
            }
            prvR = curR;
            curR = nxtR;
        }
        R start = rms[0][0];
        start.nxt = rms[n][m];
        return start;
    }

    public static int minTimeToReach(int[][] mt) {
        R start = init(mt);
        R finish = start.nxt;
        Queue<R> waitQ = new PriorityQueue<>();
        waitQ.add(DUMMY);
        start.nxt = null;
        R exitH = start;
        int time = 0;
        while (true) {
            R exitHNew = null;
            while (exitH != null) {
                for (R adj : exitH.adj)
                    if (adj.nxt == adj) {
                        if (adj == finish)
                            return Math.max(time, finish.t) + 1;
                        if (adj.t <= time) {
                            adj.nxt = exitHNew;
                            exitHNew = adj;
                        } else {
                            adj.nxt = null;
                            waitQ.offer(adj);
                        }
                    }
                exitH = exitH.nxt;
            }
            exitH = exitHNew;
            int qTime;
            while ((qTime = waitQ.peek().t) <= time) {
                R enter = waitQ.poll();
                enter.nxt = exitH;
                exitH = enter;
            }
            if (++time < qTime && exitH == null)
                time = qTime;
        }
    }
}