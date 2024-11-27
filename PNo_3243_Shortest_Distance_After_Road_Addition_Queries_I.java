/*
You are given an integer n and a 2D integer array queries.

There are n cities numbered from 0 to n - 1. Initially, there is a unidirectional road from city i to city i + 1 for all 0 <= i < n - 1.

queries[i] = [ui, vi] represents the addition of a new unidirectional road from city ui to city vi. After each query, you need to find the length of the shortest path from city 0 to city n - 1.

Return an array answer where for each i in the range [0, queries.length - 1], answer[i] is the length of the shortest path from city 0 to city n - 1 after processing the first i + 1 queries.



Example 1:

Input: n = 5, queries = [[2,4],[0,2],[0,4]]

Output: [3,2,1]

Explanation:



After the addition of the road from 2 to 4, the length of the shortest path from 0 to 4 is 3.



After the addition of the road from 0 to 2, the length of the shortest path from 0 to 4 is 2.



After the addition of the road from 0 to 4, the length of the shortest path from 0 to 4 is 1.

Example 2:

Input: n = 4, queries = [[0,3],[0,2]]

Output: [1,1]

Explanation:



After the addition of the road from 0 to 3, the length of the shortest path from 0 to 3 is 1.



After the addition of the road from 0 to 2, the length of the shortest path remains 1.



Constraints:

3 <= n <= 500
1 <= queries.length <= 500
queries[i].length == 2
0 <= queries[i][0] < queries[i][1] < n
1 < queries[i][1] - queries[i][0]
There are no repeated roads among the queries.
*/

class Solution {
    public int[] shortestDistanceAfterQueries(final int n, final int[][] q) {
        final int[][] ch = initChildren(n, q);

        final int[] dist = new int[n];
        for (int i = 1; i < n; ++i) {
            dist[i] = i;
        }

        final int[] bfsQueue = new int[n];
        final int[] res = new int[q.length];

        for (int i = 0; i < q.length; ++i) {
            final int u = q[i][0];
            final int v = q[i][1];
            for (int j = 0;; ++j) {
                if (ch[u][j] == 0) {
                    ch[u][j] = v;
                    break;
                }
            }
            bfsQueue[0] = u;
            for (int next = 0, end = 1; next < end;) {
                final int curr = bfsQueue[next++];
                for (final int nextNode : ch[curr]) {
                    if (nextNode == 0) {
                        break;
                    }
                    if (dist[nextNode] <= dist[curr] + 1) {
                        continue;
                    }
                    dist[nextNode] = dist[curr] + 1;
                    bfsQueue[end++] = nextNode;
                }
            }
            res[i] = dist[n - 1];
        }
        return res;
    }

    private int[][] initChildren(final int n, final int[][] q) {
        final int[] childCount = new int[n];
        Arrays.fill(childCount, 1);
        childCount[n - 1] = 0;

        for (final int[] query : q) {
            final int u = query[0];
            childCount[u]++;
        }

        final int[][] ch = new int[n][];
        for (int i = 0; i < n - 1; ++i) {
            ch[i] = new int[childCount[i]];
            ch[i][0] = i + 1;
        }
        assert childCount[n - 1] == 0;
        ch[n - 1] = new int[0];
        return ch;
    }
}