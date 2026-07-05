/*
You are given a square board of characters. You can move on the board starting at the bottom right square marked with the character 'S'.

You need to reach the top left square marked with the character 'E'. The rest of the squares are labeled either with a numeric character 1, 2, ..., 9 or with an obstacle 'X'. In one move you can go up, left or up-left (diagonally) only if there is no obstacle there.

Return a list of two integers: the first integer is the maximum sum of numeric characters you can collect, and the second is the number of such paths that you can take to get that maximum sum, taken modulo 10^9 + 7.

In case there is no path, return [0, 0].

 

Example 1:

Input: board = ["E23","2X2","12S"]
Output: [7,1]
Example 2:

Input: board = ["E12","1X1","21S"]
Output: [4,2]
Example 3:

Input: board = ["E11","XXX","11S"]
Output: [0,0]
 

Constraints:

2 <= board.length == board[i].length <= 100
*/

class Solution {
    int[][] cnt;
    Integer[][] dp;

    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int m = board.get(0).length();
        char[][] arr = new char[n][];
        dp = new Integer[n][m];
        cnt = new int[n][m];

        for (int i = 0; i < n; i++) {
            arr[i] = board.get(i).toCharArray();
        }
        arr[0][0] = arr[n - 1][m - 1] = '0';
        cnt[0][0] = 1;

        int max = find(n - 1, m - 1, arr, n, m);
        if (max < 0)
            return new int[] { 0, 0 };
        return new int[] { max, cnt[n - 1][m - 1] };
    }

    int inf = (int) 1e7;
    int MOD = (int) 1e9 + 7;

    int find(int i, int j, char[][] arr, int n, int m) {
        if (i == 0 && j == 0) {
            return 0;
        }
        if (dp[i][j] != null)
            return dp[i][j];

        int ans = -inf;
        int knt = 0;
        for (int[] d : dirs) {
            int x = i + d[0];
            int y = j + d[1];

            if (isValid(x, y, n, m) && arr[x][y] != 'X') {
                int res = find(x, y, arr, n, m) + arr[x][y] - '0';
                if (ans == res) {
                    knt += cnt[x][y];
                    knt %= MOD;
                } else if (res > ans) {
                    knt = cnt[x][y];
                    ans = res;
                }
            }
        }
        cnt[i][j] = knt;
        return dp[i][j] = ans;
    }

    int[][] dirs = { 
        { -1, -1 }, 
        { -1, 0 }, 
        { 0, -1 } 
        };

    boolean isValid(int i, int j, int n, int m) {
        return i >= 0 && j >= 0 && i < n && j < m;
    }
}