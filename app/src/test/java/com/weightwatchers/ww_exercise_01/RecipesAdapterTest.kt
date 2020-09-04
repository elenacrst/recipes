package com.weightwatchers.ww_exercise_01

import com.weightwatchers.ww_exercise_01.ui.recipes.RecipeListener
import com.weightwatchers.ww_exercise_01.ui.recipes.RecipesAdapter
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*

class RecipesAdapterTest {
    @Test
    fun test_create() {
        val adapter = RecipesAdapter(RecipeListener { }, mockk())

        assertNotNull(adapter)
        assertEquals(adapter.itemCount, 0)
    }
}