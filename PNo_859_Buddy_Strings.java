/*
Given two strings s and goal, return true if you can swap two letters in s so the result is equal to goal, otherwise, return false.

Swapping letters is defined as taking two indices i and j (0-indexed) such that i != j and swapping the characters at s[i] and s[j].

For example, swapping at indices 0 and 2 in "abcd" results in "cbad".


Example 1:

Input: s = "ab", goal = "ba"
Output: true
Explanation: You can swap s[0] = 'a' and s[1] = 'b' to get "ba", which is equal to goal.
Example 2:

Input: s = "ab", goal = "ab"
Output: false
Explanation: The only letters you can swap are s[0] = 'a' and s[1] = 'b', which results in "ba" != goal.
Example 3:

Input: s = "aa", goal = "aa"
Output: true
Explanation: You can swap s[0] = 'a' and s[1] = 'a' to get "aa", which is equal to goal.


Constraints:

1 <= s.length, goal.length <= 2 * 104
s and goal consist of lowercase letters.
*/

class Solution {
    public boolean buddyStrings(String s, String goal) {
        if (s.length() != goal.length())
            return false;
        if (s.equals(goal)) {
            int[] freq = new int[26];
            for (char ch : s.toCharArray()) {
                freq[ch - 'a']++;
                if (freq[ch - 'a'] > 1)
                    return true;
            }
            return false;
        }
        char[] src = s.toCharArray();
        char[] tgt = goal.toCharArray();
        int len = src.length;
        int i = 0;
        int c1 = -1;
        int c2 = -1;
        while (i < len) {
            if (src[i] != tgt[i]) {
                if (c1 == -1)
                    c1 = i;
                else if (c2 == -1)
                    c2 = i;
                else
                    return false;
            }
            i++;
        }
        if (c1 == -1 || c2 == -1)
            return false;
        char temp = src[c1];
        src[c1] = src[c2];
        src[c2] = temp;
        return String.valueOf(src).equals(String.valueOf(tgt));
    }
}