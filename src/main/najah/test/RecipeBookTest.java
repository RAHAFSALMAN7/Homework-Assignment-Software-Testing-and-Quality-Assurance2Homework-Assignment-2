package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;

import java.util.concurrent.TimeUnit;

class RecipeBookTest {
    RecipeBook recipeBook;
    Recipe recipe1, recipe2, recipe3;

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        
        recipe2 = new Recipe();
        recipe2.setName("Tea");
        
        recipe3 = new Recipe();
        recipe3.setName("Latte");
    }

    @Test
    @DisplayName("Test adding valid recipe")
    void testAddRecipe() {
        assertTrue(recipeBook.addRecipe(recipe1), "Failed to add a valid recipe");
        assertTrue(recipeBook.addRecipe(recipe2), "Failed to add a second valid recipe");
        assertTrue(recipeBook.addRecipe(recipe3), "Failed to add a third valid recipe");
    }

    @Test
    @DisplayName("Test adding duplicate recipe")
    void testAddDuplicateRecipe() {
        recipeBook.addRecipe(recipe1);
        assertFalse(recipeBook.addRecipe(recipe1), "Duplicate recipe should not be added");
    }

    @Test
    @DisplayName("Test deleting valid recipe")
    void testDeleteRecipe() {
        recipeBook.addRecipe(recipe1);
        assertEquals("Coffee", recipeBook.deleteRecipe(0), "Failed to delete the valid recipe");
        assertNull(recipeBook.deleteRecipe(0), "Deleted recipe should be null");
    }

    @Test
    @DisplayName("Test deleting non-existing recipe")
    void testDeleteNonExistingRecipe() {
        assertNull(recipeBook.deleteRecipe(0), "Deleting non-existing recipe should return null");
    }

    @Test
    @DisplayName("Test editing valid recipe")
    void testEditRecipe() {
        recipeBook.addRecipe(recipe1);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Espresso");
        assertEquals("Coffee", recipeBook.editRecipe(0, newRecipe), "Failed to edit the valid recipe");
        assertEquals("Espresso", recipeBook.getRecipes()[0].getName(), "Edited recipe name should match");
    }

    @Test
    @DisplayName("Test editing non-existing recipe")
    void testEditNonExistingRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Espresso");
        assertNull(recipeBook.editRecipe(0, newRecipe), "Editing non-existing recipe should return null");
    }

    @Test
    @DisplayName("Test retrieving recipes")
    void testGetRecipes() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        recipeBook.addRecipe(recipe3);
        assertEquals(3, recipeBook.getRecipes().length, "Recipes array should contain 3 recipes");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Cappuccino", "Mocha", "Americano"})
    @DisplayName("Test adding recipes with parameterized input")
    void testAddParameterizedRecipe(String recipeName) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipeName);
        assertTrue(recipeBook.addRecipe(newRecipe), "Failed to add a parameterized recipe: " + recipeName);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test timeout scenario when adding recipes")
    void testTimeout() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        try {
            Thread.sleep(50); // Simulate some delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test multiple assertions for valid recipe")
    void testMultipleAssertions() {
        recipeBook.addRecipe(recipe1);
        Recipe[] recipes = recipeBook.getRecipes();
        
        assertEquals(1, recipes.length, "Recipes array should contain 1 recipe");
        assertEquals("Coffee", recipes[0].getName(), "Recipe name should be 'Coffee'");
        assertNotNull(recipes[0], "Recipe at position 0 should not be null");
    }
}
