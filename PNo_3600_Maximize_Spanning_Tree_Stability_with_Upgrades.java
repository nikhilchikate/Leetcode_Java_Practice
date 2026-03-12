/*
You are given an integer n, representing n nodes numbered from 0 to n - 1 and a list of edges, where edges[i] = [ui, vi, si, musti]:

ui and vi indicates an undirected edge between nodes ui and vi.
si is the strength of the edge.
musti is an integer (0 or 1). If musti == 1, the edge must be included in the spanning tree. These edges cannot be upgraded.
You are also given an integer k, the maximum number of upgrades you can perform. Each upgrade doubles the strength of an edge, and each eligible edge (with musti == 0) can be upgraded at most once.

The stability of a spanning tree is defined as the minimum strength score among all edges included in it.

Return the maximum possible stability of any valid spanning tree. If it is impossible to connect all nodes, return -1.

Note: A spanning tree of a graph with n nodes is a subset of the edges that connects all nodes together (i.e. the graph is connected) without forming any cycles, and uses exactly n - 1 edges.



Example 1:

Input: n = 3, edges = [[0,1,2,1],[1,2,3,0]], k = 1

Output: 2

Explanation:

Edge [0,1] with strength = 2 must be included in the spanning tree.
Edge [1,2] is optional and can be upgraded from 3 to 6 using one upgrade.
The resulting spanning tree includes these two edges with strengths 2 and 6.
The minimum strength in the spanning tree is 2, which is the maximum possible stability.
Example 2:

Input: n = 3, edges = [[0,1,4,0],[1,2,3,0],[0,2,1,0]], k = 2

Output: 6

Explanation:

Since all edges are optional and up to k = 2 upgrades are allowed.
Upgrade edges [0,1] from 4 to 8 and [1,2] from 3 to 6.
The resulting spanning tree includes these two edges with strengths 8 and 6.
The minimum strength in the tree is 6, which is the maximum possible stability.
Example 3:

Input: n = 3, edges = [[0,1,1,1],[1,2,1,1],[2,0,1,1]], k = 0

Output: -1

Explanation:

All edges are mandatory and form a cycle, which violates the spanning tree property of acyclicity. Thus, the answer is -1.


Constraints:

2 <= n <= 105
1 <= edges.length <= 105
edges[i] = [ui, vi, si, musti]
0 <= ui, vi < n
ui != vi
1 <= si <= 105
musti is either 0 or 1.
0 <= k <= n
There are no duplicate edges.
*/

class Dsu {
    private int[] parent;
    private int[] size;
    private int n;

    public Dsu(int n) {
        this.n = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;
    }

    public int find(int node) {
        if (parent[node] == node)
            return node;
        parent[node] = find(parent[node]);
        return parent[node];
    }

    public void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if (pa != pb) {
            if (size[pa] > size[pb]) {
                int temp = pa;
                pa = pb;
                pb = temp;
            }
            parent[pb] = pa;
            size[pa] += size[pb];
        }
    }
}

class Edge implements Comparable<Edge> {
    private int src;
    private int dst;
    private int weight;

    public Edge(int s, int d, int w) {
        src = s;
        dst = d;
        weight = w;
    }

    public int getSrc() {
        return src;
    }

    public int getDst() {
        return dst;
    }

    public int getWeight() {
        return weight;
    }

    public int compareTo(Edge e) {
        return e.weight - this.weight;
    }

    public String toString() {
        return this.src + " " + this.dst + " " + this.weight;
    }
}

class Solution {
    public int maxStability(int n, int[][] edges, int k) {
        int minStrength = (int) 1e6;
        Dsu dsu = new Dsu(n);
        boolean hasTypeOne = false;

        List<Edge> edgeList = new ArrayList<>();

        for (int i = 0; i < edges.length; i++) {
            if (edges[i][3] == 1) {
                int pa = dsu.find(edges[i][0]);
                int pb = dsu.find(edges[i][1]);

                if (pa == pb)
                    return -1;

                dsu.union(edges[i][0], edges[i][1]);
                minStrength = Math.min(minStrength, edges[i][2]);
                hasTypeOne = true;
            } else {
                edgeList.add(new Edge(edges[i][0], edges[i][1], edges[i][2]));
            }
        }

        Collections.sort(edgeList);

        List<Integer> zeroEdges = new ArrayList<>();

        for (int i = 0; i < edgeList.size(); i++) {
            Edge e = edgeList.get(i);
            int w = e.getWeight();

            int pa = dsu.find(e.getSrc());
            int pb = dsu.find(e.getDst());

            if (pa != pb) {
                zeroEdges.add(w);
                dsu.union(pa, pb);
            }
        }

        for (int i = 0; i < n; i++) {
            if (dsu.find(i) != dsu.find(0))
                return -1;
        }

        int val = (int) 1e6;

        if (zeroEdges.size() != 0) {
            val = 0;

            if (k == 0) {
                val = zeroEdges.get(zeroEdges.size() - 1);
            } else if (k >= zeroEdges.size()) {
                val = zeroEdges.get(zeroEdges.size() - 1) * 2;
            } else {
                val = Math.min(zeroEdges.get(zeroEdges.size() - 1) * 2,
                        zeroEdges.get(zeroEdges.size() - k - 1));
            }
        }

        return Math.min(minStrength, val);
    }
}