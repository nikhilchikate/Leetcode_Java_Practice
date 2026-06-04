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

1 <= num1 <= num2 <= 105 */

class Solution {
    char[] digits;
    Pair[][][] memo;

    class Pair {
        long count, sum;

        Pair(long count, long sum) {
            this.count = count;
            this.sum = sum;
        }
    }

    public int totalWaviness(int num1, int num2) {
        return (int) calc(num2) - calc(num1 - 1);
    }

    private int calc(int x) {
        if (x <= 0)
            return 0;

        digits = String.valueOf(x).toCharArray();

        memo = new Pair[digits.length][11][3];

        return (int) dfs(0, 10, 0, true, false).sum;
    }

    private Pair dfs(int pos, int prevDigit, int prevDir, boolean tight, boolean started) {
        if (pos == digits.length) {
            return started ? new Pair(1, 0) : new Pair(0, 0);
        }

        if (!tight && started && memo[pos][prevDigit][prevDir + 1] != null) {
            return memo[pos][prevDigit][prevDir + 1];
        }

        int limit = tight ? digits[pos] - '0' : 9;

        long totalCount = 0;
        long totalSum = 0;

        for (int d = 0; d <= limit; d++) {
            boolean nextTight = tight && d == limit;

            if (!started && d == 0) {
                Pair child = dfs(pos + 1, 10, 0, nextTight, false);
                totalCount += child.count;
                totalSum += child.sum;
                continue;
            }

            int nextDir = prevDir;
            int addWave = 0;

            if (!started) {
                nextDir = 0;
            } else {
                if (d > prevDigit)
                    nextDir = 1;
                else if (d < prevDigit)
                    nextDir = -1;
                else
                    nextDir = 0;

                if (prevDir == 1 && nextDir == -1)
                    addWave = 1;
                if (prevDir == -1 && nextDir == 1)
                    addWave = 1;
            }

            Pair child = dfs(pos + 1, d, nextDir, nextTight, true);

            totalCount += child.count;
            totalSum += child.sum + (long) addWave * child.count;
        }

        Pair ans = new Pair(totalCount, totalSum);

        if (!tight && started) {
            memo[pos][prevDigit][prevDir + 1] = ans;
        }

        return ans;
    }
}