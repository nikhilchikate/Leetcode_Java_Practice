/*
A magician has various spells.

You are given an array power, where each element represents the damage of a spell. Multiple spells can have the same damage value.

It is a known fact that if a magician decides to cast a spell with a damage of power[i], they cannot cast any spell with a damage of power[i] - 2, power[i] - 1, power[i] + 1, or power[i] + 2.

Each spell can be cast only once.

Return the maximum possible total damage that a magician can cast.



Example 1:

Input: power = [1,1,3,4]

Output: 6

Explanation:

The maximum possible damage of 6 is produced by casting spells 0, 1, 3 with damage 1, 1, 4.

Example 2:

Input: power = [7,1,6,6]

Output: 13

Explanation:

The maximum possible damage of 13 is produced by casting spells 1, 2, 3 with damage 1, 6, 6.



Constraints:

1 <= power.length <= 105
1 <= power[i] <= 109
*/

class Solution {
    public long maximumTotalDamage(int[] P) {
        Arrays.sort(P);

        long[] D = new long[P.length];
        long M = 0; // Max damage from non-conflicting previous groups (md in original)

        // The original logic assumes P.length > 0 based on dp[0] initialization
        D[0] = P[0];

        for (int i = 1, j = 0; i < P.length; i++) {
            if (P[i] == P[i - 1]) {
                D[i] = D[i - 1] + P[i];
            } else {
                // Advance j past all indices whose damage P[j] conflicts with P[i] (P[i] - 1 or P[i] - 2)
                while (P[j] + 2 < P[i]) {
                    M = Math.max(M, D[j]);
                    j++;
                }
                D[i] = M + P[i];
            }
        }

        long R = 0;
        for (long d : D) {
            R = Math.max(R, d);
        }
        return R;
    }
}