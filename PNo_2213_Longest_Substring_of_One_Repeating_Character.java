/*
You are given a 0-indexed string s. You are also given a 0-indexed string queryCharacters of length k and a 0-indexed array of integer indices queryIndices of length k, both of which are used to describe k queries.

The ith query updates the character in s at index queryIndices[i] to the character queryCharacters[i].

Return an array lengths of length k where lengths[i] is the length of the longest substring of s consisting of only one repeating character after the ith query is performed.

 

Example 1:

Input: s = "babacc", queryCharacters = "bcb", queryIndices = [1,3,3]
Output: [3,3,4]
Explanation: 
- 1st query updates s = "bbbacc". The longest substring consisting of one repeating character is "bbb" with length 3.
- 2nd query updates s = "bbbccc". 
  The longest substring consisting of one repeating character can be "bbb" or "ccc" with length 3.
- 3rd query updates s = "bbbbcc". The longest substring consisting of one repeating character is "bbbb" with length 4.
Thus, we return [3,3,4].
Example 2:

Input: s = "abyzz", queryCharacters = "aa", queryIndices = [2,1]
Output: [2,3]
Explanation:
- 1st query updates s = "abazz". The longest substring consisting of one repeating character is "zz" with length 2.
- 2nd query updates s = "aaazz". The longest substring consisting of one repeating character is "aaa" with length 3.
Thus, we return [2,3].
 

Constraints:

1 <= s.length <= 105
s consists of lowercase English letters.
k == queryCharacters.length == queryIndices.length
1 <= k <= 105
queryCharacters consists of lowercase English letters.
0 <= queryIndices[i] < s.length
*/

class Solution {
    private static class SegmentTree {
        private final int n;
        private final int[] pre;
        private final int[] suf;
        private final int[] best;
        private final char[] cs;

        public SegmentTree(String s) {
            n = s.length();
            pre = new int[n << 2];
            suf = new int[n << 2];
            best = new int[n << 2];
            cs = s.toCharArray();

            build(1, 0, n - 1);
        }

        private void build(int node, int l, int r) {
            if (l == r) {
                pre[node] = suf[node] = best[node] = 1;
                return;
            }
            int mid = (l + r) >>> 1;
            build(node << 1, l, mid);
            build(node << 1 | 1, mid + 1, r);
            pushUp(node, l, r);
        }

        private void pushUp(int node, int l, int r) {
            int left = node << 1;
            int right = node << 1 | 1;
            int mid = (l + r) >>> 1;
            int lenL = mid - l + 1;
            int lenR = r - mid;

            pre[node] = pre[left];
            suf[node] = suf[right];
            best[node] = Math.max(best[left], best[right]);
            if (cs[mid] == cs[mid + 1]) {
                if (pre[left] == lenL) {
                    pre[node] = lenL + pre[right];
                }
                if (suf[right] == lenR) {
                    suf[node] = lenR + suf[left];
                }
                best[node] = Math.max(best[node], suf[left] + pre[right]);
            }
        }

        public void update(int i) {
            update(1, 0, n - 1, i);
        }

        private void update(int node, int l, int r, int i) {
            if (l == r) {
                return;
            }
            int mid = (l + r) >>> 1;
            if (i <= mid) {
                update(node << 1, l, mid, i);
            } else {
                update(node << 1 | 1, mid + 1, r, i);
            }
            pushUp(node, l, r);
        }

        public void updateChar(char c, int i) {
            cs[i] = c;
        }
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int k = queryIndices.length;
        SegmentTree tree = new SegmentTree(s);
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            int index = queryIndices[i];
            tree.updateChar(queryCharacters.charAt(i), index);
            tree.update(index);
            ans[i] = tree.best[1];
        }
        return ans;
    }
}