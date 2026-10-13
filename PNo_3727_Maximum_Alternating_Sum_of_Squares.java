/*
You are given an integer array nums. You may rearrange the elements in any order.

The alternating score of an array arr is defined as:

score = arr[0]2 - arr[1]2 + arr[2]2 - arr[3]2 + ...
Return an integer denoting the maximum possible alternating score of nums after rearranging its elements.

 

Example 1:

Input: nums = [1,2,3]

Output: 12

Explanation:

A possible rearrangement for nums is [2,1,3], which gives the maximum alternating score among all possible rearrangements.

The alternating score is calculated as:

score = 22 - 12 + 32 = 4 - 1 + 9 = 12

Example 2:

Input: nums = [1,-1,2,-2,3,-3]

Output: 16

Explanation:

A possible rearrangement for nums is [-3,-1,-2,1,3,2], which gives the maximum alternating score among all possible rearrangements.

The alternating score is calculated as:

score = (-3)2 - (-1)2 + (-2)2 - (1)2 + (3)2 - (2)2 = 9 - 1 + 4 - 1 + 9 - 4 = 16

 

Constraints:

1 <= nums.length <= 105
-4 * 104 <= nums[i] <= 4 * 104
*/

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        int maxVal = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] < 0)
                nums[i] *= -1;
            maxVal = Math.max(nums[i], maxVal);
        }
        int[] freq = new int[maxVal + 1];
        for (int x : nums)
            freq[x]++;
        int l = 0, r = maxVal;
        long sum = 0;
        while (r > l) {
            while (l < r && freq[l] == 0)
                l++;
            while (l < r && freq[r] == 0)
                r--;

            int cnt = Math.min(freq[l], freq[r]);
            freq[l] -= cnt;
            freq[r] -= cnt;
            long diff = r * r - l * l;
            sum += (diff * cnt);
        }
        if ((freq[r] & 1) != 0) {
            sum += r * r;
        } else if ((freq[l] & 1) != 0) {
            sum += l * l;
        }
        return sum;
    }
}