/*
You are given two strings s1 and s2 of equal length. A string swap is an operation where you choose two indices in a string (not necessarily different) and swap the characters at these indices.

Return true if it is possible to make both strings equal by performing at most one string swap on exactly one of the strings. Otherwise, return false.



Example 1:

Input: s1 = "bank", s2 = "kanb"
Output: true
Explanation: For example, swap the first character with the last character of s2 to make "bank".
Example 2:

Input: s1 = "attack", s2 = "defend"
Output: false
Explanation: It is impossible to make them equal with one string swap.
Example 3:

Input: s1 = "kelb", s2 = "kelb"
Output: true
Explanation: The two strings are already equal, so no string swap operation is required.


Constraints:

1 <= s1.length, s2.length <= 100
s1.length == s2.length
s1 and s2 consist of only lowercase English letters.
*/

class Solution {
    public boolean areAlmostEqual(String s1, String s2) {
        int n = s1.length();
        if (n != s2.length())
            return false;

        int mismatchCount = 0;
        int firstMismatchIndex = -1;
        int secondMismatchIndex = -1;

        for (int i = 0; i < n; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                mismatchCount++;
                if (mismatchCount > 2)
                    return false;
                if (firstMismatchIndex == -1) {
                    firstMismatchIndex = i;
                } else {
                    secondMismatchIndex = i;
                }
            }
        }

        if (mismatchCount == 0)
            return true;

        if (mismatchCount == 2) {
            return s1.charAt(firstMismatchIndex) == s2.charAt(secondMismatchIndex) &&
                    s1.charAt(secondMismatchIndex) == s2.charAt(firstMismatchIndex);
        }

        return false;
    }
}