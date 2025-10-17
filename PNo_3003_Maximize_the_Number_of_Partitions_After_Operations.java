/*
You are given a string s and an integer k.

First, you are allowed to change at most one index in s to another lowercase English letter.

After that, do the following partitioning operation until s is empty:

Choose the longest prefix of s containing at most k distinct characters.
Delete the prefix from s and increase the number of partitions by one. The remaining characters (if any) in s maintain their initial order.
Return an integer denoting the maximum number of resulting partitions after the operations by optimally choosing at most one index to change.



Example 1:

Input: s = "accca", k = 2

Output: 3

Explanation:

The optimal way is to change s[2] to something other than a and c, for example, b. then it becomes "acbca".

Then we perform the operations:

The longest prefix containing at most 2 distinct characters is "ac", we remove it and s becomes "bca".
Now The longest prefix containing at most 2 distinct characters is "bc", so we remove it and s becomes "a".
Finally, we remove "a" and s becomes empty, so the procedure ends.
Doing the operations, the string is divided into 3 partitions, so the answer is 3.

Example 2:

Input: s = "aabaab", k = 3

Output: 1

Explanation:

Initially s contains 2 distinct characters, so whichever character we change, it will contain at most 3 distinct characters, so the longest prefix with at most 3 distinct characters would always be all of it, therefore the answer is 1.

Example 3:

Input: s = "xxyz", k = 1

Output: 4

Explanation:

The optimal way is to change s[0] or s[1] to something other than characters in s, for example, to change s[0] to w.

Then s becomes "wxyz", which consists of 4 distinct characters, so as k is 1, it will divide into 4 partitions.



Constraints:

1 <= s.length <= 104
s consists only of lowercase English letters.
1 <= k <= 26
*/

class Solution {
    public int maxPartitionsAfterOperations(String S, int K) {
        if (K == 26)
            return 1;
        char[] C = S.toCharArray();
        int N = C.length;

        // L1: left_mask1 (current partition mask if no change)
        // L2: left_mask2 (mask of characters seen more than once if no change)
        // P: partitions (max partitions up to index i, without changing any character)
        int[] L1 = new int[N], L2 = new int[N];
        int[] P = new int[N];

        // Forward pass to pre-calculate partitions and masks
        int M1 = 0, M2 = 0, c = 1;
        for (int i = 0; i < N; i++) {
            int f = 1 << (C[i] - 'a');
            M2 |= M1 & f;
            M1 |= f;
            if (Integer.bitCount(M1) > K) {
                M1 = f;
                M2 = 0;
                c++;
            }
            L1[i] = M1;
            L2[i] = M2;
            P[i] = c;
        }

        int A = c; // Initialize result with the case of no changes

        // Backward pass for the single change operation
        M1 = M2 = c = 0;
        for (int i = N - 1; i >= 0; i--) {
            int f = 1 << (C[i] - 'a');
            M2 |= M1 & f;
            M1 |= f;
            if (Integer.bitCount(M1) > K) {
                M1 = f;
                M2 = 0;
                c++;
            }

            // i is the potential index where the single change occurs.
            // M1 is the mask for S[i+1...N-1] (right partition)
            // L1[i] is the mask for S[0...i] (left partition)

            if (Integer.bitCount(M1) == K) {
                // If the right partition S[i+1...N-1] already uses K chars

                // Case where the change at S[i] creates *two* new partitions
                if ((f & M2) != 0 && (f & L2[i]) != 0 && Integer.bitCount(L1[i]) == K && (L1[i] | M1) != (1 << 26) - 1)
                    A = Math.max(A, c + P[i] + 2);

                    // Case where the change at S[i] creates *one* new partition
                else if (M2 != 0)
                    A = Math.max(A, c + P[i] + 1);
            }
        }
        return A;
    }
}