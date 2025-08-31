/*
Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
The '.' character indicates empty cells.



Example 1:


Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
Explanation: The input board is shown above and the only valid solution is shown below:




Constraints:

board.length == 9
board[i].length == 9
board[i][j] is a digit or '.'.
It is guaranteed that the input board has only one solution.
*/

class Solution {
    private final int[] rMask = new int[9];
    private final int[] cMask = new int[9];
    private final int[] bMask = new int[9];

    private final int[] emptyCells = new int[81];
    private int emptyCount = 0;

    public void solveSudoku(char[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val == '.') {
                    emptyCells[emptyCount++] = r * 9 + c;
                } else {
                    int digit = val - '1';
                    int bit = 1 << digit;
                    rMask[r] |= bit;
                    cMask[c] |= bit;
                    bMask[boxIdx(r, c)] |= bit;
                }
            }
        }
        backtrack(board, 0);
    }

    private boolean backtrack(char[][] board, int k) {
        if (k == emptyCount)
            return true;

        int bestIndex = k;
        int bestChoicesMask = 0;
        int bestChoicesCount = 10;

        for (int i = k; i < emptyCount; i++) {
            int pos = emptyCells[i];
            int r = pos / 9;
            int c = pos % 9;
            int b = boxIdx(r, c);
            int usedBits = rMask[r] | cMask[c] | bMask[b];
            int choices = (~usedBits) & 0x1FF;
            int count = Integer.bitCount(choices);
            if (count < bestChoicesCount) {
                bestChoicesCount = count;
                bestChoicesMask = choices;
                bestIndex = i;
                if (count == 1)
                    break;
            }
            if (count == 0)
                return false;
        }

        swap(emptyCells, k, bestIndex);

        int pos = emptyCells[k];
        int r = pos / 9;
        int c = pos % 9;
        int b = boxIdx(r, c);
        int choices = bestChoicesMask == 0
                ? ((~(rMask[r] | cMask[c] | bMask[b])) & 0x1FF)
                : bestChoicesMask;

        while (choices != 0) {
            int bit = choices & -choices;
            int d = Integer.numberOfTrailingZeros(bit);

            set(board, r, c, b, d, bit);
            if (backtrack(board, k + 1))
                return true;
            unset(board, r, c, b, d, bit);

            choices -= bit;
        }

        swap(emptyCells, k, k);
        return false;
    }

    private void set(char[][] board, int r, int c, int b, int d, int bit) {
        board[r][c] = (char) ('1' + d);
        rMask[r] |= bit;
        cMask[c] |= bit;
        bMask[b] |= bit;
    }

    private void unset(char[][] board, int r, int c, int b, int d, int bit) {
        board[r][c] = '.';
        rMask[r] ^= bit;
        cMask[c] ^= bit;
        bMask[b] ^= bit;
    }

    private static int boxIdx(int r, int c) {
        return (r / 3) * 3 + (c / 3);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}