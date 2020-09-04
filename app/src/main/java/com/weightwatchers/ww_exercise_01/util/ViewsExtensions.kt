package com.weightwatchers.ww_exercise_01.util

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.weightwatchers.ww_exercise_01.R
import com.weightwatchers.ww_exercise_01.WeightWatchersApplication


fun ImageView.loadImage(imageUrl: String, activity: Activity) {
    val picasso = PicassoLoader().picasso
    picasso
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .resize(
                    activity.getScreenWidth(),
                    activity.getScreenHeight()
            )
            .into(this, object : Callback {
                override fun onSuccess() {
                    setBackgroundColor(ContextCompat.getColor(WeightWatchersApplication.weightWatchersAppContext, android.R.color.transparent))
                }

                override fun onError(e: Exception?) {
                    setBackgroundColor(ContextCompat.getColor(WeightWatchersApplication.weightWatchersAppContext, R.color.colorPrimaryDark))
                }
            })
}

fun View.showSnackBar(message: String) {
    val snackBar = Snackbar
            .make(this, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.action_close) {}
    snackBar.show()
}