/*
You are given two positive integers low and high.

An integer x consisting of 2 * n digits is symmetric if the sum of the first n digits of x is equal to the sum of the last n digits of x. Numbers with an odd number of digits are never symmetric.

Return the number of symmetric integers in the range [low, high].



Example 1:

Input: low = 1, high = 100
Output: 9
Explanation: There are 9 symmetric integers between 1 and 100: 11, 22, 33, 44, 55, 66, 77, 88, and 99.
Example 2:

Input: low = 1200, high = 1230
Output: 4
Explanation: There are 4 symmetric integers between 1200 and 1230: 1203, 1212, 1221, and 1230.


Constraints:

1 <= low <= high <= 104
*/

class Solution {
    private static final short[] symCnt = new short[10_001];

    public int countSymmetricIntegers(int low, int high) {
        if (symCnt[11] == 0) buildSymCnt();
        return symCnt[high] - symCnt[low - 1];
    }

    private void buildSymCnt() {
        for (int num = 11; num <= 99; num++)
            symCnt[num] = (short) (num / 11);

        short prevCnt = symCnt[99];
        for (int num = 100; num <= 999; num++)
            symCnt[num] = prevCnt;

        prevCnt = symCnt[999];
        int idx = 1000;
        for (int d1 = 1; d1 <= 9; d1++) {
            for (int d2 = 0; d2 <= 9; d2++) {
                final int sumFront = d1 + d2;
                for (int d3 = 0; d3 <= 9; d3++)
                    for (int d4 = 0; d4 <= 9; d4++)
                        symCnt[idx++] = (short) ((sumFront == d3 + d4) ? ++prevCnt : prevCnt);
            }
        }

        symCnt[10_000] = symCnt[9999];
    }
}