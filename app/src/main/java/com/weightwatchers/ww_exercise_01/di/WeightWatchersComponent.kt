package com.weightwatchers.ww_exercise_01.di

import com.weightwatchers.ww_exercise_01.WeightWatchersApplication
import com.weightwatchers.ww_exercise_01.ui.MainActivity
import com.weightwatchers.ww_exercise_01.ui.recipes.RecipesFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
        modules = [AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            AppModule::class
        ]
)
@Singleton
interface WeightWatchersComponent {
    fun inject(activity: MainActivity)
    fun inject(app: WeightWatchersApplication)
    fun inject(fragment: RecipesFragment)
}