/*
A binary watch has 4 LEDs on the top to represent the hours (0-11), and 6 LEDs on the bottom to represent the minutes (0-59). Each LED represents a zero or one, with the least significant bit on the right.

For example, the below binary watch reads "4:51".


Given an integer turnedOn which represents the number of LEDs that are currently on (ignoring the PM), return all possible times the watch could represent. You may return the answer in any order.

The hour must not contain a leading zero.

For example, "01:00" is not valid. It should be "1:00".
The minute must consist of two digits and may contain a leading zero.

For example, "10:2" is not valid. It should be "10:02".


Example 1:

Input: turnedOn = 1
Output: ["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
Example 2:

Input: turnedOn = 9
Output: []


Constraints:

0 <= turnedOn <= 10
*/

class Solution {

    public List<String> readBinaryWatch(int turnedOn) {
        List<String> res = new ArrayList<>();
        if (turnedOn > 8)
            return res;

        buildHours(res, new StringBuilder(), 0, 4, turnedOn, 0);
        return res;
    }

    // -------- build hour part (4 bits) --------
    private void buildHours(List<String> res, StringBuilder sb,
                            int hour, int bits, int limit, int on) {
        if (bits == 0) {
            if (hour > 11)
                return;

            sb.append(hour);
            sb.append(':');
            buildMinutes(res, sb, 0, 6, limit, on, sb.length());
            sb.setLength(0);
            return;
        }

        int val = 1 << (bits - 1);
        if (on < limit) {
            buildHours(res, sb, hour + val, bits - 1, limit, on + 1);
        }
        buildHours(res, sb, hour, bits - 1, limit, on);
    }

    // -------- build minute part (6 bits) --------
    private void buildMinutes(List<String> res, StringBuilder sb,
                              int min, int bits, int limit, int on, int baseLen) {
        if (bits == 0) {
            if (on != limit || min > 59)
                return;

            if (min < 10)
                sb.append('0');
            sb.append(min);

            res.add(sb.toString());
            sb.setLength(baseLen);
            return;
        }

        int val = 1 << (bits - 1);
        if (on < limit) {
            buildMinutes(res, sb, min + val, bits - 1, limit, on + 1, baseLen);
        }
        buildMinutes(res, sb, min, bits - 1, limit, on, baseLen);
    }
}