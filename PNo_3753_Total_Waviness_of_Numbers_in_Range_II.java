/*
You are given two integers num1 and num2 representing an inclusive range [num1, num2].

The waviness of a number is defined as the total count of its peaks and valleys:

A digit is a peak if it is strictly greater than both of its immediate neighbors.
A digit is a valley if it is strictly less than both of its immediate neighbors.
The first and last digits of a number cannot be peaks or valleys.
Any number with fewer than 3 digits has a waviness of 0.
Return the total sum of waviness for all numbers in the range [num1, num2].
 

Example 1:

Input: num1 = 120, num2 = 130

Output: 3

Explanation:

In the range [120, 130]:

120: middle digit 2 is a peak, waviness = 1.
121: middle digit 2 is a peak, waviness = 1.
130: middle digit 3 is a peak, waviness = 1.
All other numbers in the range have a waviness of 0.
Thus, total waviness is 1 + 1 + 1 = 3.

Example 2:

Input: num1 = 198, num2 = 202

Output: 3

Explanation:

In the range [198, 202]:

198: middle digit 9 is a peak, waviness = 1.
201: middle digit 0 is a valley, waviness = 1.
202: middle digit 0 is a valley, waviness = 1.
All other numbers in the range have a waviness of 0.
Thus, total waviness is 1 + 1 + 1 = 3.

Example 3:

Input: num1 = 4848, num2 = 4848

Output: 2

Explanation:

Number 4848: the second digit 8 is a peak, and the third digit 4 is a valley, giving a waviness of 2.

 

Constraints:

1 <= num1 <= num2 <= 1015​​​​​​​
 */

class Solution {
    static final int DIR_N = 0, DIR_U = 1, DIR_D = 2, DIR_F = 3;

    int[] dArr;
    Map<Long, long[]> dpCache;

    public long totalWaviness(long startVal, long endVal) {
        return compute(endVal) - compute(startVal - 1);
    }

    long compute(long val) {
        if (val <= 0)
            return 0;
        dpCache = new HashMap<>();

        String valStr = Long.toString(val);
        dArr = new int[valStr.length()];
        for (int i = 0; i < valStr.length(); i++) {
            dArr[i] = valStr.charAt(i) - '0';
        }

        return traverse(0, 0, DIR_N, true, false)[1];
    }

    long[] traverse(int idx, int lastDigit, int currentDir, boolean isBound, boolean isActive) {
        if (idx == dArr.length) {
            return new long[] { isActive ? 1 : 0, 0 };
        }

        long stateHash = ((long) idx * 10 + lastDigit) * 4 + currentDir;
        stateHash = stateHash * 2 + (isBound ? 1 : 0);
        stateHash = stateHash * 2 + (isActive ? 1 : 0);
        if (dpCache.containsKey(stateHash))
            return dpCache.get(stateHash);

        int maxDigit = isBound ? dArr[idx] : 9;
        long validCount = 0, totalWave = 0;

        for (int currDigit = 0; currDigit <= maxDigit; currDigit++) {
            boolean nextBound = isBound && (currDigit == maxDigit);
            boolean nextActive = isActive || (currDigit != 0);

            int nextLastDigit, nextDir;
            if (!nextActive) {
                nextLastDigit = 0;
                nextDir = DIR_N;
            } else if (!isActive) {
                nextLastDigit = currDigit;
                nextDir = DIR_N;
            } else {
                nextLastDigit = currDigit;
                if (currDigit > lastDigit)
                    nextDir = DIR_U;
                else if (currDigit < lastDigit)
                    nextDir = DIR_D;
                else
                    nextDir = DIR_F;
            }

            long waveInc = 0;
            if (isActive) {
                if (currentDir == DIR_U && currDigit < lastDigit)
                    waveInc = 1;
                if (currentDir == DIR_D && currDigit > lastDigit)
                    waveInc = 1;
            }

            long[] nextState = traverse(idx + 1, nextLastDigit, nextDir, nextBound, nextActive);
            validCount += nextState[0];
            totalWave += nextState[1] + waveInc * nextState[0];
        }

        long[] resultPair = new long[] { validCount, totalWave };
        dpCache.put(stateHash, resultPair);
        return resultPair;
    }
}