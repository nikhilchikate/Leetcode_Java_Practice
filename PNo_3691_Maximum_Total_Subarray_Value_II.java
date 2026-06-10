/*
You are given an integer array nums of length n and an integer k.

You must select exactly k distinct non-empty subarrays nums[l..r] of nums. Subarrays may overlap, but the exact same subarray (same l and r) cannot be chosen more than once.

The value of a subarray nums[l..r] is defined as: max(nums[l..r]) - min(nums[l..r]).

The total value is the sum of the values of all chosen subarrays.

Return the maximum possible total value you can achieve.

 

Example 1:

Input: nums = [1,3,2], k = 2

Output: 4

Explanation:

One optimal approach is:

Choose nums[0..1] = [1, 3]. The maximum is 3 and the minimum is 1, giving a value of 3 - 1 = 2.
Choose nums[0..2] = [1, 3, 2]. The maximum is still 3 and the minimum is still 1, so the value is also 3 - 1 = 2.
Adding these gives 2 + 2 = 4.

Example 2:

Input: nums = [4,2,5,1], k = 3

Output: 12

Explanation:

One optimal approach is:

Choose nums[0..3] = [4, 2, 5, 1]. The maximum is 5 and the minimum is 1, giving a value of 5 - 1 = 4.
Choose nums[1..3] = [2, 5, 1]. The maximum is 5 and the minimum is 1, so the value is also 4.
Choose nums[2..3] = [5, 1]. The maximum is 5 and the minimum is 1, so the value is again 4.
Adding these gives 4 + 4 + 4 = 12.

 

Constraints:

1 <= n == nums.length <= 5 * 10​​​​​​​4
0 <= nums[i] <= 109
1 <= k <= min(105, n * (n + 1) / 2)
 */

class Solution {
    public long maxTotalValue(int[] nums, int k) {
        int n = nums.length;
        SparseTable table = new SparseTable(nums);
        PriorityQueue<int[]> pq = new PriorityQueue<>(n, (a, b) -> b[0] - a[0]);
        long sum = 0;

        for (int i = 0; i < n; i++)
            pq.offer(new int[] { table.query(i, n - 1), i, n - 1 });

        for (int i = 0; i < k; i++) {
            int[] current = pq.poll();
            int a = current[1], b = current[2];
            sum += current[0];
            if (a < b) {
                current[2] = b - 1;
                current[0] = table.query(a, b - 1);
                pq.offer(current);
            }
        }
        return sum;
    }
}

class SparseTable {
    private final int[] pow;
    private final int[][] table1, table2;

    public SparseTable(int[] arr) {
        int n = arr.length;
        this.pow = new int[n + 1];
        for (int i = 2; i <= n; i++)
            pow[i] = pow[i >> 1] + 1;
        int max = pow[n];
        this.table1 = new int[max + 1][n];
        this.table2 = new int[max + 1][n];
        this.table1[0] = this.table2[0] = arr;
        for (int p = 1; p <= max; p++) {
            int len = n - (1 << p), val = 1 << (p - 1);
            for (int i = 0; i <= len; i++) {
                table1[p][i] = Math.max(table1[p - 1][i + val], table1[p - 1][i]);
                table2[p][i] = Math.min(table2[p - 1][i + val], table2[p - 1][i]);
            }
        }
    }

    public int query(int left, int right) {
        int p = pow[right - left + 1];
        return Math.max(table1[p][right - (1 << p) + 1], table1[p][left])
                - Math.min(table2[p][right - (1 << p) + 1], table2[p][left]);
    }
}