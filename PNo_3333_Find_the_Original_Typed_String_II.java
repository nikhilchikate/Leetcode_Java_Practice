/*
Alice is attempting to type a specific string on her computer. However, she tends to be clumsy and may press a key for too long, resulting in a character being typed multiple times.

You are given a string word, which represents the final output displayed on Alice's screen. You are also given a positive integer k.

Return the total number of possible original strings that Alice might have intended to type, if she was trying to type a string of size at least k.

Since the answer may be very large, return it modulo 109 + 7.



Example 1:

Input: word = "aabbccdd", k = 7

Output: 5

Explanation:

The possible strings are: "aabbccdd", "aabbccd", "aabbcdd", "aabccdd", and "abbccdd".

Example 2:

Input: word = "aabbccdd", k = 8

Output: 1

Explanation:

The only possible string is "aabbccdd".

Example 3:

Input: word = "aaabbb", k = 3

Output: 8



Constraints:

1 <= word.length <= 5 * 105
word consists only of lowercase English letters.
1 <= k <= 2000
*/

class Solution {
    private static final long MOD = (long) 1e9 + 7;

    public int possibleStringCount(String inputWord, int targetLen) {
        if (inputWord.length() == targetLen)
            return 1;
        List<Integer> groupLengths = new ArrayList<>();
        int wordLen = inputWord.length();
        int idx = 0;
        while (idx < wordLen) {
            int nextIdx = idx + 1;
            while (nextIdx < wordLen && inputWord.charAt(nextIdx) == inputWord.charAt(nextIdx - 1))
                nextIdx++;
            groupLengths.add(nextIdx - idx);
            idx = nextIdx;
        }
        int numGroups = groupLengths.size();
        long[] totalCombinations = new long[numGroups];
        totalCombinations[numGroups - 1] = groupLengths.get(numGroups - 1);
        for (idx = numGroups - 2; idx >= 0; idx--) {
            totalCombinations[idx] = (totalCombinations[idx + 1] * groupLengths.get(idx)) % MOD;
        }
        if (numGroups >= targetLen)
            return (int) totalCombinations[0];

        long[][] memo = new long[numGroups][targetLen - numGroups + 1];
        for (idx = 0; idx < targetLen - numGroups + 1; idx++) {
            if (groupLengths.get(numGroups - 1) + idx + numGroups > targetLen) {
                memo[numGroups - 1][idx] = groupLengths.get(numGroups - 1) - (targetLen - numGroups - idx);
            }
        }
        for (idx = numGroups - 2; idx >= 0; idx--) {
            long currentSum = (memo[idx + 1][targetLen - numGroups] * groupLengths.get(idx)) % MOD;
            for (int j = targetLen - numGroups; j >= 0; j--) {
                currentSum = (currentSum + memo[idx + 1][j]) % MOD;
                if (j + groupLengths.get(idx) > targetLen - numGroups) {
                    currentSum = (currentSum - memo[idx + 1][targetLen - numGroups] + MOD) % MOD;
                } else {
                    currentSum = (currentSum - memo[idx + 1][j + groupLengths.get(idx)] + MOD) % MOD;
                }
                memo[idx][j] = currentSum;
            }
        }
        return (int) memo[0][0];
    }
}