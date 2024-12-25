/*
There exist two undirected trees with n and m nodes, numbered from 0 to n - 1 and from 0 to m - 1, respectively. You are given two 2D integer arrays edges1 and edges2 of lengths n - 1 and m - 1, respectively, where edges1[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the first tree and edges2[i] = [ui, vi] indicates that there is an edge between nodes ui and vi in the second tree.

You must connect one node from the first tree with another node from the second tree with an edge.

Return the minimum possible diameter of the resulting tree.

The diameter of a tree is the length of the longest path between any two nodes in the tree.



Example 1:

Input: edges1 = [[0,1],[0,2],[0,3]], edges2 = [[0,1]]

Output: 3

Explanation:

We can obtain a tree of diameter 3 by connecting node 0 from the first tree with any node from the second tree.

Example 2:


Input: edges1 = [[0,1],[0,2],[0,3],[2,4],[2,5],[3,6],[2,7]], edges2 = [[0,1],[0,2],[0,3],[2,4],[2,5],[3,6],[2,7]]

Output: 5

Explanation:

We can obtain a tree of diameter 5 by connecting node 0 from the first tree with node 0 from the second tree.



Constraints:

1 <= n, m <= 105
edges1.length == n - 1
edges2.length == m - 1
edges1[i].length == edges2[i].length == 2
edges1[i] = [ai, bi]
0 <= ai, bi < n
edges2[i] = [ui, vi]
0 <= ui, vi < m
The input is generated such that edges1 and edges2 represent valid trees.
*/

class Solution {
    private int dia;  // Variable to store the maximum diameter found

    public int minimumDiameterAfterMerge(int[][] e1, int[][] e2) {
        // Number of nodes in each tree (edges + 1)
        int n = e1.length + 1;
        int m = e2.length + 1;

        // Initialize adjacency lists for both trees
        List<Integer>[] t1 = new List[n];
        List<Integer>[] t2 = new List[m];

        // Fill adjacency lists with empty ArrayLists
        Arrays.setAll(t1, i -> new ArrayList<>());
        Arrays.setAll(t2, i -> new ArrayList<>());

        // Construct the first tree using edges from e1
        for (int[] e : e1) {
            int u = e[0], v = e[1];
            t1[u].add(v);  // Add v to u's list
            t1[v].add(u);  // Add u to v's list
        }

        // Construct the second tree using edges from e2
        for (int[] e : e2) {
            int u = e[0], v = e[1];
            t2[u].add(v);  // Add v to u's list
            t2[v].add(u);  // Add u to v's list
        }

        // Calculate the diameter of the first tree
        dia = 0;
        calcDia(t1, 0, -1);  // Start DFS from node 0 with no parent
        int d1 = dia;  // Store the diameter of the first tree

        // Calculate the diameter of the second tree
        dia = 0;
        calcDia(t2, 0, -1);  // Start DFS from node 0 with no parent
        int d2 = dia;  // Store the diameter of the second tree

        // Return the maximum diameter after merging the trees
        return Math.max(Math.max(d1, d2), (d1 + 1) / 2 + 1 + (d2 + 1) / 2);
    }

    // Helper method to calculate the diameter of a tree using DFS
    private int calcDia(List<Integer>[] t, int n, int p) {
        int m1 = 0;  // Variable to store the longest distance from current node
        int m2 = 0;  // Variable to store the second longest distance

        // Iterate through all neighbors of the current node
        for (int nr : t[n]) {
            if (nr == p)  // Skip the parent node
                continue;

            // Recursively calculate the diameter of the subtree
            int d = calcDia(t, nr, n);

            // Update the first and second longest distances
            if (m1 <= d) {
                m2 = m1;
                m1 = d;
            } else if (m2 < d) {
                m2 = d;
            }
        }

        // Update the overall diameter of the tree
        dia = Math.max(dia, m1 + m2);

        // Return the longest distance from the current node
        return m1 + 1;
    }
}