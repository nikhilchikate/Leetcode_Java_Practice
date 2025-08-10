/*
You are given an integer n. We reorder the digits in any order (including the original order) such that the leading digit is not zero.

Return true if and only if we can do this so that the resulting number is a power of two.



Example 1:

Input: n = 1
Output: true
Example 2:

Input: n = 10
Output: false


Constraints:

1 <= n <= 109
*/

class Solution {
    public boolean reorderedPowerOf2(int n) {
        String inputCount = getDigitCounts(n);
        for (int p = 1; p <= 1000000000; p <<= 1) {
            if (inputCount.equals(getDigitCounts(p))) {
                return true;
            }
        }
        return false;
    }

    private String getDigitCounts(int x) {
        char[] counts = new char[10];
        for (; x > 0; x /= 10) {
            counts[x % 10]++;
        }
        return new String(counts);
    }
}