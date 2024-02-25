package prus.justweatherapp.core.ui.dragdrop

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun Modifier.dragGestureHandler(
    scope: CoroutineScope,
    itemListDragAndDropState: ItemListDragAndDropState,
    overscrollJob: MutableState<Job?>
): Modifier = this.pointerInput(Unit) {
    detectDragGestures(
        onDrag = { change, offset ->
            if (!itemListDragAndDropState.dragDropEnabled) {
                return@detectDragGestures
            }
            change.consume()
            itemListDragAndDropState.onDrag(offset)
            handleOverscrollJob(overscrollJob, scope, itemListDragAndDropState)
        },
        onDragStart = { offset ->
            if (!itemListDragAndDropState.dragDropEnabled) {
                return@detectDragGestures
            }
            itemListDragAndDropState.onDragStart(offset)
        },
        onDragEnd = {
            itemListDragAndDropState.onDragInterrupted()
        },
        onDragCancel = {
            itemListDragAndDropState.onDragInterrupted()
        }
    )
}

private fun handleOverscrollJob(
    overscrollJob: MutableState<Job?>,
    scope: CoroutineScope,
    itemListDragAndDropState: ItemListDragAndDropState
) {
    if (overscrollJob.value?.isActive == true) return
    val overscrollOffset = itemListDragAndDropState.checkForOverScroll()
    if (overscrollOffset != 0f) {
        overscrollJob.value = scope.launch {
            itemListDragAndDropState.getLazyListState().scrollBy(overscrollOffset)
        }
    } else {
        overscrollJob.value?.cancel()
    }
}

fun Modifier.dragDropStateChangeHandler(
    onDragDropStateChanged: (Boolean) -> Unit
): Modifier = this.pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            if (event.type == PointerEventType.Press)
                onDragDropStateChanged(true)
            else if (event.type == PointerEventType.Release)
                onDragDropStateChanged(false)
        }
    }
}