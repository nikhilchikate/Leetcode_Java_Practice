/*
Given an integer numRows, return the first numRows of Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:




Example 1:

Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
Example 2:

Input: numRows = 1
Output: [[1]]


Constraints:

1 <= numRows <= 30
*/


class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList();
        for (int i = 1; i <= numRows; i++) {
            int num = 1;
            List<Integer> val = new ArrayList();
            val.add(1);
            for (int j = 1; j < i; j++) {
                num = num * (i - j) / j;
                val.add(num);
            }
            res.add(val);
        }
        return res;
    }
}