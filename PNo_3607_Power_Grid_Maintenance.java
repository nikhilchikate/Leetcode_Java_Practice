/*
You are given an integer c representing c power stations, each with a unique identifier id from 1 to c (1‑based indexing).

These stations are interconnected via n bidirectional cables, represented by a 2D array connections, where each element connections[i] = [ui, vi] indicates a connection between station ui and station vi. Stations that are directly or indirectly connected form a power grid.

Initially, all stations are online (operational).

You are also given a 2D array queries, where each query is one of the following two types:

[1, x]: A maintenance check is requested for station x. If station x is online, it resolves the check by itself. If station x is offline, the check is resolved by the operational station with the smallest id in the same power grid as x. If no operational station exists in that grid, return -1.

[2, x]: Station x goes offline (i.e., it becomes non-operational).

Return an array of integers representing the results of each query of type [1, x] in the order they appear.

Note: The power grid preserves its structure; an offline (non‑operational) node remains part of its grid and taking it offline does not alter connectivity.



Example 1:

Input: c = 5, connections = [[1,2],[2,3],[3,4],[4,5]], queries = [[1,3],[2,1],[1,1],[2,2],[1,2]]

Output: [3,2,3]

Explanation:



Initially, all stations {1, 2, 3, 4, 5} are online and form a single power grid.
Query [1,3]: Station 3 is online, so the maintenance check is resolved by station 3.
Query [2,1]: Station 1 goes offline. The remaining online stations are {2, 3, 4, 5}.
Query [1,1]: Station 1 is offline, so the check is resolved by the operational station with the smallest id among {2, 3, 4, 5}, which is station 2.
Query [2,2]: Station 2 goes offline. The remaining online stations are {3, 4, 5}.
Query [1,2]: Station 2 is offline, so the check is resolved by the operational station with the smallest id among {3, 4, 5}, which is station 3.
Example 2:

Input: c = 3, connections = [], queries = [[1,1],[2,1],[1,1]]

Output: [1,-1]

Explanation:

There are no connections, so each station is its own isolated grid.
Query [1,1]: Station 1 is online in its isolated grid, so the maintenance check is resolved by station 1.
Query [2,1]: Station 1 goes offline.
Query [1,1]: Station 1 is offline and there are no other stations in its grid, so the result is -1.


Constraints:

1 <= c <= 105
0 <= n == connections.length <= min(105, c * (c - 1) / 2)
connections[i].length == 2
1 <= ui, vi <= c
ui != vi
1 <= queries.length <= 2 * 105
queries[i].length == 2
queries[i][0] is either 1 or 2.
1 <= queries[i][1] <= c
*/

class Solution {
    public int[] processQueries(int nNodes, int[][] edges, int[][] requests) {
        int arraySize = nNodes + 1;
        final int[] parentOrLabel = new int[arraySize];
        for (int idx = 1; idx < arraySize; ++idx) {
            parentOrLabel[idx] = idx;
        }

        for (int[] edge : edges) {
            parentOrLabel[getLabel(parentOrLabel, edge[0])] = parentOrLabel[getLabel(parentOrLabel, edge[1])];
        }

        final int[] freqCounts = new int[arraySize];
        for (int idx = 0; idx < arraySize; idx++) {
            freqCounts[getLabel(parentOrLabel, idx)]++;
        }

        updatePrefixSums(freqCounts);
        final int[] startPointers = freqCounts.clone();
        final int[] sortedElements = new int[arraySize];
        for (int idx = 0; idx < arraySize; idx++) {
            sortedElements[freqCounts[parentOrLabel[idx]]++] = idx;
        }

        final int[] results = new int[requests.length];
        int resLen = 0;
        final boolean[] isOffline = new boolean[arraySize];

        for (var req : requests) {
            final int targetNode = req[1];
            if (req[0] == 1) {
                if (isOffline[targetNode]) {
                    final int componentLabel = parentOrLabel[targetNode];
                    final int endIndex = freqCounts[componentLabel];
                    int startIndex = startPointers[componentLabel];
                    while (startIndex < endIndex && isOffline[sortedElements[startIndex]]) {
                        startIndex++;
                    }
                    startPointers[componentLabel] = startIndex;
                    results[resLen++] = startIndex == endIndex ? -1 : sortedElements[startIndex];
                } else {
                    results[resLen++] = targetNode;
                }
            } else {
                isOffline[targetNode] = true;
            }
        }
        return Arrays.copyOf(results, resLen);
    }


    static final int[] auxArr = new int[100_001];

    private static int check(int[] sortedList, int[] sourceArr, int[] targetArr, int startIdx, int endIdx) {
        int countDiff = 0;
        for (int idx = startIdx; idx < endIdx; ++idx) {
            final int elementIdx = sortedList[idx];
            if (++auxArr[sourceArr[elementIdx]] > 0) countDiff++;
            if (auxArr[targetArr[elementIdx]]-- > 0) countDiff--;
        }
        for (int idx = startIdx; idx < endIdx; ++idx) {
            final int elementIdx = sortedList[idx];
            auxArr[sourceArr[elementIdx]] = 0;
            auxArr[targetArr[elementIdx]] = 0;
        }
        return countDiff;
    }

    private static void updatePrefixSums(int[] prefixSumArr) {
        int runningSum = 0;
        for (int idx = 0; idx < prefixSumArr.length; ++idx) {
            final int nextSum = runningSum + prefixSumArr[idx];
            prefixSumArr[idx] = runningSum;
            runningSum = nextSum;
        }
    }

    static int getLabel(final int[] parentArr, int elementIdx) {
        final int currentParent = parentArr[elementIdx];
        return (currentParent == elementIdx || currentParent < 0) ? elementIdx : (parentArr[elementIdx] = getLabel(parentArr, currentParent));
    }
}