/*
You are given a 0-indexed integer array nums, where nums[i] is a digit between 0 and 9 (inclusive).

The triangular sum of nums is the value of the only element present in nums after the following process terminates:

Let nums comprise of n elements. If n == 1, end the process. Otherwise, create a new 0-indexed integer array newNums of length n - 1.
For each index i, where 0 <= i < n - 1, assign the value of newNums[i] as (nums[i] + nums[i+1]) % 10, where % denotes modulo operator.
Replace the array nums with newNums.
Repeat the entire process starting from step 1.
Return the triangular sum of nums.



Example 1:


Input: nums = [1,2,3,4,5]
Output: 8
Explanation:
The above diagram depicts the process from which we obtain the triangular sum of the array.
Example 2:

Input: nums = [5]
Output: 5
Explanation:
Since there is only one element in nums, the triangular sum is the value of that element itself.


Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 9
*/

class Solution {
    private static final int[][] C5T = bC5();

    public int triangularSum(int[] v) {
        int L = v.length;
        int M = L - 1;
        int r = 0;
        for (int i = 0; i < L; i++) {
            int r2 = m2(M, i);
            int r5 = m5(M, i);
            int r10 = crt(r2, r5);
            r = (r + r10 * v[i]) % 10;
        }
        return r;
    }

    private static int m2(int n, int k) {
        return ((k & (n - k)) == 0) ? 1 : 0;
    }

    private static int m5(int n, int k) {
        int res = 1;
        int n0 = n, k0 = k;
        while (n0 > 0 || k0 > 0) {
            int nd = n0 % 5, kd = k0 % 5;
            if (kd > nd)
                return 0;
            res = (res * C5T[nd][kd]) % 5;
            n0 /= 5;
            k0 /= 5;
        }
        return res;
    }

    private static int crt(int r2, int r5) {
        int cr = r5;
        if ((cr & 1) != r2)
            cr += 5;
        return cr;
    }

    private static int[][] bC5() {
        int[][] t = new int[5][5];
        for (int a = 0; a < 5; a++) {
            t[a][0] = t[a][a] = 1;
            for (int b = 1; b < a; b++)
                t[a][b] = (t[a - 1][b - 1] + t[a - 1][b]) % 5;
        }
        return t;
    }

    static int triangularSumSlow(int[] v) {
        int[] a = Arrays.copyOf(v, v.length);
        for (int l = a.length; l > 1; l--) {
            for (int j = 0; j < l - 1; j++)
                a[j] = (a[j] + a[j + 1]) % 10;
        }
        return a[0];
    }
}