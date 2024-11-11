/*
You are given a 0-indexed integer array nums of length n.

You can perform the following operation as many times as you want:

Pick an index i that you haven’t picked before, and pick a prime p strictly less than nums[i], then subtract p from nums[i].
Return true if you can make nums a strictly increasing array using the above operation and false otherwise.

A strictly increasing array is an array whose each element is strictly greater than its preceding element.



Example 1:

Input: nums = [4,9,6,10]
Output: true
Explanation: In the first operation: Pick i = 0 and p = 3, and then subtract 3 from nums[0], so that nums becomes [1,9,6,10].
In the second operation: i = 1, p = 7, subtract 7 from nums[1], so nums becomes equal to [1,2,6,10].
After the second operation, nums is sorted in strictly increasing order, so the answer is true.
Example 2:

Input: nums = [6,8,11,12]
Output: true
Explanation: Initially nums is sorted in strictly increasing order, so we don't need to make any operations.
Example 3:

Input: nums = [5,8,3]
Output: false
Explanation: It can be proven that there is no way to perform operations to make nums sorted in strictly increasing order, so the answer is false.


Constraints:

1 <= nums.length <= 1000
1 <= nums[i] <= 1000
nums.length == n
*/

class Solution {
    public boolean primeSubOperation(int[] nums) {
        // Find the largest element in nums
        int maxNum = findMax(nums);

        // Create sieve array for primes up to maxNum
        boolean[] primeSieve = new boolean[maxNum + 1];
        initializeSieve(primeSieve, true);
        primeSieve[1] = false; // 1 is not prime

        // Apply Sieve of Eratosthenes to mark non-primes
        for (int i = 2; i <= Math.sqrt(maxNum); i++) {
            if (primeSieve[i]) {
                for (int j = i * i; j <= maxNum; j += i) {
                    primeSieve[j] = false;
                }
            }
        }

        // Initialize current value and index
        int targetValue = 1;
        int idx = 0;

        while (idx < nums.length) {
            // Calculate the difference to make nums[idx] equal to targetValue
            int diff = nums[idx] - targetValue;

            // If the difference is negative, return false (can't make strictly increasing)
            if (diff < 0)
                return false;

            // If the difference is prime or zero, proceed
            if (primeSieve[diff] || diff == 0) {
                idx++; // Move to next element
                targetValue++; // Increment target value
            } else {
                // Otherwise, try the next targetValue
                targetValue++;
            }
        }
        return true;
    }

    // Find the maximum element in the nums array
    private int findMax(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        return max;
    }

    // Fill the sieve array with the given value
    private void initializeSieve(boolean[] sieve, boolean value) {
        for (int i = 0; i < sieve.length; i++) {
            sieve[i] = value;
        }
    }
}