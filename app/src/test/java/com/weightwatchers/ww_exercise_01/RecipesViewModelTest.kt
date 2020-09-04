package com.weightwatchers.ww_exercise_01

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weightwatchers.ww_exercise_01.data.Result
import com.weightwatchers.ww_exercise_01.ui.recipes.RecipesViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipesViewModelTest : BaseUnitTest() {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Test
    fun test_getCategories() {
        setEndpointPath("/assets/cmx/us/messages/collections.json")
        setMockResponseCode(200)
        setMockResponseBody(getJson("get_recipes_success_response.json"))
        val viewModel = RecipesViewModel(repository)
        Assert.assertNull(viewModel.recipes.value)
        Assert.assertNotNull(viewModel.recipesEvent.value)
        Assert.assertEquals(viewModel.recipesEvent.value!!.peekContent(), Result.None)
        runBlockingTest {
            flow<Unit> {
                viewModel.getCategories()
            }.collect {
                Assert.assertNotNull(viewModel.recipes.value)
                Assert.assertEquals(viewModel.recipes.value!!.size, 2)
                Assert.assertTrue(viewModel.recipesEvent.value!!.peekContent() is Result.Success)
            }
        }
    }
}
