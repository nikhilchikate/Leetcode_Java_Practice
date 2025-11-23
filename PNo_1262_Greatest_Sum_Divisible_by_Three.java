/*
Given an integer array nums, return the maximum possible sum of elements of the array such that it is divisible by three.



Example 1:

Input: nums = [3,6,5,1,8]
Output: 18
Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
Example 2:

Input: nums = [4]
Output: 0
Explanation: Since 4 is not divisible by 3, do not pick any number.
Example 3:

Input: nums = [1,2,3,4,4]
Output: 12
Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).


Constraints:

1 <= nums.length <= 4 * 104
1 <= nums[i] <= 104
*/

class Solution {
    public int maxSumDivThree(int[] nums) {
        int total = 0;
        for (int v : nums)
            total += v;

        if (total % 3 == 0)
            return total;

        if (total % 3 == 1) {
            PairSmall p2 = new PairSmall();
            int s1 = Integer.MAX_VALUE;

            for (int v : nums) {
                if (v % 3 == 2)
                    p2.add(v);
                else if (v % 3 == 1)
                    s1 = Math.min(s1, v);
            }

            return Math.max(0, Math.max(total - p2.sum(), total - s1));
        } else {
            PairSmall p1 = new PairSmall();
            int s2 = Integer.MAX_VALUE;

            for (int v : nums) {
                if (v % 3 == 2)
                    s2 = Math.min(s2, v);
                else if (v % 3 == 1)
                    p1.add(v);
            }

            return Math.max(0, Math.max(total - p1.sum(), total - s2));
        }
    }

    public static class PairSmall {
        int x;
        int y;

        public PairSmall() {
            x = Integer.MAX_VALUE;
            y = Integer.MAX_VALUE;
        }

        public void add(int v) {
            if (v <= x) {
                y = x;
                x = v;
            } else if (v < y) {
                y = v;
            }
        }

        public int sum() {
            if (x == Integer.MAX_VALUE || y == Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            return x + y;
        }
    }
}
