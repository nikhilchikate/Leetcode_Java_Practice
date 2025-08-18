/*
You are given an integer array cards of length 4. You have four cards, each containing a number in the range [1, 9]. You should arrange the numbers on these cards in a mathematical expression using the operators ['+', '-', '*', '/'] and the parentheses '(' and ')' to get the value 24.

You are restricted with the following rules:

The division operator '/' represents real division, not integer division.
For example, 4 / (1 - 2 / 3) = 4 / (1 / 3) = 12.
Every operation done is between two numbers. In particular, we cannot use '-' as a unary operator.
For example, if cards = [1, 1, 1, 1], the expression "-1 - 1 - 1 - 1" is not allowed.
You cannot concatenate numbers together
For example, if cards = [1, 2, 1, 2], the expression "12 + 12" is not valid.
Return true if you can get such expression that evaluates to 24, and false otherwise.



Example 1:

Input: cards = [4,1,8,7]
Output: true
Explanation: (8-4) * (7-1) = 24
Example 2:

Input: cards = [1,2,1,2]
Output: false


Constraints:

cards.length == 4
1 <= cards[i] <= 9
*/

class Solution {
    private static final double EPS = 1e-6;

    private boolean checkOperations(double[] numbers, int size) {
        if (size == 1)
            return Math.abs(numbers[0] - 24) < EPS;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                double num1 = numbers[i], num2 = numbers[j];
                numbers[j] = numbers[size - 1];
                numbers[i] = num1 + num2;
                if (checkOperations(numbers, size - 1))
                    return true;
                numbers[i] = num1 - num2;
                if (checkOperations(numbers, size - 1))
                    return true;
                numbers[i] = num2 - num1;
                if (checkOperations(numbers, size - 1))
                    return true;
                numbers[i] = num1 * num2;
                if (checkOperations(numbers, size - 1))
                    return true;
                if (Math.abs(num2) > EPS) {
                    numbers[i] = num1 / num2;
                    if (checkOperations(numbers, size - 1))
                        return true;
                }
                if (Math.abs(num1) > EPS) {
                    numbers[i] = num2 / num1;
                    if (checkOperations(numbers, size - 1))
                        return true;
                }
                numbers[i] = num1;
                numbers[j] = num2;
            }
        }
        return false;
    }

    public boolean judgePoint24(int[] numbers) {
        double[] doubleNumbers = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++)
            doubleNumbers[i] = numbers[i];
        return checkOperations(doubleNumbers, doubleNumbers.length);
    }
}