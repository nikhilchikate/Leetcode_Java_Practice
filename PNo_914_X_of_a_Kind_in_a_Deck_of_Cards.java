/*
You are given an integer array deck where deck[i] represents the number written on the ith card.

Partition the cards into one or more groups such that:

Each group has exactly x cards where x > 1, and
All the cards in one group have the same integer written on them.
Return true if such partition is possible, or false otherwise.



Example 1:

Input: deck = [1,2,3,4,4,3,2,1]
Output: true
Explanation: Possible partition [1,1],[2,2],[3,3],[4,4].
Example 2:

Input: deck = [1,1,1,2,2,2,3,3]
Output: false
Explanation: No possible partition.


Constraints:

1 <= deck.length <= 104
0 <= deck[i] < 104
*/

class Solution {
    public boolean hasGroupsSizeX(int[] cards) {
        if (cards.length < 2) return false;
        int gcd = 0;
        int freq[] = new int[100];
        for (int card : cards) {
            freq[card]++;
        }
        for (int i = 0; i < 100; i++) {
            if (freq[i] != 0) {
                gcd = findGCD(gcd, freq[i]);
            }
        }
        return (gcd > 1);
    }

    private int findGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (b > a) {
            return findGCD(b, a);
        }
        return findGCD(b, a % b);
    }
}