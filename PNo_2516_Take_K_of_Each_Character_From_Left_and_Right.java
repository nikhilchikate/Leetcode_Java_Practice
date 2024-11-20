/*
You are given a string s consisting of the characters 'a', 'b', and 'c' and a non-negative integer k. Each minute, you may take either the leftmost character of s, or the rightmost character of s.

Return the minimum number of minutes needed for you to take at least k of each character, or return -1 if it is not possible to take k of each character.



Example 1:

Input: s = "aabaaaacaabc", k = 2
Output: 8
Explanation:
Take three characters from the left of s. You now have two 'a' characters, and one 'b' character.
Take five characters from the right of s. You now have four 'a' characters, two 'b' characters, and two 'c' characters.
A total of 3 + 5 = 8 minutes is needed.
It can be proven that 8 is the minimum number of minutes needed.
Example 2:

Input: s = "a", k = 1
Output: -1
Explanation: It is not possible to take one 'b' or 'c' so return -1.


Constraints:

1 <= s.length <= 105
s consists of only the letters 'a', 'b', and 'c'.
0 <= k <= s.length
*/

class Solution {
    public int takeCharacters(String s, int k) {
        int[] freq = new int[3]; // Frequency array for 'a', 'b', 'c'
        char[] str = s.toCharArray();
        int idx, len = str.length;

        // Count the frequency of each character until a valid substring is found
        for (idx = 0; idx < len; idx++) {
            freq[str[idx] - 'a']++;
            if (freq[0] >= k && freq[1] >= k && freq[2] >= k)
                break;
        }

        // If no valid substring is found, return -1
        if (idx == len)
            return -1;

        int count = idx + 1, minLen = count, end = len - 1;

        // Shrink the window from the left and adjust the count
        while (idx >= 0) {
            if (freq[str[idx] - 'a'] == k) {
                while (str[idx] != str[end]) {
                    freq[str[end] - 'a']++;
                    end--;
                    count++;
                }
                end--;
            } else {
                freq[str[idx] - 'a']--;
                count--;
                minLen = Math.min(count, minLen);
            }
            idx--;
        }
        return minLen;
    }
}