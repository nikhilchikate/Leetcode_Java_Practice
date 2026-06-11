/*
There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1. The tree is represented by a 2D integer array edges of length n - 1, where edges[i] = [ui, vi] indicates that there is an edge between nodes ui and vi.

Initially, all edges have a weight of 0. You must assign each edge a weight of either 1 or 2.

The cost of a path between any two nodes u and v is the total weight of all edges in the path connecting them.

Select any one node x at the maximum depth. Return the number of ways to assign edge weights in the path from node 1 to x such that its total cost is odd.

Since the answer may be large, return it modulo 109 + 7.

Note: Ignore all edges not in the path from node 1 to x.

 

Example 1:



Input: edges = [[1,2]]

Output: 1

Explanation:

The path from Node 1 to Node 2 consists of one edge (1 → 2).
Assigning weight 1 makes the cost odd, while 2 makes it even. Thus, the number of valid assignments is 1.
Example 2:



Input: edges = [[1,2],[1,3],[3,4],[3,5]]

Output: 2

Explanation:

The maximum depth is 2, with nodes 4 and 5 at the same depth. Either node can be selected for processing.
For example, the path from Node 1 to Node 4 consists of two edges (1 → 3 and 3 → 4).
Assigning weights (1,2) or (2,1) results in an odd cost. Thus, the number of valid assignments is 2.
 

Constraints:

2 <= n <= 105
edges.length == n - 1
edges[i] == [ui, vi]
1 <= ui, vi <= n
edges represents a valid tree.
*/

class Solution {
    static final long MOD = 1_000_000_007L;

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;

        int[] deg = new int[n + 1];
        for (int[] e : edges) {
            deg[e[0]]++;
            deg[e[1]]++;
        }

        int[][] g = new int[n + 1][];
        for (int i = 1; i <= n; i++) {
            g[i] = new int[deg[i]];
        }

        int[] idx = new int[n + 1];
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            g[u][idx[u]++] = v;
            g[v][idx[v]++] = u;
        }

        int[] depth = new int[n + 1];
        boolean[] vis = new boolean[n + 1];

        int[] q = new int[n];
        int l = 0, r = 0;
        q[r++] = 1;
        vis[1] = true;

        int maxDepth = 0;

        while (l < r) {
            int u = q[l++];
            maxDepth = Math.max(maxDepth, depth[u]);

            for (int v : g[u]) {
                if (!vis[v]) {
                    vis[v] = true;
                    depth[v] = depth[u] + 1;
                    q[r++] = v;
                }
            }
        }

        return (int) modPow(2, maxDepth - 1);
    }

    private long modPow(long a, int b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1)
                res = res * a % MOD;
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }
}