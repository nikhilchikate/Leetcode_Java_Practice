/*
You are given a string s consisting of lowercase English letters.

In one operation, you can select any substring of s that is not the entire string and sort it in non-descending alphabetical order.

Return the minimum number of operations required to make s sorted in non-descending order. If it is not possible, return -1.

 

Example 1:

Input: s = "dog"

Output: 1

Explanation:​​​​​​​

Sort substring "og" to "go".
Now, s = "dgo", which is sorted in ascending order. Thus, the answer is 1.
Example 2:

Input: s = "card"

Output: 2

Explanation:

Sort substring "car" to "acr", so s = "acrd".
Sort substring "rd" to "dr", making s = "acdr", which is sorted in ascending order. Thus, the answer is 2.
Example 3:

Input: s = "gf"

Output: -1

Explanation:

It is impossible to sort s under the given constraints. Thus, the answer is -1.
 

Constraints:

1 <= s.length <= 105
s consists of only lowercase English letters.
*/

class Solution {
    boolean isSorted(String s) {
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) < s.charAt(i - 1))
                return false;
        }
        return true;
    }

    public int minOperations(String s) {
        int n = s.length();

        if (isSorted(s))
            return 0;

        if (n == 2)
            return -1;

        int[] freq = new int[26];
        for (int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a']++;
        }

        int first = 0, last = 25;
        while (freq[first] == 0)
            first++;

        while (freq[last] == 0)
            last--;

        if (s.charAt(0) - 'a' == first)
            return 1;
        else if (s.charAt(n - 1) - 'a' == last)
            return 1;
        else if (s.charAt(0) - 'a' != last || s.charAt(n - 1) - 'a' != first || freq[first] > 1 || freq[last] > 1)
            return 2;
        else
            return 3;
    }
}