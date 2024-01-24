package prus.justweatherapp.app

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JustWeatherApplication : Application() {

    companion object {
        lateinit var INSTANCE: JustWeatherApplication

        fun getRes(): Resources {
            return INSTANCE.resources
        }
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }

}