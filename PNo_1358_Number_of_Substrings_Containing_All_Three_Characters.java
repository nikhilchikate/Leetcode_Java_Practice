/*
Given a string s consisting only of characters a, b and c.

Return the number of substrings containing at least one occurrence of all these characters a, b and c.



Example 1:

Input: s = "abcabc"
Output: 10
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).
Example 2:

Input: s = "aaacb"
Output: 3
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".
Example 3:

Input: s = "abc"
Output: 1


Constraints:

3 <= s.length <= 5 x 10^4
s only consists of a, b or c characters.
*/

class Solution {
    public int numberOfSubstrings(String s) {
        char[] arr = s.toCharArray();
        int[] last = new int[3];
        for (int i = 0; i < last.length; i++) {
            last[i] = -1;
        }
        int ans = 0, r = 0;
        while (r < arr.length) {
            last[arr[r] - 'a'] = r;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                min = Math.min(min, last[i]);
            }
            ans += (min + 1);
            r++;
        }
        return ans;
    }
}