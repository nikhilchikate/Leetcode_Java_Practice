/*
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Every close bracket has a corresponding open bracket of the same type.
 

Example 1:

Input: s = "()"

Output: true

Example 2:

Input: s = "()[]{}"

Output: true

Example 3:

Input: s = "(]"

Output: false

Example 4:

Input: s = "([])"

Output: true

Example 5:

Input: s = "([)]"

Output: false

 

Constraints:

1 <= s.length <= 104
s consists of parentheses only '()[]{}'.
*/

class Solution {
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) return false;

        char[] stack = new char[s.length()];
        int head = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack[head++] = ')';
            } else if (c == '{') {
                stack[head++] = '}';
            } else if (c == '[') {
                stack[head++] = ']';
            } else {
                if (head == 0 || stack[--head] != c) {
                    return false;
                }
            }
        }

        return head == 0;
    }
}