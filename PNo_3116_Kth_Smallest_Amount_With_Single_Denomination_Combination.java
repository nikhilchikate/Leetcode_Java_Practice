/*
You are given an integer array coins representing coins of different denominations and an integer k.

You have an infinite number of coins of each denomination. However, you are not allowed to combine coins of different denominations.

Return the kth smallest amount that can be made using these coins.

 

Example 1:

Input: coins = [3,6,9], k = 3

Output: 9

Explanation: The given coins can make the following amounts:
Coin 3 produces multiples of 3: 3, 6, 9, 12, 15, etc.
Coin 6 produces multiples of 6: 6, 12, 18, 24, etc.
Coin 9 produces multiples of 9: 9, 18, 27, 36, etc.
All of the coins combined produce: 3, 6, 9, 12, 15, etc.

Example 2:

Input: coins = [5,2], k = 7

Output: 12

Explanation: The given coins can make the following amounts:
Coin 5 produces multiples of 5: 5, 10, 15, 20, etc.
Coin 2 produces multiples of 2: 2, 4, 6, 8, 10, 12, etc.
All of the coins combined produce: 2, 4, 5, 6, 8, 10, 12, 14, 15, etc.

 

Constraints:

1 <= coins.length <= 15
1 <= coins[i] <= 25
1 <= k <= 2 * 109
coins contains pairwise distinct integers.
*/

class Solution {
    public long findKthSmallest(int[] coins, int k) {
        Arrays.sort(coins);
        List<Integer> newCoins = new ArrayList<>();

        for (int x : coins) {
            boolean flag = true;
            for (int y : newCoins) {
                if (x % y == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                newCoins.add(x);
            }
        }
        
        coins = newCoins
            .stream()
            .mapToInt(i -> i)
            .toArray();

        int n = coins.length;
        int m = 1 << n;
        int[] bitCount = new int[m];
        long[] lcm = new long[m];
        long l = k;
        long r = (long) coins[0] * k + 1;

        for (int mask = 1; mask < m; mask++) {
            bitCount[mask] = bitCount[mask >> 1] + (mask & 1);
        }

        lcm[0] = 1;
        for (int mask = 1; mask < m; mask++) {
            int preMask = mask & (mask - 1);
            int i = Integer.numberOfTrailingZeros(mask);

            long tmp = lcm[preMask] / gcd(lcm[preMask], coins[i]);
            if (tmp <= r / coins[i]) {
                lcm[mask] = tmp * coins[i];
            } else {
                lcm[mask] = r + 1;
            }
        }

        while (l < r) {
            long x = l + (r - l) / 2;
            if (count(x, m, lcm, bitCount) >= k) {
                r = x;
            } else {
                l = x + 1;
            }
        }
        return l;
    }

    private long count(long x, int m, long[] lcm, int[] bitCount) {
        long res = 0;
        for (int mask = 1; mask < m; mask++) {
            if (lcm[mask] > x) continue;

            if ((bitCount[mask] & 1) == 1) {
                res += x / lcm[mask];
            } else {
                res -= x / lcm[mask];
            }
        }
        return res;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}