/*
You are given an integer array nums of length n.

You start at index 0, and your goal is to reach index n - 1.

From any index i, you may perform one of the following operations:

Adjacent Step: Jump to index i + 1 or i - 1, if the index is within bounds.
Prime Teleportation: If nums[i] is a prime number p, you may instantly jump to any index j != i such that nums[j] % p == 0.
Return the minimum number of jumps required to reach index n - 1.

 

Example 1:

Input: nums = [1,2,4,6]

Output: 2

Explanation:

One optimal sequence of jumps is:

Start at index i = 0. Take an adjacent step to index 1.
At index i = 1, nums[1] = 2 is a prime number. Therefore, we teleport to index i = 3 as nums[3] = 6 is divisible by 2.
Thus, the answer is 2.

Example 2:

Input: nums = [2,3,4,7,9]

Output: 2

Explanation:

One optimal sequence of jumps is:

Start at index i = 0. Take an adjacent step to index i = 1.
At index i = 1, nums[1] = 3 is a prime number. Therefore, we teleport to index i = 4 since nums[4] = 9 is divisible by 3.
Thus, the answer is 2.

Example 3:

Input: nums = [4,6,5,8]

Output: 3

Explanation:

Since no teleportation is possible, we move through 0 → 1 → 2 → 3. Thus, the answer is 3.
 

Constraints:

1 <= n == nums.length <= 105
1 <= nums[i] <= 106
*/

import java.util.Arrays;

class Solution {
    public int minJumps(int[] data) {
        int len = data.length;
        if (len <= 1) return 0;

        int limit = 0;
        for (int val : data) {
            if (val > limit) {
                limit = val;
            }
        }

        boolean[] notPrime = new boolean[limit + 1];
        if (limit >= 0) notPrime[0] = true;
        if (limit >= 1) notPrime[1] = true;
        for (int i = 2; i * i <= limit; i++) {
            if (!notPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    notPrime[j] = true;
                }
            }
        }

        int[] firstIdx = new int[limit + 1];
        Arrays.fill(firstIdx, -1);
        int[] linkedNext = new int[len];
        for (int i = len - 1; i >= 0; i--) {
            linkedNext[i] = firstIdx[data[i]];
            firstIdx[data[i]] = i;
        }

        int[] stepCount = new int[len];
        Arrays.fill(stepCount, -1);
        int[] queue = new int[len];
        int headPtr = 0, tailPtr = 0;

        queue[tailPtr++] = 0;
        stepCount[0] = 0;

        boolean[] visitedFactor = new boolean[limit + 1];

        while (headPtr < tailPtr) {
            int curr = queue[headPtr++];
            
            if (curr == len - 1) return stepCount[curr];

            if (curr - 1 >= 0 && stepCount[curr - 1] == -1) {
                stepCount[curr - 1] = stepCount[curr] + 1;
                if (curr - 1 == len - 1) return stepCount[curr - 1];
                queue[tailPtr++] = curr - 1;
            }

            if (curr + 1 < len && stepCount[curr + 1] == -1) {
                stepCount[curr + 1] = stepCount[curr] + 1;
                if (curr + 1 == len - 1) return stepCount[curr + 1];
                queue[tailPtr++] = curr + 1;
            }

            int currentVal = data[curr];
            if (!notPrime[currentVal] && !visitedFactor[currentVal]) {
                visitedFactor[currentVal] = true;
                
                for (int multiple = currentVal; multiple <= limit; multiple += currentVal) {
                    int targetIdx = firstIdx[multiple];
                    while (targetIdx != -1) {
                        if (stepCount[targetIdx] == -1) {
                            stepCount[targetIdx] = stepCount[curr] + 1;
                            if (targetIdx == len - 1) return stepCount[targetIdx];
                            queue[tailPtr++] = targetIdx;
                        }
                        targetIdx = linkedNext[targetIdx];
                    }
                    firstIdx[multiple] = -1; 
                }
            }
        }

        return -1;
    }
}