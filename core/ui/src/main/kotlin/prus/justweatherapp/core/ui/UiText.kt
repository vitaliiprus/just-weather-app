package prus.justweatherapp.core.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data object Empty : UiText()

    data class DynamicString(
        val value: String
    ) : UiText()

    class StringResource(
        @StringRes val id: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context? = null): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value
            is StringResource -> context?.getString(id, *args).orEmpty()
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value
            is StringResource -> stringResource(id, *args)
        }
    }
}