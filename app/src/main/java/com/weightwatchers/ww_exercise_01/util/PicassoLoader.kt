package com.weightwatchers.ww_exercise_01.util

import com.squareup.picasso.LruCache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.weightwatchers.ww_exercise_01.BuildConfig
import com.weightwatchers.ww_exercise_01.WeightWatchersApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import kotlin.math.roundToInt

class PicassoLoader {

    val picasso: Picasso by lazy {
        buildPicasso()
    }

    private val memoryCacheSizePercent = 0.3f
    private val defaultCacheSize = (1024 * 1024 * 50).toLong() // 50MB

    private fun buildPicasso(): Picasso {

        val picassoBuilder: Picasso.Builder = Picasso.Builder(WeightWatchersApplication.weightWatchersAppContext)
        val storageDirectory: File? = WeightWatchersApplication.weightWatchersAppContext.filesDir

        if (storageDirectory != null) {
            val memoryCacheSize =
                    (memoryCacheSizePercent * Runtime.getRuntime().maxMemory()).roundToInt()
            val okHttpClient = OkHttpClient.Builder()
                    .cache(Cache(storageDirectory, defaultCacheSize))
                    .build()

            val okHttp3Downloader = OkHttp3Downloader(okHttpClient)

            picassoBuilder
                    .downloader(okHttp3Downloader)
                    .memoryCache(LruCache(memoryCacheSize))
                    .loggingEnabled(BuildConfig.DEBUG)

        }
        return picassoBuilder.build()
    }
}