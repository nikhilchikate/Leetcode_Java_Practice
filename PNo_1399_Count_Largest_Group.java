/*
You are given an integer n.

Each number from 1 to n is grouped according to the sum of its digits.

Return the number of groups that have the largest size.



Example 1:

Input: n = 13
Output: 4
Explanation: There are 9 groups in total, they are grouped according sum of its digits of numbers from 1 to 13:
[1,10], [2,11], [3,12], [4,13], [5], [6], [7], [8], [9].
There are 4 groups with largest size.
Example 2:

Input: n = 2
Output: 2
Explanation: There are 2 groups [1], [2] of size 1.


Constraints:

1 <= n <= 104
*/

class Solution {
    private int[][] ref = { { 1 }, new int[10], new int[19], new int[28] };

    public int countLargestGroup(int n) {
        int[] dg = { 1, 0, 0, 0 };
        int[] ct = new int[37];
        int pow = 0;
        int ps = 0;

        for (int num = Math.min(n, 9999); num > 0; num /= 10) {
            ps += dg[pow++] += num % 10;
        }

        for (int d = 0; d < pow; d++) {
            int dig = dg[d];
            int prevSz = ref[d].length;
            int currSz = d > 2 || ref[d + 1][ref[d + 1].length - 1] > 0 ? 0 : ref[d + 1].length;
            int lim = Math.max(d * 9 + dig, currSz);
            ps -= dig;

            for (int i = 0, v = 0; i < lim; i++) {
                v += (i < prevSz ? ref[d][i] : 0)
                        - (i >= dig && i - dig < prevSz ? ref[d][i - dig] : 0);
                ct[ps + i] += v;

                if (currSz > 0) {
                    ref[d + 1][i] = (i > 0 && i - 1 < currSz ? ref[d + 1][i - 1] : 0)
                            + (i < prevSz ? ref[d][i] : 0)
                            - (i >= 10 && i - 10 < prevSz ? ref[d][i - 10] : 0);
                }
            }
        }

        ct[0] = 0;
        int ans = 0;
        int mx = 0;

        for (int x : ct) {
            if (x > mx) {
                mx = x;
                ans = 1;
            } else if (x == mx) {
                ans++;
            }
        }

        return ans;
    }
}