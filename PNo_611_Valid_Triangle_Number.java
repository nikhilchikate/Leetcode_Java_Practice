/*
Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.



Example 1:

Input: nums = [2,2,3,4]
Output: 3
Explanation: Valid combinations are:
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3
Example 2:

Input: nums = [4,2,3,4]
Output: 4


Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 1000
*/

class Solution {
    private static final int DUMMY = 0;

    public int triangleNumber(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);

        int[] freq = new int[max + 1];
        int[] ps = new int[max + 1];

        for (var x : arr) {
            if (x > 0)
                freq[x]++;
        }

        for (int i = 0; i <= max; i++) {
            ps[i] = freq[i];
            if (i > 0)
                ps[i] += ps[i - 1];
        }

        debugPrint(freq);
        debugPrint(ps);

        int ans = 0;

        for (int i = 1; i <= max; i++) {
            if (freq[i] > 1) {
                int maxC = i + i - 1;
                int c = ps[Math.min(max, maxC)] - ps[i];

                ans += c * freq[i] * (freq[i] - 1) / 2;

                if (freq[i] > 2) {
                    ans += freq[i] * (freq[i] - 1) * (freq[i] - 2) / 6;
                }
            }

            for (int j = i + 1; j <= max; j++) {
                if (freq[i] * freq[j] == 0)
                    continue;

                int maxC = i + j - 1;
                int c = ps[Math.min(max, maxC)] - ps[j];
                ans += freq[i] * freq[j] * c;

                if (freq[j] > 1) {
                    ans += freq[i] * freq[j] * (freq[j] - 1) / 2;
                }
            }
        }
        return ans;
    }

    private static void debugPrint(Object... args) {
    }
}