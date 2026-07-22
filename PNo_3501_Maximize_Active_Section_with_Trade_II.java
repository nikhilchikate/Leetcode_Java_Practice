/*
You are given a binary string s of length n, where:

'1' represents an active section.
'0' represents an inactive section.
You can perform at most one trade to maximize the number of active sections in s. In a trade, you:

Convert a contiguous block of '1's that is surrounded by '0's to all '0's.
Afterward, convert a contiguous block of '0's that is surrounded by '1's to all '1's.
Additionally, you are given a 2D array queries, where queries[i] = [li, ri] represents a substring s[li...ri].

For each query, determine the maximum possible number of active sections in s after making the optimal trade on the substring s[li...ri].

Return an array answer, where answer[i] is the result for queries[i].

Note

For each query, treat s[li...ri] as if it is augmented with a '1' at both ends, forming t = '1' + s[li...ri] + '1'. The augmented '1's do not contribute to the final count.
The queries are independent of each other.
 

Example 1:

Input: s = "01", queries = [[0,1]]

Output: [1]

Explanation:

Because there is no block of '1's surrounded by '0's, no valid trade is possible. The maximum number of active sections is 1.

Example 2:

Input: s = "0100", queries = [[0,3],[0,2],[1,3],[2,3]]

Output: [4,3,1,1]

Explanation:

Query [0, 3] → Substring "0100" → Augmented to "101001"
Choose "0100", convert "0100" → "0000" → "1111".
The final string without augmentation is "1111". The maximum number of active sections is 4.

Query [0, 2] → Substring "010" → Augmented to "10101"
Choose "010", convert "010" → "000" → "111".
The final string without augmentation is "1110". The maximum number of active sections is 3.

Query [1, 3] → Substring "100" → Augmented to "11001"
Because there is no block of '1's surrounded by '0's, no valid trade is possible. The maximum number of active sections is 1.

Query [2, 3] → Substring "00" → Augmented to "1001"
Because there is no block of '1's surrounded by '0's, no valid trade is possible. The maximum number of active sections is 1.

Example 3:

Input: s = "1000100", queries = [[1,5],[0,6],[0,4]]

Output: [6,7,2]

Explanation:

Query [1, 5] → Substring "00010" → Augmented to "1000101"
Choose "00010", convert "00010" → "00000" → "11111".
The final string without augmentation is "1111110". The maximum number of active sections is 6.

Query [0, 6] → Substring "1000100" → Augmented to "110001001"
Choose "000100", convert "000100" → "000000" → "111111".
The final string without augmentation is "1111111". The maximum number of active sections is 7.

Query [0, 4] → Substring "10001" → Augmented to "1100011"
Because there is no block of '1's surrounded by '0's, no valid trade is possible. The maximum number of active sections is 2.

Example 4:

Input: s = "01010", queries = [[0,3],[1,4],[1,3]]

Output: [4,4,2]

Explanation:

Query [0, 3] → Substring "0101" → Augmented to "101011"
Choose "010", convert "010" → "000" → "111".
The final string without augmentation is "11110". The maximum number of active sections is 4.

Query [1, 4] → Substring "1010" → Augmented to "110101"
Choose "010", convert "010" → "000" → "111".
The final string without augmentation is "01111". The maximum number of active sections is 4.

Query [1, 3] → Substring "101" → Augmented to "11011"
Because there is no block of '1's surrounded by '0's, no valid trade is possible. The maximum number of active sections is 2.

 

Constraints:

1 <= n == s.length <= 105
1 <= queries.length <= 105
s[i] is either '0' or '1'.
queries[i] = [li, ri]
0 <= li <= ri < n
*/

class Group {
    public int start;
    public int length;

    public Group(int start, int length) {
        this.start = start;
        this.length = length;
    }
}

class SparseTable {
    public SparseTable(int[] nums) {
        n = nums.length;
        st = new int[bitLength(n) + 1][n + 1];
        System.arraycopy(nums, 0, st[0], 0, n);
        for (int i = 1; i <= st.length; ++i)
            for (int j = 0; j + (1 << i) <= n; ++j)
                st[i][j] = Math.max(st[i - 1][j], st[i - 1][j + (1 << (i - 1))]);
    }

    // Returns max(nums[l..r])
    public int query(int l, int r) {
        final int i = bitLength(r - l + 1) - 1;
        return Math.max(st[i][l], st[i][r - (1 << i) + 1]);
    }

    private final int n;
    private final int[][] st; // st[i][j] := max(nums[j..j + 2^i - 1])

    private int bitLength(int n) {
        return Integer.SIZE - Integer.numberOfLeadingZeros(n);
    }
}

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        final int n = s.length();
        final int ones = (int) s.chars().filter(c -> c == '1').count();
        final Pair<List<Group>, int[]> zeroGroupsInfo = getZeroGroups(s);
        final List<Group> zeroGroups = zeroGroupsInfo.getKey();
        final int[] zeroGroupIndex = zeroGroupsInfo.getValue();

        if (zeroGroups.isEmpty())
            return Collections.nCopies(queries.length, ones);

        final SparseTable st = new SparseTable(getZeroMergeLengths(zeroGroups));
        final List<Integer> ans = new ArrayList<>();

        for (int[] query : queries) {
            final int l = query[0];
            final int r = query[1];
            final int left = zeroGroupIndex[l] == -1 ? -1
                    : (zeroGroups.get(zeroGroupIndex[l]).length -
                            (l - zeroGroups.get(zeroGroupIndex[l]).start));
            final int right = zeroGroupIndex[r] == -1 ? -1 : (r - zeroGroups.get(zeroGroupIndex[r]).start + 1);
            final Pair<Integer, Integer> adjacentIndices = mapToAdjacentGroupIndices(
                    zeroGroupIndex[l] + 1, s.charAt(r) == '1' ? zeroGroupIndex[r] : zeroGroupIndex[r] - 1);
            final int startAdjacentGroupIndex = adjacentIndices.getKey();
            final int endAdjacentGroupIndex = adjacentIndices.getValue();

            int activeSections = ones;
            if (s.charAt(l) == '0' && s.charAt(r) == '0' && zeroGroupIndex[l] + 1 == zeroGroupIndex[r])
                activeSections = Math.max(activeSections, ones + left + right);
            else if (startAdjacentGroupIndex <= endAdjacentGroupIndex)
                activeSections = Math.max(activeSections,
                        ones + st.query(startAdjacentGroupIndex, endAdjacentGroupIndex));
            if (s.charAt(l) == '0' &&
                    zeroGroupIndex[l] + 1 <= (s.charAt(r) == '1' ? zeroGroupIndex[r] : zeroGroupIndex[r] - 1))
                activeSections = Math.max(activeSections, ones + left + zeroGroups.get(zeroGroupIndex[l] + 1).length);
            if (s.charAt(r) == '0' && zeroGroupIndex[l] < zeroGroupIndex[r] - 1)
                activeSections = Math.max(activeSections, ones + right + zeroGroups.get(zeroGroupIndex[r] - 1).length);
            ans.add(activeSections);
        }

        return ans;
    }

    // Returns the zero groups and the index of the zero group that contains the i-th character
    private Pair<List<Group>, int[]> getZeroGroups(String s) {
        final List<Group> zeroGroups = new ArrayList<>();
        final int[] zeroGroupIndex = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0')
                    zeroGroups.get(zeroGroups.size() - 1).length++;
                else
                    zeroGroups.add(new Group(i, 1));
            }
            zeroGroupIndex[i] = zeroGroups.size() - 1;
        }

        return new Pair<>(zeroGroups, zeroGroupIndex);
    }

    // Returns the sums of the lengths of the adjacent groups
    private int[] getZeroMergeLengths(List<Group> zeroGroups) {
        final int[] zeroMergeLengths = new int[zeroGroups.size() - 1];
        for (int i = 0; i < zeroGroups.size() - 1; ++i)
            zeroMergeLengths[i] = zeroGroups.get(i).length + zeroGroups.get(i + 1).length;
        return zeroMergeLengths;
    }

    // Returns the indices of the adjacent groups that contain l and r completely
    private Pair<Integer, Integer> mapToAdjacentGroupIndices(int startGroupIndex, int endGroupIndex) {
        return new Pair<>(startGroupIndex, endGroupIndex - 1);
    }
}