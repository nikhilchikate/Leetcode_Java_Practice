/*
Given an array of positive integers nums, remove the smallest subarray (possibly empty) such that the sum of the remaining elements is divisible by p. It is not allowed to remove the whole array.

Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.

A subarray is defined as a contiguous block of elements in the array.



Example 1:

Input: nums = [3,1,4,2], p = 6
Output: 1
Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and the sum of the remaining elements is 6, which is divisible by 6.
Example 2:

Input: nums = [6,3,5,2], p = 9
Output: 2
Explanation: We cannot remove a single element to get a sum divisible by 9. The best way is to remove the subarray [5,2], leaving us with [6,3] with sum 9.
Example 3:

Input: nums = [1,2,3], p = 3
Output: 0
Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.


Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
1 <= p <= 109
*/

class Solution {
    public int minSubarray(int[] arr, int modVal) {
        long total = 0;
        for (int x : arr) {
            total += x;
        }
        if (total < modVal)
            return -1;
        long need = total % modVal;
        if (need == 0)
            return 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        total = 0;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % modVal == need)
                return 1;
            total += arr[i];
            int cur = (int) (total % modVal);
            int prev = (int) ((total - need) % modVal);
            if (map.containsKey(prev))
                best = Math.min(i - map.get(prev), best);
            map.put(cur, i);
        }
        if (best >= arr.length)
            return -1;
        return best;
    }
}