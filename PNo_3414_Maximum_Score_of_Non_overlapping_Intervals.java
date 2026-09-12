/*
You are given a 2D integer array intervals, where intervals[i] = [li, ri, weighti]. Interval i starts at position li and ends at ri, and has a weight of weighti. You can choose up to 4 non-overlapping intervals. The score of the chosen intervals is defined as the total sum of their weights.

Return the lexicographically smallest array of at most 4 indices from intervals with maximum score, representing your choice of non-overlapping intervals.

Two intervals are said to be non-overlapping if they do not share any points. In particular, intervals sharing a left or right boundary are considered overlapping.

 

Example 1:

Input: intervals = [[1,3,2],[4,5,2],[1,5,5],[6,9,3],[6,7,1],[8,9,1]]

Output: [2,3]

Explanation:

You can choose the intervals with indices 2, and 3 with respective weights of 5, and 3.

Example 2:

Input: intervals = [[5,8,1],[6,7,7],[4,7,3],[9,10,6],[7,8,2],[11,14,3],[3,5,5]]

Output: [1,3,5,6]

Explanation:

You can choose the intervals with indices 1, 3, 5, and 6 with respective weights of 7, 6, 3, and 5.

 

Constraints:

1 <= intevals.length <= 5 * 104
intervals[i].length == 3
intervals[i] = [li, ri, weighti]
1 <= li <= ri <= 109
1 <= weighti <= 109
*/

class Solution {
    static class State {
        long score;
        int[] ids;

        State(long score, int[] ids) {
            this.score = score;
            this.ids = ids;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        long[][] arr = new long[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        Arrays.sort(arr, (a, b) -> {
            if (a[0] != b[0]) {
                return Long.compare(a[0], b[0]);
            }
            return Long.compare(a[1], b[1]);
        });

        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            int lo = i + 1;
            int hi = n;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;

                if (arr[mid][0] > arr[i][1]) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }

            next[i] = lo;
        }

        State[][] dp = new State[5][n + 1];

        for (int c = 0; c <= 4; c++) {
            dp[c][n] = new State(0, new int[0]);
        }

        for (int i = n - 1; i >= 0; i--) {

            dp[0][i] = new State(0, new int[0]);

            for (int c = 1; c <= 4; c++) {

                State skip = dp[c][i + 1];
                State rest = dp[c - 1][next[i]];
                long takeScore = arr[i][2] + rest.score;
                int[] takeIds = insertSorted(
                        rest.ids,
                        (int) arr[i][3]);
                State take = new State(takeScore, takeIds);
                dp[c][i] = better(skip, take);
            }
        }

        return dp[4][0].ids;
    }

    private State better(State a, State b) {
        if (a.score > b.score) {
            return a;
        }

        if (b.score > a.score) {
            return b;
        }

        if (lexSmaller(a.ids, b.ids)) {
            return a;
        }

        return b;
    }

    private boolean lexSmaller(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return a[i] < b[i];
            }
        }

        return a.length < b.length;
    }

    private int[] insertSorted(int[] arr, int value) {
        int[] result = new int[arr.length + 1];

        int i = 0;

        while (i < arr.length && arr[i] < value) {
            result[i] = arr[i];
            i++;
        }

        result[i] = value;

        while (i < arr.length) {
            result[i + 1] = arr[i];
            i++;
        }

        return result;
    }
}

