/*
You are given two integers n and maxValue, which are used to describe an ideal array.

A 0-indexed integer array arr of length n is considered ideal if the following conditions hold:

Every arr[i] is a value from 1 to maxValue, for 0 <= i < n.
Every arr[i] is divisible by arr[i - 1], for 0 < i < n.
Return the number of distinct ideal arrays of length n. Since the answer may be very large, return it modulo 109 + 7.



Example 1:

Input: n = 2, maxValue = 5
Output: 10
Explanation: The following are the possible ideal arrays:
- Arrays starting with the value 1 (5 arrays): [1,1], [1,2], [1,3], [1,4], [1,5]
- Arrays starting with the value 2 (2 arrays): [2,2], [2,4]
- Arrays starting with the value 3 (1 array): [3,3]
- Arrays starting with the value 4 (1 array): [4,4]
- Arrays starting with the value 5 (1 array): [5,5]
There are a total of 5 + 2 + 1 + 1 + 1 = 10 distinct ideal arrays.
Example 2:

Input: n = 5, maxValue = 3
Output: 11
Explanation: The following are the possible ideal arrays:
- Arrays starting with the value 1 (9 arrays):
   - With no other distinct values (1 array): [1,1,1,1,1]
   - With 2nd distinct value 2 (4 arrays): [1,1,1,1,2], [1,1,1,2,2], [1,1,2,2,2], [1,2,2,2,2]
   - With 2nd distinct value 3 (4 arrays): [1,1,1,1,3], [1,1,1,3,3], [1,1,3,3,3], [1,3,3,3,3]
- Arrays starting with the value 2 (1 array): [2,2,2,2,2]
- Arrays starting with the value 3 (1 array): [3,3,3,3,3]
There are a total of 9 + 1 + 1 = 11 distinct ideal arrays.


Constraints:

2 <= n <= 104
1 <= maxValue <= 104
*/

import java.math.BigInteger;

class Solution {
    public static final int MOD = (int) 1e9 + 7;

    public static int idealArrays(int arrayLength, int maxVal) {
        int[] smallestPrimeDivisor = new int[maxVal + 1];
        for (int prime = 2; prime <= maxVal; prime++) {
            if (smallestPrimeDivisor[prime] != 0) {
                continue;
            }
            for (int multiple = prime; multiple <= maxVal; multiple += prime) {
                if (smallestPrimeDivisor[multiple] == 0) {
                    smallestPrimeDivisor[multiple] = prime;
                }
            }
        }

        int maxPowerOfTwo = (int) (Math.log(maxVal) / Math.log(2));
        int[] combinations = new int[maxPowerOfTwo + 1];
        BigInteger currentCombination = BigInteger.ONE;
        BigInteger moduloValue = BigInteger.valueOf(MOD);
        for (int i = 1; i <= maxPowerOfTwo; i++) {
            currentCombination = currentCombination.multiply(BigInteger.valueOf(arrayLength + i - 1));
            currentCombination = currentCombination.divide(BigInteger.valueOf(i));
            combinations[i] = currentCombination.mod(moduloValue).intValue();
        }

        int totalIdealArrays = 0;
        for (int num = 1; num <= maxVal; num++) {
            int tempNum = num;
            long productOfCombinations = 1;
            while (tempNum > 1) {
                int primeFactor = smallestPrimeDivisor[tempNum];
                int power = 0;
                do {
                    power++;
                    tempNum /= primeFactor;
                } while (tempNum % primeFactor == 0);
                productOfCombinations = productOfCombinations * combinations[power] % MOD;
            }
            totalIdealArrays = (totalIdealArrays + (int) productOfCombinations) % MOD;
        }

        return totalIdealArrays;
    }
}