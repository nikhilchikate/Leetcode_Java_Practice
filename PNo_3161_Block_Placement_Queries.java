/*
There exists an infinite number line, with its origin at 0 and extending towards the positive x-axis.

You are given a 2D array queries, which contains two types of queries:

For a query of type 1, queries[i] = [1, x]. Build an obstacle at distance x from the origin. It is guaranteed that there is no obstacle at distance x when the query is asked.
For a query of type 2, queries[i] = [2, x, sz]. Check if it is possible to place a block of size sz anywhere in the range [0, x] on the line, such that the block entirely lies in the range [0, x]. A block cannot be placed if it intersects with any obstacle, but it may touch it. Note that you do not actually place the block. Queries are separate.
Return a boolean array results, where results[i] is true if you can place the block specified in the ith query of type 2, and false otherwise.

 

Example 1:

Input: queries = [[1,2],[2,3,3],[2,3,1],[2,2,2]]

Output: [false,true,true]

Explanation:



For query 0, place an obstacle at x = 2. A block of size at most 2 can be placed before x = 3.

Example 2:

Input: queries = [[1,7],[2,7,6],[1,2],[2,7,5],[2,7,6]]

Output: [true,true,false]

Explanation:



Place an obstacle at x = 7 for query 0. A block of size at most 7 can be placed before x = 7.
Place an obstacle at x = 2 for query 2. Now, a block of size at most 5 can be placed before x = 7, and a block of size at most 2 before x = 2.
 

Constraints:

1 <= queries.length <= 15 * 104
2 <= queries[i].length <= 3
1 <= queries[i][0] <= 2
1 <= x, sz <= min(5 * 104, 3 * queries.length)
The input is generated such that for queries of type 1, no obstacle exists at distance x when the query is asked.
The input is generated such that there is at least one query of type 2.
*/

import java.util.*;

class Solution {
    class SegmentTreeNode {
        SegmentTreeNode leftChild;
        SegmentTreeNode rightChild;
        int start;
        int end;
        int maxFreeSpace = 0;
        int nearestObstacle = Integer.MAX_VALUE;

        SegmentTreeNode(int start, int end, int obstaclePosition) {
            this.start = start;
            this.end = end;
            this.nearestObstacle = obstaclePosition;
            this.maxFreeSpace = obstaclePosition == Integer.MAX_VALUE ? Integer.MAX_VALUE : obstaclePosition - start;
        }

        void printTree() {
            System.out.print("[" + start + "-" + end + ": " + nearestObstacle + ", " + maxFreeSpace);
            if (leftChild != null) {
                System.out.print(" -- ");
                leftChild.printTree();
                rightChild.printTree();
                System.out.print(" -- " + start + "-" + end);
            }
            System.out.print("]");
        }
    }

    public List<Boolean> getResults(int[][] queries) {
        int maxRange = 0;
        for (int[] query : queries) {
            if (query[0] == 1) {
                maxRange = Math.max(maxRange, query[1]);
            }
        }
        SegmentTreeNode root = new SegmentTreeNode(0, maxRange, Integer.MAX_VALUE);
        List<Boolean> results = new ArrayList<>();
        for (int[] query : queries) {
            if (query[0] == 1) {
                addObstacle(root, query[1]);
            } else {
                int blockStart = query[1] - query[2];
                if (blockStart >= root.end) {
                    results.add(true);
                } else {
                    results.add(isBlockPlaceable(root, blockStart, query[2]));
                }
            }
        }
        return results;
    }

    boolean isBlockPlaceable(SegmentTreeNode root, int blockStart, int blockSize) {
        if (root.leftChild == null && root.rightChild == null) {
            if (blockStart >= root.end) {
                return blockSize <= root.maxFreeSpace;
            } else if (blockStart < root.start) {
                return false;
            } else {
                return blockSize <= (root.nearestObstacle - root.start);
            }
        }

        if (root.rightChild.end <= blockStart) {
            if (root.rightChild.maxFreeSpace >= blockSize) {
                return true;
            }
        }
        if (root.leftChild.end <= blockStart) {
            if (root.leftChild.maxFreeSpace >= blockSize) {
                return true;
            }
        } else {
            return isBlockPlaceable(root.leftChild, blockStart, blockSize);
        }
        if (root.rightChild.start <= blockStart && root.rightChild.end >= blockStart) {
            return isBlockPlaceable(root.rightChild, blockStart, blockSize);
        }

        return false;
    }

    int addObstacle(SegmentTreeNode root, int obstaclePosition) {
        if (root.end == root.start) {
            root.nearestObstacle = (root.end < obstaclePosition && obstaclePosition < root.nearestObstacle)
                    ? obstaclePosition
                    : root.nearestObstacle;
            root.maxFreeSpace = root.nearestObstacle == Integer.MAX_VALUE ? root.nearestObstacle
                    : root.nearestObstacle - root.start;
            return root.maxFreeSpace;
        }
        if (obstaclePosition <= root.start)
            return root.maxFreeSpace;
        if (obstaclePosition > root.end) {
            if (obstaclePosition < root.nearestObstacle) {
                root.nearestObstacle = obstaclePosition;
                if (root.leftChild == null && root.rightChild == null) {
                    root.maxFreeSpace = (obstaclePosition - root.start);
                } else {
                    root.maxFreeSpace = Math.max(addObstacle(root.leftChild, obstaclePosition),
                            addObstacle(root.rightChild, obstaclePosition));
                }
            }
            return root.maxFreeSpace;
        }
        if (root.leftChild != null && root.rightChild != null) {
            root.maxFreeSpace = Math.max(addObstacle(root.leftChild, obstaclePosition),
                    addObstacle(root.rightChild, obstaclePosition));
            return root.maxFreeSpace;
        }
        int mid = (root.end - root.start) / 2 + root.start;
        root.leftChild = new SegmentTreeNode(root.start, mid, root.nearestObstacle);
        root.rightChild = new SegmentTreeNode(mid + 1, root.end, root.nearestObstacle);
        root.maxFreeSpace = Math.max(addObstacle(root.leftChild, obstaclePosition),
                addObstacle(root.rightChild, obstaclePosition));
        return root.maxFreeSpace;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] queries = { { 1, 5 }, { 2, 7, 2 }, { 2, 6, 2 } };
        System.out.println(solution.getResults(queries));
    }
}