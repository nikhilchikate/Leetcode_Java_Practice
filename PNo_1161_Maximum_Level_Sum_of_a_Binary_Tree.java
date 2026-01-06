/*
Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.

Return the smallest level x such that the sum of all the values of nodes at level x is maximal.



Example 1:


Input: root = [1,7,0,7,-8,null,null]
Output: 2
Explanation:
Level 1 sum = 1.
Level 2 sum = 7 + 0 = 7.
Level 3 sum = 7 + -8 = -1.
So we return the level with the maximum sum which is level 2.
Example 2:

Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
Output: 2


Constraints:

The number of nodes in the tree is in the range [1, 104].
-105 <= Node.val <= 105
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {
    int deepest = 0;

    public int maxLevelSum(TreeNode root) {
        int[] levelSums = new int[10000];
        collect(root, 0, levelSums);

        int bestLevel = 0;
        int bestSum = Integer.MIN_VALUE;

        for (int i = 0; i <= deepest; ++i) {
            if (levelSums[i] > bestSum) {
                bestSum = levelSums[i];
                bestLevel = i;
            }
        }
        return bestLevel + 1;
    }

    void collect(TreeNode node, int depth, int[] levelSums) {
        if (node == null) {
            return;
        }
        levelSums[depth] += node.val;
        deepest = Math.max(deepest, depth);
        collect(node.left, depth + 1, levelSums);
        collect(node.right, depth + 1, levelSums);
    }
}