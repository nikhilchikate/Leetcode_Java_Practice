/*
You are given an undirected tree with n nodes labeled 0 to n - 1. This is represented by a 2D array edges of length n - 1, where edges[i] = [ui, vi] indicates an undirected edge between nodes ui and vi.

You are also given a string s of length n consisting of lowercase English letters, where s[i] represents the character assigned to node i.

You are also given a string array queries, where each queries[i] is either:

"update ui c": Change the character at node ui to c. Formally, update s[ui] = c.
"query ui vi": Determine whether the string formed by the characters on the unique path from ui to vi (inclusive) can be rearranged into a palindrome.
Return a boolean array answer, where answer[j] is true if the jth query of type "query ui vi"​​​​​​​ can be rearranged into a palindrome, and false otherwise.

 

Example 1:

Input: n = 3, edges = [[0,1],[1,2]], s = "aac", queries = ["query 0 2","update 1 b","query 0 2"]

Output: [true,false]

Explanation:

"query 0 2": Path 0 → 1 → 2 gives "aac", which can be rearranged to form "aca", a palindrome. Thus, answer[0] = true.
"update 1 b": Update node 1 to 'b', now s = "abc".
"query 0 2": Path characters are "abc", which cannot be rearranged to form a palindrome. Thus, answer[1] = false.
Thus, answer = [true, false].

Example 2:

Input: n = 4, edges = [[0,1],[0,2],[0,3]], s = "abca", queries = ["query 1 2","update 0 b","query 2 3","update 3 a","query 1 3"]

Output: [false,false,true]

Explanation:

"query 1 2": Path 1 → 0 → 2 gives "bac", which cannot be rearranged to form a palindrome. Thus, answer[0] = false.
"update 0 b": Update node 0 to 'b', now s = "bbca".
"query 2 3": Path 2 → 0 → 3 gives "cba", which cannot be rearranged to form a palindrome. Thus, answer[1] = false.
"update 3 a": Update node 3 to 'a', s = "bbca".
"query 1 3": Path 1 → 0 → 3 gives "bba", which can be rearranged to form "bab", a palindrome. Thus, answer[2] = true.
Thus, answer = [false, false, true].

 

Constraints:

1 <= n == s.length <= 5 * 104
edges.length == n - 1
edges[i] = [ui, vi]
0 <= ui, vi <= n - 1
s consists of lowercase English letters.
The input is generated such that edges represents a valid tree.
1 <= queries.length <= 5 * 104​​​​​​​
queries[i] = "update ui c" or
queries[i] = "query ui vi"
0 <= ui, vi <= n - 1
c is a lowercase English letter.
*/

class Solution {
    private List<Integer>[] graph;
    private int[][] ancestors;
    private int[] depth;
    private int[] tin;
    private int[] tout;
    private int timer;
    private int[] fenwick;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int levels = 1;
        while ((1 << levels) <= n) {
            levels++;
        }
        ancestors = new int[levels][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        timer = 0;
        dfs(0, 0);

        fenwick = new int[n + 2];
        char[] current = s.toCharArray();
        for (int node = 0; node < n; node++) {
            addSubtree(node, 1 << (current[node] - 'a'));
        }

        List<Boolean> answer = new ArrayList<>();
        for (String query : queries) {
            String[] parts = query.split(" ");
            if (parts[0].equals("update")) {
                int node = Integer.parseInt(parts[1]);
                int oldMask = 1 << (current[node] - 'a');
                int newMask = 1 << (parts[2].charAt(0) - 'a');
                addSubtree(node, oldMask ^ newMask);
                current[node] = parts[2].charAt(0);
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int common = lca(u, v);
                int parity = prefixMask(u) ^ prefixMask(v) ^ (1 << (current[common] - 'a'));
                answer.add((parity & (parity - 1)) == 0);
            }
        }
        return answer;
    }

    private void dfs(int root, int parent) {
        int[] nodes = new int[2 * graph.length];
        int[] parents = new int[2 * graph.length];
        int[] states = new int[2 * graph.length];
        int stackSize = 0;
        nodes[stackSize] = root;
        parents[stackSize] = parent;
        states[stackSize++] = 0;

        while (stackSize > 0) {
            stackSize--;
            int node = nodes[stackSize];
            int nodeParent = parents[stackSize];
            if (states[stackSize] == 1) {
                tout[node] = timer;
                continue;
            }

            tin[node] = ++timer;
            ancestors[0][node] = nodeParent;
            for (int level = 1; level < ancestors.length; level++) {
                ancestors[level][node] =
                        ancestors[level - 1][ancestors[level - 1][node]];
            }

            nodes[stackSize] = node;
            parents[stackSize] = nodeParent;
            states[stackSize++] = 1;
            List<Integer> neighbors = graph[node];
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                int next = neighbors.get(i);
                if (next == nodeParent) {
                    continue;
                }
                depth[next] = depth[node] + 1;
                nodes[stackSize] = next;
                parents[stackSize] = node;
                states[stackSize++] = 0;
            }
        }
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        int difference = depth[u] - depth[v];
        for (int level = 0; difference > 0; level++, difference >>= 1) {
            if ((difference & 1) != 0) {
                u = ancestors[level][u];
            }
        }
        if (u == v) {
            return u;
        }
        for (int level = ancestors.length - 1; level >= 0; level--) {
            if (ancestors[level][u] != ancestors[level][v]) {
                u = ancestors[level][u];
                v = ancestors[level][v];
            }
        }
        return ancestors[0][u];
    }

    private void addSubtree(int node, int mask) {
        add(tin[node], mask);
        add(tout[node] + 1, mask);
    }

    private int prefixMask(int node) {
        int result = 0;
        for (int index = tin[node]; index > 0; index -= index & -index) {
            result ^= fenwick[index];
        }
        return result;
    }

    private void add(int index, int mask) {
        for (; index < fenwick.length; index += index & -index) {
            fenwick[index] ^= mask;
        }
    }
}
