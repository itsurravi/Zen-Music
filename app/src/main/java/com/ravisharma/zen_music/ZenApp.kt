package com.ravisharma.zen_music

import android.app.Application
import android.graphics.Bitmap
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ZenApp: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).apply {
            allowRgb565(true)
            bitmapConfig(Bitmap.Config.RGB_565)
            error(R.drawable.error)
        }.build()
    }
}