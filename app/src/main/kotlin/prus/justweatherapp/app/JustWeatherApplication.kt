package prus.justweatherapp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class JustWeatherApplication : Application() {

    companion object {
        lateinit var INSTANCE: JustWeatherApplication
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        if (Timber.treeCount == 0 && BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}