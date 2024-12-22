/*
You are given a 0-indexed array heights of positive integers, where heights[i] represents the height of the ith building.

If a person is in building i, they can move to any other building j if and only if i < j and heights[i] < heights[j].

You are also given another array queries where queries[i] = [ai, bi]. On the ith query, Alice is in building ai while Bob is in building bi.

Return an array ans where ans[i] is the index of the leftmost building where Alice and Bob can meet on the ith query. If Alice and Bob cannot move to a common building on query i, set ans[i] to -1.



Example 1:

Input: heights = [6,4,8,5,2,7], queries = [[0,1],[0,3],[2,4],[3,4],[2,2]]
Output: [2,5,-1,5,2]
Explanation: In the first query, Alice and Bob can move to building 2 since heights[0] < heights[2] and heights[1] < heights[2].
In the second query, Alice and Bob can move to building 5 since heights[0] < heights[5] and heights[3] < heights[5].
In the third query, Alice cannot meet Bob since Alice cannot move to any other building.
In the fourth query, Alice and Bob can move to building 5 since heights[3] < heights[5] and heights[4] < heights[5].
In the fifth query, Alice and Bob are already in the same building.
For ans[i] != -1, It can be shown that ans[i] is the leftmost building where Alice and Bob can meet.
For ans[i] == -1, It can be shown that there is no building where Alice and Bob can meet.
Example 2:

Input: heights = [5,3,8,2,6,1,4,6], queries = [[0,7],[3,5],[5,2],[3,0],[1,6]]
Output: [7,6,-1,4,6]
Explanation: In the first query, Alice can directly move to Bob's building since heights[0] < heights[7].
In the second query, Alice and Bob can move to building 6 since heights[3] < heights[6] and heights[5] < heights[6].
In the third query, Alice cannot meet Bob since Bob cannot move to any other building.
In the fourth query, Alice and Bob can move to building 4 since heights[3] < heights[4] and heights[0] < heights[4].
In the fifth query, Alice can directly move to Bob's building since heights[1] < heights[6].
For ans[i] != -1, It can be shown that ans[i] is the leftmost building where Alice and Bob can meet.
For ans[i] == -1, It can be shown that there is no building where Alice and Bob can meet.



Constraints:

1 <= heights.length <= 5 * 104
1 <= heights[i] <= 109
1 <= queries.length <= 5 * 104
queries[i] = [ai, bi]
0 <= ai, bi <= heights.length - 1
*/

class Solution {
    public int[] leftmostBuildingQueries(int[] hts, int[][] qry) {
        List<int[]>[] lsts = new ArrayList[hts.length];
        int[] res = new int[qry.length];
        Arrays.fill(res, -1);

        for (int i = 0; i < qry.length; i++) {
            if (qry[i][0] > qry[i][1]) {
                if (hts[qry[i][0]] > hts[qry[i][1]]) {
                    res[i] = qry[i][0];
                } else {
                    if (lsts[qry[i][0]] == null)
                        lsts[qry[i][0]] = new ArrayList<>();
                    lsts[qry[i][0]].add(new int[] { hts[qry[i][1]], i });
                }
            } else if (qry[i][0] < qry[i][1]) {
                if (hts[qry[i][0]] < hts[qry[i][1]]) {
                    res[i] = qry[i][1];
                } else {
                    if (lsts[qry[i][1]] == null)
                        lsts[qry[i][1]] = new ArrayList<>();
                    lsts[qry[i][1]].add(new int[] { hts[qry[i][0]], i });
                }
            } else {
                res[i] = qry[i][0];
            }
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        for (int i = 0; i < hts.length; i++) {
            while (!pq.isEmpty() && pq.peek()[0] < hts[i]) {
                res[pq.poll()[1]] = i;
            }
            if (lsts[i] != null) {
                for (int[] ar : lsts[i]) {
                    pq.add(ar);
                }
            }
        }

        return res;
    }
}