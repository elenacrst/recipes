package com.weightwatchers.ww_exercise_01.data.api

import com.weightwatchers.ww_exercise_01.data.model.Recipe
import retrofit2.Response
import retrofit2.http.*

interface WeightWatchersApiService {

    @GET("/assets/cmx/us/messages/collections.json")
    suspend fun getRecipes(): Response<List<Recipe>>
}
