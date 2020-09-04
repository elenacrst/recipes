package com.weightwatchers.ww_exercise_01

import com.weightwatchers.ww_exercise_01.data.model.Recipe
import org.junit.Test

import org.junit.Assert.*

class RecipeTest {
    @Test
    fun test_createRecipe() {
        var recipe = Recipe()
        assertNotNull(recipe)
        assertNull(recipe.image)
        assertNull(recipe.title)
        assertNull(recipe.filter)
        recipe = Recipe(IMAGE_URL, TITLE, FILTER)
        assertNotNull(recipe)
        assertEquals(recipe.filter, FILTER)
        assertEquals(recipe.title, TITLE)
        assertEquals(recipe.image, IMAGE_URL)
    }

    companion object {
        const val IMAGE_URL = "http://imageURL"
        const val TITLE = "oatmeal"
        const val FILTER = "breakfast"
    }
}