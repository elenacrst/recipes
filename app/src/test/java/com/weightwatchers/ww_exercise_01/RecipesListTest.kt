package com.weightwatchers.ww_exercise_01

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RecipesListTest : BaseUnitTest() {

    @Before
    fun start() {
        super.setUp()
    }

    @Test
    fun test_getRecipes_success() = runBlocking {
        setEndpointPath("/assets/cmx/us/messages/collections.json")
        setMockResponseCode(200)
        setMockResponseBody(getJson("get_recipes_success_response.json"))
        val dataReceived = repository.getRecipes()
        Assert.assertNotNull(dataReceived)
        val response = (dataReceived as com.weightwatchers.ww_exercise_01.data.Result.Success).data as List<*>
        Assert.assertTrue(response.isNotEmpty())
        Assert.assertTrue(response.size == 2)
    }

    @Test
    fun test_getRecipes_serverError() = runBlocking {
        setEndpointPath("/assets/cmx/us/messages/collections.json")
        setMockResponseCode(500)
        setMockResponseBody("{\"errors\":[\"Something went wrong. Please try again later.\"]}")
        val dataReceived = repository.getRecipes()
        Assert.assertNotNull(dataReceived)
        val response = dataReceived as com.weightwatchers.ww_exercise_01.data.Result.Error
        Assert.assertEquals(response.code, 500)
        Assert.assertNotNull(response.message)
        Assert.assertEquals(response.message, "Server error!")
    }
}