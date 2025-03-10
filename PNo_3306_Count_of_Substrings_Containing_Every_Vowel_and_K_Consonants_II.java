/*
You are given a string word and a non-negative integer k.

Return the total number of substrings of word that contain every vowel ('a', 'e', 'i', 'o', and 'u') at least once and exactly k consonants.



Example 1:

Input: word = "aeioqq", k = 1

Output: 0

Explanation:

There is no substring with every vowel.

Example 2:

Input: word = "aeiou", k = 0

Output: 1

Explanation:

The only substring with every vowel and zero consonants is word[0..4], which is "aeiou".

Example 3:

Input: word = "ieaouqqieaouqq", k = 1

Output: 3

Explanation:

The substrings with every vowel and one consonant are:

word[0..5], which is "ieaouq".
word[6..11], which is "qieaou".
word[7..12], which is "ieaouq".


Constraints:

5 <= word.length <= 2 * 105
word consists only of lowercase English letters.
0 <= k <= word.length - 5
*/

class Solution {
    public long countOfSubstrings(String inputString, int maxNonVowels) {
        int[][] vowelPresence = new int[2][128];
        vowelPresence[0]['a'] = 1;
        vowelPresence[0]['e'] = 1;
        vowelPresence[0]['i'] = 1;
        vowelPresence[0]['o'] = 1;
        vowelPresence[0]['u'] = 1;

        long totalSubstrings = 0;

        int nonVowelCount = 0;
        int vowelCount = 0;
        int redundantLeftShift = 0;
        for (int rightIndex = 0, leftIndex = 0; rightIndex < inputString.length(); rightIndex++) {
            char currentChar = inputString.charAt(rightIndex);

            if (vowelPresence[0][currentChar] == 1 && ++vowelPresence[1][currentChar] == 1) {
                vowelCount++;
            } else if (vowelPresence[0][currentChar] == 0) {
                nonVowelCount++;
            }

            while (nonVowelCount > maxNonVowels) {
                char leftChar = inputString.charAt(leftIndex);

                if (vowelPresence[0][leftChar] == 1 && --vowelPresence[1][leftChar] == 0) {
                    vowelCount--;
                } else if (vowelPresence[0][leftChar] == 0) {
                    nonVowelCount--;
                }
                leftIndex++;
                redundantLeftShift = 0;
            }

            // try to shrink
            while (vowelCount == 5 && nonVowelCount == maxNonVowels && leftIndex < rightIndex
                    && vowelPresence[0][inputString.charAt(leftIndex)] == 1
                    && vowelPresence[1][inputString.charAt(leftIndex)] > 1) {
                redundantLeftShift++;
                vowelPresence[1][inputString.charAt(leftIndex)]--;
                leftIndex++;
            }

            if (nonVowelCount == maxNonVowels && vowelCount == 5) {
                totalSubstrings++;
                totalSubstrings += redundantLeftShift;
            }
        }

        return totalSubstrings;
    }
}