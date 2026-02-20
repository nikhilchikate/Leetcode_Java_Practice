/*
Special binary strings are binary strings with the following two properties:

The number of 0's is equal to the number of 1's.
Every prefix of the binary string has at least as many 1's as 0's.
You are given a special binary string s.

A move consists of choosing two consecutive, non-empty, special substrings of s, and swapping them. Two strings are consecutive if the last character of the first string is exactly one index before the first character of the second string.

Return the lexicographically largest resulting string possible after applying the mentioned operations on the string.



Example 1:

Input: s = "11011000"
Output: "11100100"
Explanation: The strings "10" [occuring at s[1]] and "1100" [at s[3]] are swapped.
This is the lexicographically largest string possible after some number of swaps.
Example 2:

Input: s = "10"
Output: "10"


Constraints:

1 <= s.length <= 50
s[i] is either '0' or '1'.
s is a special binary string.
*/

class Solution {
    public String makeLargestSpecial(String s) {
        return solve(s.toCharArray(), 0, s.length());
    }

    private String solve(char[] arr, int l, int r) {
        List<String> parts = new ArrayList<>();
        int balance = 0;
        int start = l;

        for (int i = l; i < r; i++) {
            balance += arr[i] == '1' ? 1 : -1;
            if (balance == 0) {
                String inner = solve(arr, start + 1, i);
                parts.add("1" + inner + "0");
                start = i + 1;
            }
        }

        if (parts.isEmpty()) {
            return new String(arr, l, r - l);
        }

        parts.sort(Collections.reverseOrder());

        StringBuilder sb = new StringBuilder();
        for (String p : parts)
            sb.append(p);
        return sb.toString();
    }
}