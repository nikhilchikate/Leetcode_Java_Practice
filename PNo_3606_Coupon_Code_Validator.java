/*
You are given three arrays of length n that describe the properties of n coupons: code, businessLine, and isActive. The ith coupon has:

code[i]: a string representing the coupon identifier.
businessLine[i]: a string denoting the business category of the coupon.
isActive[i]: a boolean indicating whether the coupon is currently active.
A coupon is considered valid if all of the following conditions hold:

code[i] is non-empty and consists only of alphanumeric characters (a-z, A-Z, 0-9) and underscores (_).
businessLine[i] is one of the following four categories: "electronics", "grocery", "pharmacy", "restaurant".
isActive[i] is true.
Return an array of the codes of all valid coupons, sorted first by their businessLine in the order: "electronics", "grocery", "pharmacy", "restaurant", and then by code in lexicographical (ascending) order within each category.



Example 1:

Input: code = ["SAVE20","","PHARMA5","SAVE@20"], businessLine = ["restaurant","grocery","pharmacy","restaurant"], isActive = [true,true,true,true]

Output: ["PHARMA5","SAVE20"]

Explanation:

First coupon is valid.
Second coupon has empty code (invalid).
Third coupon is valid.
Fourth coupon has special character @ (invalid).
Example 2:

Input: code = ["GROCERY15","ELECTRONICS_50","DISCOUNT10"], businessLine = ["grocery","electronics","invalid"], isActive = [false,true,true]

Output: ["ELECTRONICS_50"]

Explanation:

First coupon is inactive (invalid).
Second coupon is valid.
Third coupon has invalid business line (invalid).


Constraints:

n == code.length == businessLine.length == isActive.length
1 <= n <= 100
0 <= code[i].length, businessLine[i].length <= 100
code[i] and businessLine[i] consist of printable ASCII characters.
isActive[i] is either true or false.
*/

class Solution {
    public List<String> validateCoupons(String[] arr1, String[] arr2, boolean[] arr3) {
        List<String> listA = new ArrayList<>();
        List<String> listB = new ArrayList<>();
        List<String> listC = new ArrayList<>();
        List<String> listD = new ArrayList<>();

        for (int i = 0; i < arr1.length; ++i) {
            if (!arr3[i] || arr1[i].isEmpty())
                continue;

            boolean ok = true;
            for (char ch : arr1[i].toCharArray()) {
                if (!Character.isLetterOrDigit(ch) && ch != '_') {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                if (arr2[i].equals("electronics")) {
                    listA.add(arr1[i]);
                } else if (arr2[i].equals("grocery")) {
                    listB.add(arr1[i]);
                } else if (arr2[i].equals("pharmacy")) {
                    listC.add(arr1[i]);
                } else if (arr2[i].equals("restaurant")) {
                    listD.add(arr1[i]);
                }
            }
        }

        Collections.sort(listA);
        Collections.sort(listB);
        Collections.sort(listC);
        Collections.sort(listD);

        List<String> out = new ArrayList<>();
        out.addAll(listA);
        out.addAll(listB);
        out.addAll(listC);
        out.addAll(listD);

        return out;
    }
}