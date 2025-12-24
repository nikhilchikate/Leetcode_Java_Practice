/*
You are given an array apple of size n and an array capacity of size m.

There are n packs where the ith pack contains apple[i] apples. There are m boxes as well, and the ith box has a capacity of capacity[i] apples.

Return the minimum number of boxes you need to select to redistribute these n packs of apples into boxes.

Note that, apples from the same pack can be distributed into different boxes.



Example 1:

Input: apple = [1,3,2], capacity = [4,3,1,5,2]
Output: 2
Explanation: We will use boxes with capacities 4 and 5.
It is possible to distribute the apples as the total capacity is greater than or equal to the total number of apples.
Example 2:

Input: apple = [5,5,5], capacity = [2,4,2,7]
Output: 4
Explanation: We will need to use all the boxes.


Constraints:

1 <= n == apple.length <= 50
1 <= m == capacity.length <= 50
1 <= apple[i], capacity[i] <= 50
The input is generated such that it's possible to redistribute packs of apples into boxes.
*/

class Solution {
    public int minimumBoxes(int[] arrA, int[] arrB) {
        int total = 0;
        for (int v : arrA)
            total += v;

        int max = 0;
        for (int v : arrB)
            max = Math.max(max, v);

        int[] freq = new int[max + 1];
        for (int v : arrB)
            freq[v]++;

        int cnt = 0;
        for (int i = max; i >= 0 && total > 0; --i) {
            while (freq[i]-- > 0 && total > 0) {
                total -= i;
                cnt++;
            }
        }
        return cnt;
    }
}