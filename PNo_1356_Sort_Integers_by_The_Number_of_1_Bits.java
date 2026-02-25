/*
You are given an integer array arr. Sort the integers in the array in ascending order by the number of 1's in their binary representation and in case of two or more integers have the same number of 1's you have to sort them in ascending order.

Return the array after sorting it.



Example 1:

Input: arr = [0,1,2,3,4,5,6,7,8]
Output: [0,1,2,4,8,3,5,6,7]
Explantion: [0] is the only integer with 0 bits.
[1,2,4,8] all have 1 bit.
[3,5,6] have 2 bits.
[7] has 3 bits.
The sorted array by bits is [0,1,2,4,8,3,5,6,7]
Example 2:

Input: arr = [1024,512,256,128,64,32,16,8,4,2,1]
Output: [1,2,4,8,16,32,64,128,256,512,1024]
Explantion: All integers have 1 bit in the binary representation, you should just sort them in ascending order.


Constraints:

1 <= arr.length <= 500
0 <= arr[i] <= 104
*/

class Solution {
    public int[] sortByBits(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++)
            arr[i] += 10001 * Integer.bitCount(arr[i]);
        quicksort(arr, 0, n - 1);
        for (int i = 0; i < n; i++)
            arr[i] %= 10001;
        return arr;
    }

    private static void quicksort(int[] nums, int left, int right) {
        if (left < right) {
            int part = partition(nums, left - 1, right + 1);
            quicksort(nums, left, part);
            quicksort(nums, part + 1, right);
        }
    }

    private static int partition(int[] nums, int left, int right) {
        int current = getPivot(nums[left + 1], nums[left + right >>> 1], nums[right - 1]), temp = 0;
        while (true) {
            do {
                left++;
            } while (nums[left] < current);
            do {
                right--;
            } while (nums[right] > current);

            if (left >= right)
                return right;

            temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
    }

    private static int getPivot(int a, int b, int c) {
        if ((a >= b) ^ (a >= c))
            return a;
        if ((a >= b) ^ (c >= b))
            return b;
        return c;
    }
}