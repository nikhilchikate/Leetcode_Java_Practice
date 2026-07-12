/*
Given an array of integers arr, replace each element with its rank.

The rank represents how large the element is. The rank has the following rules:

Rank is an integer starting from 1.
The larger the element, the larger the rank. If two elements are equal, their rank must be the same.
Rank should be as small as possible.
 

Example 1:

Input: arr = [40,10,20,30]
Output: [4,1,2,3]
Explanation: 40 is the largest element. 10 is the smallest. 20 is the second smallest. 30 is the third smallest.
Example 2:

Input: arr = [100,100,100]
Output: [1,1,1]
Explanation: Same elements share the same rank.
Example 3:

Input: arr = [37,12,28,9,100,56,80,5,12]
Output: [5,3,4,2,8,6,7,1,3]
 

Constraints:

0 <= arr.length <= 105
-109 <= arr[i] <= 109
*/

class Solution {
    public int[] arrayRankTransform(int[] arr) {
        int[] res = new int[arr.length];
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        // Find max and min values in arr
        for (int v : arr) {
            max = Math.max(v, max);
            min = Math.min(v, min);
        }

        // Handle case with large range and small array
        if (max - min > 200 && arr.length < 10) {
            int[] tmp = arr.clone();
            Arrays.sort(arr);
            int idx = 1;
            boolean seen = false;

            for (int i = 0; i < arr.length; i++) {
                int v = arr[i];
                seen = false;

                for (int j = 0; j < tmp.length; j++) {
                    if (tmp[j] == v) {
                        if (seen) {
                            i++; // Skip duplicates
                        }
                        res[j] = idx;
                        seen = true;
                    }
                }
                idx++;
            }
            return res;
        } else {
            // Create a rank mapping for elements within the range
            int[] nums = new int[max - min + 1];
            for (int v : arr) {
                nums[v - min] = 1; // Mark presence
            }

            int rank = 1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 1) {
                    nums[i] = rank++;
                }
            }

            // Assign ranks based on the mapping
            for (int i = 0; i < arr.length; i++) {
                res[i] = nums[arr[i] - min];
            }

            return res;
        }
    }
}