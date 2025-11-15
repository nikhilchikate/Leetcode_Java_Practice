/*
You are given a binary string s.

Return the number of substrings with dominant ones.

A string has dominant ones if the number of ones in the string is greater than or equal to the square of the number of zeros in the string.



Example 1:

Input: s = "00011"

Output: 5

Explanation:

The substrings with dominant ones are shown in the table below.

i	j	s[i..j]	Number of Zeros	Number of Ones
3	3	1	0	1
4	4	1	0	1
2	3	01	1	1
3	4	11	0	2
2	4	011	1	2
Example 2:

Input: s = "101101"

Output: 16

Explanation:

The substrings with non-dominant ones are shown in the table below.

Since there are 21 substrings total and 5 of them have non-dominant ones, it follows that there are 16 substrings with dominant ones.

i	j	s[i..j]	Number of Zeros	Number of Ones
1	1	0	1	0
4	4	0	1	0
1	4	0110	2	2
0	4	10110	2	3
1	5	01101	2	3


Constraints:

1 <= s.length <= 4 * 104
s consists only of characters '0' and '1'.
*/

class Solution {
    public int numberOfSubstrings(String s) {
        return computePrefixes(s);
    }

    private int computePrefixes(String s) {
        int len = s.length();

        int[] zeroPositions = new int[len + 1];
        int zeroPtr = 1;
        zeroPositions[0] = -1;

        int oneCount = 0;

        int answer = 0;

        for (int r = 0; r < len; r++) {
            if (s.charAt(r) == '0') {
                zeroPositions[zeroPtr++] = r;
            } else {
                answer += r - zeroPositions[zeroPtr - 1];
                oneCount++;
            }

            for (int zp = zeroPtr - 1; zp > 0 && (zeroPtr - zp) * (zeroPtr - zp) <= oneCount; zp--) {
                int zerosInRange = zeroPtr - zp;
                int onesInRange = r - zeroPositions[zp] + 1 - zerosInRange;
                int neededOnes = zerosInRange * zerosInRange - onesInRange;
                int backSpan = zeroPositions[zp] - zeroPositions[zp - 1];
                answer += Math.max(backSpan - Math.max(neededOnes, 0), 0);
            }
        }

        return answer;
    }
}