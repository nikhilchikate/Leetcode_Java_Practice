/*
You are given a string s. We want to partition the string into as many parts as possible so that each letter appears in at most one part. For example, the string "ababcc" can be partitioned into ["abab", "cc"], but partitions such as ["aba", "bcc"] or ["ab", "ab", "cc"] are invalid.

Note that the partition is done so that after concatenating all the parts in order, the resultant string should be s.

Return a list of integers representing the size of these parts.



Example 1:

Input: s = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.
Example 2:

Input: s = "eccbbbbdec"
Output: [10]


Constraints:

1 <= s.length <= 500
s consists of lowercase English letters.
*/

class Solution {
    public List<Integer> partitionLabels(String str) {
        int[] lastIdx = new int[26];
        int index = 0;
        for (char ch : str.toCharArray()) {
            lastIdx[ch - 'a'] = index;
            index++;
        }

        List<Integer> result = new ArrayList<>();
        index = 0;
        int start = 0, length = str.length();
        while (index < length) {
            start = findMax(str, lastIdx, index);
            result.add(start - index + 1);
            index = start + 1;
        }
        return result;
    }

    private int findMax(String str, int[] lastIdx, int start) {
        int left = Math.max(start, lastIdx[str.charAt(start) - 'a']), maxIdx = left;
        while (start <= Math.max(maxIdx, left)) {
            maxIdx = Math.max(maxIdx, lastIdx[str.charAt(start) - 'a']);
            start++;
        }
        return maxIdx;
    }
}