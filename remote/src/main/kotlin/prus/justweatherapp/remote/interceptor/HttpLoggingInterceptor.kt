package prus.justweatherapp.remote.interceptor

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import prus.justweatherapp.remote.BuildConfig

fun getHttpLoggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }