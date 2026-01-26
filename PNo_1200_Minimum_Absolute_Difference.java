/*
Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements.

Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows

a, b are from arr
a < b
b - a equals to the minimum absolute difference of any two elements in arr


Example 1:

Input: arr = [4,2,1,3]
Output: [[1,2],[2,3],[3,4]]
Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.
Example 2:

Input: arr = [1,3,6,10,15]
Output: [[1,3]]
Example 3:

Input: arr = [3,8,-10,23,19,-4,-14,27]
Output: [[-14,-10],[19,23],[23,27]]


Constraints:

2 <= arr.length <= 105
-106 <= arr[i] <= 106
*/

class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] values) {
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        for (int element : values) {
            highest = Math.max(element, highest);
            lowest = Math.min(element, lowest);
        }

        int range = highest - lowest + 1;
        boolean[] present = new boolean[range];
        for (int element : values) {
            present[element - lowest] = true;
        }

        int pointer = 0;
        for (int idx = 0; idx < values.length; ++idx) {
            while (!present[pointer]) pointer++;
            values[idx] = pointer++ + lowest;
        }

        List<List<Integer>> output = new ArrayList<>();
        int minimumGap = Integer.MAX_VALUE;
        for (int idx = 0; idx < values.length - 1; ++idx) {
            int gap = values[idx + 1] - values[idx];
            if (gap < minimumGap) {
                output.clear();
                minimumGap = gap;
            }
            if (gap == minimumGap)
                output.add(Arrays.asList(values[idx], values[idx + 1]));
        }

        return output;
    }
}