/*
Given two integer arrays, preorder and postorder where preorder is the preorder traversal of a binary tree of distinct values and postorder is the postorder traversal of the same tree, reconstruct and return the binary tree.

If there exist multiple answers, you can return any of them.



Example 1:


Input: preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
Output: [1,2,3,4,5,6,7]
Example 2:

Input: preorder = [1], postorder = [1]
Output: [1]


Constraints:

1 <= preorder.length <= 30
1 <= preorder[i] <= preorder.length
All the values of preorder are unique.
postorder.length == preorder.length
1 <= postorder[i] <= postorder.length
All the values of postorder are unique.
It is guaranteed that preorder and postorder are the preorder traversal and postorder traversal of the same binary tree.
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
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        return build(pre, post, 0, pre.length - 1, 0);
    }

    private TreeNode build(int[] pre, int[] post, int preSt, int preEnd, int postSt) {
        if (preSt > preEnd || postSt >= post.length || preSt >= pre.length) return null;
        if (preSt == preEnd) return new TreeNode(pre[preSt]);
        TreeNode root = new TreeNode(pre[preSt]);
        int leftRt = pre[preSt + 1];
        int leftCnt = 1;
        while (leftRt != post[postSt + leftCnt - 1]) leftCnt++;
        TreeNode leftNd = build(pre, post, preSt + 1, preSt + leftCnt, postSt);
        root.left = leftNd;
        TreeNode rghtNd = build(pre, post, preSt + leftCnt + 1, preEnd, postSt + leftCnt);
        root.right = rghtNd;
        return root;
    }
}