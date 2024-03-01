package prus.justweatherapp.core.common.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CoroutineTimer(
    private val delayMillis: Long,
    private val onFinish: () -> Unit
) {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private val timer = scope.launch {
        delay(delayMillis)
        if (isActive)
            onFinish.invoke()
    }

    fun start() {
        timer.start()
    }

    fun cancel() {
        timer.cancel()
    }
}