package com.weightwatchers.ww_exercise_01

import android.app.Activity
import android.graphics.Rect
import android.view.WindowManager
import android.view.WindowMetrics
import com.weightwatchers.ww_exercise_01.util.Event
import com.weightwatchers.ww_exercise_01.util.getScreenHeight
import com.weightwatchers.ww_exercise_01.util.getScreenWidth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox

@RunWith(PowerMockRunner::class)
@PrepareForTest(Rect::class, WeightWatchersApplication::class)
@PowerMockIgnore(
        "io.mockk.proxy.jvm.dispatcher.JvmMockKWeakMap",
        "io.mockk.proxy.jvm.dispatcher.JvmMockKDispatcher"
)
class UtilsTest {

    @Test
    fun test_contextExtensions() {
        val windowManager = PowerMockito.mock(WindowManager::class.java)
        val windowMetrics = PowerMockito.mock(WindowMetrics::class.java)
        val bounds = PowerMockito.mock(Rect::class.java)
        Whitebox.setInternalState(bounds, "top", 10)
        Whitebox.setInternalState(bounds, "bottom", 30)
        Whitebox.setInternalState(bounds, "left", 5)
        Whitebox.setInternalState(bounds, "right", 15)

        PowerMockito.`when`(windowMetrics.bounds)
                .thenReturn(bounds)
        PowerMockito.`when`(windowManager.currentWindowMetrics)
                .thenReturn(windowMetrics)
        val activity: Activity = PowerMockito.mock(Activity::class.java)
        PowerMockito.`when`(activity.windowManager).thenReturn(windowManager)
        assertEquals(activity.getScreenHeight(), 20)
        assertEquals(activity.getScreenWidth(), 10)
    }

    @Test
    fun test_event() {
        val event = Event(EVENT_CONTENT)
        assertEquals(event.getContentIfNotHandled(), EVENT_CONTENT)
        assertNull(event.getContentIfNotHandled())
        assertEquals(event.peekContent(), EVENT_CONTENT)
    }

    companion object {
        const val EVENT_CONTENT = "content"
    }
}