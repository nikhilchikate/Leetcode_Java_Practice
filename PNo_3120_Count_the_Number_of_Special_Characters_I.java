/*
You are given a string word. A letter is called special if it appears both in lowercase and uppercase in word.

Return the number of special letters in word.



Example 1:

Input: word = "aaAbcBC"

Output: 3

Explanation:

The special characters in word are 'a', 'b', and 'c'.

Example 2:

Input: word = "abc"

Output: 0

Explanation:

No character in word appears in uppercase.

Example 3:

Input: word = "abBCab"

Output: 1

Explanation:

The only special character in word is 'b'.



Constraints:

1 <= word.length <= 50
word consists of only lowercase and uppercase English letters.
*/

class Solution {
    public int numberOfSpecialChars(String word) {
        boolean[] low = new boolean[26];
        boolean[] up = new boolean[26];

        // Traverse the word and mark corresponding lowercase and uppercase characters
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (Character.isLowerCase(c)) {
                low[c - 'a'] = true;
            } else if (Character.isUpperCase(c)) {
                up[c - 'A'] = true;
            }
        }

        // Count matching pairs of lowercase and uppercase characters
        int res = 0;
        for (int i = 0; i < 26; ++i) {
            if (low[i] && up[i]) {
                ++res;
            }
        }

        return res;
    }
}