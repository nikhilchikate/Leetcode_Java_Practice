/*
You are given an integer array nums.

A subarray is called balanced if the number of distinct even numbers in the subarray is equal to the number of distinct odd numbers.

Return the length of the longest balanced subarray.



Example 1:

Input: nums = [2,5,4,3]

Output: 4

Explanation:

The longest balanced subarray is [2, 5, 4, 3].
It has 2 distinct even numbers [2, 4] and 2 distinct odd numbers [5, 3]. Thus, the answer is 4.
Example 2:

Input: nums = [3,2,2,5,4]

Output: 5

Explanation:

The longest balanced subarray is [3, 2, 2, 5, 4].
It has 2 distinct even numbers [2, 4] and 2 distinct odd numbers [3, 5]. Thus, the answer is 5.
Example 3:

Input: nums = [1,2,3,2]

Output: 3

Explanation:

The longest balanced subarray is [2, 3, 2].
It has 1 distinct even number [2] and 1 distinct odd number [3]. Thus, the answer is 3.


Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 105
*/

class Solution {
    public int longestBalanced(int[] nums) {
        return CompletableFuture.supplyAsync(
                        () -> Optional.of(new Object[]{new int[3][4 * nums.length], new HashMap<Integer, Integer>(), null})
                                .map(s -> {
                                    BiFunction<Object, int[], Integer> f = (g, a) -> a[0] == 0
                                            ? (((int[][]) s[0])[2][a[1]] == 0
                                            ? 0
                                            : (((int[][]) s[0])[0][a[1]] += ((int[][]) s[0])[2][a[1]]) * 0
                                            + (((int[][]) s[0])[1][a[1]] += ((int[][]) s[0])[2][a[1]]) * 0
                                            + (a[2] == a[3]
                                            ? 0
                                            : (((int[][]) s[0])[2][2
                                            * a[1]] += ((int[][]) s[0])[2][a[1]]) * 0
                                            + (((int[][]) s[0])[2][2 * a[1]
                                            + 1] += ((int[][]) s[0])[2][a[1]]) * 0)
                                            + (((int[][]) s[0])[2][a[1]] = 0) * 0)
                                            : a[0] == 1
                                            ? ((BiFunction<Object, int[], Integer>) g)
                                            .apply(g, new int[]{0, a[1], a[2], a[3]}) * 0
                                            + (a[2] > a[5] || a[3] < a[4]
                                            ? 0
                                            : a[4] <= a[2] && a[3] <= a[5]
                                            ? (((int[][]) s[0])[2][a[1]] += a[6]) * 0
                                            + ((BiFunction<Object, int[], Integer>) g)
                                            .apply(g,
                                                    new int[]{0, a[1], a[2],
                                                            a[3]})
                                            : ((BiFunction<Object, int[], Integer>) g)
                                            .apply(g,
                                                    new int[]{1, 2 * a[1], a[2],
                                                            (a[2] + a[3]) / 2, a[4],
                                                            a[5], a[6]})
                                            * 0
                                            + ((BiFunction<Object, int[], Integer>) g)
                                            .apply(g,
                                                    new int[]{1, 2 * a[1] + 1,
                                                            (a[2] + a[3]) / 2
                                                                    + 1,
                                                            a[3], a[4], a[5],
                                                            a[6]})
                                            * 0
                                            + (((int[][]) s[0])[0][a[1]] = Math.min(
                                            ((int[][]) s[0])[0][2 * a[1]],
                                            ((int[][]) s[0])[0][2 * a[1] + 1]))
                                            * 0
                                            + (((int[][]) s[0])[1][a[1]] = Math.max(
                                            ((int[][]) s[0])[1][2 * a[1]],
                                            ((int[][]) s[0])[1][2 * a[1] + 1]))
                                            * 0)
                                            : ((BiFunction<Object, int[], Integer>) g)
                                            .apply(g, new int[]{0, a[1], a[2], a[3]}) * 0
                                            + (((int[][]) s[0])[0][a[1]] > 0 || ((int[][]) s[0])[1][a[1]] < 0
                                            ? -1
                                            : a[2] == a[3]
                                            ? (((int[][]) s[0])[0][a[1]] == 0 ? a[2] : -1)
                                            : Optional
                                            .of(((BiFunction<Object, int[], Integer>) g)
                                                    .apply(g, new int[]{2, 2 * a[1],
                                                            a[2], (a[2] + a[3]) / 2}))
                                            .map(r -> r != -1
                                                    ? r
                                                    : ((BiFunction<Object, int[], Integer>) g)
                                                    .apply(g, new int[]{2,
                                                            2 * a[1] + 1,
                                                            (a[2] + a[3]) / 2
                                                                    + 1,
                                                            a[3]}))
                                            .get());
                                    s[2] = f;
                                    return s;
                                })
                                .map(st0 -> {
                                    Object[] st = (Object[]) st0;
                                    return IntStream.range(0, nums.length).boxed().reduce(
                                            0,
                                            (mx, i) -> {
                                                int v = nums[i] % 2 == 0 ? 1 : -1;
                                                if (((Map<?, ?>) st[1]).containsKey(nums[i])) {
                                                    ((BiFunction<Object, int[], Integer>) st[2])
                                                            .apply(st[2], new int[]{1, 1, 0, nums.length - 1, 0,
                                                                    (int) ((Map<?, ?>) st[1]).get(nums[i]), -v});
                                                }
                                                ((BiFunction<Object, int[], Integer>) st[2])
                                                        .apply(st[2], new int[]{1, 1, 0, nums.length - 1, 0, i, v});
                                                ((Map<Integer, Integer>) st[1]).put(nums[i], i);
                                                int l = ((BiFunction<Object, int[], Integer>) st[2])
                                                        .apply(st[2], new int[]{2, 1, 0, nums.length - 1});
                                                return l != -1 && l <= i ? Math.max(mx, i - l + 1) : mx;
                                            },
                                            (a, b) -> b);
                                })
                                .orElse(0))
                .join();
    }
}

// class Solution {

//     static class Node {
//         int l, r;
//         int mn, mx;
//         int lazy;
//     }

//     static class SegmentTree {
//         Node[] t;

//         SegmentTree(int n) {
//             t = new Node[n << 2];
//             for (int i = 0; i < t.length; i++)
//                 t[i] = new Node();
//             build(1, 0, n);
//         }

//         void build(int i, int l, int r) {
//             t[i].l = l;
//             t[i].r = r;
//             t[i].mn = t[i].mx = 0;
//             t[i].lazy = 0;
//             if (l == r)
//                 return;
//             int m = (l + r) >> 1;
//             build(i << 1, l, m);
//             build(i << 1 | 1, m + 1, r);
//         }

//         void add(int i, int l, int r, int v) {
//             if (t[i].l >= l && t[i].r <= r) {
//                 apply(i, v);
//                 return;
//             }
//             pushDown(i);
//             int m = (t[i].l + t[i].r) >> 1;
//             if (l <= m)
//                 add(i << 1, l, r, v);
//             if (r > m)
//                 add(i << 1 | 1, l, r, v);
//             pushUp(i);
//         }

//         int find(int i, int v) {
//             if (t[i].l == t[i].r)
//                 return t[i].l;
//             pushDown(i);
//             int lc = i << 1, rc = i << 1 | 1;
//             if (t[lc].mn <= v && v <= t[lc].mx)
//                 return find(lc, v);
//             return find(rc, v);
//         }

//         void apply(int i, int v) {
//             t[i].mn += v;
//             t[i].mx += v;
//             t[i].lazy += v;
//         }

//         void pushUp(int i) {
//             t[i].mn = Math.min(t[i << 1].mn, t[i << 1 | 1].mn);
//             t[i].mx = Math.max(t[i << 1].mx, t[i << 1 | 1].mx);
//         }

//         void pushDown(int i) {
//             if (t[i].lazy != 0) {
//                 apply(i << 1, t[i].lazy);
//                 apply(i << 1 | 1, t[i].lazy);
//                 t[i].lazy = 0;
//             }
//         }
//     }

//     public int longestBalanced(int[] nums) {
//         int n = nums.length;
//         SegmentTree st = new SegmentTree(n);
//         Map<Integer, Integer> lastPos = new HashMap<>();
//         int cur = 0, res = 0;

//         for (int i = 1; i <= n; i++) {
//             int x = nums[i - 1];
//             int d = (x & 1) == 1 ? 1 : -1;

//             if (lastPos.containsKey(x)) {
//                 st.add(1, lastPos.get(x), n, -d);
//                 cur -= d;
//             }

//             lastPos.put(x, i);
//             st.add(1, i, n, d);
//             cur += d;

//             int p = st.find(1, cur);
//             res = Math.max(res, i - p);
//         }

//         return res;
//     }
// }