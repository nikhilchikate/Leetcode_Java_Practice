/*
You are given an integer n representing the number of nodes in a directed weighted graph, numbered from 0 to n - 1. This is represented by a 2D integer array edges, where edges[i] = [ui, vi, wi] represents a directed edge from node ui to node vi with weight wi.

You are also given a string labels of length n, where labels[i] is the character assigned to node i, and an integer k.

Return the minimum total edge weight of a path from node 0 to node n - 1 such that the concatenation of the labels of the nodes along the path contains at most k consecutive identical characters. If no valid path exists, return -1.

 

Example 1:

Input: n = 3, edges = [[0,1,1],[1,2,1],[0,2,3]], labels = "aab", k = 1

Output: 3

Explanation:

The optimal valid path from node 0 to node 2 is as follows:

Use edges[2] = [0, 2, 3] to reach node 2 with a weight wi = 3.
The corresponding concatenation of labels is "ab", which satisfies at most k = 1 consecutive identical characters. Thus, the answer is 3.
Example 2:

Input: n = 3, edges = [[0,1,1],[1,2,1],[0,2,3]], labels = "aab", k = 2

Output: 2

Explanation:

The optimal valid path from node 0 to node 2 is as follows:

Use edges[0] = [0, 1, 1] to reach node 1 with weight wi = 1.
Use edges[1] = [1, 2, 1] to reach node 2 with weight wi = 1.
The corresponding concatenation of labels is "aab", which satisfies at most k = 2 consecutive identical characters. Thus, the answer is 2.
Example 3:

Input: n = 3, edges = [[0,1,1],[1,2,1]], labels = "aaa", k = 2

Output: -1

Explanation:

There is no valid path from node 0 to node 2 that satisfies at most k = 2 consecutive identical characters. Thus, the answer is -1.

 

Constraints:

1 <= n == labels.length <= 5 * 104
0 <= edges.length <= 5 * 104
edges[i] == [ui, vi, wi]
0 <= ui, vi <= n - 1
ui != vi
1 <= wi <= 104
labels consists of lowercase English letters
1 <= k <= 50
*/

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        if (n == 1)
            return 0;
        int m = edges.length;
        int[] head = new int[n], nxt = new int[m], to = new int[m], w = new int[m];
        Arrays.fill(head, -1);
        for (int i = 0; i < m; i++) {
            to[i] = edges[i][1];
            w[i] = edges[i][2];
            nxt[i] = head[edges[i][0]];
            head[edges[i][0]] = i;
        }
        char[] lab = labels.toCharArray();

        int total = n * k;
        int[] dist = new int[total];
        Arrays.fill(dist, Integer.MAX_VALUE);
        long[] heap = new long[1024];
        int size = 0;

        dist[0] = 0;
        heap[size++] = 0L << 22 | 0;

        while (size > 0) {
            long top = heap[0];
            heap[0] = heap[--size];
            siftDown(heap, size, 0);
            int d = (int) (top >>> 22);
            int state = (int) (top & ((1 << 22) - 1));
            if (d > dist[state])
                continue;
            int u = state / k, c = state % k;
            if (u == n - 1)
                return d;
            for (int e = head[u]; e != -1; e = nxt[e]) {
                int v = to[e];
                int nc = lab[v] == lab[u] ? c + 1 : 0;
                if (nc >= k)
                    continue;
                int ns = v * k + nc;
                int nd = d + w[e];
                if (nd < dist[ns]) {
                    dist[ns] = nd;
                    if (size == heap.length)
                        heap = Arrays.copyOf(heap, size * 2);
                    heap[size] = ((long) nd << 22) | ns;
                    siftUp(heap, size++);
                }
            }
        }
        return -1;
    }

    static void siftUp(long[] h, int i) {
        while (i > 0) {
            int p = (i - 1) >>> 1;
            if (h[p] <= h[i])
                break;
            long t = h[p];
            h[p] = h[i];
            h[i] = t;
            i = p;
        }
    }

    static void siftDown(long[] h, int size, int i) {
        while (true) {
            int l = 2 * i + 1, r = l + 1, s = i;
            if (l < size && h[l] < h[s])
                s = l;
            if (r < size && h[r] < h[s])
                s = r;
            if (s == i)
                break;
            long t = h[s];
            h[s] = h[i];
            h[i] = t;
            i = s;
        }
    }
}