package com.weightwatchers.ww_exercise_01

import com.weightwatchers.ww_exercise_01.di.NetworkApi
import org.junit.Test

import org.junit.Assert.*

class DependencyInjectionTest {
    @Test
    fun test_getRetrofit() {
        assertNotNull(NetworkApi.getRetrofit())
    }
}