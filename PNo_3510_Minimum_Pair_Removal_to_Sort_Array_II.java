/*
Given an array nums, you can perform the following operation any number of times:

Select the adjacent pair with the minimum sum in nums. If multiple such pairs exist, choose the leftmost one.
Replace the pair with their sum.
Return the minimum number of operations needed to make the array non-decreasing.

An array is said to be non-decreasing if each element is greater than or equal to its previous element (if it exists).



Example 1:

Input: nums = [5,2,3,1]

Output: 2

Explanation:

The pair (3,1) has the minimum sum of 4. After replacement, nums = [5,2,4].
The pair (2,4) has the minimum sum of 6. After replacement, nums = [5,6].
The array nums became non-decreasing in two operations.

Example 2:

Input: nums = [1,2,2]

Output: 0

Explanation:

The array nums is already sorted.



Constraints:

1 <= nums.length <= 105
-109 <= nums[i] <= 109
*/

class Solution {
    static class Element {
        long data;
        Element previous, following;
        boolean deleted;
        int position;

        Element(long data, int position) {
            this.data = data;
            this.position = position;
        }
    }

    static class Candidate {
        long total;
        Element first, second;
        int firstPos;

        Candidate(Element first, Element second) {
            this.first = first;
            this.second = second;
            this.total = first.data + second.data;
            this.firstPos = first.position;
        }
    }

    private static boolean valid(Element node) {
        return node != null && node.position != -1;
    }

    private static int inversion(Element left, Element right) {
        if (!valid(left) || right == null) {
            return 0;
        }
        return left.data > right.data ? 1 : 0;
    }

    public static int minimumPairRemoval(int[] values) {
        if (values == null || values.length <= 1) {
            return 0;
        }

        Element sentinel = new Element(0, -1);
        Element pointer = sentinel;

        for (int idx = 0; idx < values.length; idx++) {
            Element node = new Element(values[idx], idx);
            pointer.following = node;
            node.previous = pointer;
            pointer = node;
        }

        PriorityQueue<Candidate> queue = new PriorityQueue<>(
                (x, y) -> {
                    int cmp = Long.compare(x.total, y.total);
                    if (cmp != 0) {
                        return cmp;
                    }
                    return Integer.compare(x.firstPos, y.firstPos);
                });

        int inversions = 0;
        Element cursor = sentinel.following;
        while (cursor != null && cursor.following != null) {
            inversions += inversion(cursor, cursor.following);
            queue.offer(new Candidate(cursor, cursor.following));
            cursor = cursor.following;
        }

        if (inversions == 0) {
            return 0;
        }

        int steps = 0;

        while (inversions > 0) {
            Candidate choice = queue.poll();
            if (choice == null) {
                break;
            }

            Element left = choice.first;
            Element right = choice.second;

            if (left.deleted || right.deleted) {
                continue;
            }
            if (left.following != right) {
                continue;
            }

            steps++;

            Element before = left.previous;
            Element after = right.following;

            inversions -= inversion(before, left);
            inversions -= inversion(left, right);
            inversions -= inversion(right, after);

            Element combined = new Element(left.data + right.data, left.position);

            before.following = combined;
            combined.previous = before;

            combined.following = after;
            if (after != null) {
                after.previous = combined;
            }

            left.deleted = true;
            right.deleted = true;

            inversions += inversion(before, combined);
            inversions += inversion(combined, after);

            if (valid(before)) {
                queue.offer(new Candidate(before, combined));
            }
            if (after != null) {
                queue.offer(new Candidate(combined, after));
            }
        }

        return steps;
    }
}