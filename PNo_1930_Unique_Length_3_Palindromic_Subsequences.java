/*
Given a string s, return the number of unique palindromes of length three that are a subsequence of s.

Note that even if there are multiple ways to obtain the same subsequence, it is still only counted once.

A palindrome is a string that reads the same forwards and backwards.

A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

For example, "ace" is a subsequence of "abcde".


Example 1:

Input: s = "aabca"
Output: 3
Explanation: The 3 palindromic subsequences of length 3 are:
- "aba" (subsequence of "aabca")
- "aaa" (subsequence of "aabca")
- "aca" (subsequence of "aabca")
Example 2:

Input: s = "adc"
Output: 0
Explanation: There are no palindromic subsequences of length 3 in "adc".
Example 3:

Input: s = "bbcbaba"
Output: 4
Explanation: The 4 palindromic subsequences of length 3 are:
- "bbb" (subsequence of "bbcbaba")
- "bcb" (subsequence of "bbcbaba")
- "bab" (subsequence of "bbcbaba")
- "aba" (subsequence of "bbcbaba")


Constraints:

3 <= s.length <= 105
s consists of only lowercase English letters.
*/

class Solution {
    public int countPalindromicSubsequence(String s) {
        final int len = s.length();
        final byte[] arr = new byte[len];
        s.getBytes(0, len, arr, 0);

        final int[][] cnts = new int[26][26];
        final int[] first = new int[26];
        final int[] freq = new int[26];
        int total = 0;

        for (int i = 0; i < len; ++i) {
            final int ch = arr[i] - 'a';
            freq[ch]++;
            if (first[ch] == 0) {
                first[ch] = i + 1;
                final int[] chCnts = cnts[ch];
                for (int j = 0; j < 26; ++j)
                    chCnts[j] = freq[j];
                total++;
            }
        }

        int result = 0;
        final boolean[] lastSeen = new boolean[26];
        for (int i = len - 1; i >= 2; --i) {
            final int ch = arr[i] - 'a';
            freq[ch]--;
            if (!lastSeen[ch]) {
                lastSeen[ch] = true;
                if (i > first[ch]) {
                    final int[] chCnts = cnts[ch];
                    for (int j = 0; j < 26; ++j)
                        if (freq[j] > chCnts[j])
                            result++;
                }
                if (--total == 0)
                    break;
            }
        }
        return result;
    }
}