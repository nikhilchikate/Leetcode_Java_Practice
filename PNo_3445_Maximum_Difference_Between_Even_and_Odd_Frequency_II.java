/*
You are given a string s and an integer k. Your task is to find the maximum difference between the frequency of two characters, freq[a] - freq[b], in a substring subs of s, such that:

subs has a size of at least k.
Character a has an odd frequency in subs.
Character b has an even frequency in subs.
Return the maximum difference.

Note that subs can contain more than 2 distinct characters.



Example 1:

Input: s = "12233", k = 4

Output: -1

Explanation:

For the substring "12233", the frequency of '1' is 1 and the frequency of '3' is 2. The difference is 1 - 2 = -1.

Example 2:

Input: s = "1122211", k = 3

Output: 1

Explanation:

For the substring "11222", the frequency of '2' is 3 and the frequency of '1' is 2. The difference is 3 - 2 = 1.

Example 3:

Input: s = "110", k = 3

Output: -1



Constraints:

3 <= s.length <= 3 * 104
s consists only of digits '0' to '4'.
The input is generated that at least one substring has a character with an even frequency and a character with an odd frequency.
1 <= k <= s.length
*/

class Solution {
    private static final int INF_DIFF = Integer.MAX_VALUE / 2;

    public static int maxDifference(String inputStr, int minLen) {
        char[] charArr = inputStr.toCharArray();
        int strLen = charArr.length;
        int[] minPfdAB = new int[4];
        int[] minPfdBA = new int[4];
        boolean[] charMissing = new boolean[5];
        int maxRes = Integer.MIN_VALUE;

        outerLoop: for (char charA = '1'; charA <= '4'; charA++) {
            innerLoop: for (char charB = '0'; charB < charA; charB++) {
                if (charMissing[charB - '0']) {
                    continue;
                }

                int rightPtr = 0;
                int countA_R = 0;
                int countB_R = 0;

                while (rightPtr < minLen || countA_R + countB_R < 3 || countA_R == 0 || countB_R == 0) {
                    if (rightPtr == strLen) {
                        if (countB_R == 0) {
                            charMissing[charB - '0'] = true;
                        }
                        if (countA_R == 0) {
                            charMissing[charA - '0'] = true;
                            continue outerLoop;
                        } else {
                            continue innerLoop;
                        }
                    }
                    char currentCh = charArr[rightPtr++];
                    if (currentCh == charA) {
                        countA_R++;
                    } else if (currentCh == charB) {
                        countB_R++;
                    }
                }

                int leftPtr = 0;
                int countA_L = 0;
                int countB_L = 0;

                Arrays.fill(minPfdAB, INF_DIFF);
                Arrays.fill(minPfdBA, INF_DIFF);
                minPfdAB[0] = minPfdBA[0] = 0;

                while (true) {
                    int parState, freqDiff;

                    while (leftPtr < rightPtr - minLen) {
                        char currentCh = charArr[leftPtr++];
                        if (currentCh == charA) {
                            if (countA_R == countA_L + 1) {
                                leftPtr--;
                                break;
                            }
                            countA_L++;
                        } else if (currentCh == charB) {
                            if (countB_R == countB_L + 1) {
                                leftPtr--;
                                break;
                            }
                            countB_L++;
                        } else {
                            continue;
                        }
                        parState = ((countA_L & 1) << 1) + (countB_L & 1);
                        freqDiff = countA_L - countB_L;
                        minPfdAB[parState] = Math.min(minPfdAB[parState], freqDiff);
                        minPfdBA[parState] = Math.min(minPfdBA[parState], -freqDiff);
                    }

                    parState = ((countA_R & 1) << 1) + (countB_R & 1);
                    freqDiff = countA_R - countB_R;
                    maxRes = Math.max(maxRes, freqDiff - minPfdAB[parState ^ 2]);
                    maxRes = Math.max(maxRes, -freqDiff - minPfdBA[parState ^ 1]);

                    if (rightPtr == strLen) {
                        break;
                    }

                    char currentCh = charArr[rightPtr++];
                    if (currentCh == charA) {
                        countA_R++;
                    } else if (currentCh == charB) {
                        countB_R++;
                    } else {
                        while (rightPtr < strLen && (currentCh = charArr[rightPtr]) != charA && currentCh != charB) {
                            rightPtr++;
                        }
                    }
                }
            }
        }
        return maxRes;
    }
}