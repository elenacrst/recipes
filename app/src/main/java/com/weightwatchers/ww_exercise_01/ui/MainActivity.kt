package com.weightwatchers.ww_exercise_01.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.weightwatchers.ww_exercise_01.R
import com.weightwatchers.ww_exercise_01.WeightWatchersApplication
import com.weightwatchers.ww_exercise_01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.lifecycleOwner = this
        setSupportActionBar(mainBinding.tbMain)

        WeightWatchersApplication.weightWatchersAppContext.appComponent.inject(this)
    }
}
