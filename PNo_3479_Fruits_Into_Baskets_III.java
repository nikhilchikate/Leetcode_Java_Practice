/*
You are given two arrays of integers, fruits and baskets, each of length n, where fruits[i] represents the quantity of the ith type of fruit, and baskets[j] represents the capacity of the jth basket.

From left to right, place the fruits according to these rules:

Each fruit type must be placed in the leftmost available basket with a capacity greater than or equal to the quantity of that fruit type.
Each basket can hold only one type of fruit.
If a fruit type cannot be placed in any basket, it remains unplaced.
Return the number of fruit types that remain unplaced after all possible allocations are made.



Example 1:

Input: fruits = [4,2,5], baskets = [3,5,4]

Output: 1

Explanation:

fruits[0] = 4 is placed in baskets[1] = 5.
fruits[1] = 2 is placed in baskets[0] = 3.
fruits[2] = 5 cannot be placed in baskets[2] = 4.
Since one fruit type remains unplaced, we return 1.

Example 2:

Input: fruits = [3,6,1], baskets = [6,4,7]

Output: 0

Explanation:

fruits[0] = 3 is placed in baskets[0] = 6.
fruits[1] = 6 cannot be placed in baskets[1] = 4 (insufficient capacity) but can be placed in the next available basket, baskets[2] = 7.
fruits[2] = 1 is placed in baskets[1] = 4.
Since all fruits are successfully placed, we return 0.



Constraints:

n == fruits.length == baskets.length
1 <= n <= 105
1 <= fruits[i], baskets[i] <= 109
*/

class SegmentTree {
    int[] data;
    int[] treeArr;

    public SegmentTree(int[] data) {
        this.data = data;
        int size = data.length;
        this.treeArr = new int[size << 2];
        build(1, 1, size);
    }

    public void build(int nodeIdx, int rangeLeft, int rangeRight) {
        if (rangeLeft == rangeRight) {
            treeArr[nodeIdx] = data[rangeLeft - 1];
            return;
        }
        int midPoint = (rangeLeft + rangeRight) >> 1;
        build(nodeIdx << 1, rangeLeft, midPoint);
        build(nodeIdx << 1 | 1, midPoint + 1, rangeRight);
        pushup(nodeIdx);
    }

    public void modify(int nodeIdx, int rangeLeft, int rangeRight, int updateIdx, int newValue) {
        if (rangeLeft == rangeRight) {
            treeArr[nodeIdx] = newValue;
            return;
        }
        int midPoint = (rangeLeft + rangeRight) >> 1;
        if (updateIdx <= midPoint) {
            modify(nodeIdx << 1, rangeLeft, midPoint, updateIdx, newValue);
        } else {
            modify(nodeIdx << 1 | 1, midPoint + 1, rangeRight, updateIdx, newValue);
        }
        pushup(nodeIdx);
    }

    public int query(int nodeIdx, int rangeLeft, int rangeRight, int targetVal) {
        if (treeArr[nodeIdx] < targetVal) {
            return -1;
        }
        if (rangeLeft == rangeRight) {
            return rangeLeft;
        }
        int midPoint = (rangeLeft + rangeRight) >> 1;
        if (treeArr[nodeIdx << 1] >= targetVal) {
            return query(nodeIdx << 1, rangeLeft, midPoint, targetVal);
        }
        return query(nodeIdx << 1 | 1, midPoint + 1, rangeRight, targetVal);
    }

    public void pushup(int nodeIdx) {
        treeArr[nodeIdx] = Math.max(treeArr[nodeIdx << 1], treeArr[nodeIdx << 1 | 1]);
    }
}

class Solution {
    public int numOfUnplacedFruits(int[] fruitSizes, int[] basketCapacities) {
        int numBaskets = basketCapacities.length;
        int treeSize = 1;
        while(treeSize <= numBaskets) treeSize <<= 1;
        int[] segmentTree = new int[treeSize << 1];
        for (int i = 0; i < numBaskets; i++)
            segmentTree[treeSize + i] = basketCapacities[i];
        for (int i = treeSize - 1; i > 0; i--)
            segmentTree[i] = Math.max(segmentTree[2 * i], segmentTree[2 * i + 1]);
        int unplacedCount = 0;
        for (int i = 0; i < numBaskets; ++i) {
            int currentFruit = fruitSizes[i];
            int idx = 1;
            if (segmentTree[idx] < currentFruit) {
                unplacedCount++;
                continue;
            }
            while (idx < treeSize) {
                if (segmentTree[idx * 2] >= currentFruit)
                    idx *= 2;
                else
                    idx = idx * 2 + 1;
            }
            segmentTree[idx] = 0;
            while (idx > 1) {
                idx >>= 1;
                segmentTree[idx] = Math.max(segmentTree[2 * idx], segmentTree[2 * idx + 1]);
            }
        }
        return unplacedCount;
    }
}
