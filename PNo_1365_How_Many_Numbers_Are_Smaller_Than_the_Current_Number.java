/*
Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it. That is, for each nums[i] you have to count the number of valid j's such that j != i and nums[j] < nums[i].

Return the answer in an array.



Example 1:

Input: nums = [8,1,2,2,3]
Output: [4,0,1,1,3]
Explanation:
For nums[0]=8 there exist four smaller numbers than it (1, 2, 2 and 3).
For nums[1]=1 does not exist any smaller number than it.
For nums[2]=2 there exist one smaller number than it (1).
For nums[3]=2 there exist one smaller number than it (1).
For nums[4]=3 there exist three smaller numbers than it (1, 2 and 2).
Example 2:

Input: nums = [6,5,4,8]
Output: [2,1,0,3]
Example 3:

Input: nums = [7,7,7,7]
Output: [0,0,0,0]


Constraints:

2 <= nums.length <= 500
0 <= nums[i] <= 100
*/

class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int n = nums.length;
        int[] count = new int[101];
        int[] res = new int[n];

        // Step 1: Count the frequency of each number in the array
        for (int num : nums) {
            count[num]++;
        }

        // Step 2: Calculate the cumulative counts of numbers smaller than each number
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1]; // count[i] now represents the number of elements < i
        }

        // Step 3: Build the result array based on the precomputed smaller counts
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                res[i] = 0; // No numbers are smaller than 0
            } else {
                res[i] = count[nums[i] - 1]; // Numbers smaller than nums[i]
            }
        }

        return res;
    }
}