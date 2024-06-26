package prus.justweatherapp.core.ui.dragdrop

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.Job

@Composable
fun rememberDragDropListState(
    lazyListState: LazyListState = rememberLazyListState(),
    onDragFinish: (Int, Int) -> Unit
): ItemListDragAndDropState {
    return remember { ItemListDragAndDropState(lazyListState, onDragFinish) }
}

class ItemListDragAndDropState(
    private val lazyListState: LazyListState,
    private val onDragFinish: (Int, Int) -> Unit,
) {
    private var draggedDistance by mutableFloatStateOf(0f)
    private var initiallyDraggedElement by mutableStateOf<LazyListItemInfo?>(null)
    private var currentIndexOfDraggedItem by mutableIntStateOf(-1)
    private var overscrollJob by mutableStateOf<Job?>(null)
    var dragDropEnabled by mutableStateOf(true)

    var onSwap: (Int, Int) -> Unit = { _, _ -> }

    private val currentElement: LazyListItemInfo?
        get() = currentIndexOfDraggedItem.let {
            lazyListState.getVisibleItemInfoFor(absoluteIndex = it)
        }

    private val initialOffsets: Pair<Int, Int>?
        get() = initiallyDraggedElement?.let { Pair(it.offset, it.offsetEnd) }

    val elementDisplacement: Float?
        get() = currentIndexOfDraggedItem
            .let { lazyListState.getVisibleItemInfoFor(absoluteIndex = it) }
            ?.let { item ->
                (initiallyDraggedElement?.offset ?: 0f).toFloat() + draggedDistance - item.offset
            }

    fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo
            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
            ?.also {
                currentIndexOfDraggedItem = it.index
                initiallyDraggedElement = it
            }
    }

    fun onDragInterrupted() {
        initiallyDraggedElement?.index?.let { fromIndex ->
            onDragFinish.invoke(fromIndex, currentIndexOfDraggedItem)
        }
        draggedDistance = 0f
        currentIndexOfDraggedItem = -1
        initiallyDraggedElement = null
        overscrollJob?.cancel()
    }

    private fun calculateOffsets(offset: Float): Pair<Float, Float> {
        val startOffset = offset + draggedDistance
        val currentElementSize = currentElement?.size ?: 0
        val endOffset = offset + draggedDistance + currentElementSize
        return startOffset to endOffset
    }

    fun onDrag(offset: Offset) {
        draggedDistance += offset.y
        val topOffset = initialOffsets?.first ?: return
        val (startOffset, endOffset) = calculateOffsets(topOffset.toFloat())

        val hoveredElement = currentElement
        if (hoveredElement != null) {
            val delta = startOffset - hoveredElement.offset
            val isDeltaPositive = delta > 0
            val isEndOffsetGreater = endOffset > hoveredElement.offsetEnd

            val validItems = lazyListState.layoutInfo.visibleItemsInfo.filter { item ->
                !(item.offsetEnd < startOffset || item.offset > endOffset || hoveredElement.index == item.index)
            }

            val targetItem = validItems.firstOrNull {
                when {
                    isDeltaPositive -> isEndOffsetGreater
                    else -> startOffset < it.offset
                }
            }

            if (targetItem != null) {
                currentIndexOfDraggedItem.let { current ->
                    onSwap.invoke(current, targetItem.index)
                    currentIndexOfDraggedItem = targetItem.index
                }
            }
        }
    }

    fun checkForOverScroll(): Float {
        val draggedElement = initiallyDraggedElement
        if (draggedElement != null) {
            val (startOffset, endOffset) = calculateOffsets(draggedElement.offset.toFloat())
            val diffToEnd = endOffset - lazyListState.layoutInfo.viewportEndOffset
            val diffToStart = startOffset - lazyListState.layoutInfo.viewportStartOffset
            return when {
                draggedDistance > 0 && diffToEnd > 0 -> diffToEnd
                draggedDistance < 0 && diffToStart < 0 -> diffToStart
                else -> 0f
            }
        }
        return 0f
    }

    fun getLazyListState(): LazyListState {
        return lazyListState
    }

    fun getCurrentIndexOfDraggedListItem(): Int {
        return currentIndexOfDraggedItem
    }
}

fun LazyListState.getVisibleItemInfoFor(absoluteIndex: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(
        absoluteIndex - this.layoutInfo.visibleItemsInfo.first().index
    )
}

val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size
