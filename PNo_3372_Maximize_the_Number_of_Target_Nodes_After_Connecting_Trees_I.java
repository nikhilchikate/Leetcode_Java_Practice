/*
There exist two undirected trees with n and m nodes, with distinct labels in ranges [0, n - 1] and [0, m - 1], respectively.

You are given two 2D integer arrays edges1 and edges2 of lengths n - 1 and m - 1, respectively, where edges1[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the first tree and edges2[i] = [ui, vi] indicates that there is an edge between nodes ui and vi in the second tree. You are also given an integer k.

Node u is target to node v if the number of edges on the path from u to v is less than or equal to k. Note that a node is always target to itself.

Return an array of n integers answer, where answer[i] is the maximum possible number of nodes target to node i of the first tree if you have to connect one node from the first tree to another node in the second tree.

Note that queries are independent from each other. That is, for every query you will remove the added edge before proceeding to the next query.



Example 1:

Input: edges1 = [[0,1],[0,2],[2,3],[2,4]], edges2 = [[0,1],[0,2],[0,3],[2,7],[1,4],[4,5],[4,6]], k = 2

Output: [9,7,9,8,8]

Explanation:

For i = 0, connect node 0 from the first tree to node 0 from the second tree.
For i = 1, connect node 1 from the first tree to node 0 from the second tree.
For i = 2, connect node 2 from the first tree to node 4 from the second tree.
For i = 3, connect node 3 from the first tree to node 4 from the second tree.
For i = 4, connect node 4 from the first tree to node 4 from the second tree.

Example 2:

Input: edges1 = [[0,1],[0,2],[0,3],[0,4]], edges2 = [[0,1],[1,2],[2,3]], k = 1

Output: [6,3,3,3,3]

Explanation:

For every i, connect node i of the first tree with any node of the second tree.




Constraints:

2 <= n, m <= 1000
edges1.length == n - 1
edges2.length == m - 1
edges1[i].length == edges2[i].length == 2
edges1[i] = [ai, bi]
0 <= ai, bi < n
edges2[i] = [ui, vi]
0 <= ui, vi < m
The input is generated such that edges1 and edges2 represent valid trees.
0 <= k <= 1000
*/

class Solution {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int n1 = edges1.length + 1;
        int n2 = edges2.length + 1;

        int maxReachableInGraph2 = 0;
        if (k > 0) {
            int[] reachable2 = calculateReachableForAllNodes(edges2, k - 1);
            for (int count : reachable2) {
                maxReachableInGraph2 = Math.max(maxReachableInGraph2, count);
            }
        }

        int[] reachable1 = calculateReachableForAllNodes(edges1, k);
        int[] ans = new int[n1];

        for (int i = 0; i < n1; ++i) {
            ans[i] = reachable1[i] + maxReachableInGraph2;
        }

        return ans;
    }

    private int[] calculateReachableForAllNodes(int[][] edges, int k) {
        int n = edges.length + 1;
        if (n == 1) {
            return new int[] { 1 };
        }
        if (k < 0) {
            int[] result = new int[n];
            Arrays.fill(result, (k == 0) ? 1 : 0);
            if (k == 0)
                return result;
            else
                return new int[n];
        }

        List<Integer>[] graph = buildGraph(edges);
        int[][] count = new int[n][k + 1];
        int[][] dp_up = new int[n][k + 1];

        List<Integer>[] children = new ArrayList[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        for (int i = 0; i < n; ++i)
            children[i] = new ArrayList<>();

        buildTreeStructure(0, -1, graph, children, parent);

        dfs_down(0, children, count, k);
        dfs_up(0, children, parent, count, dp_up, k);

        int[] result = new int[n];
        for (int i = 0; i < n; ++i) {
            int current_sum = 0;
            for (int d = 0; d <= k; ++d) {
                current_sum += count[i][d];
                if (d > 0) {
                    current_sum += dp_up[i][d];
                }
            }
            result[i] = current_sum;
        }
        return result;
    }

    private void buildTreeStructure(int u, int p, List<Integer>[] graph, List<Integer>[] children, int[] parent) {
        parent[u] = p;
        for (int v : graph[u]) {
            if (v != p) {
                children[u].add(v);
                buildTreeStructure(v, u, graph, children, parent);
            }
        }
    }

    private void dfs_down(int u, List<Integer>[] children, int[][] count, int k) {
        count[u][0] = 1;
        for (int v : children[u]) {
            dfs_down(v, children, count, k);
            for (int d = 1; d <= k; ++d) {
                count[u][d] += count[v][d - 1];
            }
        }
    }

    private void dfs_up(int u, List<Integer>[] children, int[] parent, int[][] count, int[][] dp_up, int k) {
        int p = parent[u];
        if (p != -1) {
            for (int d = 1; d <= k; ++d) {
                int up_from_p = dp_up[p][d - 1];
                int down_from_p = count[p][d - 1];
                int u_contrib_to_p = (d >= 2) ? count[u][d - 2] : 0;

                dp_up[u][d] = up_from_p + (down_from_p - u_contrib_to_p);
            }
        }

        for (int v : children[u]) {
            dfs_up(v, children, parent, count, dp_up, k);
        }
    }

    private List<Integer>[] buildGraph(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        return graph;
    }
}