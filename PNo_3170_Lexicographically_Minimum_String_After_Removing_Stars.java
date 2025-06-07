/*
You are given a string s. It may contain any number of '*' characters. Your task is to remove all '*' characters.

While there is a '*', do the following operation:

Delete the leftmost '*' and the smallest non-'*' character to its left. If there are several smallest characters, you can delete any of them.
Return the lexicographically smallest resulting string after removing all '*' characters.



Example 1:

Input: s = "aaba*"

Output: "aab"

Explanation:

We should delete one of the 'a' characters with '*'. If we choose s[3], s becomes the lexicographically smallest.

Example 2:

Input: s = "abc"

Output: "abc"

Explanation:

There is no '*' in the string.



Constraints:

1 <= s.length <= 105
s consists only of lowercase English letters and '*'.
The input is generated such that it is possible to delete all '*' characters.
*/

class Solution {
    public String clearStars(String s) {
        int len = s.length();
        byte[] charArr = new byte[len + 1];
        s.getBytes(0, len, charArr, 0);
        charArr[len] = '*';

        int procStart = 0;
        int starCount = 0;
        for (int i = 0; i < len; i++) {
            if (charArr[i] == '*') {
                starCount++;
                if (starCount == ((i + 2) >> 1))
                    procStart = i + 1;
            }
        }
        if (starCount == 0)
            return s;
        if (procStart == len)
            return "";

        int[] prevIdx = new int[len];
        int[] heads = new int[128];
        for (int i = 'a'; i <= 'z'; i++)
            heads[i] = -1;
        for (int currIdx = procStart;; currIdx++) {
            int charVal = charArr[currIdx];
            if (charVal == '*') {
                if (currIdx >= len)
                    break;
                charArr[currIdx] = 0;
                for (int i = 'a';; i++) {
                    if (heads[i] >= 0) {
                        charArr[heads[i]] = 0;
                        heads[i] = prevIdx[heads[i]];
                        break;
                    }
                }
            } else {
                prevIdx[currIdx] = heads[charVal];
                heads[charVal] = currIdx;
            }
        }

        int outputIdx = 0;
        for (int i = procStart; i < len; i++)
            if (charArr[i] != 0)
                charArr[outputIdx++] = charArr[i];

        return new String(charArr, 0, outputIdx);
    }
}