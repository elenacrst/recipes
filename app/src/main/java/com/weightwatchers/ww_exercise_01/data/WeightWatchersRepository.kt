package com.weightwatchers.ww_exercise_01.data

import com.weightwatchers.ww_exercise_01.R
import com.weightwatchers.ww_exercise_01.WeightWatchersApplication
import com.weightwatchers.ww_exercise_01.data.api.NoNetworkConnectionException
import com.weightwatchers.ww_exercise_01.data.model.Recipe
import com.weightwatchers.ww_exercise_01.di.NetworkApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class WeightWatchersRepository @Inject constructor(private val networkApi: NetworkApi) {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    @Throws(Exception::class)
    suspend fun getRecipes(): Result<*> {
        var response: Response<List<Recipe>>? = null

        withContext(ioDispatcher) {
            try {
                response = networkApi.getRetrofit().getRecipes()
            } catch (e: Exception) {
                e.printStackTrace()
                throw NoNetworkConnectionException()
            }
        }

        return if (response!!.isSuccessful) {
            Result.Success(response?.body())
        } else {
            Result.Error(
                    message = WeightWatchersApplication.weightWatchersAppContext.getString(
                            R.string.general_error_message
                    ),
                    code = response!!.code()
            )
        }
    }
}