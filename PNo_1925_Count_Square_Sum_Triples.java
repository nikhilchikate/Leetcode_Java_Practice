/*
A square triple (a,b,c) is a triple where a, b, and c are integers and a2 + b2 = c2.

Given an integer n, return the number of square triples such that 1 <= a, b, c <= n.



Example 1:

Input: n = 5
Output: 2
Explanation: The square triples are (3,4,5) and (4,3,5).
Example 2:

Input: n = 10
Output: 4
Explanation: The square triples are (3,4,5), (4,3,5), (6,8,10), and (8,6,10).


Constraints:

1 <= n <= 250
*/

class Solution {
    public int countTriples(int n) {
        int ans = 0;
        int limit = n << 1;

        for (int u = 3; u * u < limit; u += 2) {
            int uu = u * u;
            for (int v = 1; v < u && uu + v * v <= limit; v += 2) {

                if (gcd(u, v) == 1) {
                    ans += limit / (uu + v * v);
                }
            }
        }
        return ans << 1;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int t = a;
            a = b % a;
            b = t;
        }
        return b;
    }
}
