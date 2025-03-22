/*
You are given an integer n. There is an undirected graph with n vertices, numbered from 0 to n - 1. You are given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting vertices ai and bi.

Return the number of complete connected components of the graph.

A connected component is a subgraph of a graph in which there exists a path between any two vertices, and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.

A connected component is said to be complete if there exists an edge between every pair of its vertices.



Example 1:



Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4]]
Output: 3
Explanation: From the picture above, one can see that all of the components of this graph are complete.
Example 2:



Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
Output: 1
Explanation: The component containing vertices 0, 1, and 2 is complete since there is an edge between every pair of two vertices. On the other hand, the component containing vertices 3, 4, and 5 is not complete since there is no edge between vertices 4 and 5. Thus, the number of complete components in this graph is 1.


Constraints:

1 <= n <= 50
0 <= edges.length <= n * (n - 1) / 2
edges[i].length == 2
0 <= ai, bi <= n - 1
ai != bi
There are no repeated edges.
*/

class UnionFind {
    private int[] id;
    private int[] rank;
    private int[] nodeCount;
    private int[] edgeCount;

    public UnionFind(int n) {
        id = new int[n];
        rank = new int[n];
        nodeCount = new int[n];
        edgeCount = new int[n];
        for (int i = 0; i < n; ++i) {
            id[i] = i;
            nodeCount[i] = 1;
        }
    }

    public void unionByRank(int u, int v) {
        int i = find(u);
        int j = find(v);
        if (i != j) {
            if (rank[i] < rank[j]) {
                id[i] = j;
                edgeCount[j] += edgeCount[i] + 1; // Increment edge count after union
                nodeCount[j] += nodeCount[i];
            } else if (rank[i] > rank[j]) {
                id[j] = i;
                edgeCount[i] += edgeCount[j] + 1; // Increment edge count after union
                nodeCount[i] += nodeCount[j];
            } else {
                id[i] = j;
                edgeCount[j] += edgeCount[i] + 1; // Increment edge count after union
                nodeCount[j] += nodeCount[i];
                ++rank[j];
            }
        } else {
            edgeCount[i]++; // if already in the same set, increment edge count.
        }
    }

    public int find(int u) {
        if (id[u] == u) {
            return u;
        }
        return id[u] = find(id[u]);
    }

    public boolean isComplete(int u) {
        return nodeCount[u] * (nodeCount[u] - 1) / 2 == edgeCount[u];
    }
}

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);

        for (int[] edge : edges) {
            uf.unionByRank(edge[0], edge[1]);
        }

        int count = 0;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int parent = uf.find(i);
                if (!visited[parent] && uf.isComplete(parent)) {
                    count++;
                    visited[parent] = true;
                }
            }
        }
        return count;
    }
}