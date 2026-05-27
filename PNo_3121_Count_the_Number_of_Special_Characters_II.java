/*
You are given a string word. A letter c is called special if it appears both in lowercase and uppercase in word, and every lowercase occurrence of c appears before the first uppercase occurrence of c.

Return the number of special letters in word.

 

Example 1:

Input: word = "aaAbcBC"

Output: 3

Explanation:

The special characters are 'a', 'b', and 'c'.

Example 2:

Input: word = "abc"

Output: 0

Explanation:

There are no special characters in word.

Example 3:

Input: word = "AbBCab"

Output: 0

Explanation:

There are no special characters in word.

 

Constraints:

1 <= word.length <= 2 * 105
word consists of only lowercase and uppercase English letters.
 */

class Solution {
    public int numberOfSpecialChars(String word) {
        int n = word.length();
        char[] chars = word.toCharArray();
        int[] lastLower = new int[26];
        int[] firstUpper = new int[26];
        Arrays.fill(lastLower, -1);
        Arrays.fill(firstUpper, -1);

        for (int i = 0; i < n; ++i) {
            if (chars[i] >= 'a') {
                lastLower[chars[i] - 'a'] = i;
            } else if (firstUpper[chars[i] - 'A'] == -1) {
                firstUpper[chars[i] - 'A'] = i;
            }
        }

        int ans = 0;

        for (int i = 0; i < 26; ++i) {
            if (lastLower[i] != -1 && lastLower[i] < firstUpper[i]) {
                ans++;
            }
        }
        
        return ans;
    }
}