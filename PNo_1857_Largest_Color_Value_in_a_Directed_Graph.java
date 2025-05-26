/*
There is a directed graph of n colored nodes and m edges. The nodes are numbered from 0 to n - 1.

You are given a string colors where colors[i] is a lowercase English letter representing the color of the ith node in this graph (0-indexed). You are also given a 2D array edges where edges[j] = [aj, bj] indicates that there is a directed edge from node aj to node bj.

A valid path in the graph is a sequence of nodes x1 -> x2 -> x3 -> ... -> xk such that there is a directed edge from xi to xi+1 for every 1 <= i < k. The color value of the path is the number of nodes that are colored the most frequently occurring color along that path.

Return the largest color value of any valid path in the given graph, or -1 if the graph contains a cycle.



Example 1:



Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
Output: 3
Explanation: The path 0 -> 2 -> 3 -> 4 contains 3 nodes that are colored "a" (red in the above image).
Example 2:



Input: colors = "a", edges = [[0,0]]
Output: -1
Explanation: There is a cycle from 0 to 0.


Constraints:

n == colors.length
m == edges.length
1 <= n <= 105
0 <= m <= 105
colors consists of lowercase English letters.
0 <= aj, bj < n
*/

class Solution {
    public int largestPathValue(String colorsString, int[][] graphEdges) {

        char[] nodeColors = colorsString.toCharArray();
        int numberOfNodes = nodeColors.length;
        List<Integer>[] adjacencyList = new List[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++)
            adjacencyList[i] = new LinkedList<>();

        int[] inDegrees = new int[numberOfNodes];
        for (int[] edge : graphEdges) {
            int sourceNode = edge[0];
            int destinationNode = edge[1];
            inDegrees[destinationNode]++;
            adjacencyList[sourceNode].add(destinationNode);
        }

        int[][] colorFrequency = new int[numberOfNodes][26];

        Queue<Integer> nodesToProcess = new LinkedList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            if (inDegrees[i] == 0)
                nodesToProcess.offer(i);
        }

        int nodesVisited = 0;
        int maximumPathValue = 0;

        while (!nodesToProcess.isEmpty()) {
            ++nodesVisited;
            int currentNode = nodesToProcess.poll();
            int colorIndex = nodeColors[currentNode] - 'a';
            maximumPathValue = Math.max(maximumPathValue, ++colorFrequency[currentNode][colorIndex]);

            for (int neighborNode : adjacencyList[currentNode]) {
                for (int i = 0; i < 26; ++i)
                    colorFrequency[neighborNode][i] = Math.max(colorFrequency[neighborNode][i],
                            colorFrequency[currentNode][i]);

                if (--inDegrees[neighborNode] == 0)
                    nodesToProcess.offer(neighborNode);

            }
        }

        return nodesVisited == numberOfNodes ? maximumPathValue : -1;
    }
}