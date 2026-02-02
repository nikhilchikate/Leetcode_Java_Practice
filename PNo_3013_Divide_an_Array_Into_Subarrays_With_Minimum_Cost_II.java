/*
You are given a 0-indexed array of integers nums of length n, and two positive integers k and dist.

The cost of an array is the value of its first element. For example, the cost of [1,2,3] is 1 while the cost of [3,4,1] is 3.

You need to divide nums into k disjoint contiguous subarrays, such that the difference between the starting index of the second subarray and the starting index of the kth subarray should be less than or equal to dist. In other words, if you divide nums into the subarrays nums[0..(i1 - 1)], nums[i1..(i2 - 1)], ..., nums[ik-1..(n - 1)], then ik-1 - i1 <= dist.

Return the minimum possible sum of the cost of these subarrays.



Example 1:

Input: nums = [1,3,2,6,4,2], k = 3, dist = 3
Output: 5
Explanation: The best possible way to divide nums into 3 subarrays is: [1,3], [2,6,4], and [2]. This choice is valid because ik-1 - i1 is 5 - 2 = 3 which is equal to dist. The total cost is nums[0] + nums[2] + nums[5] which is 1 + 2 + 2 = 5.
It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 5.
Example 2:

Input: nums = [10,1,2,2,2,1], k = 4, dist = 3
Output: 15
Explanation: The best possible way to divide nums into 4 subarrays is: [10], [1], [2], and [2,2,1]. This choice is valid because ik-1 - i1 is 3 - 1 = 2 which is less than dist. The total cost is nums[0] + nums[1] + nums[2] + nums[3] which is 10 + 1 + 2 + 2 = 15.
The division [10], [1], [2,2,2], and [1] is not valid, because the difference between ik-1 and i1 is 5 - 1 = 4, which is greater than dist.
It can be shown that there is no possible way to divide nums into 4 subarrays at a cost lower than 15.
Example 3:

Input: nums = [10,8,18,9], k = 3, dist = 1
Output: 36
Explanation: The best possible way to divide nums into 4 subarrays is: [10], [8], and [18,9]. This choice is valid because ik-1 - i1 is 2 - 1 = 1 which is equal to dist.The total cost is nums[0] + nums[1] + nums[2] which is 10 + 8 + 18 = 36.
The division [10], [8,18], and [9] is not valid, because the difference between ik-1 and i1 is 3 - 1 = 2, which is greater than dist.
It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 36.


Constraints:

3 <= n <= 105
1 <= nums[i] <= 109
3 <= k <= n
k - 2 <= dist <= n - 2
*/

class Solution {
    public long minimumCost(int[] values, int limit, int range) {
        int length = values.length;
        PriorityQueue<Integer> leftHeap = new PriorityQueue<>((a, b) -> b - a);
        PriorityQueue<Integer> rightHeap = new PriorityQueue<>();
        Map<Integer, Integer> lazy = new HashMap<>();
        int leftCount = 0;
        long leftSum = 0;

        long best = Long.MAX_VALUE;

        for (int pos = 1; pos < length; pos++) {
            if (pos >= range + 2) {
                int removed = values[pos - range - 1];
                if (removed < leftHeap.peek()) {
                    lazy.merge(removed, 1, Integer::sum);
                    leftCount--;
                    leftSum -= removed;
                } else if (removed == leftHeap.peek()) {
                    leftHeap.poll();
                    leftCount--;
                    leftSum -= removed;
                } else if (removed == rightHeap.peek()) {
                    rightHeap.poll();
                } else {
                    lazy.merge(removed, 1, Integer::sum);
                }
            }

            if (pos <= limit - 1 || values[pos] <= leftHeap.peek()) {
                leftHeap.offer(values[pos]);
                leftCount++;
                leftSum += values[pos];
            } else {
                rightHeap.offer(values[pos]);
            }

            if (pos > limit - 1) {
                if (leftCount < limit - 1) {
                    int moved = rightHeap.poll();
                    leftHeap.offer(moved);
                    leftCount++;
                    leftSum += moved;
                } else if (leftCount > limit - 1) {
                    int moved = leftHeap.poll();
                    leftCount--;
                    leftSum -= moved;
                    rightHeap.offer(moved);
                }
            }

            while (!leftHeap.isEmpty() && lazy.getOrDefault(leftHeap.peek(), 0) > 0) {
                int v = leftHeap.poll();
                lazy.merge(v, -1, Integer::sum);
            }
            while (!rightHeap.isEmpty() && lazy.getOrDefault(rightHeap.peek(), 0) > 0) {
                int v = rightHeap.poll();
                lazy.merge(v, -1, Integer::sum);
            }

            if (pos >= range + 1) {
                best = Math.min(best, leftSum);
            }
        }

        return best + values[0];
    }
}