/*
Given a set of distinct positive integers nums, return the largest subset answer such that every pair (answer[i], answer[j]) of elements in this subset satisfies:

answer[i] % answer[j] == 0, or
answer[j] % answer[i] == 0
If there are multiple solutions, return any of them.



Example 1:

Input: nums = [1,2,3]
Output: [1,2]
Explanation: [1,3] is also accepted.
Example 2:

Input: nums = [1,2,4,8]
Output: [1,2,4,8]


Constraints:

1 <= nums.length <= 1000
1 <= nums[i] <= 2 * 109
All the integers in nums are unique.
*/

class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] lens = new int[n];
        int[] prevIdx = new int[n];
        int maxEndIdx = 0;
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int maxLen = 0;
            prevIdx[i] = i;
            lens[i] = 1;
            int limit = (nums[i] + 1) / 2;
            for (int j = 0; j < i && nums[j] <= limit; j++) {
                if (nums[i] % nums[j] == 0 && lens[j] + 1 > lens[i]) {
                    lens[i] = 1 + lens[j];
                    prevIdx[i] = j;
                }
            }
            maxEndIdx = lens[i] > lens[maxEndIdx] ? i : maxEndIdx;
        }

        while (maxEndIdx != prevIdx[maxEndIdx]) {
            result.add(nums[maxEndIdx]);
            maxEndIdx = prevIdx[maxEndIdx];
        }
        result.add(nums[maxEndIdx]);
        Collections.reverse(result);
        return result;
    }
}