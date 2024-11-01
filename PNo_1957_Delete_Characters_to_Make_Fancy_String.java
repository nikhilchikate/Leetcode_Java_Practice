/*
A fancy string is a string where no three consecutive characters are equal.

Given a string s, delete the minimum possible number of characters from s to make it fancy.

Return the final string after the deletion. It can be shown that the answer will always be unique.



Example 1:

Input: s = "leeetcode"
Output: "leetcode"
Explanation:
Remove an 'e' from the first group of 'e's to create "leetcode".
No three consecutive characters are equal, so return "leetcode".
Example 2:

Input: s = "aaabaaaa"
Output: "aabaa"
Explanation:
Remove an 'a' from the first group of 'a's to create "aabaaaa".
Remove two 'a's from the second group of 'a's to create "aabaa".
No three consecutive characters are equal, so return "aabaa".
Example 3:

Input: s = "aab"
Output: "aab"
Explanation: No three consecutive characters are equal, so return "aab".


Constraints:

1 <= s.length <= 105
s consists only of lowercase English letters.
*/

class Solution {
    public String makeFancyString(String s) {
        int cnt = 0; // Count of consecutive characters
        StringBuilder res = new StringBuilder(); // Resulting string
        char lastChar = s.charAt(0); // Previous character

        for (char currChar : s.toCharArray()) { // Iterate through each character
            if (currChar == lastChar) {
                cnt++; // Increment count if the same as last
            } else {
                cnt = 1; // Reset count for new character
            }

            // Append character if it appears less than 3 times consecutively
            if (cnt < 3)
                res.append(currChar);
            lastChar = currChar; // Update last character
        }

        return res.toString(); // Return the final fancy string
    }
}