/*
You are given a string s and an integer t, representing the number of transformations to perform. In one transformation, every character in s is replaced according to the following rules:

If the character is 'z', replace it with the string "ab".
Otherwise, replace it with the next character in the alphabet. For example, 'a' is replaced with 'b', 'b' is replaced with 'c', and so on.
Return the length of the resulting string after exactly t transformations.

Since the answer may be very large, return it modulo 109 + 7.



Example 1:

Input: s = "abcyy", t = 2

Output: 7

Explanation:

First Transformation (t = 1):
'a' becomes 'b'
'b' becomes 'c'
'c' becomes 'd'
'y' becomes 'z'
'y' becomes 'z'
String after the first transformation: "bcdzz"
Second Transformation (t = 2):
'b' becomes 'c'
'c' becomes 'd'
'd' becomes 'e'
'z' becomes "ab"
'z' becomes "ab"
String after the second transformation: "cdeabab"
Final Length of the string: The string is "cdeabab", which has 7 characters.
Example 2:

Input: s = "azbk", t = 1

Output: 5

Explanation:

First Transformation (t = 1):
'a' becomes 'b'
'z' becomes "ab"
'b' becomes 'c'
'k' becomes 'l'
String after the first transformation: "babcl"
Final Length of the string: The string is "babcl", which has 5 characters.


Constraints:

1 <= s.length <= 105
s consists only of lowercase English letters.
1 <= t <= 105
*/

class Solution {
    public int lengthAfterTransformations(String input, int transformations) {
        int modulo = (int) 1e9 + 7;
        int finalLength = 0;
        long[] charCounts = new long[26];

        for (char c : input.toCharArray()) {
            charCounts[c - 'a']++;
        }

        for (; transformations >= 26; transformations -= 26) {
            long zCount = charCounts[25];
            for (int i = 25; i > 0; i--) {
                charCounts[i] = (charCounts[i] + charCounts[i - 1]) % modulo;
            }
            charCounts[0] = (charCounts[0] + zCount) % modulo;
            charCounts[1] = (charCounts[1] + zCount) % modulo;
        }

        for (int i = 0; i < 26; i++) {
            finalLength = (int) ((finalLength + charCounts[i]) % modulo);
        }

        for (int i = 26 - transformations; i < 26; i++) {
            finalLength = (int) ((finalLength + charCounts[i]) % modulo);
        }

        return finalLength;
    }
}