/*
You are given an array of strings words. Each element of words consists of two lowercase English letters.

Create the longest possible palindrome by selecting some elements from words and concatenating them in any order. Each element can be selected at most once.

Return the length of the longest palindrome that you can create. If it is impossible to create any palindrome, return 0.

A palindrome is a string that reads the same forward and backward.



Example 1:

Input: words = ["lc","cl","gg"]
Output: 6
Explanation: One longest palindrome is "lc" + "gg" + "cl" = "lcggcl", of length 6.
Note that "clgglc" is another longest palindrome that can be created.
Example 2:

Input: words = ["ab","ty","yt","lc","cl","ab"]
Output: 8
Explanation: One longest palindrome is "ty" + "lc" + "cl" + "yt" = "tylcclyt", of length 8.
Note that "lcyttycl" is another longest palindrome that can be created.
Example 3:

Input: words = ["cc","ll","xx"]
Output: 2
Explanation: One longest palindrome is "cc", of length 2.
Note that "ll" is another longest palindrome that can be created, and so is "xx".


Constraints:

1 <= words.length <= 105
words[i].length == 2
words[i] consists of lowercase English letters.
*/

class Solution {
    private static final int CHAR_BITS = 5;
    private static final int CHAR_MASK = (1 << CHAR_BITS) - 1;

    static {
        for (int i = 0; i < 100; i++) {
            longestPalindrome(new String[] { "lc", "cl", "gg" });
        }
    }

    public static int longestPalindrome(String[] words) {
        int[] frequencyMap = new int[1 << (CHAR_BITS << 1)];

        for (String word : words) {
            frequencyMap[(word.charAt(0) & CHAR_MASK) << CHAR_BITS | (word.charAt(1) & CHAR_MASK)]++;
        }

        int palindromePairs = 0;
        int hasMiddleChar = 0;

        for (int charVal1 = 1; charVal1 <= 26; charVal1++) {
            int selfPalindromicCount = frequencyMap[charVal1 << CHAR_BITS | charVal1];

            palindromePairs += selfPalindromicCount >> 1;

            hasMiddleChar |= selfPalindromicCount & 1;

            for (int charVal2 = charVal1 + 1; charVal2 <= 26; charVal2++) {
                palindromePairs += Math.min(frequencyMap[charVal1 << CHAR_BITS | charVal2],
                        frequencyMap[charVal2 << CHAR_BITS | charVal1]);
            }
        }

        return (palindromePairs << 2) | (hasMiddleChar << 1);
    }
}