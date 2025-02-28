/*
Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences. If there are multiple valid strings, return any of them.

A string s is a subsequence of string t if deleting some number of characters from t (possibly 0) results in the string s.



Example 1:

Input: str1 = "abac", str2 = "cab"
Output: "cabac"
Explanation:
str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.
Example 2:

Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa"
Output: "aaaaaaaa"


Constraints:

1 <= str1.length, str2.length <= 1000
str1 and str2 consist of lowercase English letters.
*/

class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        String res = "";
        try {
            Callable<String> task = () -> generateLCS(str1, str2);
            FutureTask<String> future = new FutureTask<>(task);
            new Thread(future).start();
            // Set a timeout to prevent indefinite waiting.
            res = future.get(5, TimeUnit.SECONDS); // Timeout after 5 seconds
        } catch (TimeoutException e) {
            // Handle timeout: The task took too long.
            System.err.println("Task timed out. Returning a fallback or empty string.");
            res = handleTimeout(str1, str2); // Implement a method to handle timeout, or return ""
        } catch (Exception e) {
            // Handle other exceptions (InterruptedException, ExecutionException, etc.)
            System.err.println("An error occurred: " + e.getMessage());
            res = handleGeneralException(str1, str2); // Implement a method to handle general exceptions, or return ""
        }
        return res;
    }

    private String handleTimeout(String str1, String str2) {
        // Provide a fallback strategy. This can be a simpler, less efficient algorithm,
        // or return an empty string/error message.
        // For example, a basic concatenation or a very simple approximation.
        return fallbackApproximation(str1, str2);
    }

    private String handleGeneralException(String str1, String str2) {
        // Handle other exceptions. Return a default value or log the error.
        return "";
    }

    private String fallbackApproximation(String str1, String str2) {
        // A very basic fallback, can be improved.
        return str1 + str2;
    }

    public String generateLCS(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();

        int[][] lcsMatrix = new int[l1 + 1][l2 + 1];

        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    lcsMatrix[i][j] = lcsMatrix[i - 1][j - 1] + 1;
                } else {
                    lcsMatrix[i][j] = Math.max(lcsMatrix[i - 1][j], lcsMatrix[i][j - 1]);
                }
            }
        }

        int row = l1;
        int col = l2;

        StringBuilder ans = new StringBuilder();
        while (row != 0 && col != 0) {
            if (str1.charAt(row - 1) == str2.charAt(col - 1)) {
                ans.append(str1.charAt(row - 1));
                row--;
                col--;
            } else if (lcsMatrix[row - 1][col] > lcsMatrix[row][col - 1]) {
                ans.append(str1.charAt(row - 1));
                row--;
            } else {
                ans.append(str2.charAt(col - 1));
                col--;
            }
        }

        // Add remaining characters from str1, if any.
        while (row > 0) {
            ans.append(str1.charAt(row - 1));
            row--;
        }

        // Add remaining characters from str2, if any.
        while (col > 0) {
            ans.append(str2.charAt(col - 1));
            col--;
        }

        return ans.reverse().toString();
    }
}