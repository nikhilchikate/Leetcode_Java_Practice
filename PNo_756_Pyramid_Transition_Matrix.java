/*
You are stacking blocks to form a pyramid. Each block has a color, which is represented by a single letter. Each row of blocks contains one less block than the row beneath it and is centered on top.

To make the pyramid aesthetically pleasing, there are only specific triangular patterns that are allowed. A triangular pattern consists of a single block stacked on top of two blocks. The patterns are given as a list of three-letter strings allowed, where the first two characters of a pattern represent the left and right bottom blocks respectively, and the third character is the top block.

For example, "ABC" represents a triangular pattern with a 'C' block stacked on top of an 'A' (left) and 'B' (right) block. Note that this is different from "BAC" where 'B' is on the left bottom and 'A' is on the right bottom.
You start with a bottom row of blocks bottom, given as a single string, that you must use as the base of the pyramid.

Given bottom and allowed, return true if you can build the pyramid all the way to the top such that every triangular pattern in the pyramid is in allowed, or false otherwise.



Example 1:


Input: bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]
Output: true
Explanation: The allowed triangular patterns are shown on the right.
Starting from the bottom (level 3), we can build "CE" on level 2 and then build "A" on level 1.
There are three triangular patterns in the pyramid, which are "BCC", "CDE", and "CEA". All are allowed.
Example 2:


Input: bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
Output: false
Explanation: The allowed triangular patterns are shown on the right.
Starting from the bottom (level 4), there are multiple ways to build level 3, but trying all the possibilites, you will get always stuck before building level 1.


Constraints:

2 <= bottom.length <= 6
0 <= allowed.length <= 216
allowed[i].length == 3
The letters in all input strings are from the set {'A', 'B', 'C', 'D', 'E', 'F'}.
All the values of allowed are unique.
*/

class Solution {
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        return new Solver(allowed, bottom.length()).canDo(bottom);
    }

    static final class Solver {
        final int[][] cache;
        final int[][][] allowed = new int[6][6][];

        public Solver(List<String> allowed, int len) {
            final int[][] cnt = new int[6][6];
            for (String a : allowed) {
                cnt[a.charAt(0) - 'A'][a.charAt(1) - 'A']++;
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    this.allowed[i][j] = new int[cnt[i][j]];
                }
            }
            this.cache = new int[len][];
            int size = 6 * 6;
            for (int i = 3; i < len; i++) {
                cache[i] = new int[size *= 6];
            }
            for (String a : allowed) {
                final int first = a.charAt(0) - 'A';
                final int second = a.charAt(1) - 'A';
                final int third = a.charAt(2) - 'A';
                this.allowed[first][second][--cnt[first][second]] = third;
            }
        }

        boolean canDo(String s) {
            final int[] arr = new int[s.length()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = s.charAt(i) - 'A';
            }
            return s.length() == 2 ? allowed[arr[0]][arr[1]].length > 0 : compute(arr, arr.length);
        }

        boolean canDo(int[] arr, int len) {
            if (len == 2) {
                return allowed[arr[0]][arr[1]].length > 0;
            } else {
                final var key = encode(arr, len);
                final var cached = cache[len][key];
                if (cached != 0) {
                    return cached == 1;
                }
                final var result = compute(arr, len);
                cache[len][key] = result ? 1 : 2;
                return result;
            }
        }

        private int encode(int[] arr, int len) {
            int r = arr[0];
            for (int i = 1; i < len; i++) {
                r = r * 6 + arr[i];
            }
            return r;
        }

        boolean compute(int[] arr, int len) {
            for (int i = 2; i < len; i++) {
                if (allowed[arr[i - 1]][arr[i]].length == 0) {
                    return false;
                }
            }
            return compute(new int[len], 0, arr, 0, len);
        }

        boolean compute(int[] prefix, int plen, int[] suffix, int sidx, int slen) {
            if (plen > 1 && !canDo(prefix, plen)) {
                return false;
            }
            final int nextSidx = sidx + 1;
            if (nextSidx < slen) {
                for (int next : allowed[suffix[sidx]][suffix[nextSidx]]) {
                    prefix[plen] = next;
                    if (compute(prefix, plen + 1, suffix, sidx + 1, slen)) {
                        return true;
                    }
                }
            } else {
                return canDo(prefix, plen);
            }
            return false;
        }
    }
}