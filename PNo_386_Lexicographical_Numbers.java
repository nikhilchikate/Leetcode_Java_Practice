/*
Given an integer n, return all the numbers in the range [1, n] sorted in lexicographical order.

You must write an algorithm that runs in O(n) time and uses O(1) extra space.



Example 1:

Input: n = 13
Output: [1,10,11,12,13,2,3,4,5,6,7,8,9]
Example 2:

Input: n = 2
Output: [1,2]


Constraints:

1 <= n <= 5 * 104
*/

public class Solution {

    public List<Integer> lexicalOrder(int n) {
        List<Integer> result = new ArrayList<>();
        // Generate numbers starting from 1 to 9
        for (int prefix = 1; prefix <= 9; ++prefix) {
            buildLexicalNumbers(prefix, n, result);
        }
        return result;
    }

    private void buildLexicalNumbers(int current, int limit, List<Integer> result) {
        // Stop recursion if current exceeds limit
        if (current > limit)
            return;

        // Add current number to result
        result.add(current);

        // Append digits 0-9 to current
        for (int digit = 0; digit <= 9; ++digit) {
            int next = current * 10 + digit;
            // Continue recursion if next is within limit
            if (next <= limit) {
                buildLexicalNumbers(next, limit, result);
            } else {
                break; // Stop if next exceeds limit
            }
        }
    }
}