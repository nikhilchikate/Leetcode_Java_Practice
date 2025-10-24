/*
An integer x is numerically balanced if for every digit d in the number x, there are exactly d occurrences of that digit in x.

Given an integer n, return the smallest numerically balanced number strictly greater than n.



Example 1:

Input: n = 1
Output: 22
Explanation:
22 is numerically balanced since:
- The digit 2 occurs 2 times.
It is also the smallest numerically balanced number strictly greater than 1.
Example 2:

Input: n = 1000
Output: 1333
Explanation:
1333 is numerically balanced since:
- The digit 1 occurs 1 time.
- The digit 3 occurs 3 times.
It is also the smallest numerically balanced number strictly greater than 1000.
Note that 1022 cannot be the answer because 0 appeared more than 0 times.
Example 3:

Input: n = 3000
Output: 3133
Explanation:
3133 is numerically balanced since:
- The digit 1 occurs 1 time.
- The digit 3 occurs 3 times.
It is also the smallest numerically balanced number strictly greater than 3000.


Constraints:

0 <= n <= 106
*/

class Solution {
    private static final int MAXL = 7;
    private static List<Integer> BNums = null;

    public int nextBeautifulNumber(int N) {
        if (BNums == null)
            buildBNums();

        int idx = Collections.binarySearch(BNums, N);

        int insertionIdx;
        if (idx >= 0) {
            insertionIdx = idx + 1;
        } else {
            insertionIdx = -(idx + 1);
        }

        if (insertionIdx == BNums.size()) {
            return -1;
        }

        return BNums.get(insertionIdx);
    }

    private void buildBNums() {
        List<Integer> List = new ArrayList<>();
        int[] Cts = new int[10];
        backtrack(1, 0, Cts, List);
        Collections.sort(List);
        BNums = List;
    }

    private void backtrack(int Dig, int Len, int[] Cts, List<Integer> List) {
        if (Dig == 10) {
            if (Len == 0)
                return;

            char[] Arr = new char[Len];
            int Idx = 0;
            for (int d = 1; d <= 9; ++d) {
                for (int k = 0; k < Cts[d]; ++k) {
                    Arr[Idx++] = (char) ('0' + d);
                }
            }
            Arrays.sort(Arr);

            do {
                int Val = 0;
                for (char ch : Arr) {
                    Val = Val * 10 + (ch - '0');
                }
                List.add(Val);
            } while (nextPerm(Arr));
            return;
        }

        backtrack(Dig + 1, Len, Cts, List);

        if (Len + Dig <= MAXL) {
            Cts[Dig] = Dig;
            backtrack(Dig + 1, Len + Dig, Cts, List);
            Cts[Dig] = 0;
        }
    }

    private boolean nextPerm(char[] Arr) {
        int i = Arr.length - 2;
        while (i >= 0 && Arr[i] >= Arr[i + 1])
            i--;
        if (i < 0)
            return false;
        int j = Arr.length - 1;
        while (Arr[j] <= Arr[i])
            j--;
        swapChar(Arr, i, j);
        revChar(Arr, i + 1, Arr.length - 1);
        return true;
    }

    private void swapChar(char[] Arr, int i, int j) {
        char Temp = Arr[i];
        Arr[i] = Arr[j];
        Arr[j] = Temp;
    }

    private void revChar(char[] Arr, int i, int j) {
        while (i < j)
            swapChar(Arr, i++, j--);
    }
}