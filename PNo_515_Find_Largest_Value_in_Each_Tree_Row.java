/*
Given the root of a binary tree, return an array of the largest value in each row of the tree (0-indexed).



Example 1:


Input: root = [1,3,2,5,3,null,9]
Output: [1,3,9]
Example 2:

Input: root = [1,2,3]
Output: [1,3]


Constraints:

The number of nodes in the tree will be in the range [0, 104].
-231 <= Node.val <= 231 - 1
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    private List<Integer> res;
    public List<Integer> largestValues(TreeNode n) {
        res = new ArrayList<>();
        dfs(n, 0);
        return res;
    }

    private void dfs(TreeNode n, int d) {
        if(n != null) {
            int m = n.val;
            if(d == res.size()) {
                res.add(m);
            } else {
                res.set(d, Math.max(res.get(d), m));
            }
            dfs(n.left, d + 1);
            dfs(n.right, d + 1);
        }
    }
}