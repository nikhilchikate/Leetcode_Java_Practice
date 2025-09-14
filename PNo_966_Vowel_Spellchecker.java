/*
Given a wordlist, we want to implement a spellchecker that converts a query word into a correct word.

For a given query word, the spell checker handles two categories of spelling mistakes:

Capitalization: If the query matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the case in the wordlist.
Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the query word with any vowel individually, it matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the match in the wordlist.
Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
In addition, the spell checker operates under the following precedence rules:

When the query exactly matches a word in the wordlist (case-sensitive), you should return the same word back.
When the query matches a word up to capitlization, you should return the first such match in the wordlist.
When the query matches a word up to vowel errors, you should return the first such match in the wordlist.
If the query has no matches in the wordlist, you should return the empty string.
Given some queries, return a list of words answer, where answer[i] is the correct word for query = queries[i].



Example 1:

Input: wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
Example 2:

Input: wordlist = ["yellow"], queries = ["YellOw"]
Output: ["yellow"]


Constraints:

1 <= wordlist.length, queries.length <= 5000
1 <= wordlist[i].length, queries[i].length <= 7
wordlist[i] and queries[i] consist only of only English letters.
*/

class Solution {
    public String[] spellchecker(String[] wordList, String[] queries) {
        int m = wordList.length;
        int n = queries.length;
        String[] results = new String[n];
        Map<String, Integer> exactMatch = new HashMap<>();
        Map<String, Integer> lowerCaseMatch = new HashMap<>();
        Map<String, Integer> vowelErrorMatch = new HashMap<>();

        for (int i = m - 1; i >= 0; --i) {
            String word = wordList[i];
            exactMatch.put(word, i);
            String lowerCaseWord = word.toLowerCase();

            char[] vowelRepArray = lowerCaseWord.toCharArray();
            for (int j = 0; j < vowelRepArray.length; ++j) {
                if ("aeiou".indexOf(vowelRepArray[j]) != -1) {
                    vowelRepArray[j] = 'a';
                }
            }
            lowerCaseMatch.put(lowerCaseWord, i);
            vowelErrorMatch.put(new String(vowelRepArray), i);
        }

        for (int i = 0; i < n; ++i) {
            String query = queries[i];
            if (exactMatch.containsKey(query)) {
                results[i] = query;
                continue;
            }

            String lowerCaseQuery = query.toLowerCase();
            if (lowerCaseMatch.containsKey(lowerCaseQuery)) {
                results[i] = wordList[lowerCaseMatch.get(lowerCaseQuery)];
                continue;
            }

            char[] vowelRepArray = lowerCaseQuery.toCharArray();
            for (int j = 0; j < vowelRepArray.length; ++j) {
                if ("aeiou".indexOf(vowelRepArray[j]) != -1) {
                    vowelRepArray[j] = 'a';
                }
            }
            String vowelRepString = new String(vowelRepArray);
            if (vowelErrorMatch.containsKey(vowelRepString)) {
                results[i] = wordList[vowelErrorMatch.get(vowelRepString)];
                continue;
            }
            results[i] = "";
        }

        return results;
    }
}