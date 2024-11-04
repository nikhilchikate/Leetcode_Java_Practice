/*
Given a string word, compress it using the following algorithm:

Begin with an empty string comp. While word is not empty, use the following operation:
Remove a maximum length prefix of word made of a single character c repeating at most 9 times.
Append the length of the prefix followed by c to comp.
Return the string comp.



Example 1:

Input: word = "abcde"

Output: "1a1b1c1d1e"

Explanation:

Initially, comp = "". Apply the operation 5 times, choosing "a", "b", "c", "d", and "e" as the prefix in each operation.

For each prefix, append "1" followed by the character to comp.

Example 2:

Input: word = "aaaaaaaaaaaaaabb"

Output: "9a5a2b"

Explanation:

Initially, comp = "". Apply the operation 3 times, choosing "aaaaaaaaa", "aaaaa", and "bb" as the prefix in each operation.

For prefix "aaaaaaaaa", append "9" followed by "a" to comp.
For prefix "aaaaa", append "5" followed by "a" to comp.
For prefix "bb", append "2" followed by "b" to comp.


Constraints:

1 <= word.length <= 2 * 105
word consists only of lowercase English letters.
*/

class Solution {
    public String compressedString(String str) {
        StringBuilder sb = new StringBuilder(); // StringBuilder to hold compressed result
        int count = 0; // Count of consecutive characters
        int j = 0; // Index for inner loop

        for (int i = 0; i < str.length(); ++i) {
            j = i; // Start counting from current index
            char ch = str.charAt(i); // Current character

            // Count consecutive occurrences of ch
            while (j < str.length() && str.charAt(j) == ch) {
                ++count;
                ++j;
            }

            // Handle counts greater than 9
            while (count > 9) {
                sb.append('9'); // Append '9' for every 9 occurrences
                sb.append(ch); // Append the character
                count -= 9; // Decrease count
            }

            // Append remaining count and character
            sb.append((char) (count + '0')); // Append count as character
            sb.append(ch); // Append the character
            count = 0; // Reset count
            i = j - 1; // Update i to last counted index
        }

        return sb.toString(); // Return the compressed string
    }
}