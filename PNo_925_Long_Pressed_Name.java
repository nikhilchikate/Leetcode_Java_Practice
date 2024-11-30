/*
Your friend is typing his name into a keyboard. Sometimes, when typing a character c, the key might get long pressed, and the character will be typed 1 or more times.

You examine the typed characters of the keyboard. Return True if it is possible that it was your friends name, with some characters (possibly none) being long pressed.



Example 1:

Input: name = "alex", typed = "aaleex"
Output: true
Explanation: 'a' and 'e' in 'alex' were long pressed.
Example 2:

Input: name = "saeed", typed = "ssaaedd"
Output: false
Explanation: 'e' must have been pressed twice, but it was not in the typed output.


Constraints:

1 <= name.length, typed.length <= 1000
name and typed consist of only lowercase English letters.
*/

class Solution {
    public boolean isLongPressedName(String name, String typed) {
        int n1 = name.length();
        int n2 = typed.length();
        int i1 = 0;
        int i2 = 0;
        char prevChar = name.charAt(0);
        char tChar = typed.charAt(0);
        char c1 = prevChar;
        while (i1 < n1 && i2 < n2) {
            c1 = name.charAt(i1);
            tChar = typed.charAt(i2);
            if (c1 == tChar) {
                i1++;
                i2++;
                prevChar = c1;
            } else if (tChar == prevChar) {
                i2++;
            } else {
                return false;
            }
        }
        while (i2 < n2) {
            tChar = typed.charAt(i2);
            if (tChar != c1) {
                return false;
            }
            i2++;
        }
        return i1 == n1 && i2 == n2;
    }
}