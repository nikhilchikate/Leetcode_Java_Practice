/*
You are given an integer array nums and an integer k. Your task is to partition nums into one or more non-empty contiguous segments such that in each segment, the difference between its maximum and minimum elements is at most k.

Return the total number of ways to partition nums under this condition.

Since the answer may be too large, return it modulo 109 + 7.



Example 1:

Input: nums = [9,4,1,3,7], k = 4

Output: 6

Explanation:

There are 6 valid partitions where the difference between the maximum and minimum elements in each segment is at most k = 4:

[[9], [4], [1], [3], [7]]
[[9], [4], [1], [3, 7]]
[[9], [4], [1, 3], [7]]
[[9], [4, 1], [3], [7]]
[[9], [4, 1], [3, 7]]
[[9], [4, 1, 3], [7]]
Example 2:

Input: nums = [3,3,4], k = 0

Output: 2

Explanation:

There are 2 valid partitions that satisfy the given conditions:

[[3], [3], [4]]
[[3, 3], [4]]


Constraints:

2 <= nums.length <= 5 * 104
1 <= nums[i] <= 109
0 <= k <= 109
*/

class Solution {
    public int countPartitions(int[] nums, int k) {
        final int MOD = (int) 1e9 + 7;

        int n = nums.length;
        int[] f = new int[n + 1];
        int[] g = new int[n + 1];

        f[0] = 1;
        g[0] = 1;

        Deque<Integer> maxQ = new ArrayDeque<>();
        Deque<Integer> minQ = new ArrayDeque<>();

        int l = 1;

        for (int r = 1; r <= n; r++) {
            int x = nums[r - 1];

            // maintain maxQ (decreasing)
            while (!maxQ.isEmpty() && maxQ.peekLast() < x)
                maxQ.pollLast();
            maxQ.addLast(x);

            // maintain minQ (increasing)
            while (!minQ.isEmpty() && minQ.peekLast() > x)
                minQ.pollLast();
            minQ.addLast(x);

            // shrink window while invalid
            while (maxQ.peekFirst() - minQ.peekFirst() > k) {
                int remove = nums[l - 1];

                if (maxQ.peekFirst() == remove) maxQ.pollFirst();
                if (minQ.peekFirst() == remove) minQ.pollFirst();

                l++;
            }

            f[r] = (g[r - 1] - (l >= 2 ? g[l - 2] : 0) + MOD) % MOD;
            g[r] = (g[r - 1] + f[r]) % MOD;
        }

        return f[n];
    }
}
