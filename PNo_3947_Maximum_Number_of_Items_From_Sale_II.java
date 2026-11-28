/*
You are given a 2D integer array items, where items[i] = [factori, pricei] represents the ith item. You are also given an integer budget.

There are unlimited copies of each item available for purchase. You may buy any number of copies of any items such that the total cost of the purchased copies is at most budget.

After buying items, you may receive free copies according to the following rules:

Each purchased copy of item i can give you at most one free copy of another item j.
The free item must satisfy i != j and factori divides factorj.
For each ordered pair (i, j), you can receive a free copy of item j from purchases of item i at most once, regardless of how many copies of item i you buy.
The same item j can be received multiple times for free if it is received from purchases of different item types.
Return the maximum total number of item copies you can obtain, including both purchased copies and free copies, while spending at most budget on purchased items.

 

Example 1:

Input: items = [[1,6],[2,4],[3,5]], budget = 19

Output: 5

Explanation:

You can buy 2 copies of item 0 and 1 copy of item 1 for a total cost of 2 * 6 + 4 = 16, which is not greater than budget = 19.
One purchased copy of item 0 gives 1 free copy of item 1, because factor0 = 1 divides factor1 = 2.
The other purchased copy of item 0 gives 1 free copy of item 2, because factor0 = 1 divides factor2 = 3.
You leave with 3 purchased copies and 2 free copies, for a total of 5 item copies.
Example 2:

Input: items = [[2,8],[1,10],[6,6],[4,12],[5,20],[5,17]], budget = 35

Output: 7

Explanation:

You can buy 2 copies of item 0, 1 copy of item 1, and 1 copy of item 2 for a total cost of 2 * 8 + 10 + 6 = 32, which is not greater than budget = 35.
One purchased copy of item 0 gives 1 free copy of item 2, because factor0 = 2 divides factor2 = 6.
The other purchased copy of item 0 gives 1 free copy of item 3, because factor0 = 2 divides factor3 = 4.
The purchased copy of item 1 gives 1 free copy of item 2, because factor1 = 1 divides factor2 = 6.
Buying item 2 gives no free copy, because factor2 = 6 does not divide the factor of any other item.
You leave with 4 purchased copies and 3 free copies, for a total of 7 item copies.
 

Constraints:

1 <= items.length <= 105
items[i] = [factori, pricei]
1 <= factori <= items.length
1 <= pricei <= 109
1 <= budget <= 109
*/

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int count[] = new int[n + 1];
        long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            count[items[i][0]]++;
            if (items[i][1] < min) {
                min = items[i][1];
            }
        }

        int temp[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (count[i] > 0) {
                int m = -1;
                for (int j = i; j <= n; j += i) {
                    m += count[j];
                }
                temp[i] = m;
            }
        }

        long[] deal = new long[n];
        int dealcount = 0;
        long limit = min * 2;
        for (int i = 0; i < n; i++) {
            int first = items[i][0];
            int second = items[i][1];
            int m = temp[first];
            if (second < limit && m > 0) {
                deal[dealcount++] = ((long) second << 32) | m;
            }
        }

        Arrays.sort(deal, 0, dealcount);
        long total = 0;
        for (int i = 0; i < dealcount; i++) {
            long deals = deal[i];
            int m = (int) (deals >> 32);
            int counts = (int) deals;
            if (budget >= m) {
                long time = Math.min((long) counts, budget / m);
                budget -= time * m;
                total += 2 * time;
            }
        }
        total += budget / min;
        return (int) total;
    }
}