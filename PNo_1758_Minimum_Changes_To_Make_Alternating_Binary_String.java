/*
You are given a string s consisting only of the characters '0' and '1'. In one operation, you can change any '0' to '1' or vice versa.

The string is called alternating if no two adjacent characters are equal. For example, the string "010" is alternating, while the string "0100" is not.

Return the minimum number of operations needed to make s alternating.



Example 1:

Input: s = "0100"
Output: 1
Explanation: If you change the last character to '1', s will be "0101", which is alternating.
Example 2:

Input: s = "10"
Output: 0
Explanation: s is already alternating.
Example 3:

Input: s = "1111"
Output: 2
Explanation: You need two operations to reach "0101" or "1010".


Constraints:

1 <= s.length <= 104
s[i] is either '0' or '1'.
*/

class Solution {
    public static int calculateOperations(char[] characters) {
        int operationCount = 0;
        int length = characters.length;

        for (int index = 1; index < length; index++) {
            if (characters[index] != characters[index - 1]) {
                continue;
            }
            characters[index] = characters[index - 1] == '0' ? '1' : '0';
            operationCount++;
        }

        return operationCount;
    }

    public int minOperations(String s) {
        int length = s.length();

        char[] firstPattern = s.toCharArray();
        int operationsFirst = calculateOperations(firstPattern);

        char[] secondPattern = s.toCharArray();
        secondPattern[0] = secondPattern[0] == '0' ? '1' : '0';
        int operationsSecond = calculateOperations(secondPattern) + 1;

        return Math.min(operationsFirst, operationsSecond);
    }
}