/*
Given an integer array nums and an integer k, return the number of good subarrays of nums.

A subarray arr is good if there are at least k pairs of indices (i, j) such that i < j and arr[i] == arr[j].

A subarray is a contiguous non-empty sequence of elements within an array.



Example 1:

Input: nums = [1,1,1,1,1], k = 10
Output: 1
Explanation: The only good subarray is the array nums itself.
Example 2:

Input: nums = [3,1,4,3,2,2,4], k = 2
Output: 4
Explanation: There are 4 different good subarrays:
- [3,1,4,3,2,2] that has 2 pairs.
- [3,1,4,3,2,2,4] that has 3 pairs.
- [1,4,3,2,2,4] that has 2 pairs.
- [4,3,2,2,4] that has 2 pairs.


Constraints:

1 <= nums.length <= 105
1 <= nums[i], k <= 109
*/

public class Solution {
    public long countGood(int[] numbers, int targetPairs) {
        if (numbers.length < 2) {
            return 0L;
        }

        Map<Integer, Integer> freqMap = new HashMap<>(numbers.length, 0.99f);
        long goodCount = 0L;
        long currentPairs = 0L;
        int leftPtr = 0;
        int rightPtr = -1;

        while (leftPtr < numbers.length) {
            if (currentPairs < targetPairs) {
                if (++rightPtr == numbers.length) {
                    break;
                }

                Integer num = numbers[rightPtr];
                Integer frequency = freqMap.get(num);
                if (frequency == null) {
                    frequency = 1;
                } else {
                    currentPairs += frequency;
                    if (currentPairs >= targetPairs) {
                        goodCount += numbers.length - rightPtr;
                    }
                    frequency = frequency + 1;
                }
                freqMap.put(num, frequency);
            } else {
                Integer num = numbers[leftPtr++];
                int frequency = freqMap.get(num) - 1;
                if (frequency > 0) {
                    freqMap.put(num, frequency);
                    currentPairs -= frequency;
                } else {
                    freqMap.remove(num);
                }
                if (currentPairs >= targetPairs) {
                    goodCount += numbers.length - rightPtr;
                }
            }
        }

        return goodCount;
    }
}