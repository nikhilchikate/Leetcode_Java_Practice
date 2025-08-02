/*
You have two fruit baskets containing n fruits each. You are given two 0-indexed integer arrays basket1 and basket2 representing the cost of fruit in each basket. You want to make both baskets equal. To do so, you can use the following operation as many times as you want:

Chose two indices i and j, and swap the ith fruit of basket1 with the jth fruit of basket2.
The cost of the swap is min(basket1[i],basket2[j]).
Two baskets are considered equal if sorting them according to the fruit cost makes them exactly the same baskets.

Return the minimum cost to make both the baskets equal or -1 if impossible.



Example 1:

Input: basket1 = [4,2,2,2], basket2 = [1,4,1,2]
Output: 1
Explanation: Swap index 1 of basket1 with index 0 of basket2, which has cost 1. Now basket1 = [4,1,2,2] and basket2 = [2,4,1,2]. Rearranging both the arrays makes them equal.
Example 2:

Input: basket1 = [2,3,4,1], basket2 = [3,2,5,1]
Output: -1
Explanation: It can be shown that it is impossible to make both the baskets equal.


Constraints:

basket1.length == basket2.length
1 <= basket1.length <= 105
1 <= basket1[i],basket2[i] <= 109
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public long minCost(int[] array1, int[] array2) {
        List<int[]> counts = new ArrayList<>();
        Arrays.sort(array1);
        Arrays.sort(array2);
        int ptr1 = 0, ptr2 = 0;

        int totalToMove = 0;
        while (ptr1 < array1.length || ptr2 < array2.length) {
            if (ptr1 < array1.length && ptr2 < array2.length && array1[ptr1] == array2[ptr2]) {
                ptr1++;
                ptr2++;
                continue;
            }
            int currentVal = 0;
            int currentCount = 0;
            if ((ptr1 < array1.length && ptr2 < array2.length && array1[ptr1] < array2[ptr2])
                    || ptr2 >= array2.length) {
                currentVal = array1[ptr1];
                while (ptr1 < array1.length && array1[ptr1] == currentVal) {
                    currentCount++;
                    ptr1++;
                }
            } else {
                currentVal = array2[ptr2];
                while (ptr2 < array2.length && array2[ptr2] == currentVal) {
                    currentCount++;
                    ptr2++;
                }
            }
            if (currentCount % 2 != 0) {
                return -1;
            }
            totalToMove += currentCount / 2;
            counts.add(new int[] { currentVal, currentCount / 2 });
        }

        long totalCost = 0;
        int movedCount = 0;
        int minElement = Math.min(array1[0], array2[0]);
        int idx = 0;
        int accumulated = 0;

        while (idx < counts.size()) {
            int[] pair = counts.get(idx);
            totalCost += Math.min((long) minElement * 2, pair[0]);
            movedCount += 2;
            if (movedCount == totalToMove) {
                break;
            }
            accumulated++;
            if (accumulated == pair[1]) {
                accumulated = 0;
                idx++;
            }
        }
        return totalCost;
    }
}