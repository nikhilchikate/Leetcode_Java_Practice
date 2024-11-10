/*
You are given the strings key and message, which represent a cipher key and a secret message, respectively. The steps to decode message are as follows:

Use the first appearance of all 26 lowercase English letters in key as the order of the substitution table.
Align the substitution table with the regular English alphabet.
Each letter in message is then substituted using the table.
Spaces ' ' are transformed to themselves.
For example, given key = "happy boy" (actual key would have at least one instance of each letter in the alphabet), we have the partial substitution table of ('h' -> 'a', 'a' -> 'b', 'p' -> 'c', 'y' -> 'd', 'b' -> 'e', 'o' -> 'f').
Return the decoded message.



Example 1:


Input: key = "the quick brown fox jumps over the lazy dog", message = "vkbs bs t suepuv"
Output: "this is a secret"
Explanation: The diagram above shows the substitution table.
It is obtained by taking the first appearance of each letter in "the quick brown fox jumps over the lazy dog".
Example 2:


Input: key = "eljuxhpwnyrdgtqkviszcfmabo", message = "zwx hnfx lqantp mnoeius ycgk vcnjrdb"
Output: "the five boxing wizards jump quickly"
Explanation: The diagram above shows the substitution table.
It is obtained by taking the first appearance of each letter in "eljuxhpwnyrdgtqkviszcfmabo".


Constraints:

26 <= key.length <= 2000
key consists of lowercase English letters and ' '.
key contains every letter in the English alphabet ('a' to 'z') at least once.
1 <= message.length <= 2000
message consists of lowercase English letters and ' '.
*/

class Solution {
    public String decodeMessage(String key, String message) {
        char[] map = new char[128]; // To store the character mappings.
        map[' '] = ' '; // Space maps to space.

        // Create character-to-alphabet mapping.
        for (int i = 0, idx = 0; i < key.length(); ++i) {
            char ch = key.charAt(i);
            if (map[ch] == 0) { // If the character hasn't been mapped yet.
                map[ch] = (char) ('a' + idx++); // Map to the next letter.
                if (idx == 26)
                    break; // Stop when all alphabet letters are mapped.
            }
        }

        // Decode the message using the mapping.
        char[] result = message.toCharArray();
        for (int i = 0; i < result.length; ++i) {
            result[i] = map[result[i]]; // Replace with mapped character.
        }

        return String.valueOf(result); // Convert char array back to string.
    }
}