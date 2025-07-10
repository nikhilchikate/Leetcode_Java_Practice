/*
You are given an integer eventTime denoting the duration of an event. You are also given two integer arrays startTime and endTime, each of length n.

These represent the start and end times of n non-overlapping meetings that occur during the event between time t = 0 and time t = eventTime, where the ith meeting occurs during the time [startTime[i], endTime[i]].

You can reschedule at most one meeting by moving its start time while maintaining the same duration, such that the meetings remain non-overlapping, to maximize the longest continuous period of free time during the event.

Return the maximum amount of free time possible after rearranging the meetings.

Note that the meetings can not be rescheduled to a time outside the event and they should remain non-overlapping.

Note: In this version, it is valid for the relative ordering of the meetings to change after rescheduling one meeting.



Example 1:

Input: eventTime = 5, startTime = [1,3], endTime = [2,5]

Output: 2

Explanation:



Reschedule the meeting at [1, 2] to [2, 3], leaving no meetings during the time [0, 2].

Example 2:

Input: eventTime = 10, startTime = [0,7,9], endTime = [1,8,10]

Output: 7

Explanation:



Reschedule the meeting at [0, 1] to [8, 9], leaving no meetings during the time [0, 7].

Example 3:

Input: eventTime = 10, startTime = [0,3,7,9], endTime = [1,4,8,10]

Output: 6

Explanation:



Reschedule the meeting at [3, 4] to [8, 9], leaving no meetings during the time [1, 7].

Example 4:

Input: eventTime = 5, startTime = [0,1,2,3,4], endTime = [1,2,3,4,5]

Output: 0

Explanation:

There is no time during the event not occupied by meetings.



Constraints:

1 <= eventTime <= 109
n == startTime.length == endTime.length
2 <= n <= 105
0 <= startTime[i] < endTime[i] <= eventTime
endTime[i] <= startTime[i + 1] where i lies in the range [0, n - 2].
*/

class Solution {
    public int maxFreeTime(int dur, int[] starts, int[] ends) {
        int len = starts.length;

        int[] gaps = new int[len + 1];
        int prevEnd = 0;
        for (int i = 0; i < len; ++i) {
            int gap = starts[i] - prevEnd;
            gaps[i] = gap;
            prevEnd = ends[i];
        }

        gaps[len] = dur - ends[len - 1];

        int[] prefMax = new int[len];
        int[] suffMax = new int[len];

        prefMax[0] = gaps[0];
        for (int i = 1; i < len; ++i) {
            prefMax[i] = Math.max(prefMax[i - 1], gaps[i]);
        }

        suffMax[len - 1] = gaps[len];
        for (int i = len - 2; i >= 0; --i) {
            suffMax[i] = Math.max(suffMax[i + 1], gaps[i + 1]);
        }

        int ans = 0;
        for (int i = 0; i < len; ++i) {
            int curr = gaps[i] + gaps[i + 1];

            int meetDur = ends[i] - starts[i];

            boolean canMove = false;
            if (i - 1 >= 0) {
                canMove = prefMax[i - 1] >= meetDur;
            }

            if (i + 1 < len) {
                canMove = canMove || suffMax[i + 1] >= meetDur;
            }

            if (canMove) {
                curr += meetDur;
            }

            ans = Math.max(ans, curr);
        }

        return ans;
    }
}