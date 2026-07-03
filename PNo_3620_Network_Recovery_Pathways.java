/*
You are given a directed acyclic graph of n nodes numbered from 0 to n − 1. This is represented by a 2D array edges of length m, where edges[i] = [ui, vi, costi] indicates a one‑way communication from node ui to node vi with a recovery cost of costi.

Some nodes may be offline. You are given a boolean array online where online[i] = true means node i is online. Nodes 0 and n − 1 are always online.

A path from 0 to n − 1 is valid if:

All intermediate nodes on the path are online.
The total recovery cost of all edges on the path does not exceed k.
For each valid path, define its score as the minimum edge‑cost along that path.

Return the maximum path score (i.e., the largest minimum-edge cost) among all valid paths. If no valid path exists, return -1.

 

Example 1:

Input: edges = [[0,1,5],[1,3,10],[0,2,3],[2,3,4]], online = [true,true,true,true], k = 10

Output: 3

Explanation:



The graph has two possible routes from node 0 to node 3:

Path 0 → 1 → 3

Total cost = 5 + 10 = 15, which exceeds k (15 > 10), so this path is invalid.

Path 0 → 2 → 3

Total cost = 3 + 4 = 7 <= k, so this path is valid.

The minimum edge‐cost along this path is min(3, 4) = 3.

There are no other valid paths. Hence, the maximum among all valid path‐scores is 3.

Example 2:

Input: edges = [[0,1,7],[1,4,5],[0,2,6],[2,3,6],[3,4,2],[2,4,6]], online = [true,true,true,false,true], k = 12

Output: 6

Explanation:



Node 3 is offline, so any path passing through 3 is invalid.

Consider the remaining routes from 0 to 4:

Path 0 → 1 → 4

Total cost = 7 + 5 = 12 <= k, so this path is valid.

The minimum edge‐cost along this path is min(7, 5) = 5.

Path 0 → 2 → 3 → 4

Node 3 is offline, so this path is invalid regardless of cost.

Path 0 → 2 → 4

Total cost = 6 + 6 = 12 <= k, so this path is valid.

The minimum edge‐cost along this path is min(6, 6) = 6.

Among the two valid paths, their scores are 5 and 6. Therefore, the answer is 6.

 

Constraints:

n == online.length
2 <= n <= 5 * 104
0 <= m == edges.length <= min(105, n * (n - 1) / 2)
edges[i] = [ui, vi, costi]
0 <= ui, vi < n
ui != vi
0 <= costi <= 109
0 <= k <= 5 * 1013
online[i] is either true or false, and both online[0] and online[n − 1] are true.
The given graph is a directed acyclic graph.
*/

class Solution {
    private int[] head, next, to, weight;
    private long k;
    private int n;

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        this.n = online.length;
        this.k = k;

        int m = edges.length;
        this.head = new int[n];
        this.next = new int[m];
        this.to = new int[m];
        this.weight = new int[m];
        Arrays.fill(head, -1);
        int left = Integer.MAX_VALUE, right = 0;

        for (int i = 0; i < m; i++) {
            int a = edges[i][0], b = edges[i][1], c = edges[i][2];
            if (online[a] && online[b]) {
                to[i] = b;
                next[i] = head[a];
                weight[i] = c;
                head[a] = i;

                if (c > right)
                    right = c;
                if (c < left)
                    left = c;
            }
        }

        if (!check(0))
            return -1;

        while (left < right) {
            int mid = left + right + 1 >>> 1;
            if (check(mid))
                left = mid;
            else
                right = mid - 1;
        }
        
        return left;
    }

    private static final int[] queue = new int[50001];
    private static final long[] sum = new long[50001];

    private boolean check(int threshold) {
        long[] dist = new long[n];
        Arrays.fill(dist, k + 1);
        dist[0] = 0;

        int read = 0, write = 1;
        while (read < write) {
            int current = queue[read];
            long val = sum[read++];
            if (val > dist[current])
                continue;
            for (int i = head[current]; i != -1; i = next[i]) {
                if (weight[i] < threshold)
                    continue;
                int nextIndex = to[i];
                long d = val + weight[i];
                if (d < dist[nextIndex]) {
                    if (nextIndex == n - 1)
                        return true;
                    dist[nextIndex] = d;
                    sum[write] = d;
                    queue[write++] = nextIndex;
                }
            }
        }
        return false;
    }

    private static final class Node {
        private final int index;
        private final long dist;

        private Node(int index, long dist) {
            this.index = index;
            this.dist = dist;
        }
    }
}