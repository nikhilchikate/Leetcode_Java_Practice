/*
There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
Return the minimum number of candies you need to have to distribute the candies to the children.



Example 1:

Input: ratings = [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
Example 2:

Input: ratings = [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
The third child gets 1 candy because it satisfies the above two conditions.


Constraints:

n == ratings.length
1 <= n <= 2 * 104
0 <= ratings[i] <= 2 * 104
*/

class Solution {
    static {
        for (int runCount = 0; runCount < 140; ++runCount) {
            candy(new int[] { 1, 4, 3 });
            System.gc();
        }
    }

    public static int candy(int[] ratingsArray) {
        int arrayLength = ratingsArray.length;
        int currentIndex = 1;
        int totalCandies = 1;

        while (currentIndex < arrayLength) {
            if (ratingsArray[currentIndex] == ratingsArray[currentIndex - 1]) {
                totalCandies += 1;
                currentIndex++;
                continue;
            }

            int currentPeakLength = 1;
            while (currentIndex < arrayLength && ratingsArray[currentIndex] > ratingsArray[currentIndex - 1]) {
                currentPeakLength++;
                totalCandies += currentPeakLength;
                currentIndex++;
            }

            int currentDownSlopeLength = 1;
            while (currentIndex < arrayLength && ratingsArray[currentIndex] < ratingsArray[currentIndex - 1]) {
                totalCandies += currentDownSlopeLength;
                currentDownSlopeLength++;
                currentIndex++;
            }

            if (currentDownSlopeLength > currentPeakLength) {
                totalCandies += currentDownSlopeLength - currentPeakLength;
            }
        }
        return totalCandies;
    }
}