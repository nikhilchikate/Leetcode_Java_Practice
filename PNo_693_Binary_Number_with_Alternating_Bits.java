/*
Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits will always have different values.



Example 1:

Input: n = 5
Output: true
Explanation: The binary representation of 5 is: 101
Example 2:

Input: n = 7
Output: false
Explanation: The binary representation of 7 is: 111.
Example 3:

Input: n = 11
Output: false
Explanation: The binary representation of 11 is: 1011.


Constraints:

1 <= n <= 231 - 1
*/

class Solution {
    public boolean hasAlternatingBits(int n) {
        //            n = 0b010101
        //       n >> 2 = 0b000101
        // n ^ (n >> 2) = 0b010000 = a
        //        a - 1 = 0b001111
        //  a & (a - 1) = 0
        final int a = n ^ (n >> 2);
        return (a & (a - 1)) == 0;
    }
}