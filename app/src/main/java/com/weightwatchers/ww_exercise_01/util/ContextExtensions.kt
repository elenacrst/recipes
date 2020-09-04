package com.weightwatchers.ww_exercise_01.util

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Activity.getScreenHeight(): Int {
    return windowManager.currentWindowMetrics.bounds.bottom - windowManager.currentWindowMetrics.bounds.top
}

fun Activity.getScreenWidth(): Int {
    return windowManager.currentWindowMetrics.bounds.right - windowManager.currentWindowMetrics.bounds.left
}