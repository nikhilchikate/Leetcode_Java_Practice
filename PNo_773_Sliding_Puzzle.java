/*
On an 2 x 3 board, there are five tiles labeled from 1 to 5, and an empty square represented by 0. A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].

Given the puzzle board board, return the least number of moves required so that the state of the board is solved. If it is impossible for the state of the board to be solved, return -1.



Example 1:


Input: board = [[1,2,3],[4,0,5]]
Output: 1
Explanation: Swap the 0 and the 5 in one move.
Example 2:


Input: board = [[1,2,3],[5,4,0]]
Output: -1
Explanation: No number of moves will make the board solved.
Example 3:


Input: board = [[4,1,2],[5,0,3]]
Output: 5
Explanation: 5 is the smallest number of moves that solves the board.
An example path:
After move 0: [[4,1,2],[5,0,3]]
After move 1: [[4,1,2],[0,5,3]]
After move 2: [[0,1,2],[4,5,3]]
After move 3: [[1,0,2],[4,5,3]]
After move 4: [[1,2,0],[4,5,3]]
After move 5: [[1,2,3],[4,5,0]]


Constraints:

board.length == 2
board[i].length == 3
0 <= board[i][j] <= 5
Each value board[i][j] is unique.
*/

class Solution {
    private static final int ROWS = 2;
    private static final int COLS = 3;
    private static final String TARGET = "123450"; // Target state for the sliding puzzle.
    private static final int[] DIRECTIONS = { -1, 0, 1, 0, -1 }; // Direction vectors for up, left, down, right.

    public int slidingPuzzle(int[][] board) {
        // Convert the board to a string for easier manipulation
        String start = boardToString(board);
        if (!isSolvable(start)) {
            return -1;
        }

        // Priority Queue for A* search, ordered by the sum of cost and heuristic
        PriorityQueue<Pair<Integer, String>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));
        Map<String, Integer> dist = new HashMap<>();
        dist.put(start, 0);
        pq.offer(new Pair<>(computeHeuristic(start), start));

        // A* search
        while (!pq.isEmpty()) {
            String state = pq.poll().getValue();
            int steps = dist.get(state);
            if (state.equals(TARGET)) {
                return steps; // Found the solution
            }

            // Find the position of '0' in the current state
            int zeroPos = state.indexOf('0');
            int row = zeroPos / COLS;
            int col = zeroPos % COLS;
            char[] stateArray = state.toCharArray();

            // Explore the four possible directions
            for (int i = 0; i < 4; i++) {
                int newRow = row + DIRECTIONS[i];
                int newCol = col + DIRECTIONS[i + 1];
                if (isValidMove(newRow, newCol)) {
                    int newZeroPos = newRow * COLS + newCol;
                    swap(stateArray, zeroPos, newZeroPos);
                    String nextState = new String(stateArray);

                    if (!dist.containsKey(nextState) || dist.get(nextState) > steps + 1) {
                        dist.put(nextState, steps + 1);
                        pq.offer(new Pair<>(steps + 1 + computeHeuristic(nextState), nextState));
                    }
                    swap(stateArray, zeroPos, newZeroPos); // Revert the swap
                }
            }
        }

        return -1; // No solution found
    }

    // Convert board into a string representation
    private String boardToString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    // Check if the board configuration is solvable
    private boolean isSolvable(String state) {
        int inversions = 0;
        for (int i = 0; i < state.length(); i++) {
            for (int j = i + 1; j < state.length(); j++) {
                if (state.charAt(i) > state.charAt(j) && state.charAt(i) != '0' && state.charAt(j) != '0') {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }

    // Compute the heuristic value using Manhattan Distance
    private int computeHeuristic(String state) {
        int heuristic = 0;
        for (int i = 0; i < state.length(); i++) {
            char c = state.charAt(i);
            if (c == '0')
                continue; // Skip the empty space
            int targetRow = (c - '1') / COLS;
            int targetCol = (c - '1') % COLS;
            int currentRow = i / COLS;
            int currentCol = i % COLS;
            heuristic += Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
        }
        return heuristic;
    }

    // Check if the move is within bounds
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    // Swap two characters in an array
    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}