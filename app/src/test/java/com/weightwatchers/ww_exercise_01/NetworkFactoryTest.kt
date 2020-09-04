package com.weightwatchers.ww_exercise_01

import com.squareup.moshi.Moshi
import com.weightwatchers.ww_exercise_01.data.api.NetworkFactory
import com.weightwatchers.ww_exercise_01.data.api.WeightWatchersApiService
import io.mockk.mockk
import okhttp3.Interceptor
import org.junit.Test

import org.junit.Assert.*

class NetworkFactoryTest {
    @Test
    fun test_createApiWithInterceptors() {
        val moshi = mockk<Moshi>()
        val networkFactory = NetworkFactory(moshi)
        val interceptor = mockk<Interceptor>()
        val api = networkFactory.createApi(WeightWatchersApiService::class.java, BASE_URL, listOf(interceptor))
        assertNotNull(api)
    }

    companion object {
        const val BASE_URL = "http://baseURL"
    }
}