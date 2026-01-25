/*
You are given a 0-indexed integer array nums, where nums[i] represents the score of the ith student. You are also given an integer k.

Pick the scores of any k students from the array so that the difference between the highest and the lowest of the k scores is minimized.

Return the minimum possible difference.



Example 1:

Input: nums = [90], k = 1
Output: 0
Explanation: There is one way to pick score(s) of one student:
- [90]. The difference between the highest and lowest score is 90 - 90 = 0.
The minimum possible difference is 0.
Example 2:

Input: nums = [9,4,1,7], k = 2
Output: 2
Explanation: There are six ways to pick score(s) of two students:
- [9,4,1,7]. The difference between the highest and lowest score is 9 - 4 = 5.
- [9,4,1,7]. The difference between the highest and lowest score is 9 - 1 = 8.
- [9,4,1,7]. The difference between the highest and lowest score is 9 - 7 = 2.
- [9,4,1,7]. The difference between the highest and lowest score is 4 - 1 = 3.
- [9,4,1,7]. The difference between the highest and lowest score is 7 - 4 = 3.
- [9,4,1,7]. The difference between the highest and lowest score is 7 - 1 = 6.
The minimum possible difference is 2.


Constraints:

1 <= k <= nums.length <= 1000
0 <= nums[i] <= 105
*/

class Solution {
    public int minimumDifference(int[] nums, int k) {
        if (nums.length == 1 || k == 1)
            return 0;

        sort(nums, 0, nums.length - 1);

        int min = Integer.MAX_VALUE;
        int len = nums.length;

        for (int idx = k - 1; idx < len; ++idx) {
            min = Math.min(min, nums[idx] - nums[idx - k + 1]);
        }

        return min;
    }

    public void sort(int[] nums, int begin, int finish) {
        int low = begin;
        int high = finish;
        int pivotValue = nums[begin];

        while (low <= high) {
            while (low <= high && nums[low] < pivotValue) {
                low++;
            }

            while (low <= high && nums[high] > pivotValue) {
                high--;
            }

            if (low <= high) {
                int swap = nums[low];
                nums[low] = nums[high];
                nums[high] = swap;
                low++;
                high--;
            }
        }

        if (high > begin) {
            sort(nums, begin, high);
        }

        if (low < finish) {
            sort(nums, low, finish);
        }
    }
}