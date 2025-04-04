package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
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
        assertAll("Add valid recipes",
            () -> assertTrue(recipeBook.addRecipe(recipe1), "Failed to add 'Coffee' recipe"),
            () -> assertTrue(recipeBook.addRecipe(recipe2), "Failed to add 'Tea' recipe"),
            () -> assertTrue(recipeBook.addRecipe(recipe3), "Failed to add 'Latte' recipe")
        );
    }

    @Test
    @DisplayName("Test adding duplicate recipe")
    void testAddDuplicateRecipe() {
        recipeBook.addRecipe(recipe1);
        assertFalse(recipeBook.addRecipe(recipe1), "Duplicate recipe should not be added");
    }

    @Test
    @DisplayName("Test adding empty recipe name")
    void testAddEmptyRecipeName() {
        Recipe emptyRecipe = new Recipe();
        emptyRecipe.setName("");
        assertThrows(IllegalArgumentException.class, () -> recipeBook.addRecipe(emptyRecipe), "Recipe name should not be empty");
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
        
        assertAll("Multiple assertions for recipe",
            () -> assertEquals(1, recipes.length, "Recipes array should contain 1 recipe"),
            () -> assertEquals("Coffee", recipes[0].getName(), "Recipe name should be 'Coffee'"),
            () -> assertNotNull(recipes[0], "Recipe at position 0 should not be null")
        );
    }

    @Test
    @DisplayName("Test recipe list is updated after deletion")
    void testRecipeListAfterDeletion() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        recipeBook.deleteRecipe(0);  // Deleting the first recipe
        assertEquals(1, recipeBook.getRecipes().length, "Recipe list should have 1 recipe after deletion");
        assertEquals("Tea", recipeBook.getRecipes()[0].getName(), "Remaining recipe should be 'Tea'");
    }

    @Test
    @DisplayName("Test adding null recipe")
    void testAddNullRecipe() {
        assertThrows(IllegalArgumentException.class, () -> recipeBook.addRecipe(null), "Null recipes should not be added");
    }

    @Test
    @DisplayName("Test editing recipe with invalid index")
    void testEditRecipeWithInvalidIndex() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Espresso");
        assertNull(recipeBook.editRecipe(99, newRecipe), "Editing with an invalid index should return null");
    }

    @Test
    @DisplayName("Test adding a large number of recipes")
    void testAddLargeNumberOfRecipes() {
        for (int i = 0; i < 1000; i++) {
            Recipe newRecipe = new Recipe();
            newRecipe.setName("Recipe " + i);
            assertTrue(recipeBook.addRecipe(newRecipe), "Failed to add recipe number " + i);
        }
        assertEquals(1000, recipeBook.getRecipes().length, "Recipe book should contain 1000 recipes");
    }

    @Test
    @DisplayName("Test adding recipe with special characters in name")
    void testAddRecipeWithSpecialCharacters() {
        Recipe specialRecipe = new Recipe();
        specialRecipe.setName("Espresso@#!");
        assertTrue(recipeBook.addRecipe(specialRecipe), "Failed to add recipe with special characters in name");
    }

    @Test
    @DisplayName("Test adding recipe with a long name")
    void testAddLongRecipeName() {
        Recipe longNameRecipe = new Recipe();
        longNameRecipe.setName("Super Long Recipe Name That Exceeds Normal Length");
        assertTrue(recipeBook.addRecipe(longNameRecipe), "Failed to add recipe with long name");
    }

    @Test
    @DisplayName("Test deleting all recipes")
    void testDeleteAllRecipes() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        recipeBook.addRecipe(recipe3);
        recipeBook.deleteRecipe(0); // Deleting Coffee
        recipeBook.deleteRecipe(0); // Deleting Tea
        recipeBook.deleteRecipe(0); // Deleting Latte
        assertEquals(0, recipeBook.getRecipes().length, "Recipe list should be empty after deleting all recipes");
    }
}
