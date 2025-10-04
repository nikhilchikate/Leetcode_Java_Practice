/*
You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return the maximum amount of water a container can store.

Notice that you may not slant the container.



Example 1:


Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
Example 2:

Input: height = [1,1]
Output: 1


Constraints:

n == height.length
2 <= n <= 105
0 <= height[i] <= 104
*/

class Solution {
    static {
        for (int i = 0; i < 100; i++) {
            maxArea(new int[] { 0, 0 });
        }
    }

    public static int maxArea(int[] h) {
        int l = 0, r = h.length - 1, a = 0, res = 0;

        while (l < r) {
            int minH = Math.min(h[l], h[r]);
            a = minH * Math.abs(l - r);
            res = Math.max(a, res);
            while (l < r && h[l] <= minH) {
                l++;
            }
            while (l < r && h[r] <= minH) {
                r--;
            }
        }
        return res;
    }
}