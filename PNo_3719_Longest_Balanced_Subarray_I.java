/*
You are given an integer array nums.

A subarray is called balanced if the number of distinct even numbers in the subarray is equal to the number of distinct odd numbers.

Return the length of the longest balanced subarray.



Example 1:

Input: nums = [2,5,4,3]

Output: 4

Explanation:

The longest balanced subarray is [2, 5, 4, 3].
It has 2 distinct even numbers [2, 4] and 2 distinct odd numbers [5, 3]. Thus, the answer is 4.
Example 2:

Input: nums = [3,2,2,5,4]

Output: 5

Explanation:

The longest balanced subarray is [3, 2, 2, 5, 4].
It has 2 distinct even numbers [2, 4] and 2 distinct odd numbers [3, 5]. Thus, the answer is 5.
Example 3:

Input: nums = [1,2,3,2]

Output: 3

Explanation:

The longest balanced subarray is [2, 3, 2].
It has 1 distinct even number [2] and 1 distinct odd number [3]. Thus, the answer is 3.


Constraints:

1 <= nums.length <= 1500
1 <= nums[i] <= 105
*/

class Solution {
    // Reuse arrays to avoid repeated allocation
    int[] seenEven = new int[100001];
    int[] seenOdd = new int[100001];

    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int timer = 0;

        for (int i = 0; i < n; i++) {
            // Prune if remaining length cannot beat current answer
            if (ans >= n - i) {
                break;
            }

            timer++;
            int even = 0, odd = 0;

            for (int j = i; j < n; j++) {
                int v = nums[j];
                if ((v & 1) == 0) {
                    if (seenEven[v] != timer) {
                        seenEven[v] = timer;
                        even++;
                    }
                } else {
                    if (seenOdd[v] != timer) {
                        seenOdd[v] = timer;
                        odd++;
                    }
                }

                if (even == odd) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }
}