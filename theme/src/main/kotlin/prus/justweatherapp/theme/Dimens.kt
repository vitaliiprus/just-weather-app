package prus.justweatherapp.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

open class Dimens {

    val screenPaddings = ContentPaddings(Paddings.large, Paddings.large)
    val contentPaddings = ContentPaddings(Paddings.large, Paddings.large)
    val dialogPaddings = ContentPaddings(Paddings.large, Paddings.large)

    val topBarHeight = 60.dp

    class ContentPaddings(
        val start: Dp,
        val end: Dp,
        val top: Dp,
        val bottom: Dp,
    ) {

        constructor(horizontal: Dp, vertical: Dp) : this(
            start = horizontal,
            end = horizontal,
            top = vertical,
            bottom = vertical,
        )
    }

    open class Paddings {
        val default = 8.dp
        val small = 4.dp
        val large = 16.dp
        val extraLarge = 24.dp

        companion object : Paddings()
    }

    companion object : Dimens()
}

fun Modifier.screenContentPaddings(
    start: Dp = Dimens.screenPaddings.start,
    end: Dp = Dimens.screenPaddings.end,
    top: Dp = Dimens.screenPaddings.top,
    bottom: Dp = Dimens.screenPaddings.bottom,
): Modifier {
    return this.then(padding(start, top, end, bottom))
}
fun Modifier.dialogPaddings(
    start: Dp = Dimens.dialogPaddings.start,
    end: Dp = Dimens.dialogPaddings.end,
    top: Dp = Dimens.dialogPaddings.top,
    bottom: Dp = Dimens.dialogPaddings.bottom,
): Modifier {
    return this.then(padding(start, top, end, bottom))
}

fun Modifier.contentPaddings(
    start: Dp = Dimens.contentPaddings.start,
    end: Dp = Dimens.contentPaddings.end,
    top: Dp = Dimens.contentPaddings.top,
    bottom: Dp = Dimens.contentPaddings.bottom,
): Modifier {
    return this.then(padding(start, top, end, bottom))
}

fun Modifier.startEndPaddings(
    start: Dp = Dimens.contentPaddings.start,
    end: Dp = Dimens.contentPaddings.end,
): Modifier {
    return this.then(padding(start, 0.dp, end, 0.dp))
}

fun Modifier.topBarSize(
    height: Dp = Dimens.topBarHeight,
): Modifier {
    return this
        .then(height(height))
        .then(fillMaxWidth())
}