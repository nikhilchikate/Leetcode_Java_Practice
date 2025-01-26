/*
A company is organizing a meeting and has a list of n employees, waiting to be invited. They have arranged for a large circular table, capable of seating any number of employees.

The employees are numbered from 0 to n - 1. Each employee has a favorite person and they will attend the meeting only if they can sit next to their favorite person at the table. The favorite person of an employee is not themself.

Given a 0-indexed integer array favorite, where favorite[i] denotes the favorite person of the ith employee, return the maximum number of employees that can be invited to the meeting.



Example 1:


Input: favorite = [2,2,1,2]
Output: 3
Explanation:
The above figure shows how the company can invite employees 0, 1, and 2, and seat them at the round table.
All employees cannot be invited because employee 2 cannot sit beside employees 0, 1, and 3, simultaneously.
Note that the company can also invite employees 1, 2, and 3, and give them their desired seats.
The maximum number of employees that can be invited to the meeting is 3.
Example 2:

Input: favorite = [1,2,0]
Output: 3
Explanation:
Each employee is the favorite person of at least one other employee, and the only way the company can invite them is if they invite every employee.
The seating arrangement will be the same as that in the figure given in example 1:
- Employee 0 will sit between employees 2 and 1.
- Employee 1 will sit between employees 0 and 2.
- Employee 2 will sit between employees 1 and 0.
The maximum number of employees that can be invited to the meeting is 3.
Example 3:


Input: favorite = [3,0,1,4,1]
Output: 4
Explanation:
The above figure shows how the company will invite employees 0, 1, 3, and 4, and seat them at the round table.
Employee 2 cannot be invited because the two spots next to their favorite employee 1 are taken.
So the company leaves them out of the meeting.
The maximum number of employees that can be invited to the meeting is 4.


Constraints:

n == favorite.length
2 <= n <= 105
0 <= favorite[i] <= n - 1
favorite[i] != i
*/

class Solution {
    public static int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] depth = new int[n];
        int[] inDeg = new int[n];
        int[] queue = new int[n];

        for (int i = 0; i < n; ++i) {
            inDeg[favorite[i]]++;
        }

        int l = 0, r = 0;
        for (int i = 0; i < n; ++i) {
            if (inDeg[i] == 0)
                queue[r++] = i;
        }

        while (l < r) {
            int cur = queue[l++];
            int next = favorite[cur];
            depth[next] = Math.max(depth[next], depth[cur] + 1);
            if (--inDeg[next] == 0) {
                queue[r++] = next;
            }
        }

        int small = 0;
        int big = 0;

        for (int i = 0; i < n; ++i) {
            if (inDeg[i] != 0) {
                inDeg[i] = 0;
                int count = 1;
                for (int j = favorite[i]; j != i; j = favorite[j]) {
                    count++;
                    inDeg[j] = 0;
                }

                if (count == 2) {
                    small += depth[i] + depth[favorite[i]] + 2;
                } else {
                    big = Math.max(big, count);
                }
            }
        }
        return Math.max(big, small);
    }
}