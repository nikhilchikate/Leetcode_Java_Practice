/*
You are given a 0-indexed 2D integer array pairs where pairs[i] = [starti, endi]. An arrangement of pairs is valid if for every index i where 1 <= i < pairs.length, we have endi-1 == starti.

Return any valid arrangement of pairs.

Note: The inputs will be generated such that there exists a valid arrangement of pairs.



Example 1:

Input: pairs = [[5,1],[4,5],[11,9],[9,4]]
Output: [[11,9],[9,4],[4,5],[5,1]]
Explanation:
This is a valid arrangement since endi-1 always equals starti.
end0 = 9 == 9 = start1
end1 = 4 == 4 = start2
end2 = 5 == 5 = start3
Example 2:

Input: pairs = [[1,3],[3,2],[2,1]]
Output: [[1,3],[3,2],[2,1]]
Explanation:
This is a valid arrangement since endi-1 always equals starti.
end0 = 3 == 3 = start1
end1 = 2 == 2 = start2
The arrangements [[2,1],[1,3],[3,2]] and [[3,2],[2,1],[1,3]] are also valid.
Example 3:

Input: pairs = [[1,2],[1,3],[2,1]]
Output: [[1,2],[2,1],[1,3]]
Explanation:
This is a valid arrangement since endi-1 always equals starti.
end0 = 2 == 2 = start1
end1 = 1 == 1 = start2


Constraints:

1 <= pairs.length <= 105
pairs[i].length == 2
0 <= starti, endi <= 109
starti != endi
No two pairs are exactly the same.
There exists a valid arrangement of pairs.
*/

class Solution {
    public int[][] validArrangement(int[][] pairs) {
        int n = pairs.length;

        int[][] result = new int[n][2]; // result to store the valid arrangement
        for (int[] res : result) {
            res[0] = -1;
            res[1] = -1;
        }

        Map<Integer, Integer> degreeDiff = new HashMap<>(); // track degree differences (outdegree - indegree)
        Map<Integer, Deque<Integer>> adjList = new HashMap<>(); // adjacency list for the graph

        // Build degree differences and adjacency list
        for (int[] pair : pairs) {
            degreeDiff.put(pair[0], degreeDiff.getOrDefault(pair[0], 0) + 1);
            degreeDiff.put(pair[1], degreeDiff.getOrDefault(pair[1], 0) - 1);

            adjList.computeIfAbsent(pair[0], k -> new ArrayDeque<>());
            adjList.computeIfAbsent(pair[1], k -> new ArrayDeque<>());

            adjList.get(pair[0]).addLast(pair[1]);
        }

        // Identify the start and end nodes based on degree differences
        for (Map.Entry<Integer, Integer> entry : degreeDiff.entrySet()) {
            if (entry.getValue() == 1)
                result[0][0] = entry.getKey(); // Start node
            if (entry.getValue() == -1)
                result[n - 1][1] = entry.getKey(); // End node
        }

        // If no start or end node is found, pick the first pair's start node
        if (result[0][0] == -1) {
            result[0][0] = pairs[0][0];
            result[n - 1][1] = pairs[0][0];
        }

        int left = 0;
        int right = n - 1;
        // Traverse the graph to build the valid arrangement
        while (left < right) {
            int from = result[left][0];

            Deque<Integer> nextNodes = adjList.get(from);

            if (nextNodes.isEmpty()) {
                result[right][0] = result[--left][0];
                result[--right][1] = result[right + 1][0];
            } else {
                result[left++][1] = nextNodes.removeLast();
                result[left][0] = result[left - 1][1];
            }
        }

        return result;
    }
}