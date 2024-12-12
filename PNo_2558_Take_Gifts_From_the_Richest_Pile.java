/*
You are given an integer array gifts denoting the number of gifts in various piles. Every second, you do the following:

Choose the pile with the maximum number of gifts.
If there is more than one pile with the maximum number of gifts, choose any.
Leave behind the floor of the square root of the number of gifts in the pile. Take the rest of the gifts.
Return the number of gifts remaining after k seconds.



Example 1:

Input: gifts = [25,64,9,4,100], k = 4
Output: 29
Explanation:
The gifts are taken in the following way:
- In the first second, the last pile is chosen and 10 gifts are left behind.
- Then the second pile is chosen and 8 gifts are left behind.
- After that the first pile is chosen and 5 gifts are left behind.
- Finally, the last pile is chosen again and 3 gifts are left behind.
The final remaining gifts are [5,8,9,4,3], so the total number of gifts remaining is 29.
Example 2:

Input: gifts = [1,1,1,1], k = 4
Output: 4
Explanation:
In this case, regardless which pile you choose, you have to leave behind 1 gift in each pile.
That is, you can't take any pile with you.
So, the total gifts remaining are 4.


Constraints:

1 <= gifts.length <= 103
1 <= gifts[i] <= 109
1 <= k <= 103
*/

class Solution {
    private long useHeap(int[] gv, int k) {
        int[] heap = new int[gv.length];
        long total = 0;
        for (int i = 0; i < gv.length; i++) {
            int v = gv[i];
            insert(v, heap, i);
            total += v;
        }
        for (int i = 0; i < k; i++) {
            int largest = remove(heap);
            int reduced = (int) Math.sqrt(largest);
            total -= largest - reduced;
            insert(reduced, heap);
        }
        return total;
    }

    private void insert(int v, int[] heap, int pos) {
        heap[pos] = v;
        while (pos > 0) {
            int p = (pos - 1) / 2;
            if (heap[p] < heap[pos]) {
                swap(heap, p, pos);
                pos = p;
            } else {
                break;
            }
        }
    }

    private void insert(int v, int[] heap) {
        insert(v, heap, heap.length - 1);
    }

    private void swap(int[] heap, int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    private int remove(int[] heap) {
        int ret = heap[0];
        heap[0] = heap[heap.length - 1];
        heapify(heap, heap.length - 1);
        return ret;
    }

    private void heapify(int[] heap, int size) {
        int pos = 0;
        while (pos < size) {
            int left = pos * 2 + 1;
            int right = pos * 2 + 2;
            if (left >= size) {
                break;
            }
            if (right < size && heap[left] < heap[right]) {
                if (heap[right] > heap[pos]) {
                    swap(heap, pos, right);
                    pos = right;
                } else {
                    break;
                }
            } else {
                if (heap[left] > heap[pos]) {
                    swap(heap, pos, left);
                    pos = left;
                } else {
                    break;
                }
            }
        }
    }

    public long pickGifts(int[] gv, int k) {
        return useHeap(gv, k);
    }
}