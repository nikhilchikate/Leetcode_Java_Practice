/*
You are given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at endDayi.

You can attend an event i at any day d where startTimei <= d <= endTimei. You can only attend one event at any time d.

Return the maximum number of events you can attend.



Example 1:


Input: events = [[1,2],[2,3],[3,4]]
Output: 3
Explanation: You can attend all the three events.
One way to attend them all is as shown.
Attend the first event on day 1.
Attend the second event on day 2.
Attend the third event on day 3.
Example 2:

Input: events= [[1,2],[2,3],[3,4],[1,2]]
Output: 4


Constraints:

1 <= events.length <= 105
events[i].length == 2
1 <= startDayi <= endDayi <= 105
*/

class Solution {
    public int maxEvents(int[][] evts) {
        Arrays.sort(evts, Comparator.comparingInt(a -> a[1]));

        int[] par = new int[evts[evts.length - 1][1] + 2];

        for(int i = 0; i < par.length; i++) {
            par[i] = i;
        }

        int ans = 0;
        for(int[] evt : evts) {
            int slot = getPar(par, evt[0]);

            if(slot <= evt[1]) {
                ans++;
                par[slot] = getPar(par, slot + 1);
            }
        }
        return ans;
    }

    public int getPar(int[] par, int idx) {
        if(par[idx] != idx) {
            return par[idx] = getPar(par, par[idx]);
        }
        return idx;
    }
}