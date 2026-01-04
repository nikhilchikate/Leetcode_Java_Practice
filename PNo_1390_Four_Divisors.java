/*
Given an integer array nums, return the sum of divisors of the integers in that array that have exactly four divisors. If there is no such integer in the array, return 0.



Example 1:

Input: nums = [21,4,7]
Output: 32
Explanation:
21 has 4 divisors: 1, 3, 7, 21
4 has 3 divisors: 1, 2, 4
7 has 2 divisors: 1, 7
The answer is the sum of divisors of 21 only.
Example 2:

Input: nums = [21,21]
Output: 64
Example 3:

Input: nums = [1,2,3,4,5]
Output: 0


Constraints:

1 <= nums.length <= 104
1 <= nums[i] <= 105
*/

class Solution {
    public int sumFourDivisors(int[] values) {
        int result = 0;
        for (int value : values) {
            if (value < 6) continue;

            int root = (int) Math.round(Math.cbrt(value));
            if (root * root * root == value && isPrime(root)) {
                result += 1 + root + root * root + value;
                continue;
            }

            for (int d = 2; d * d <= value; d++) {
                if (value % d == 0) {
                    int first = d;
                    int second = value / d;

                    if (first != second && isPrime(first) && isPrime(second)) {
                        result += 1 + first + second + value;
                    }
                    break;
                }
            }
        }
        return result;
    }

    private boolean isPrime(int candidate) {
        if (candidate <= 1) return false;
        if (candidate <= 3) return true;
        if (candidate % 2 == 0 || candidate % 3 == 0) return false;

        for (int step = 5; step * step <= candidate; step += 6) {
            if (candidate % step == 0 || candidate % (step + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}