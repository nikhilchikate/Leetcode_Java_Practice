/*
There is a directed graph of n nodes with each node labeled from 0 to n - 1. The graph is represented by a 0-indexed 2D integer array graph where graph[i] is an integer array of nodes adjacent to node i, meaning there is an edge from node i to each node in graph[i].

A node is a terminal node if there are no outgoing edges. A node is a safe node if every possible path starting from that node leads to a terminal node (or another safe node).

Return an array containing all the safe nodes of the graph. The answer should be sorted in ascending order.



Example 1:

Illustration of graph
Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
Output: [2,4,5,6]
Explanation: The given graph is shown above.
Nodes 5 and 6 are terminal nodes as there are no outgoing edges from either of them.
Every path starting at nodes 2, 4, 5, and 6 all lead to either node 5 or 6.
Example 2:

Input: graph = [[1,2,3,4],[1,2],[3,4],[0,4],[]]
Output: [4]
Explanation:
Only node 4 is a terminal node, and every path starting at node 4 leads to node 4.


Constraints:

n == graph.length
1 <= n <= 104
0 <= graph[i].length <= n
0 <= graph[i][j] <= n - 1
graph[i] is sorted in a strictly increasing order.
The graph may contain self-loops.
The number of edges in the graph will be in the range [1, 4 * 104].
*/

import java.util.AbstractList;

class Solution {
    List<Integer> evntSafeNodes;

    public List<Integer> eventualSafeNodes(int[][] graph) {
        return new AbstractList<Integer>() {
            @Override
            public int size() {
                init();
                return evntSafeNodes.size();
            }

            @Override
            public Integer get(int index) {
                init();
                return evntSafeNodes.get(index);
            }

            private void init() {
                if (evntSafeNodes != null)
                    return;

                int n = graph.length;
                List<List<Integer>> reverseAdj = new ArrayList<>();
                evntSafeNodes = new ArrayList<>();
                int[] inDegree = new int[n];

                for (int i = 0; i < n; ++i) {
                    reverseAdj.add(new ArrayList<>());
                }

                for (int i = 0; i < n; ++i) {
                    for (int neighborNode : graph[i]) {
                        reverseAdj.get(neighborNode).add(i);
                        inDegree[i]++;
                    }
                }

                Queue<Integer> queue = new LinkedList<Integer>();
                for (int i = 0; i < n; ++i) {
                    if (inDegree[i] == 0) {
                        queue.offer(i);
                    }
                }

                while (!queue.isEmpty()) {
                    int evntSafeNode = queue.poll();
                    evntSafeNodes.add(evntSafeNode);

                    for (int neighbor : reverseAdj.get(evntSafeNode)) {
                        inDegree[neighbor]--;
                        if (inDegree[neighbor] == 0) {
                            queue.offer(neighbor);
                        }
                    }
                }
                Collections.sort(evntSafeNodes);
            }
        };
    }
}