package com.weightwatchers.ww_exercise_01

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.weightwatchers.ww_exercise_01.util.PicassoLoader
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class PicassoLoaderTest {
    private lateinit var ctx: Context

    @Before
    fun prepareApplication() {
        ctx = InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as Context
    }

    @Test
    fun test_picasso() {
        val picasso = PicassoLoader().picasso
        Assert.assertNotNull(picasso)
    }

}