package com.weightwatchers.ww_exercise_01.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.weightwatchers.ww_exercise_01.BuildConfig
import com.weightwatchers.ww_exercise_01.data.WeightWatchersRepository
import com.weightwatchers.ww_exercise_01.data.api.BasicHeaderInterceptor
import com.weightwatchers.ww_exercise_01.data.api.NetworkFactory
import com.weightwatchers.ww_exercise_01.data.api.WeightWatchersApiService
import com.weightwatchers.ww_exercise_01.ui.recipes.RecipesViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

val basicHeaderInterceptor = BasicHeaderInterceptor()

object NetworkApi {
    private val retrofitWeightWatchersService: WeightWatchersApiService by lazy {
        val networkFactory = NetworkFactory(moshi)
        networkFactory.createApi(
                WeightWatchersApiService::class.java,
                BuildConfig.SERVER_URL,
                listOf(basicHeaderInterceptor)
        )
    }

    fun getRetrofit() = retrofitWeightWatchersService
}


@Module
open class AppModule {

    @Provides
    @Singleton
    open fun provideRepository(): WeightWatchersRepository = WeightWatchersRepository(NetworkApi)

    @Provides
    @Singleton
    open fun provideRecipesViewModel(repository: WeightWatchersRepository): RecipesViewModel =
            RecipesViewModel(repository)

}