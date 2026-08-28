/*
You are given two strings s and target, each of length n, consisting of lowercase English letters.

Return the lexicographically smallest string that is both a palindromic permutation of s and strictly greater than target. If no such permutation exists, return an empty string.

 

Example 1:

Input: s = "baba", target = "abba"

Output: "baab"

Explanation:

The palindromic permutations of s (in lexicographical order) are "abba" and "baab".
The lexicographically smallest permutation that is strictly greater than target is "baab".
Example 2:

Input: s = "baba", target = "bbaa"

Output: ""

Explanation:

The palindromic permutations of s (in lexicographical order) are "abba" and "baab".
None of them is lexicographically strictly greater than target. Therefore, the answer is "".
Example 3:

Input: s = "abc", target = "abb"

Output: ""

Explanation:

s has no palindromic permutations. Therefore, the answer is "".

Example 4:

Input: s = "aac", target = "abb"

Output: "aca"

Explanation:

The only palindromic permutation of s is "aca".
"aca" is strictly greater than target. Therefore, the answer is "aca".
 

Constraints:

1 <= n == s.length == target.length <= 300
s and target consist of only lowercase English letters.
*/

class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int n = s.length();

        int oddCount = 0;
        char oddChar = '\0';
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                oddCount++;
                oddChar = (char) ('a' + i);
            }
        }

        if ((n % 2 == 0 && oddCount > 0) || (n % 2 == 1 && oddCount != 1)) {
            return "";
        }

        int halfLen = n / 2;
        boolean hasOdd = oddCount > 0;
        int[] leftFreq = new int[26];
        for (int i = 0; i < 26; i++) {
            leftFreq[i] = freq[i] / 2;
        }

        char[] leftHalf = new char[halfLen];
        String result = dfs(leftHalf, leftFreq, target, halfLen, hasOdd, oddChar, 0, -1);
        return result != null ? result : "";
    }

    private String dfs(char[] leftHalf, int[] leftFreq, String target, int halfLen,
            boolean hasOdd, char oddChar, int pos, int cmp) {

        if (pos == halfLen) {
            int n = halfLen * 2 + (hasOdd ? 1 : 0);
            char[] result = new char[n];

            for (int i = 0; i < halfLen; i++) {
                result[i] = leftHalf[i];
            }
            if (hasOdd) {
                result[halfLen] = oddChar;
            }
            for (int i = 0; i < halfLen; i++) {
                result[n - 1 - i] = leftHalf[i];
            }

            String palindrome = new String(result);

            if (cmp == 0 || palindrome.compareTo(target) > 0) {
                return palindrome;
            }
            return null;
        }

        for (char c = 'a'; c <= 'z'; c++) {
            int idx = c - 'a';
            if (leftFreq[idx] > 0) {
                leftHalf[pos] = c;
                leftFreq[idx]--;

                int newCmp = cmp;

                if (cmp == -1) {
                    char targetChar = target.charAt(pos);
                    if (c > targetChar) {
                        newCmp = 0;
                    } else if (c < targetChar) {
                        leftFreq[idx]++;
                        continue;
                    }
                }

                String candidate = dfs(leftHalf, leftFreq, target, halfLen, hasOdd, oddChar, pos + 1, newCmp);
                if (candidate != null) {
                    return candidate;
                }

                leftFreq[idx]++;
            }
        }

        return null;
    }
}