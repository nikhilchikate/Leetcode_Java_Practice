/*
You are given an integer num. You will apply the following steps to num two separate times:

Pick a digit x (0 <= x <= 9).
Pick another digit y (0 <= y <= 9). Note y can be equal to x.
Replace all the occurrences of x in the decimal representation of num by y.
Let a and b be the two results from applying the operation to num independently.

Return the max difference between a and b.

Note that neither a nor b may have any leading zeros, and must not be 0.



Example 1:

Input: num = 555
Output: 888
Explanation: The first time pick x = 5 and y = 9 and store the new integer in a.
The second time pick x = 5 and y = 1 and store the new integer in b.
We have now a = 999 and b = 111 and max difference = 888
Example 2:

Input: num = 9
Output: 8
Explanation: The first time pick x = 9 and y = 9 and store the new integer in a.
The second time pick x = 9 and y = 1 and store the new integer in b.
We have now a = 9 and b = 1 and max difference = 8


Constraints:

1 <= num <= 108
*/

class Solution {
    private int calculateDiff(int inputNum) {
        int nonNineVal = -1, nonOneVal = -1, headDigit = -1;
        int tempVal = inputNum;
        while (tempVal > 0) {
            int currentDig = tempVal % 10;
            if (currentDig != 9) {
                nonNineVal = currentDig;
            }
            if (currentDig > 1) {
                nonOneVal = currentDig;
            }
            headDigit = currentDig;
            tempVal /= 10;
        }

        tempVal = inputNum;
        int minResult = 0, maxResult = 0;
        int powerOf10 = 1;
        while (tempVal > 0) {
            int currentDig = tempVal % 10;

            int forMin = currentDig;
            int forMax = currentDig;

            if (headDigit == 1 && currentDig == nonOneVal) {
                forMin = 0;
            }

            if (headDigit != 1 && currentDig == headDigit) {
                forMin = 1;
            }

            if (currentDig == nonNineVal) {
                forMax = 9;
            }

            minResult += powerOf10 * forMin;
            maxResult += powerOf10 * forMax;

            powerOf10 *= 10;
            tempVal /= 10;
        }

        return maxResult - minResult;
    }

    public int maxDiff(int num) {
        return calculateDiff(num);
    }
}