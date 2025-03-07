/*
Given two positive integers left and right, find the two integers num1 and num2 such that:

left <= num1 < num2 <= right .
Both num1 and num2 are prime numbers.
num2 - num1 is the minimum amongst all other pairs satisfying the above conditions.
Return the positive integer array ans = [num1, num2]. If there are multiple pairs satisfying these conditions, return the one with the smallest num1 value. If no such numbers exist, return [-1, -1].



Example 1:

Input: left = 10, right = 19
Output: [11,13]
Explanation: The prime numbers between 10 and 19 are 11, 13, 17, and 19.
The closest gap between any pair is 2, which can be achieved by [11,13] or [17,19].
Since 11 is smaller than 17, we return the first pair.
Example 2:

Input: left = 4, right = 6
Output: [-1,-1]
Explanation: There exists only one prime number in the given range, so the conditions cannot be satisfied.


Constraints:

1 <= left <= right <= 106
*/

class Solution {

    public int[] closestPrimes(int lowerBound, int upperBound) {
        List<Integer> foundPrimes = new ArrayList<>();

        // Find all prime numbers in the given range
        for (int currentNumber = lowerBound; currentNumber <= upperBound; currentNumber++) {
            if (currentNumber % 2 == 0 && currentNumber > 2) {
                continue;
            }
            if (isNumberPrime(currentNumber)) {
                // If a twin prime (or [2, 3]) is found, return immediately
                if (
                        !foundPrimes.isEmpty() &&
                                currentNumber <= foundPrimes.get(foundPrimes.size() - 1) + 2
                ) {
                    return new int[] {
                            foundPrimes.get(foundPrimes.size() - 1),
                            currentNumber,
                    };
                }
                foundPrimes.add(currentNumber);
            }
        }

        // If fewer than 2 primes exist, return {-1, -1}
        if (foundPrimes.size() < 2) {
            return new int[] { -1, -1 };
        }

        // Find the closest prime pair
        int[] resultPair = new int[] { -1, -1 };
        int smallestDifference = 1000000;
        for (int index = 1; index < foundPrimes.size(); index++) {
            int currentDifference =
                    foundPrimes.get(index) - foundPrimes.get(index - 1);
            if (currentDifference < smallestDifference) {
                smallestDifference = currentDifference;
                resultPair = new int[] {
                        foundPrimes.get(index - 1),
                        foundPrimes.get(index),
                };
            }
        }

        return resultPair;
    }

    // Function to check if a number is prime
    private boolean isNumberPrime(int checkNumber) {
        if (checkNumber == 1) {
            return false;
        }
        for (int divisor = 2; divisor <= (int) Math.sqrt(checkNumber); divisor++) {
            if (checkNumber % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}