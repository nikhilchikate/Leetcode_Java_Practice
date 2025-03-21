/*
You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. A recipe can also be an ingredient for other recipes, i.e., ingredients[i] may contain a string that is in recipes.

You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them.

Return a list of all the recipes that you can create. You may return the answer in any order.

Note that two recipes may contain each other in their ingredients.



Example 1:

Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
Output: ["bread"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
Example 2:

Input: recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
Example 3:

Input: recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich","burger"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
We can create "burger" since we have the ingredient "meat" and can create the ingredients "bread" and "sandwich".


Constraints:

n == recipes.length == ingredients.length
1 <= n <= 100
1 <= ingredients[i].length, supplies.length <= 100
1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
All the values of recipes and supplies combined are unique.
Each ingredients[i] does not contain any duplicate values.
*/

import java.util.*;
class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        return new AbstractList<String>() {
            List<String> outputList;

            @Override
            public String get(int index) {
                return outputList.get(index);
            }

            @Override
            public int size() {
                if (outputList == null) {
                    initialize();
                }
                return outputList.size();
            }

            private void initialize() {
                Set<String> availableSupplies = new HashSet<>(Arrays.asList(supplies));

                Map<String, List<String>> dependencyGraph = new HashMap<>();
                Map<String, Integer> incomingEdgeCount = new HashMap<>();
                for (int recipeIndex = 0; recipeIndex < recipes.length; recipeIndex++) {
                    for (String requiredIngredient : ingredients.get(recipeIndex)) {
                        if (!availableSupplies.contains(requiredIngredient)) {
                            dependencyGraph.putIfAbsent(requiredIngredient, new ArrayList<>());
                            dependencyGraph.get(requiredIngredient).add(recipes[recipeIndex]);
                            incomingEdgeCount.merge(recipes[recipeIndex], 1, Integer::sum);
                        }
                    }
                }
                Deque<String> recipeQueue = new ArrayDeque<>();
                for (String recipe : recipes) {
                    if (incomingEdgeCount.getOrDefault(recipe, 0) == 0) {
                        recipeQueue.offer(recipe);
                    }
                }
                outputList = new ArrayList<>();
                while (!recipeQueue.isEmpty()) {
                    String currentRecipe = recipeQueue.poll();
                    outputList.add(currentRecipe);
                    List<String> dependentRecipes = dependencyGraph.get(currentRecipe);
                    if (dependentRecipes != null) {
                        for (String dependentRecipe : dependentRecipes) {
                            incomingEdgeCount.merge(dependentRecipe, -1, Integer::sum);
                            if (incomingEdgeCount.get(dependentRecipe) == 0) {
                                recipeQueue.offer(dependentRecipe);
                            }
                        }
                    }
                }
            }
        };
    }
}