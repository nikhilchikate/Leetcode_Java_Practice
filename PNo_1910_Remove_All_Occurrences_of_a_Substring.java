/*
Given two strings s and part, perform the following operation on s until all occurrences of the substring part are removed:

Find the leftmost occurrence of the substring part and remove it from s.
Return s after removing all occurrences of part.

A substring is a contiguous sequence of characters in a string.



Example 1:

Input: s = "daabcbaabcbc", part = "abc"
Output: "dab"
Explanation: The following operations are done:
- s = "daabcbaabcbc", remove "abc" starting at index 2, so s = "dabaabcbc".
- s = "dabaabcbc", remove "abc" starting at index 4, so s = "dababc".
- s = "dababc", remove "abc" starting at index 3, so s = "dab".
Now s has no occurrences of "abc".
Example 2:

Input: s = "axxxxyyyyb", part = "xy"
Output: "ab"
Explanation: The following operations are done:
- s = "axxxxyyyyb", remove "xy" starting at index 4 so s = "axxxyyyb".
- s = "axxxyyyb", remove "xy" starting at index 3 so s = "axxyyb".
- s = "axxyyb", remove "xy" starting at index 2 so s = "axyb".
- s = "axyb", remove "xy" starting at index 1 so s = "ab".
Now s has no occurrences of "xy".


Constraints:

1 <= s.length <= 1000
1 <= part.length <= 1000
s​​​​​​ and part consists of lowercase English letters.
*/

class Solution {
    public String removeOccurrences(String str, String part) {
        char[] input = str.toCharArray();
        char[] target = part.toCharArray();
        int targetLength = target.length;
        char targetEndChar = target[targetLength - 1];

        int resultLength = 0;

        for (char currentChar : input) {
            input[resultLength++] = currentChar;

            if (currentChar == targetEndChar && resultLength >= targetLength) {
                int i = resultLength - 1, j = targetLength - 1;

                while (j >= 0 && input[i] == target[j]) {
                    i--;
                    j--;
                }

                if (j < 0) {
                    resultLength = i + 1;
                }
            }
        }

        return new String(input, 0, resultLength);
    }
}