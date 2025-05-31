/*
You are given an n x n integer matrix board where the cells are labeled from 1 to n2 in a Boustrophedon style starting from the bottom left of the board (i.e. board[n - 1][0]) and alternating direction each row.

You start on square 1 of the board. In each move, starting from square curr, do the following:

Choose a destination square next with a label in the range [curr + 1, min(curr + 6, n2)].
This choice simulates the result of a standard 6-sided die roll: i.e., there are always at most 6 destinations, regardless of the size of the board.
If next has a snake or ladder, you must move to the destination of that snake or ladder. Otherwise, you move to next.
The game ends when you reach the square n2.
A board square on row r and column c has a snake or ladder if board[r][c] != -1. The destination of that snake or ladder is board[r][c]. Squares 1 and n2 are not the starting points of any snake or ladder.

Note that you only take a snake or ladder at most once per dice roll. If the destination to a snake or ladder is the start of another snake or ladder, you do not follow the subsequent snake or ladder.

For example, suppose the board is [[-1,4],[-1,3]], and on the first move, your destination square is 2. You follow the ladder to square 3, but do not follow the subsequent ladder to 4.
Return the least number of dice rolls required to reach the square n2. If it is not possible to reach the square, return -1.



Example 1:


Input: board = [[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]
Output: 4
Explanation:
In the beginning, you start at square 1 (at row 5, column 0).
You decide to move to square 2 and must take the ladder to square 15.
You then decide to move to square 17 and must take the snake to square 13.
You then decide to move to square 14 and must take the ladder to square 35.
You then decide to move to square 36, ending the game.
This is the lowest possible number of moves to reach the last square, so return 4.
Example 2:

Input: board = [[-1,-1],[-1,3]]
Output: 1


Constraints:

n == board.length == board[i].length
2 <= n <= 20
board[i][j] is either -1 or in the range [1, n2].
The squares labeled 1 and n2 are not the starting points of any snake or ladder.
*/

class Solution {
    public int snakesAndLadders(int[][] board) {
        int boardSize = board.length;
        int targetCell = boardSize * boardSize;

        short[] flatBoard = new short[targetCell + 1];
        int currentFlatIndex = 1;

        for (int row = boardSize - 1; row >= 0; row--) {
            if ((boardSize - 1 - row) % 2 == 0) {
                for (int col = 0; col < boardSize; col++) {
                    flatBoard[currentFlatIndex++] = (short) board[row][col];
                }
            } else {
                for (int col = boardSize - 1; col >= 0; col--) {
                    flatBoard[currentFlatIndex++] = (short) board[row][col];
                }
            }
        }

        short[] bfsQueue = new short[boardSize * boardSize];
        int readPointer = 0;
        int writePointer = 0;

        bfsQueue[writePointer++] = 1;

        int[] minMoves = new int[targetCell + 1];
        minMoves[1] = 1;

        while (readPointer != writePointer) {
            int currentCell = bfsQueue[readPointer++];
            readPointer %= targetCell;

            if (currentCell + 6 >= targetCell) {
                return minMoves[currentCell];
            }

            int maxDiceRollAchieved = 0;

            for (int dice = 6; dice >= 1; dice--) {
                int nextCellCandidate = currentCell + dice;

                if (flatBoard[nextCellCandidate] != -1) {
                    nextCellCandidate = flatBoard[nextCellCandidate];
                    if (nextCellCandidate == targetCell) {
                        return minMoves[currentCell];
                    }
                } else {
                    if (dice < maxDiceRollAchieved) {
                        continue;
                    }
                    maxDiceRollAchieved = dice;
                }

                if (minMoves[nextCellCandidate] == 0) {
                    minMoves[nextCellCandidate] = minMoves[currentCell] + 1;
                    bfsQueue[writePointer++] = (short) nextCellCandidate;
                    writePointer %= targetCell;
                }
            }
        }
        return -1;
    }
}