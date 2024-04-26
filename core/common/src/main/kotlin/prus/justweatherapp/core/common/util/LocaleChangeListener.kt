package prus.justweatherapp.core.common.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleChangeListener @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val _localeState = MutableStateFlow(context.resources?.configuration?.locales?.get(0))
    val localeState = _localeState.asStateFlow()

    init {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
                    val locale = context?.resources?.configuration?.locales?.get(0)
                    if (locale != null) {
                        _localeState.tryEmit(locale)
                    }
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
    }
}