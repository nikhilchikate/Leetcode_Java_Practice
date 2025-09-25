/*
Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.

If the fractional part is repeating, enclose the repeating part in parentheses.

If multiple answers are possible, return any of them.

It is guaranteed that the length of the answer string is less than 104 for all the given inputs.



Example 1:

Input: numerator = 1, denominator = 2
Output: "0.5"
Example 2:

Input: numerator = 2, denominator = 1
Output: "2"
Example 3:

Input: numerator = 4, denominator = 333
Output: "0.(012)"


Constraints:

-231 <= numerator, denominator <= 231 - 1
denominator != 0
*/

class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        long longNumerator = numerator, longDenominator = denominator;

        if (longNumerator == 0) {
            return "0";
        }

        if (longNumerator % longDenominator == 0) {
            return Long.toString(longNumerator / longDenominator);
        }

        StringBuilder resultBuilder = new StringBuilder();

        if ((longDenominator < 0) ^ (longNumerator < 0)) {
            resultBuilder.append('-');
        }

        longNumerator = Math.abs(longNumerator);
        longDenominator = Math.abs(longDenominator);

        resultBuilder.append(longNumerator / longDenominator);
        resultBuilder.append('.');

        long greatestCommonDivisor = gcd(longNumerator, longDenominator);
        longNumerator /= greatestCommonDivisor;
        longDenominator /= greatestCommonDivisor;

        longNumerator %= longDenominator;
        longNumerator *= 10;

        int countTwos = 0, countFives = 0;
        long tempDenominator = longDenominator;

        while (tempDenominator % 2 == 0) {
            countTwos++;
            tempDenominator /= 2;
        }

        while (tempDenominator % 5 == 0) {
            countFives++;
            tempDenominator /= 5;
        }

        if (tempDenominator == 1) {
            while (longNumerator != 0) {
                resultBuilder.append(longNumerator / longDenominator);
                longNumerator = (longNumerator % longDenominator) * 10;
            }
        } else {
            int nonRepeatingPartLength = Math.max(countTwos, countFives);

            for (int i = 0; i < nonRepeatingPartLength; i++) {
                resultBuilder.append(longNumerator / longDenominator);
                longNumerator = (longNumerator % longDenominator) * 10;
            }

            long initialRemainder = longNumerator;
            StringBuilder repeatingPart = new StringBuilder();

            do {
                repeatingPart.append(longNumerator / longDenominator);
                longNumerator = (longNumerator % longDenominator) * 10;
            } while (longNumerator != initialRemainder);

            resultBuilder.append('(').append(repeatingPart.toString()).append(')');
        }

        return resultBuilder.toString();
    }

    private long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a % b);
    }
}