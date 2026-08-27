/*
You are given two strings s and target, both having length n, consisting of lowercase English letters.

Return the lexicographically smallest permutation of s that is strictly greater than target. If no permutation of s is lexicographically strictly greater than target, return an empty string.

A string a is lexicographically strictly greater than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears later in the alphabet than the corresponding letter in b.

 

Example 1:

Input: s = "abc", target = "bba"

Output: "bca"

Explanation:

The permutations of s (in lexicographical order) are "abc", "acb", "bac", "bca", "cab", and "cba".
The lexicographically smallest permutation that is strictly greater than target is "bca".
Example 2:

Input: s = "leet", target = "code"

Output: "eelt"

Explanation:

The permutations of s (in lexicographical order) are "eelt", "eetl", "elet", "elte", "etel", "etle", "leet", "lete", "ltee", "teel", "tele", and "tlee".
The lexicographically smallest permutation that is strictly greater than target is "eelt".
Example 3:

Input: s = "baba", target = "bbaa"

Output: ""

Explanation:

The permutations of s (in lexicographical order) are "aabb", "abab", "abba", "baab", "baba", and "bbaa".
None of them is lexicographically strictly greater than target. Therefore, the answer is "".
 

Constraints:

1 <= s.length == target.length <= 300
s and target consist of only lowercase English letters.
*/

class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] sCount = new int[26];

        for (int i = 0; i < s.length(); i++) {
            sCount[s.charAt(i) - 'a']++;
        }

        int startIndex = 0;
        while (startIndex < target.length() && sCount[target.charAt(startIndex) - 'a'] > 0) {
            sCount[target.charAt(startIndex) - 'a'] -= 1;
            startIndex++;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (i < startIndex) {
                sCount[target.charAt(i) - 'a']++;
            }

            if (i < s.length()) {
                int targetChar = target.charAt(i) - 'a';
                for (int c = targetChar + 1; c < 26; c++) {
                    if (sCount[c] > 0) {
                        StringBuilder result = new StringBuilder();
                        result.append(target.substring(0, i));
                        result.append((char) (c + 'a'));
                        sCount[c]--;

                        for (int j = 0; j < 26; j++) {
                            while (sCount[j] > 0) {
                                result.append((char) (j + 'a'));
                                sCount[j]--;
                            }
                        }

                        return result.toString();
                    }
                }
            }
        }

        return "";
    }
}