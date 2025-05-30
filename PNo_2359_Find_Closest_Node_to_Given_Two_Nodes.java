/*
You are given a directed graph of n nodes numbered from 0 to n - 1, where each node has at most one outgoing edge.

The graph is represented with a given 0-indexed array edges of size n, indicating that there is a directed edge from node i to node edges[i]. If there is no outgoing edge from i, then edges[i] == -1.

You are also given two integers node1 and node2.

Return the index of the node that can be reached from both node1 and node2, such that the maximum between the distance from node1 to that node, and from node2 to that node is minimized. If there are multiple answers, return the node with the smallest index, and if no possible answer exists, return -1.

Note that edges may contain cycles.



Example 1:


Input: edges = [2,2,3,-1], node1 = 0, node2 = 1
Output: 2
Explanation: The distance from node 0 to node 2 is 1, and the distance from node 1 to node 2 is 1.
The maximum of those two distances is 1. It can be proven that we cannot get a node with a smaller maximum distance than 1, so we return node 2.
Example 2:


Input: edges = [1,2,-1], node1 = 0, node2 = 2
Output: 2
Explanation: The distance from node 0 to node 2 is 2, and the distance from node 2 to itself is 0.
The maximum of those two distances is 2. It can be proven that we cannot get a node with a smaller maximum distance than 2, so we return node 2.


Constraints:

n == edges.length
2 <= n <= 105
-1 <= edges[i] < n
edges[i] != i
0 <= node1, node2 < n
*/

class Solution {
    static final int VISITED1 = 1;
    static final int VISITED2 = 2;
    static final int VISITED_BOTH = VISITED1 | VISITED2;

    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int n = edges.length;
        byte[] visited = new byte[n];
        int result = Integer.MAX_VALUE;

        while (result == Integer.MAX_VALUE && node1 >= 0 && node2 >= 0) {
            if ((visited[node1] & VISITED1) != 0)
                node1 = -1;
            else if ((visited[node1] |= VISITED1) == VISITED_BOTH)
                result = node1;
            else
                node1 = edges[node1];

            if ((visited[node2] & VISITED2) != 0) {
                node2 = -1;
                break;
            }
            if ((visited[node2] |= VISITED2) == VISITED_BOTH) {
                result = Math.min(result, node2);
                break;
            }
            node2 = edges[node2];
        }
        if (result != Integer.MAX_VALUE)
            return result;

        if (node1 >= 0) {
            while (node1 >= 0) {
                if ((visited[node1] & VISITED1) != 0)
                    return -1;
                if ((visited[node1] |= VISITED1) == VISITED_BOTH)
                    return node1;
                node1 = edges[node1];
            }
            return -1;
        }

        while (node2 >= 0) {
            if ((visited[node2] & VISITED2) != 0)
                return -1;
            if ((visited[node2] |= VISITED2) == VISITED_BOTH)
                return node2;
            node2 = edges[node2];
        }
        return -1;
    }
}