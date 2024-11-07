/*
The bitwise AND of an array nums is the bitwise AND of all integers in nums.

For example, for nums = [1, 5, 3], the bitwise AND is equal to 1 & 5 & 3 = 1.
Also, for nums = [7], the bitwise AND is 7.
You are given an array of positive integers candidates. Evaluate the bitwise AND of every combination of numbers of candidates. Each number in candidates may only be used once in each combination.

Return the size of the largest combination of candidates with a bitwise AND greater than 0.



Example 1:

Input: candidates = [16,17,71,62,12,24,14]
Output: 4
Explanation: The combination [16,17,62,24] has a bitwise AND of 16 & 17 & 62 & 24 = 16 > 0.
The size of the combination is 4.
It can be shown that no combination with a size greater than 4 has a bitwise AND greater than 0.
Note that more than one combination may have the largest size.
For example, the combination [62,12,24,14] has a bitwise AND of 62 & 12 & 24 & 14 = 8 > 0.
Example 2:

Input: candidates = [8,8]
Output: 2
Explanation: The largest combination [8,8] has a bitwise AND of 8 & 8 = 8 > 0.
The size of the combination is 2, so we return 2.


Constraints:

1 <= candidates.length <= 105
1 <= candidates[i] <= 107
*/

class Solution {
    public int largestCombination(int[] candidates) {
        int[] bitCounts = new int[32]; // Array to store count of set bits at each bit position
        for (int num : candidates) {
            updateBitCounts(num, bitCounts); // Update bit counts for each number
        }

        int maxCount = 0; // Variable to store the maximum bit count across all positions
        for (int count : bitCounts) {
            maxCount = Math.max(maxCount, count); // Find the max value from the bit count array
        }

        return maxCount;
    }

    public void updateBitCounts(int num, int[] bitCounts) {
        int bitPos = 31; // Start from the highest bit position (31st bit)
        while (num > 0) {
            int bit = num & 1; // Extract the least significant bit (LSB)
            bitCounts[bitPos] += bit; // Increment the count at the corresponding bit position
            num >>= 1; // Right shift the number to check the next bit
            bitPos--; // Move to the next bit position
        }
    }
}