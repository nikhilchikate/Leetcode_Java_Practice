/*
A sequence x1, x2, ..., xn is Fibonacci-like if:

n >= 3
xi + xi+1 == xi+2 for all i + 2 <= n
Given a strictly increasing array arr of positive integers forming a sequence, return the length of the longest Fibonacci-like subsequence of arr. If one does not exist, return 0.

A subsequence is derived from another sequence arr by deleting any number of elements (including none) from arr, without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].



Example 1:

Input: arr = [1,2,3,4,5,6,7,8]
Output: 5
Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].
Example 2:

Input: arr = [1,3,7,11,12,14,18]
Output: 3
Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].


Constraints:

3 <= arr.length <= 1000
1 <= arr[i] < arr[i + 1] <= 109
*/

class Solution {
    public int lenLongestFibSubseq(int[] arr) {
        Set<Integer> index = new HashSet<>();
        for (int n : arr) {
            index.add(n);
        }
        int max = 2;
        int n = arr.length;
        for (int i = 0; i < n - max; i++) {
            if (arr[i] * Math.pow(1.618, max - 1) > arr[n - 1]) {
                break;
            }
            for (int j = i + 1; j < n - max + 1; j++) {
                if (arr[j] * Math.pow(1.618, max - 2) > arr[n - 1]) {
                    break;
                }
                int n2 = arr[i];
                int n1 = arr[j];
                int len = 2;
                while (index.contains(n1 + n2)) {
                    int temp = n1;
                    n1 = n1 + n2;
                    n2 = temp;
                    len++;
                }
                max = Math.max(max, len);
            }
        }
        return max < 3 ? 0 : max;
    }
}