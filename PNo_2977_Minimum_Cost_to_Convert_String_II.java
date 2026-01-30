/*
You are given two 0-indexed strings source and target, both of length n and consisting of lowercase English characters. You are also given two 0-indexed string arrays original and changed, and an integer array cost, where cost[i] represents the cost of converting the string original[i] to the string changed[i].

You start with the string source. In one operation, you can pick a substring x from the string, and change it to y at a cost of z if there exists any index j such that cost[j] == z, original[j] == x, and changed[j] == y. You are allowed to do any number of operations, but any pair of operations must satisfy either of these two conditions:

The substrings picked in the operations are source[a..b] and source[c..d] with either b < c or d < a. In other words, the indices picked in both operations are disjoint.
The substrings picked in the operations are source[a..b] and source[c..d] with a == c and b == d. In other words, the indices picked in both operations are identical.
Return the minimum cost to convert the string source to the string target using any number of operations. If it is impossible to convert source to target, return -1.

Note that there may exist indices i, j such that original[j] == original[i] and changed[j] == changed[i].



Example 1:

Input: source = "abcd", target = "acbe", original = ["a","b","c","c","e","d"], changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
Output: 28
Explanation: To convert "abcd" to "acbe", do the following operations:
- Change substring source[1..1] from "b" to "c" at a cost of 5.
- Change substring source[2..2] from "c" to "e" at a cost of 1.
- Change substring source[2..2] from "e" to "b" at a cost of 2.
- Change substring source[3..3] from "d" to "e" at a cost of 20.
The total cost incurred is 5 + 1 + 2 + 20 = 28.
It can be shown that this is the minimum possible cost.
Example 2:

Input: source = "abcdefgh", target = "acdeeghh", original = ["bcd","fgh","thh"], changed = ["cde","thh","ghh"], cost = [1,3,5]
Output: 9
Explanation: To convert "abcdefgh" to "acdeeghh", do the following operations:
- Change substring source[1..3] from "bcd" to "cde" at a cost of 1.
- Change substring source[5..7] from "fgh" to "thh" at a cost of 3. We can do this operation because indices [5,7] are disjoint with indices picked in the first operation.
- Change substring source[5..7] from "thh" to "ghh" at a cost of 5. We can do this operation because indices [5,7] are disjoint with indices picked in the first operation, and identical with indices picked in the second operation.
The total cost incurred is 1 + 3 + 5 = 9.
It can be shown that this is the minimum possible cost.
Example 3:

Input: source = "abcdefgh", target = "addddddd", original = ["bcd","defgh"], changed = ["ddd","ddddd"], cost = [100,1578]
Output: -1
Explanation: It is impossible to convert "abcdefgh" to "addddddd".
If you select substring source[1..3] as the first operation to change "abcdefgh" to "adddefgh", you cannot select substring source[3..7] as the second operation because it has a common index, 3, with the first operation.
If you select substring source[3..7] as the first operation to change "abcdefgh" to "abcddddd", you cannot select substring source[1..3] as the second operation because it has a common index, 3, with the first operation.


Constraints:

1 <= source.length == target.length <= 1000
source, target consist only of lowercase English characters.
1 <= cost.length == original.length == changed.length <= 100
1 <= original[i].length == changed[i].length <= source.length
original[i], changed[i] consist only of lowercase English characters.
original[i] != changed[i]
1 <= cost[i] <= 106
*/

class Solution {
    private int counter = 0;

    public long minimumCost(String start, String end, String[] from, String[] to, int[] prices) {
        PrefixNode base = new PrefixNode();
        for (String word : from)
            add(word, base);
        for (String word : to)
            add(word, base);

        int[][] matrix = new int[counter][counter];
        for (int a = 0; a < counter; a++) {
            Arrays.fill(matrix[a], Integer.MAX_VALUE);
            matrix[a][a] = 0;
        }

        for (int p = 0; p < prices.length; p++) {
            int u = fetch(from[p], base), v = fetch(to[p], base);
            if (prices[p] < matrix[u][v])
                matrix[u][v] = prices[p];
        }

        for (int mid = 0; mid < counter; mid++) {
            for (int left = 0; left < counter; left++) {
                if (matrix[left][mid] != Integer.MAX_VALUE) {
                    for (int right = 0; right < counter; right++) {
                        if (matrix[mid][right] != Integer.MAX_VALUE &&
                                matrix[left][mid] + matrix[mid][right] < matrix[left][right]) {
                            matrix[left][right] = matrix[left][mid] + matrix[mid][right];
                        }
                    }
                }
            }
        }

        char[] a = start.toCharArray(), b = end.toCharArray();
        int len = a.length;
        long[] best = new long[len + 1];
        Arrays.fill(best, Long.MAX_VALUE);
        best[0] = 0;

        for (int pos = 0; pos < len; pos++) {
            if (best[pos] == Long.MAX_VALUE)
                continue;
            PrefixNode p1 = base, p2 = base;
            if (a[pos] == b[pos] && best[pos] < best[pos + 1])
                best[pos + 1] = best[pos];
            for (int endPos = pos; endPos < len; endPos++) {
                p1 = p1.child[a[endPos] - 'a'];
                p2 = p2.child[b[endPos] - 'a'];
                if (p1 == null || p2 == null)
                    break;
                if (p1.id != -1 && p2.id != -1 &&
                        matrix[p1.id][p2.id] != Integer.MAX_VALUE &&
                        matrix[p1.id][p2.id] + best[pos] < best[endPos + 1]) {
                    best[endPos + 1] = matrix[p1.id][p2.id] + best[pos];
                }
            }
        }

        return best[len] == Long.MAX_VALUE ? -1 : best[len];
    }

    private void add(String word, PrefixNode base) {
        for (int i = 0; i < word.length(); i++) {
            int pos = word.charAt(i) - 'a';
            if (base.child[pos] == null)
                base.child[pos] = new PrefixNode();
            base = base.child[pos];
        }
        if (base.id == -1)
            base.id = counter++;
    }

    private int fetch(String word, PrefixNode base) {
        for (int i = 0; i < word.length(); i++) {
            int pos = word.charAt(i) - 'a';
            base = base.child[pos];
        }
        return base.id;
    }
}

class PrefixNode {
    PrefixNode[] child = new PrefixNode[26];
    int id = -1;
}