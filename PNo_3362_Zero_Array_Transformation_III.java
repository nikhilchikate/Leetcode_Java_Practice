/*
You are given an integer array nums of length n and a 2D array queries where queries[i] = [li, ri].

Each queries[i] represents the following action on nums:

Decrement the value at each index in the range [li, ri] in nums by at most 1.
The amount by which the value is decremented can be chosen independently for each index.
A Zero Array is an array with all its elements equal to 0.

Return the maximum number of elements that can be removed from queries, such that nums can still be converted to a zero array using the remaining queries. If it is not possible to convert nums to a zero array, return -1.



Example 1:

Input: nums = [2,0,2], queries = [[0,2],[0,2],[1,1]]

Output: 1

Explanation:

After removing queries[2], nums can still be converted to a zero array.

Using queries[0], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
Using queries[1], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
Example 2:

Input: nums = [1,1,1,1], queries = [[1,3],[0,2],[1,3],[1,2]]

Output: 2

Explanation:

We can remove queries[2] and queries[3].

Example 3:

Input: nums = [1,2,3,4], queries = [[0,3]]

Output: -1

Explanation:

nums cannot be converted to a zero array even after using all the queries.



Constraints:

1 <= nums.length <= 105
0 <= nums[i] <= 105
1 <= queries.length <= 105
queries[i].length == 2
0 <= li <= ri < nums.length
*/

class Solution {
    public int maxRemoval(int[] elementRequirements, int[][] removalQueries) {
        // Custom priority queue for managing available query end indices
        IntPriorityQueue queryEndPQ = new IntPriorityQueue(removalQueries.length + 1);
        queryEndPQ.add(-1); // Add a sentinel value

        // Preprocess queries to group end indices by their start index
        int[][] queryEndsByStart = groupQueryEndsByStart(elementRequirements.length, removalQueries);

        // This array tracks the "net removal effect" at each index.
        // A positive value means extra removals are available.
        // A negative value means there's a deficit of removals.
        int[] netRemovalEffect = new int[elementRequirements.length + 1];

        // Counter for the number of queries actually used
        int queriesUsedCount = 0;

        for (int i = 0; i < elementRequirements.length; i++) {
            // Propagate the net removal effect from the previous index
            if (i > 0) {
                netRemovalEffect[i] += netRemovalEffect[i - 1];
            }

            // Add all query end indices that start at the current index 'i' to the priority queue
            for (int endIndex : queryEndsByStart[i]) {
                queryEndPQ.add(endIndex);
            }

            // While the current element's requirement is not met by the net removal effect
            while (netRemovalEffect[i] < elementRequirements[i]) {
                // If no more valid query end indices are available, then it's impossible to meet the requirement
                int earliestRemovalEndIndex = queryEndPQ.poll();
                if (earliestRemovalEndIndex < i) { // If the earliest available removal ends before the current index, it's invalid
                    return -1;
                }

                // Use this query: increment the net removal effect at current index
                // and decrement it at (endIndex + 1) to "undo" the effect after it's no longer active
                netRemovalEffect[i]++;
                netRemovalEffect[earliestRemovalEndIndex + 1]--;
                queriesUsedCount++; // Increment the count of queries utilized
            }
        }
        // The result is the total number of queries minus the number of queries that were actually used
        return removalQueries.length - queriesUsedCount;
    }

    static int[][] groupQueryEndsByStart(int arrayLength, int[][] queries) {
        // First, count how many queries start at each index
        int[] startCounts = new int[arrayLength];
        for (int[] q : queries) {
            startCounts[q[0]]++;
        }

        // Initialize the 2D array with appropriate sizes based on counts
        int[][] groupedEnds = new int[arrayLength][];
        for (int i = 0; i < arrayLength; i++) {
            groupedEnds[i] = new int[startCounts[i]];
        }

        // Populate the 2D array with end indices
        for (int[] q : queries) {
            final int queryStart = q[0];
            // Use the counts array as a reverse pointer to fill the `groupedEnds` arrays
            // This places ends into their respective start index arrays efficiently without using List
            groupedEnds[queryStart][--startCounts[queryStart]] = q[1];
        }
        return groupedEnds;
    }

    public class IntPriorityQueue {
        public int[] values; // Stores the elements of the heap
        private int currentSize; // Current number of elements in the heap

        IntPriorityQueue(int maxSize) {
            this.values = new int[maxSize];
        }

        void add(int value) {
            values[currentSize] = value;
            bubbleUp(currentSize++); // Add to the end and move up
        }

        int poll() {
            int result = values[0]; // The largest element is at the root
            bubbleDown(0, values[--currentSize]); // Replace root with last element and move down
            return result;
        }

        private void bubbleDown(int index, int value) {
            while (true) {
                int leftChildIndex = index * 2 + 1;
                if (leftChildIndex >= currentSize)
                    break; // No children left

                int rightChildIndex = leftChildIndex + 1;
                // Determine which child (left or right) is larger and should be swapped with
                int swapCandidateIndex = (rightChildIndex < currentSize
                        && values[rightChildIndex] > values[leftChildIndex]) ? rightChildIndex : leftChildIndex;

                if (value < values[swapCandidateIndex]) {
                    // If the current value is smaller than the larger child, swap them
                    values[index] = values[swapCandidateIndex];
                    index = swapCandidateIndex; // Move down to the child's position
                } else {
                    break; // Heap property restored
                }
            }
            values[index] = value; // Place the value at its correct position
        }

        private void bubbleUp(int index) {
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                if (values[index] <= values[parentIndex]) {
                    break; // Heap property restored
                }
                swapElements(index, parentIndex); // Swap with parent if current is larger
                index = parentIndex; // Move up to the parent's position
            }
        }

        void swapElements(int indexA, int indexB) {
            int temp = values[indexA];
            values[indexA] = values[indexB];
            values[indexB] = temp;
        }

        int size() {
            return currentSize;
        }
    }
}