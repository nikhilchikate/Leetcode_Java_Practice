/*
You are given a 2D integer array squares. Each squares[i] = [xi, yi, li] represents the coordinates of the bottom-left point and the side length of a square parallel to the x-axis.

Find the minimum y-coordinate value of a horizontal line such that the total area covered by squares above the line equals the total area covered by squares below the line.

Answers within 10-5 of the actual answer will be accepted.

Note: Squares may overlap. Overlapping areas should be counted only once in this version.



Example 1:

Input: squares = [[0,0,1],[2,2,1]]

Output: 1.00000

Explanation:



Any horizontal line between y = 1 and y = 2 results in an equal split, with 1 square unit above and 1 square unit below. The minimum y-value is 1.

Example 2:

Input: squares = [[0,0,2],[1,1,1]]

Output: 1.00000

Explanation:



Since the blue square overlaps with the red square, it will not be counted again. Thus, the line y = 1 splits the squares into two equal parts.



Constraints:

1 <= squares.length <= 5 * 104
squares[i] = [xi, yi, li]
squares[i].length == 3
0 <= xi, yi <= 109
1 <= li <= 109
The total area of all the squares will not exceed 1015.
*/

class Solution {
    static final class SweepEvent {
        final long yCoord;
        final int leftIdx, rightIdx;
        final int change;

        SweepEvent(long yCoord, int leftIdx, int rightIdx, int change) {
            this.yCoord = yCoord;
            this.leftIdx = leftIdx;
            this.rightIdx = rightIdx;
            this.change = change;
        }
    }

    static final class SegmentStructure {
        final long[] xCoords;
        final long[] coveredLen;
        final int[] coverCount;

        SegmentStructure(long[] xCoords) {
            this.xCoords = xCoords;
            int size = Math.max(1, xCoords.length - 1);
            this.coveredLen = new long[size << 2];
            this.coverCount = new int[size << 2];
        }

        long totalCovered() {
            return coveredLen[1];
        }

        void apply(int l, int r, int delta) {
            if (l >= r)
                return;
            apply(1, 0, xCoords.length - 1, l, r, delta);
        }

        private void apply(int node, int start, int end, int ql, int qr, int delta) {
            if (qr <= start || end <= ql)
                return;
            if (ql <= start && end <= qr) {
                coverCount[node] += delta;
                pull(node, start, end);
                return;
            }
            int mid = (start + end) >>> 1;
            apply(node << 1, start, mid, ql, qr, delta);
            apply(node << 1 | 1, mid, end, ql, qr, delta);
            pull(node, start, end);
        }

        private void pull(int node, int start, int end) {
            if (coverCount[node] > 0) {
                coveredLen[node] = xCoords[end] - xCoords[start];
            } else if (start + 1 == end) {
                coveredLen[node] = 0;
            } else {
                coveredLen[node] = coveredLen[node << 1] + coveredLen[node << 1 | 1];
            }
        }
    }

    public double separateSquares(int[][] blocks) {
        int count = blocks.length;
        if (count == 0)
            return -1;

        long[] xVals = new long[2 * count];
        int pos = 0;
        for (int[] b : blocks) {
            long xStart = b[0];
            long xEnd = (long) b[0] + b[2];
            xVals[pos++] = xStart;
            xVals[pos++] = xEnd;
        }

        Arrays.sort(xVals);
        int uniq = 1;
        for (int i = 1; i < xVals.length; i++) {
            if (xVals[i] != xVals[uniq - 1])
                xVals[uniq++] = xVals[i];
        }
        xVals = Arrays.copyOf(xVals, uniq);

        if (xVals.length < 2) {
            long minY = Long.MAX_VALUE;
            for (int[] b : blocks)
                minY = Math.min(minY, (long) b[1]);
            return (double) minY;
        }

        SweepEvent[] sweep = new SweepEvent[2 * count];
        int evtCount = 0;
        for (int[] b : blocks) {
            long x1 = b[0];
            long x2 = (long) b[0] + b[2];
            long y1 = b[1];
            long y2 = (long) b[1] + b[2];
            int li = lower(xVals, x1);
            int ri = lower(xVals, x2);
            if (li < ri) {
                sweep[evtCount++] = new SweepEvent(y1, li, ri, 1);
                sweep[evtCount++] = new SweepEvent(y2, li, ri, -1);
            }
        }

        if (evtCount == 0)
            return -1;
        sweep = Arrays.copyOf(sweep, evtCount);
        Arrays.sort(sweep, (a, b) -> Long.compare(a.yCoord, b.yCoord));

        SegmentStructure seg = new SegmentStructure(xVals);

        long[] segStart = new long[evtCount];
        long[] segEnd = new long[evtCount];
        long[] segWidth = new long[evtCount];
        int segIdx = 0;

        long totalArea = 0;
        long prevY = sweep[0].yCoord;
        long activeWidth = 0;
        int i = 0;

        while (i < evtCount) {
            long currY = sweep[i].yCoord;
            long deltaY = currY - prevY;
            if (deltaY != 0 && activeWidth != 0) {
                totalArea += activeWidth * deltaY;
                segStart[segIdx] = prevY;
                segEnd[segIdx] = currY;
                segWidth[segIdx] = activeWidth;
                segIdx++;
            }
            int j = i;
            while (j < evtCount && sweep[j].yCoord == currY) {
                seg.apply(sweep[j].leftIdx, sweep[j].rightIdx, sweep[j].change);
                j++;
            }
            activeWidth = seg.totalCovered();
            prevY = currY;
            i = j;
        }

        if (totalArea == 0)
            return prevY;

        double half = totalArea / 2.0;
        long acc = 0;
        for (int k = 0; k < segIdx; k++) {
            long blockArea = segWidth[k] * (segEnd[k] - segStart[k]);
            if (acc + blockArea < half) {
                acc += blockArea;
            } else {
                double remain = half - acc;
                return segStart[k] + remain / segWidth[k];
            }
        }
        return prevY;
    }

    private static int lower(long[] arr, long key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < key)
                lo = mid + 1;
            else
                hi = mid;
        }
        return lo;
    }
}