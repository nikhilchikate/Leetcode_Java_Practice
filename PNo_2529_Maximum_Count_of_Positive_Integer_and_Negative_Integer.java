/*
Given an array nums sorted in non-decreasing order, return the maximum between the number of positive integers and the number of negative integers.

In other words, if the number of positive integers in nums is pos and the number of negative integers is neg, then return the maximum of pos and neg.
Note that 0 is neither positive nor negative.



Example 1:

Input: nums = [-2,-1,-1,1,2,3]
Output: 3
Explanation: There are 3 positive integers and 3 negative integers. The maximum count among them is 3.
Example 2:

Input: nums = [-3,-2,-1,0,0,1,2]
Output: 3
Explanation: There are 2 positive integers and 3 negative integers. The maximum count among them is 3.
Example 3:

Input: nums = [5,20,66,1314]
Output: 4
Explanation: There are 4 positive integers and 0 negative integers. The maximum count among them is 4.


Constraints:

1 <= nums.length <= 2000
-2000 <= nums[i] <= 2000
nums is sorted in a non-decreasing order.


Follow up: Can you solve the problem in O(log(n)) time complexity?
*/

class Solution {
    public static int findCount(int[] arr, int left, int right) {
        int negCnt = 0;
        int posCnt = 0;
        int zeroCnt = 0;
        while (left <= right) {
            int mid = right + (left - right) / 2;
            if (arr[mid] == 0) {
                zeroCnt++;
                if (zeroCnt == arr.length)
                    return 0;
                if (mid > 0 && arr[mid - 1] < 0) {
                    negCnt = mid;
                    left = mid + 1;
                } else if (mid < arr.length - 1 && arr[mid + 1] > 0) {
                    posCnt = arr.length - mid - 1;
                    right = mid - 1;
                } else if (arr[0] == 0 && arr[arr.length - 1] != 0) {
                    left = mid + 1;
                } else if (arr[0] != 0 && arr[arr.length - 1] == 0) {
                    right = mid - 1;
                }
            } else if (arr[mid] < 0) {
                negCnt = mid + 1;
                left = mid + 1;
            } else if (arr[mid] > 0) {
                posCnt = arr.length - mid;
                right = mid - 1;
            }
        }
        if (negCnt > posCnt)
            return negCnt;
        return posCnt;
    }

    public int maximumCount(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        left = findCount(arr, left, right);
        return left;
    }
}