/*
You are given a string s of length n, and an integer k. You are tasked to find the longest subsequence repeated k times in string s.

A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.

A subsequence seq is repeated k times in the string s if seq * k is a subsequence of s, where seq * k represents a string constructed by concatenating seq k times.

For example, "bba" is repeated 2 times in the string "bababcba", because the string "bbabba", constructed by concatenating "bba" 2 times, is a subsequence of the string "bababcba".
Return the longest subsequence repeated k times in string s. If multiple such subsequences are found, return the lexicographically largest one. If there is no such subsequence, return an empty string.



Example 1:

example 1
Input: s = "letsleetcode", k = 2
Output: "let"
Explanation: There are two longest subsequences repeated 2 times: "let" and "ete".
"let" is the lexicographically largest one.
Example 2:

Input: s = "bb", k = 2
Output: "b"
Explanation: The longest subsequence repeated 2 times is "b".
Example 3:

Input: s = "ab", k = 2
Output: ""
Explanation: There is no subsequence repeated 2 times. Empty string is returned.


Constraints:

n == s.length
2 <= n, k <= 2000
2 <= n < k * 8
s consists of lowercase English letters.
*/

class Solution {
    public String longestSubsequenceRepeatedK(String mainString, int repeatFactor) {
        char[] mainChars = mainString.toCharArray();
        int mainLen = mainChars.length;

        char[] charCounts = new char[26];
        for (int i = 0; i < mainLen; i++) {
            ++charCounts[mainChars[i] - 'a'];
        }

        ArrayList<String>[] candidatesByLength = new ArrayList[8];
        candidatesByLength[1] = new ArrayList<>();
        String currentLongest = "";

        for (int i = 0; i < 26; i++) {
            if (charCounts[i] >= repeatFactor) {
                candidatesByLength[1].add(currentLongest = "" + (char) ('a' + i));
            }
        }

        for (int len = 2; len < 8; len++) {
            candidatesByLength[len] = new ArrayList<>();
            for (String prevSub : candidatesByLength[len - 1]) {
                for (String singleCharSub : candidatesByLength[1]) {
                    String nextSub = prevSub + singleCharSub;
                    if (checkIfRepeatedSubsequence(mainChars, nextSub, repeatFactor)) {
                        candidatesByLength[len].add(currentLongest = nextSub);
                    }
                }
            }
        }
        return currentLongest;
    }

    boolean checkIfRepeatedSubsequence(char[] sourceChars, String targetSub, int repeatTimes) {
        char[] targetChars = targetSub.toCharArray();
        int sourceLen = sourceChars.length;
        int targetLen = targetChars.length;
        int sourceIdx = 0;

        while (repeatTimes-- > 0) {
            int targetIdx = 0;
            while (sourceIdx < sourceLen && targetIdx < targetLen) {
                if (sourceChars[sourceIdx] == targetChars[targetIdx]) {
                    targetIdx++;
                }
                sourceIdx++;
            }
            if (targetIdx != targetLen) {
                return false;
            }
        }
        return true;
    }
}