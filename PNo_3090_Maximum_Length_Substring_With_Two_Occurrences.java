/*
Given a string s, return the maximum length of a substring such that it contains at most two occurrences of each character.
 

Example 1:

Input: s = "bcbbbcba"

Output: 4

Explanation:

The following substring has a length of 4 and contains at most two occurrences of each character: "bcbbbcba".
Example 2:

Input: s = "aaaa"

Output: 2

Explanation:

The following substring has a length of 2 and contains at most two occurrences of each character: "aaaa".
 

Constraints:

2 <= s.length <= 100
s consists only of lowercase English letters.
*/

class Solution {
    public int maximumLengthSubstring(String s) {
        int ans = 0;
        int[] cnt = new int[26];

        for (int l = 0, r = 0; r < s.length(); ++r) {
            int idx = s.charAt(r) - 'a';
            ++cnt[idx];
            while (cnt[idx] > 2) {
                --cnt[s.charAt(l++) - 'a'];
            }
            ans = Math.max(ans, r - l + 1);
        }
        
        return ans;
    }
}