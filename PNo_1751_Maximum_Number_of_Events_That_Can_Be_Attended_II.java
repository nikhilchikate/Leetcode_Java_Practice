/*
You are given an array of events where events[i] = [startDayi, endDayi, valuei]. The ith event starts at startDayi and ends at endDayi, and if you attend this event, you will receive a value of valuei. You are also given an integer k which represents the maximum number of events you can attend.

You can only attend one event at a time. If you choose to attend an event, you must attend the entire event. Note that the end day is inclusive: that is, you cannot attend two events where one of them starts and the other ends on the same day.

Return the maximum sum of values that you can receive by attending events.



Example 1:



Input: events = [[1,2,4],[3,4,3],[2,3,1]], k = 2
Output: 7
Explanation: Choose the green events, 0 and 1 (0-indexed) for a total value of 4 + 3 = 7.
Example 2:



Input: events = [[1,2,4],[3,4,3],[2,3,10]], k = 2
Output: 10
Explanation: Choose event 2 for a total value of 10.
Notice that you cannot attend any other event as they overlap, and that you do not have to attend k events.
Example 3:



Input: events = [[1,1,1],[2,2,2],[3,3,3],[4,4,4]], k = 3
Output: 9
Explanation: Although the events do not overlap, you can only attend 3 events. Pick the highest valued three.


Constraints:

1 <= k <= events.length
1 <= k * events.length <= 106
1 <= startDayi <= endDayi <= 109
1 <= valuei <= 106
*/

class Solution {
    public int maxValue(int[][] evts, int numK) {
        if (numK == 1) {
            int maxV = 0;
            for (int[] evt : evts)
                maxV = Math.max(maxV, evt[2]);
            return maxV;
        }
        Arrays.sort(evts, (a, b) -> Integer.compare(a[0], b[0]));
        int len = evts.length;
        int[][] dp = new int[len + 1][numK + 1];
        for (int i = len - 1; i >= 0; i--) {
            int idx = binSrch(evts, i + 1, len, evts[i][1]);
            for (int j = numK - 1; j >= 0; j--)
                dp[i][j] = Math.max(dp[i + 1][j], dp[idx][j + 1] + evts[i][2]);
        }
        return dp[0][0];
    }

    private static int binSrch(int[][] evts, int left, int right, int target) {
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (evts[mid][0] > target)
                right = mid;
            else
                left = mid + 1;
        }
        return left;
    }
}
