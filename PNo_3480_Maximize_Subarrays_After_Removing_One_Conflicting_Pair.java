/*
You are given an integer n which represents an array nums containing the numbers from 1 to n in order. Additionally, you are given a 2D array conflictingPairs, where conflictingPairs[i] = [a, b] indicates that a and b form a conflicting pair.

Remove exactly one element from conflictingPairs. Afterward, count the number of non-empty subarrays of nums which do not contain both a and b for any remaining conflicting pair [a, b].

Return the maximum number of subarrays possible after removing exactly one conflicting pair.



Example 1:

Input: n = 4, conflictingPairs = [[2,3],[1,4]]

Output: 9

Explanation:

Remove [2, 3] from conflictingPairs. Now, conflictingPairs = [[1, 4]].
There are 9 subarrays in nums where [1, 4] do not appear together. They are [1], [2], [3], [4], [1, 2], [2, 3], [3, 4], [1, 2, 3] and [2, 3, 4].
The maximum number of subarrays we can achieve after removing one element from conflictingPairs is 9.
Example 2:

Input: n = 5, conflictingPairs = [[1,2],[2,5],[3,5]]

Output: 12

Explanation:

Remove [1, 2] from conflictingPairs. Now, conflictingPairs = [[2, 5], [3, 5]].
There are 12 subarrays in nums where [2, 5] and [3, 5] do not appear together.
The maximum number of subarrays we can achieve after removing one element from conflictingPairs is 12.


Constraints:

2 <= n <= 105
1 <= conflictingPairs.length <= 2 * n
conflictingPairs[i].length == 2
1 <= conflictingPairs[i][j] <= n
conflictingPairs[i][0] != conflictingPairs[i][1]
*/

class Solution {
    public long maxSubarrays(int n, int[][] badP) {
        int[] min1 = new int[n + 1];
        int[] min2 = new int[n + 1];

        Arrays.fill(min1, Integer.MAX_VALUE);
        Arrays.fill(min2, Integer.MAX_VALUE);

        for (int[] pair : badP) {
            int a = Math.min(pair[0], pair[1]);
            int b = Math.max(pair[0], pair[1]);
            if (min1[a] > b) {
                min2[a] = min1[a];
                min1[a] = b;
            } else if (min2[a] > b) {
                min2[a] = b;
            }
        }

        long ans = 0;
        int curMin1Idx = n;
        int curMin2Val = Integer.MAX_VALUE;
        long[] delCnt = new long[n + 1];

        for (int i = n; i >= 1; i--) {
            if (min1[curMin1Idx] > min1[i]) {
                curMin2Val = Math.min(curMin2Val, min1[curMin1Idx]);
                curMin1Idx = i;
            } else {
                curMin2Val = Math.min(curMin2Val, min1[i]);
            }
            ans += Math.min(min1[curMin1Idx], n + 1) - i;
            delCnt[curMin1Idx] += Math.min(Math.min(curMin2Val, min2[curMin1Idx]), n + 1)
                    - Math.min(min1[curMin1Idx], n + 1);
        }

        long maxDel = 0;
        for (long val : delCnt) {
            maxDel = Math.max(maxDel, val);
        }
        return ans + maxDel;
    }
}