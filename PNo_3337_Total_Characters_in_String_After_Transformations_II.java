/*
You are given a string s consisting of lowercase English letters, an integer t representing the number of transformations to perform, and an array nums of size 26. In one transformation, every character in s is replaced according to the following rules:

Replace s[i] with the next nums[s[i] - 'a'] consecutive characters in the alphabet. For example, if s[i] = 'a' and nums[0] = 3, the character 'a' transforms into the next 3 consecutive characters ahead of it, which results in "bcd".
The transformation wraps around the alphabet if it exceeds 'z'. For example, if s[i] = 'y' and nums[24] = 3, the character 'y' transforms into the next 3 consecutive characters ahead of it, which results in "zab".
Return the length of the resulting string after exactly t transformations.

Since the answer may be very large, return it modulo 109 + 7.



Example 1:

Input: s = "abcyy", t = 2, nums = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2]

Output: 7

Explanation:

First Transformation (t = 1):

'a' becomes 'b' as nums[0] == 1
'b' becomes 'c' as nums[1] == 1
'c' becomes 'd' as nums[2] == 1
'y' becomes 'z' as nums[24] == 1
'y' becomes 'z' as nums[24] == 1
String after the first transformation: "bcdzz"
Second Transformation (t = 2):

'b' becomes 'c' as nums[1] == 1
'c' becomes 'd' as nums[2] == 1
'd' becomes 'e' as nums[3] == 1
'z' becomes 'ab' as nums[25] == 2
'z' becomes 'ab' as nums[25] == 2
String after the second transformation: "cdeabab"
Final Length of the string: The string is "cdeabab", which has 7 characters.

Example 2:

Input: s = "azbk", t = 1, nums = [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]

Output: 8

Explanation:

First Transformation (t = 1):

'a' becomes 'bc' as nums[0] == 2
'z' becomes 'ab' as nums[25] == 2
'b' becomes 'cd' as nums[1] == 2
'k' becomes 'lm' as nums[10] == 2
String after the first transformation: "bcabcdlm"
Final Length of the string: The string is "bcabcdlm", which has 8 characters.



Constraints:

1 <= s.length <= 105
s consists only of lowercase English letters.
1 <= t <= 109
nums.length == 26
1 <= nums[i] <= 25
*/

class Solution {
    public static final int MOD = (int) 1e9 + 7;

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        int[] letterCounts = getLetterCounts(s);
        int[][] transformationMatrix = createTransformationMatrix(nums);
        int[] transformedCounts = matrixExponentiation(transformationMatrix, letterCounts, t);
        long result = 0;
        for (int freq : transformedCounts) {
            result += freq;
        }
        return (int) (result % MOD);
    }

    private int[] getLetterCounts(String str) {
        int[] counts = new int[26];
        for (char c : str.toCharArray()) {
            counts[c - 'a']++;
        }
        return counts;
    }

    private int[][] createTransformationMatrix(List<Integer> nums) {
        int[][] matrix = new int[26][26];
        for (int i = 0; i < 26; i++) {
            int limit = i + nums.get(i);
            for (int j = i + 1; j <= limit; j++) {
                matrix[j % 26][i] = 1;
            }
        }
        return matrix;
    }

    private int[] multiplyMatrixByVector(int[][] matrix, int[] vector) {
        int[] result = new int[26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                result[i] = (int) ((result[i] + 1L * matrix[i][j] * vector[j]) % MOD);
            }
        }
        return result;
    }

    private int[][] squareMatrix(int[][] matrix) {
        int[][] result = new int[26][26];
        long[] accumulator = new long[26];

        for (int i = 0; i < 26; i++) {
            Arrays.fill(accumulator, 0L);
            for (int k = 0; k < 26; k++) {
                int aik = matrix[i][k];
                if (aik == 0)
                    continue;
                int[] Ak = matrix[k];
                for (int j = 0; j < 26; j++) {
                    accumulator[j] += (long) aik * Ak[j];
                }
                if ((k & 3) == 3) {
                    for (int j = 0; j < 26; j++) {
                        accumulator[j] %= MOD;
                    }
                }
            }
            for (int j = 0; j < 26; j++) {
                result[i][j] = (int) (accumulator[j] % MOD);
            }
        }
        return result;
    }

    private int[] matrixExponentiation(int[][] matrix, int[] vector, int exponent) {
        int[][] squaredMatrix = copyMatrix(matrix);
        int[] currentVector = vector;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                currentVector = multiplyMatrixByVector(squaredMatrix, currentVector);
            }
            squaredMatrix = squareMatrix(squaredMatrix);
            exponent >>= 1;
        }
        return currentVector;
    }

    private int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[26][26];
        for (int i = 0; i < 26; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }
}