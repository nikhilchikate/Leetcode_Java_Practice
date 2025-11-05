/*
You are given an array nums of n integers and two integers k and x.

The x-sum of an array is calculated by the following procedure:

Count the occurrences of all elements in the array.
Keep only the occurrences of the top x most frequent elements. If two elements have the same number of occurrences, the element with the bigger value is considered more frequent.
Calculate the sum of the resulting array.
Note that if an array has less than x distinct elements, its x-sum is the sum of the array.

Return an integer array answer of length n - k + 1 where answer[i] is the x-sum of the subarray nums[i..i + k - 1].



Example 1:

Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

Output: [6,10,12]

Explanation:

For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
Example 2:

Input: nums = [3,8,7,8,7,5], k = 2, x = 2

Output: [11,15,15,15,12]

Explanation:

Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].



Constraints:

nums.length == n
1 <= n <= 105
1 <= nums[i] <= 109
1 <= x <= k <= nums.length
*/

class Solution {
    private int X;
    private long sum = 0L;
    private Map<Integer, Integer> freq;
    private final TreeSet<int[]> active = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
    private final TreeSet<int[]> inactive = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

    public long[] findXSum(int[] Arr, int K, int XVal) {
        int N = Arr.length;
        this.X = XVal;
        freq = new HashMap<>(N);
        long[] Res = new long[N - K + 1];

        for (int i = 0; i < N; i++) {
            int Cnt = freq.merge(Arr[i], 1, Integer::sum);
            remove(Cnt - 1, Arr[i]);
            add(Cnt, Arr[i]);

            if (i + 1 >= K) {
                Res[i - K + 1] = sum;

                int startIdx = i - K + 1;
                Cnt = freq.merge(Arr[startIdx], -1, Integer::sum);
                remove(Cnt + 1, Arr[startIdx]);
                add(Cnt, Arr[startIdx]);
            }
        }
        return Res;
    }

    private void add(int Cnt, int Val) {
        if (Cnt == 0) return;
        int[] Pair = new int[] { Cnt, Val };

        if (active.size() < X) {
            active.add(Pair);
            sum += (long) Cnt * Val;
            return;
        }

        int[] MinPair = active.first();
        if (MinPair[0] > Cnt || (MinPair[0] == Cnt && MinPair[1] >= Val)) {
            inactive.add(Pair);
            return;
        }

        sum += (long) Cnt * Val - (long) MinPair[0] * MinPair[1];
        inactive.add(active.pollFirst());
        active.add(Pair);
    }

    private void remove(int Cnt, int Val) {
        if (Cnt == 0) return;
        int[] Pair = new int[] { Cnt, Val };

        if (inactive.contains(Pair)) {
            inactive.remove(Pair);
            return;
        }

        active.remove(Pair);
        sum -= (long) Cnt * Val;
        if (inactive.isEmpty()) return;

        int[] MaxPair = inactive.pollLast();
        sum += (long) MaxPair[0] * MaxPair[1];
        active.add(MaxPair);
    }
}