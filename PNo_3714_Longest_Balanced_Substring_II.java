/*
You are given a string s consisting only of the characters 'a', 'b', and 'c'.

A substring of s is called balanced if all distinct characters in the substring appear the same number of times.

Return the length of the longest balanced substring of s.



Example 1:

Input: s = "abbac"

Output: 4

Explanation:

The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.

Example 2:

Input: s = "aabcc"

Output: 3

Explanation:

The longest balanced substring is "abc" because all distinct characters 'a', 'b' and 'c' each appear exactly 1 time.

Example 3:

Input: s = "aba"

Output: 2

Explanation:

One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".



Constraints:

1 <= s.length <= 105
s contains only the characters 'a', 'b', and 'c'.
*/

class Solution {

    public int longestBalanced(String s) {
        char[] arr = s.toCharArray();
        int ans = 0;

        // Case 1: only one character
        for (char ch = 'a'; ch <= 'c'; ch++) {
            ans = Math.max(ans, longestOneChar(arr, ch));
        }

        // Case 2: exactly two characters
        ans = Math.max(ans,
                Math.max(
                        longestTwoChars(arr, 'a', 'b'),
                        Math.max(
                                longestTwoChars(arr, 'a', 'c'),
                                longestTwoChars(arr, 'b', 'c'))));

        // Case 3: all three characters
        ans = Math.max(ans, longestThreeChars(arr));

        return ans;
    }

    // -------- Case 1: single character --------
    private int longestOneChar(char[] s, char x) {
        int best = 0, cur = 0;
        for (char c : s) {
            if (c == x) {
                cur++;
            } else {
                best = Math.max(best, cur);
                cur = 0;
            }
        }
        return Math.max(best, cur);
    }

    // -------- Case 2: two characters --------
    private int longestTwoChars(char[] s, char a, char b) {
        int n = s.length;
        int offset = n;
        int[] firstPos = new int[2 * n + 1];
        Arrays.fill(firstPos, -2);

        int diff = 0;
        int lastInvalid = -1;
        firstPos[offset] = -1;

        int best = 0;

        for (int i = 0; i < n; i++) {
            if (s[i] != a && s[i] != b) {
                lastInvalid = i;
                diff = 0;
                firstPos[offset] = lastInvalid;
            } else {
                diff += (s[i] == a) ? 1 : -1;
                int idx = diff + offset;

                if (firstPos[idx] < lastInvalid) {
                    firstPos[idx] = i;
                } else {
                    best = Math.max(best, i - firstPos[idx]);
                }
            }
        }
        return best;
    }

    // -------- Case 3: three characters --------
    private int longestThreeChars(char[] s) {
        // key = (count(a)-count(b), count(b)-count(c))
        Map<Long, Integer> firstPos = new HashMap<>();
        firstPos.put(encode(0, 0), -1);

        int[] cnt = new int[3];
        int best = 0;

        for (int i = 0; i < s.length; i++) {
            cnt[s[i] - 'a']++;
            int x = cnt[0] - cnt[1];
            int y = cnt[1] - cnt[2];
            long key = encode(x, y);

            if (firstPos.containsKey(key)) {
                best = Math.max(best, i - firstPos.get(key));
            } else {
                firstPos.put(key, i);
            }
        }
        return best;
    }

    private long encode(int x, int y) {
        return ((long) (x + 1_000_000) << 21) | (y + 1_000_000);
    }
}