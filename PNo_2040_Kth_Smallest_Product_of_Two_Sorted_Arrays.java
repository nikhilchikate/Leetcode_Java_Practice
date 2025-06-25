/*
Given two sorted 0-indexed integer arrays nums1 and nums2 as well as an integer k, return the kth (1-based) smallest product of nums1[i] * nums2[j] where 0 <= i < nums1.length and 0 <= j < nums2.length.


Example 1:

Input: nums1 = [2,5], nums2 = [3,4], k = 2
Output: 8
Explanation: The 2 smallest products are:
- nums1[0] * nums2[0] = 2 * 3 = 6
- nums1[0] * nums2[1] = 2 * 4 = 8
The 2nd smallest product is 8.
Example 2:

Input: nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
Output: 0
Explanation: The 6 smallest products are:
- nums1[0] * nums2[1] = (-4) * 4 = -16
- nums1[0] * nums2[0] = (-4) * 2 = -8
- nums1[1] * nums2[1] = (-2) * 4 = -8
- nums1[1] * nums2[0] = (-2) * 2 = -4
- nums1[2] * nums2[0] = 0 * 2 = 0
- nums1[2] * nums2[1] = 0 * 4 = 0
The 6th smallest product is 0.
Example 3:

Input: nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
Output: -6
Explanation: The 3 smallest products are:
- nums1[0] * nums2[4] = (-2) * 5 = -10
- nums1[0] * nums2[3] = (-2) * 4 = -8
- nums1[4] * nums2[0] = 2 * (-3) = -6
The 3rd smallest product is -6.


Constraints:

1 <= nums1.length, nums2.length <= 5 * 104
-105 <= nums1[i], nums2[j] <= 105
1 <= k <= nums1.length * nums2.length
nums1 and nums2 are sorted.
*/

class Solution {
    public long kthSmallestProduct(int[] arrayA, int[] arrayB, long targetRank) {
        int zeroIdxA = getFirstNonNegativeIndex(arrayA);
        int zeroIdxB = getFirstNonNegativeIndex(arrayB);
        int lenA = arrayA.length;
        int lenB = arrayB.length;

        int[] nonNegA = Arrays.copyOfRange(arrayA, zeroIdxA, lenA);
        int[] nonNegB = Arrays.copyOfRange(arrayB, zeroIdxB, lenB);
        int[] absNegA = createAbsNegativeArray(arrayA, zeroIdxA);
        int[] absNegB = createAbsNegativeArray(arrayB, zeroIdxB);

        long negativeProductsCount = (long) absNegA.length * nonNegB.length + (long) nonNegA.length * absNegB.length;
        long finalSign = 1L;

        if (targetRank > negativeProductsCount) {
            targetRank -= negativeProductsCount;
            finalSign = 1L;
        } else {
            int[] tempArr = absNegB;
            absNegB = nonNegB;
            nonNegB = tempArr;
            finalSign = -1L;
            targetRank = negativeProductsCount - targetRank + 1;
        }

        long searchStart = 0;
        long searchEnd = (long) Math.pow(10, 10);

        while (searchStart < searchEnd) {
            long midVal = searchStart + (searchEnd - searchStart) / 2;
            if ((countProductsBelowThreshold(absNegA, absNegB, midVal)
                    + countProductsBelowThreshold(nonNegA, nonNegB, midVal)) >= targetRank) {
                searchEnd = midVal;
            } else {
                searchStart = midVal + 1;
            }
        }

        return searchStart * finalSign;
    }

    long countProductsBelowThreshold(int[] arr1, int[] arr2, long threshold) {
        long currentCount = 0;
        int ptr2 = arr2.length - 1;
        for (int ptr1 = 0; ptr1 < arr1.length; ptr1++) {
            while (ptr2 >= 0 && ((long) arr1[ptr1] * (long) arr2[ptr2]) > threshold) {
                ptr2 -= 1;
            }
            currentCount += ptr2 + 1;
        }
        return currentCount;
    }

    private int getFirstNonNegativeIndex(int[] sourceNums) {
        int leftCursor = 0, rightCursor = sourceNums.length - 1;
        while (leftCursor <= rightCursor) {
            int midCursor = leftCursor + (rightCursor - leftCursor) / 2;
            if (sourceNums[midCursor] >= 0) {
                rightCursor = midCursor - 1;
            } else {
                leftCursor = midCursor + 1;
            }
        }
        return leftCursor;
    }

    private int[] createAbsNegativeArray(int[] sourceNums, int length) {
        int[] resultArr = new int[length];
        for (int i = 0; i < length; i++) {
            resultArr[i] = -1 * sourceNums[length - 1 - i];
        }
        return resultArr;
    }
}