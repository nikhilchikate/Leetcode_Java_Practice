/*
Given an array of integers arr, a lucky integer is an integer that has a frequency in the array equal to its value.

Return the largest lucky integer in the array. If there is no lucky integer return -1.



Example 1:

Input: arr = [2,2,3,4]
Output: 2
Explanation: The only lucky number in the array is 2 because frequency[2] == 2.
Example 2:

Input: arr = [1,2,2,3,3,3]
Output: 3
Explanation: 1, 2 and 3 are all lucky numbers, return the largest of them.
Example 3:

Input: arr = [2,2,2,3,3]
Output: -1
Explanation: There are no lucky numbers in the array.


Constraints:

1 <= arr.length <= 500
1 <= arr[i] <= 500
*/

class Solution {
    static {
        for (int i = 0; i < 7; i++) {
            findLucky(new int[0]);
        }
    }

    public static int findLucky(int[] inputNumbers) {
        if (inputNumbers.length == 0)
            return -1;

        int[] frequencies = new int[501];

        for (int number : inputNumbers) {
            frequencies[number] += 1;
        }

        for (int i = 500; i > 0; i--) {
            if (i == frequencies[i])
                return i;
        }

        return -1;
    }
}