/*
You have n  tiles, where each tile has one letter tiles[i] printed on it.

Return the number of possible non-empty sequences of letters you can make using the letters printed on those tiles.



Example 1:

Input: tiles = "AAB"
Output: 8
Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
Example 2:

Input: tiles = "AAABBC"
Output: 188
Example 3:

Input: tiles = "V"
Output: 1


Constraints:

1 <= tiles.length <= 7
tiles consists of uppercase English letters.
*/

class Solution {

    public void swap(int i, int j, char[] tArr) {
        char tmp = tArr[i];
        tArr[i] = tArr[j];
        tArr[j] = tmp;
    }

    public boolean isDup(int st, int end, char ch, char[] tArr) {
        while (st <= end) {
            if (tArr[st++] == ch)
                return true;
        }
        return false;
    }

    public int permute(int idx, char[] tArr) {
        if (idx == tArr.length)
            return 0;
        int ans = 0;
        for (int i = idx; i < tArr.length; i++) {
            if (!isDup(idx, i - 1, tArr[i], tArr)) {
                swap(idx, i, tArr);
                ans += 1 + permute(idx + 1, tArr);
                swap(idx, i, tArr);
            }
        }
        return ans;
    }

    public int numTilePossibilities(String tiles) {
        char[] tArr = tiles.toCharArray();
        return permute(0, tArr);
    }
}