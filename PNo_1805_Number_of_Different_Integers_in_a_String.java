/*
You are given a string word that consists of digits and lowercase English letters.

You will replace every non-digit character with a space. For example, "a123bc34d8ef34" will become " 123  34 8  34". Notice that you are left with some integers that are separated by at least one space: "123", "34", "8", and "34".

Return the number of different integers after performing the replacement operations on word.

Two integers are considered different if their decimal representations without any leading zeros are different.



Example 1:

Input: word = "a123bc34d8ef34"
Output: 3
Explanation: The three different integers are "123", "34", and "8". Notice that "34" is only counted once.
Example 2:

Input: word = "leet1234code234"
Output: 2
Example 3:

Input: word = "a1b01c001"
Output: 1
Explanation: The three integers "1", "01", and "001" all represent the same integer because
the leading zeros are ignored when comparing their decimal values.


Constraints:

1 <= word.length <= 1000
word consists of digits and lowercase English letters.
*/

class Solution {
    public int numDifferentIntegers(String word) {
        char[] chars = word.toCharArray();
        int n = chars.length;
        int start = 0;
        int end = 0;
        Set<String> uniqueIntegers = new HashSet<>();
        boolean foundDigit = chars[0] >= '0' && chars[0] <= '9';
        boolean isDigit = false;

        for (int i = 0; i < n; i++) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                if (!isDigit && chars[i] == '0') {
                    start = i + 1;
                } else {
                    isDigit = true;
                }
                end = i + 1;
                foundDigit = true;
            } else {
                if (foundDigit) {
                    if (start == end) {
                        uniqueIntegers.add("0");
                    } else {
                        uniqueIntegers.add(word.substring(start, end));
                    }
                    foundDigit = false;
                }
                isDigit = false;
                start = i + 1;
            }
        }
        if (start == end) {
            uniqueIntegers.add("0");
        } else if (start < end) {
            uniqueIntegers.add(word.substring(start, end));
        }
        return uniqueIntegers.size();
    }
}