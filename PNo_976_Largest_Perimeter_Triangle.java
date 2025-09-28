/*
Given an integer array nums, return the largest perimeter of a triangle with a non-zero area, formed from three of these lengths. If it is impossible to form any triangle of a non-zero area, return 0.



Example 1:

Input: nums = [2,1,2]
Output: 5
Explanation: You can form a triangle with three side lengths: 1, 2, and 2.
Example 2:

Input: nums = [1,2,1,10]
Output: 0
Explanation:
You cannot use the side lengths 1, 1, and 2 to form a triangle.
You cannot use the side lengths 1, 1, and 10 to form a triangle.
You cannot use the side lengths 1, 2, and 10 to form a triangle.
As we cannot use any three side lengths to form a triangle of non-zero area, we return 0.


Constraints:

3 <= nums.length <= 104
1 <= nums[i] <= 106
*/

class Solution {
    public int largestPerimeter(int[] a) {
        int len = a.length;

        selectAndSwapMax(a, len - 1);
        selectAndSwapMax(a, len - 2);

        for (int i = len - 1; i >= 2; i--) {
            selectAndSwapMax(a, i - 2);

            if (a[i] < a[i - 1] + a[i - 2]) {
                return a[i] + a[i - 1] + a[i - 2];
            }
        }

        return 0;
    }

    private void selectAndSwapMax(int[] a, int k) {
        int maxV = a[0];
        int maxI = 0;

        for (int i = 1; i <= k; i++) {
            if (a[i] > maxV) {
                maxV = a[i];
                maxI = i;
            }
        }

        int t = a[k];
        a[k] = maxV;
        a[maxI] = t;
    }
}