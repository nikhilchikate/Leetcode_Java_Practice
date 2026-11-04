/*
You are given an integer array nums and two integers, x and k. You can perform the following operation any number of times (including zero):

Increase or decrease any element of nums by 1.
Return the minimum number of operations needed to have at least k non-overlapping subarrays of size exactly x in nums, where all elements within each subarray are equal.

 

Example 1:

Input: nums = [5,-2,1,3,7,3,6,4,-1], x = 3, k = 2

Output: 8

Explanation:

Use 3 operations to add 3 to nums[1] and use 2 operations to subtract 2 from nums[3]. The resulting array is [5, 1, 1, 1, 7, 3, 6, 4, -1].
Use 1 operation to add 1 to nums[5] and use 2 operations to subtract 2 from nums[6]. The resulting array is [5, 1, 1, 1, 7, 4, 4, 4, -1].
Now, all elements within each subarray [1, 1, 1] (from indices 1 to 3) and [4, 4, 4] (from indices 5 to 7) are equal. Since 8 total operations were used, 8 is the output.
Example 2:

Input: nums = [9,-2,-2,-2,1,5], x = 2, k = 2

Output: 3

Explanation:

Use 3 operations to subtract 3 from nums[4]. The resulting array is [9, -2, -2, -2, -2, 5].
Now, all elements within each subarray [-2, -2] (from indices 1 to 2) and [-2, -2] (from indices 3 to 4) are equal. Since 3 operations were used, 3 is the output.
 

Constraints:

2 <= nums.length <= 105
-106 <= nums[i] <= 106
2 <= x <= nums.length
1 <= k <= 15
2 <= k * x <= nums.length
*/

class Solution {
    int size;
    long[] cntTree;
    long[] sumTree;

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int m = 0;

        for (int i = 0; i < n; i++) {
            if (m == 0 || sorted[m - 1] != sorted[i]) {
                sorted[m++] = sorted[i];
            }
        }

        int[] vals = Arrays.copyOf(sorted, m);
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = Arrays.binarySearch(vals, nums[i]) + 1;
        }

        size = m;
        cntTree = new long[m + 1];
        sumTree = new long[m + 1];
        int windows = n - x + 1;
        long[] cost = new long[windows];
        int half = (x + 1) / 2;
        long total = 0;

        for (int i = 0; i < n; i++) {
            add(rank[i], 1, nums[i]);
            total += nums[i];
            if (i >= x) {
                add(rank[i - x], -1, -nums[i - x]);
                total -= nums[i - x];
            }
            if (i >= x - 1) {
                int pos = 0;
                long cntBelow = 0;
                long sumBelow = 0;
                int step = Integer.highestOneBit(m);
                while (step > 0) {
                    int nxt = pos + step;
                    if (nxt <= m && cntBelow + cntTree[nxt] < half) {
                        pos = nxt;
                        cntBelow += cntTree[nxt];
                        sumBelow += sumTree[nxt];
                    }
                    step >>= 1;
                }
                long med = vals[pos];
                long leftCnt = half;
                long leftSum = sumBelow + (half - cntBelow) * med;
                long rightCnt = x - leftCnt;
                long rightSum = total - leftSum;
                cost[i - x + 1] = med * leftCnt - leftSum + rightSum - med * rightCnt;
            }
        }

        long inf = Long.MAX_VALUE / 4;
        long[] prev = new long[n + 1];
        long[] cur = new long[n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(cur, 0, n + 1, inf);
            for (int i = j * x; i <= n; i++) {
                long take = (j == 1 ? 0 : prev[i - x]) + cost[i - x];
                cur[i] = Math.min(cur[i - 1], take);
            }
            long[] tmp = prev;
            prev = cur;
            cur = tmp;
        }
        
        return prev[n];
    }

    void add(int i, int c, long v) {
        for (; i <= size; i += i & -i) {
            cntTree[i] += c;
            sumTree[i] += v;
        }
    }
}