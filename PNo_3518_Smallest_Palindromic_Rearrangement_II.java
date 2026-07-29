/*
You are given a palindromic string s and an integer k.

Return the k-th lexicographically smallest palindromic permutation of s. If there are fewer than k distinct palindromic permutations, return an empty string.

Note: Different rearrangements that yield the same palindromic string are considered identical and are counted once.

 

Example 1:

Input: s = "abba", k = 2

Output: "baab"

Explanation:

The two distinct palindromic rearrangements of "abba" are "abba" and "baab".
Lexicographically, "abba" comes before "baab". Since k = 2, the output is "baab".
Example 2:

Input: s = "aa", k = 2

Output: ""

Explanation:

There is only one palindromic rearrangement: "aa".
The output is an empty string since k = 2 exceeds the number of possible rearrangements.
Example 3:

Input: s = "bacab", k = 1

Output: "abcba"

Explanation:

The two distinct palindromic rearrangements of "bacab" are "abcba" and "bacab".
Lexicographically, "abcba" comes before "bacab". Since k = 1, the output is "abcba".
 

Constraints:

1 <= s.length <= 104
s consists of lowercase English letters.
s is guaranteed to be palindromic.
1 <= k <= 106
*/

class Solution {
    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        int n = s.length();
        int cnt = 0;
        long totalWays = 1L;
        for (int i = 0; i < n / 2; i++) {
            freq[s.charAt(i) - 'a']++;
        }

        char[] alpha = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        StringBuilder sb = new StringBuilder();
        outer: for (int i = 25; i >= 0; i--) {
            if (freq[i] == 0)
                continue;
            int j = 1;
            while (j <= freq[i]) {
                cnt++;
                totalWays = (totalWays * cnt) / j;
                if (totalWays > k) {
                    for (int l = 0; l < i; l++) {
                        while (freq[l] > 0) {
                            sb.append(alpha[l]);
                            freq[l]--;
                        }
                    }
                    while (freq[i] > j) {
                        sb.append(alpha[i]);
                        freq[i]--;
                    }
                    break outer;
                }
                j++;
            }
        }
        if (k > totalWays)
            return "";

        for (int i = 0; i < cnt; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                int j = ch - 'a';
                if (freq[j] == 0)
                    continue;
                if (k <= (totalWays * freq[j]) / (cnt - i)) {
                    totalWays = (totalWays * freq[j]) / (cnt - i);
                    freq[j]--;
                    sb.append(ch);
                    break;
                } else {
                    k = (int) (k - (totalWays * freq[j]) / (cnt - i));
                }
            }
        }
        if (n % 2 == 1) {
            sb.append(s.charAt(n / 2));
        }

        for (int i = n / 2 - 1; i >= 0; i--) {
            sb.append(sb.charAt(i));
        }

        return sb.toString();
    }
}