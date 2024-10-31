/*
There are some robots and factories on the X-axis. You are given an integer array robot where robot[i] is the position of the ith robot. You are also given a 2D integer array factory where factory[j] = [positionj, limitj] indicates that positionj is the position of the jth factory and that the jth factory can repair at most limitj robots.

The positions of each robot are unique. The positions of each factory are also unique. Note that a robot can be in the same position as a factory initially.

All the robots are initially broken; they keep moving in one direction. The direction could be the negative or the positive direction of the X-axis. When a robot reaches a factory that did not reach its limit, the factory repairs the robot, and it stops moving.

At any moment, you can set the initial direction of moving for some robot. Your target is to minimize the total distance traveled by all the robots.

Return the minimum total distance traveled by all the robots. The test cases are generated such that all the robots can be repaired.

Note that

All robots move at the same speed.
If two robots move in the same direction, they will never collide.
If two robots move in opposite directions and they meet at some point, they do not collide. They cross each other.
If a robot passes by a factory that reached its limits, it crosses it as if it does not exist.
If the robot moved from a position x to a position y, the distance it moved is |y - x|.


Example 1:


Input: robot = [0,4,6], factory = [[2,2],[6,2]]
Output: 4
Explanation: As shown in the figure:
- The first robot at position 0 moves in the positive direction. It will be repaired at the first factory.
- The second robot at position 4 moves in the negative direction. It will be repaired at the first factory.
- The third robot at position 6 will be repaired at the second factory. It does not need to move.
The limit of the first factory is 2, and it fixed 2 robots.
The limit of the second factory is 2, and it fixed 1 robot.
The total distance is |2 - 0| + |2 - 4| + |6 - 6| = 4. It can be shown that we cannot achieve a better total distance than 4.
Example 2:


Input: robot = [1,-1], factory = [[-2,1],[2,1]]
Output: 2
Explanation: As shown in the figure:
- The first robot at position 1 moves in the positive direction. It will be repaired at the second factory.
- The second robot at position -1 moves in the negative direction. It will be repaired at the first factory.
The limit of the first factory is 1, and it fixed 1 robot.
The limit of the second factory is 1, and it fixed 1 robot.
The total distance is |2 - 1| + |(-2) - (-1)| = 2. It can be shown that we cannot achieve a better total distance than 2.


Constraints:

1 <= robot.length, factory.length <= 100
factory[j].length == 2
-109 <= robot[i], positionj <= 109
0 <= limitj <= robot.length
The input will be generated such that it is always possible to repair every robot.
*/

class Solution {
    public long minimumTotalDistance(List<Integer> robots, int[][] factories) {
        // Sort the robots in ascending order
        Collections.sort(robots);
        // Sort factories by their location (first element of each sub-array)
        Arrays.sort(factories, (factory1, factory2) -> factory1[0] - factory2[0]);

        // Initialize a DP table to store the minimum distance results
        long[][] dp = new long[factories.length][robots.size()];

        // Fill the DP table with initial values of -1
        for (int factoryIndex = 0; factoryIndex < dp.length; factoryIndex++) {
            Arrays.fill(dp[factoryIndex], -1L);
        }

        // Calculate the distance for the first factory
        long distanceSum = 0;
        for (int robotIndex = 0; robotIndex < Math.min(factories[0][1], robots.size()); robotIndex++) {
            distanceSum += Math.abs(factories[0][0] - robots.get(robotIndex));
            dp[0][robotIndex] = distanceSum; // Store the distance for the first factory
        }

        // Iterate over each factory starting from the second
        for (int factoryIndex = 1; factoryIndex < factories.length; factoryIndex++) {
            int factoryLimit = factories[factoryIndex][1]; // Maximum robots for this factory
            for (int robotIndex = 0; robotIndex < robots.size(); robotIndex++) {
                distanceSum = 0; // Reset the distance sum for the current robot index
                dp[factoryIndex][robotIndex] = dp[factoryIndex - 1][robotIndex]; // Start with previous factory's result

                // Try assigning robots to the current factory
                for (int assignedCount = 0; assignedCount < factoryLimit; assignedCount++) {
                    int remainingRobots = robotIndex - assignedCount; // Remaining robots after assignment
                    if (remainingRobots < 0) {
                        break; // No more robots to assign
                    }
                    distanceSum += Math.abs(factories[factoryIndex][0] - robots.get(remainingRobots)); // Update
                    // distance for
                    // the current
                    // assignment
                    long totalDistance = distanceSum; // Total distance including previous factories

                    // If there are still robots left to consider
                    if (remainingRobots > 0) {
                        // Ensure the previous factory has valid data
                        if (dp[factoryIndex - 1][remainingRobots - 1] < 0) {
                            continue; // Skip if no valid previous result
                        }
                        totalDistance += dp[factoryIndex - 1][remainingRobots - 1]; // Add the previous factory's total
                        // distance
                    }

                    // Update DP table with the minimum distance
                    if (totalDistance < dp[factoryIndex][robotIndex] || dp[factoryIndex][robotIndex] < 0) {
                        dp[factoryIndex][robotIndex] = totalDistance;
                    }
                }
            }
        }

        // Return the minimum total distance for all robots at the last factory
        return dp[factories.length - 1][robots.size() - 1];
    }
}