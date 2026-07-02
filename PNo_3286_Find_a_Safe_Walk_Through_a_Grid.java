/*
You are given an m x n binary matrix grid and an integer health.

You start on the upper-left corner (0, 0) and would like to get to the lower-right corner (m - 1, n - 1).

You can move up, down, left, or right from one cell to another adjacent cell as long as your health remains positive.

Cells (i, j) with grid[i][j] = 1 are considered unsafe and reduce your health by 1.

Return true if you can reach the final cell with a health value of 1 or more, and false otherwise.

 

Example 1:

Input: grid = [[0,1,0,0,0],[0,1,0,1,0],[0,0,0,1,0]], health = 1

Output: true

Explanation:

The final cell can be reached safely by walking along the gray cells below.


Example 2:

Input: grid = [[0,1,1,0,0,0],[1,0,1,0,0,0],[0,1,1,1,0,1],[0,0,1,0,1,0]], health = 3

Output: false

Explanation:

A minimum of 4 health points is needed to reach the final cell safely.


Example 3:

Input: grid = [[1,1,1],[1,0,1],[1,1,1]], health = 5

Output: true

Explanation:

The final cell can be reached safely by walking along the gray cells below.



Any path that does not go through the cell (1, 1) is unsafe since your health will drop to 0 when reaching the final cell.

 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 50
2 <= m * n
1 <= health <= m + n
grid[i][j] is either 0 or 1.
*/

class Solution {
    static int[] row = { -1, 0, 1, 0 };
    int[] queue;
    int left = 0;
    int right = 0;
    int sz = 1;
    int mn;
    int m, n;

    public boolean findSafeWalk(List<List<Integer>> gridList, int health) {
        n = gridList.size();
        m = gridList.get(0).size();
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                grid[i][j] = gridList.get(i).get(j);

        int startHealth = health - grid[0][0];
        if (startHealth <= 0)
            return false;

        int[][] bestHealth = new int[n][m];
        for (int[] row : bestHealth)
            Arrays.fill(row, -1);

        bestHealth[0][0] = startHealth;
        mn = m * n;
        queue = new int[mn];
        queue[0] = 0;

        while (sz > 0) {
            int cur = queue[left];
            int x = cur / m;
            int y = cur % m;
            int h = bestHealth[x][y];
            left = (left + 1) % mn;
            sz--;
            if (x == n - 1 && y == m - 1 && h >= 1)
                return true;

            for (int d = 0; d <= 3; d++) {
                int nx = x + row[d];
                int ny = y + row[3 - d];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m)
                    continue;

                int newHealth = h - grid[nx][ny];
                if (newHealth <= 0)
                    continue; 

                if (newHealth > bestHealth[nx][ny]) {
                    bestHealth[nx][ny] = newHealth;
                    if (grid[nx][ny] == 0) {
                        left = (left - 1 + mn) % mn;
                        queue[left] = nx * m + ny;
                        if (sz == 0)
                            right = left;
                        sz++;
                    } else {
                        right = (right + 1) % mn;
                        queue[right] = nx * m + ny;
                        sz++;
                    }
                }
            }
        }
        return false;
    }
}