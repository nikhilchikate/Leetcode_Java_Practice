/*
You are given an array of integers nums and the head of a linked list. Return the head of the modified linked list after removing all nodes from the linked list that have a value that exists in nums.



Example 1:

Input: nums = [1,2,3], head = [1,2,3,4,5]

Output: [4,5]

Explanation:



Remove the nodes with values 1, 2, and 3.

Example 2:

Input: nums = [1], head = [1,2,1,2,1,2]

Output: [2,2,2]

Explanation:



Remove the nodes with value 1.

Example 3:

Input: nums = [5], head = [1,2,3,4]

Output: [1,2,3,4]

Explanation:



No node has value 5.



Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 105
All elements in nums are unique.
The number of nodes in the given list is in the range [1, 105].
1 <= Node.val <= 105
The input is generated such that there is at least one node in the linked list that has a value not present in nums.
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        int maximumValue = 0;
        for (int number : nums) {
            if (number > maximumValue) {
                maximumValue = number;
            }
        }

        boolean[] isPresent = new boolean[maximumValue + 1];

        for (int number : nums) {
            isPresent[number] = true;
        }

        while (head != null && head.val <= maximumValue && isPresent[head.val]) {
            head = head.next;
        }

        if (head == null) {
            return head;
        }

        ListNode current = head.next;
        ListNode previous = head;

        while (current != null) {
            if (current.val <= maximumValue && isPresent[current.val]) {
                previous.next = current.next;
            } else {
                previous = current;
            }
            current = current.next;
        }

        return head;
    }
}