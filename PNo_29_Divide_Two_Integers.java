/*
Given two integers dividend and divisor, divide two integers without using multiplication, division, and mod operator.

The integer division should truncate toward zero, which means losing its fractional part. For example, 8.345 would be truncated to 8, and -2.7335 would be truncated to -2.

Return the quotient after dividing dividend by divisor.

Note: Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231, 231 − 1]. For this problem, if the quotient is strictly greater than 231 - 1, then return 231 - 1, and if the quotient is strictly less than -231, then return -231.



Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Explanation: 10/3 = 3.33333.. which is truncated to 3.
Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Explanation: 7/-3 = -2.33333.. which is truncated to -2.


Constraints:

-231 <= dividend, divisor <= 231 - 1
divisor != 0
*/

class Solution {
    public int divide(int dividend, int divisor) {
        // Edge case: Overflow
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // Determine the sign of the result
        boolean negative = (dividend < 0) != (divisor < 0); // XOR to check if signs differ

        // Work with absolute values to avoid overflow issues
        long dividendAbs = Math.abs((long) dividend);
        long divisorAbs = Math.abs((long) divisor);

        int quotient = 0;

        // Exponentially subtract the divisor from dividend
        while (dividendAbs >= divisorAbs) {
            long tempDivisor = divisorAbs;
            long multiple = 1;

            // Double the divisor and multiple as long as it doesn't exceed the dividend
            while (dividendAbs >= (tempDivisor << 1)) {
                tempDivisor <<= 1;
                multiple <<= 1;
            }

            // Subtract the largest multiple of divisor from the dividend
            dividendAbs -= tempDivisor;
            quotient += multiple;
        }

        // If the result should be negative, negate the quotient
        return negative ? -quotient : quotient;
    }
}