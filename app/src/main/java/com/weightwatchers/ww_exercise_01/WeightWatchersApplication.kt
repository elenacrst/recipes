package com.weightwatchers.ww_exercise_01

import android.app.Application
import com.weightwatchers.ww_exercise_01.di.DaggerWeightWatchersComponent
import com.weightwatchers.ww_exercise_01.di.WeightWatchersComponent
import timber.log.Timber


class WeightWatchersApplication : Application() {

    private lateinit var _appComponent: WeightWatchersComponent
    val appComponent: WeightWatchersComponent
        get() = _appComponent

    override fun onCreate() {
        super.onCreate()

        weightWatchersAppContext = this

        _appComponent = DaggerWeightWatchersComponent.builder().build()
        appComponent.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var weightWatchersAppContext: WeightWatchersApplication
    }
}