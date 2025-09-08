/*
No-Zero integer is a positive integer that does not contain any 0 in its decimal representation.

Given an integer n, return a list of two integers [a, b] where:

a and b are No-Zero integers.
a + b = n
The test cases are generated so that there is at least one valid solution. If there are many valid solutions, you can return any of them.



Example 1:

Input: n = 2
Output: [1,1]
Explanation: Let a = 1 and b = 1.
Both a and b are no-zero integers, and a + b = 2 = n.
Example 2:

Input: n = 11
Output: [2,9]
Explanation: Let a = 2 and b = 9.
Both a and b are no-zero integers, and a + b = 11 = n.
Note that there are other valid answers as [8, 3] that can be accepted.


Constraints:

2 <= n <= 104
*/

class Solution {
    public int[] getNoZeroIntegers(int number) {
        for (int i = 1; i <= number; i++) {
            int partA = i;
            int partB = number - i;
            if (partA > 0 && partB > 0 && hasZeroDigit(partA) == false && hasZeroDigit(partB) == false) {
                return new int[] { partA, partB };
            }
        }
        return new int[] { -1, -1 };
    }

    public boolean hasZeroDigit(int num) {
        while (num > 0) {
            int digit = num % 10;
            if (digit == 0) {
                return true;
            }
            num /= 10;
        }
        return false;
    }
}