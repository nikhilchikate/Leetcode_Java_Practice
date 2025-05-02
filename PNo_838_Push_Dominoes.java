/*
There are n dominoes in a line, and we place each domino vertically upright. In the beginning, we simultaneously push some of the dominoes either to the left or to the right.

After each second, each domino that is falling to the left pushes the adjacent domino on the left. Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.

When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.

For the purposes of this question, we will consider that a falling domino expends no additional force to a falling or already fallen domino.

You are given a string dominoes representing the initial state where:

dominoes[i] = 'L', if the ith domino has been pushed to the left,
dominoes[i] = 'R', if the ith domino has been pushed to the right, and
dominoes[i] = '.', if the ith domino has not been pushed.
Return a string representing the final state.



Example 1:

Input: dominoes = "RR.L"
Output: "RR.L"
Explanation: The first domino expends no additional force on the second domino.
Example 2:


Input: dominoes = ".L.R...LR..L.."
Output: "LL.RR.LLRRLL.."


Constraints:

n == dominoes.length
1 <= n <= 105
dominoes[i] is either 'L', 'R', or '.'.
*/

class Solution {
    public String pushDominoes(String d) {
        int n = d.length();
        char s[] = d.toCharArray();
        int i = 0;
        while (i < n) {
            if (s[i] != '.') {
                i++;
                continue;
            }
            int j = i;
            while (j < n && s[j] == '.') {
                j++;
            }
            if (i - 1 >= 0 && j < n) {
                if (s[i - 1] == s[j]) {
                    int k = i;
                    while (k < j) {
                        s[k++] = s[i - 1];
                    }
                } else if (s[i - 1] == 'R') {
                    int u = i, v = j - 1;
                    while (u < v) {
                        s[u++] = 'R';
                        s[v--] = 'L';
                    }
                }
            } else if (i - 1 >= 0 && s[i - 1] == 'R') {
                int k = i;
                while (k < j) {
                    s[k++] = 'R';
                }
            } else if (j < n && s[j] == 'L') {
                int k = i;
                while (k < j) {
                    s[k++] = 'L';
                }
            }
            i = j;
        }
        return String.valueOf(s);
    }
}