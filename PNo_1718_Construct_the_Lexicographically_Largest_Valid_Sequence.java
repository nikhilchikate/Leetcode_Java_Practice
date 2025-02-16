/*
Given an integer n, find a sequence that satisfies all of the following:

The integer 1 occurs once in the sequence.
Each integer between 2 and n occurs twice in the sequence.
For every integer i between 2 and n, the distance between the two occurrences of i is exactly i.
The distance between two numbers on the sequence, a[i] and a[j], is the absolute difference of their indices, |j - i|.

Return the lexicographically largest sequence. It is guaranteed that under the given constraints, there is always a solution.

A sequence a is lexicographically larger than a sequence b (of the same length) if in the first position where a and b differ, sequence a has a number greater than the corresponding number in b. For example, [0,1,9,0] is lexicographically larger than [0,1,5,6] because the first position they differ is at the third number, and 9 is greater than 5.



Example 1:

Input: n = 3
Output: [3,1,2,3,2]
Explanation: [2,3,2,1,3] is also a valid sequence, but [3,1,2,3,2] is the lexicographically largest valid sequence.
Example 2:

Input: n = 5
Output: [5,3,1,4,3,5,2,4,2]


Constraints:

1 <= n <= 20
*/

class Solution {
    public int[] constructDistancedSequence(int n) {
        int[] ans = new int[2 * n - 1];
        boolean[] used = new boolean[n + 1]; // Keep track of used numbers

        if (solve(ans, used, 0, n)) {
            return ans;
        }
        return new int[0];
    }

    private boolean solve(int[] ans, boolean[] used, int index, int n) {
        if (index == ans.length) {
            return true;
        }

        if (ans[index] != 0) {
            return solve(ans, used, index + 1, n);
        }

        for (int num = n; num >= 1; num--) {
            if (used[num]) {
                continue;
            }

            if (num == 1) {
                ans[index] = 1;
                used[1] = true;
                if (solve(ans, used, index + 1, n)) {
                    return true;
                }
                used[1] = false; // Backtrack
                ans[index] = 0;
            } else {
                if (index + num < ans.length && ans[index + num] == 0) {
                    ans[index] = num;
                    ans[index + num] = num;
                    used[num] = true;
                    if (solve(ans, used, index + 1, n)) {
                        return true;
                    }
                    used[num] = false; // Backtrack
                    ans[index + num] = 0;
                    ans[index] = 0;
                }
            }
        }
        return false;
    }
}