package prus.justweatherapp.core.ui.dragdrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.Job

@Composable
fun <T> DragDropLazyColumn(
    modifier: Modifier = Modifier,
    items: List<T>,
    dragDropListState: ItemListDragAndDropState,
    contentPadding: PaddingValues = PaddingValues(),
    itemComposable: @Composable ((T) -> Unit),
) {
    val itemsState = remember { mutableStateOf(items) }
    val coroutineScope = rememberCoroutineScope()
    val overscrollJob = remember { mutableStateOf<Job?>(null) }

    dragDropListState.onSwap =
        { fromIndex, toIndex ->
            itemsState.value = itemsState.value.swap(fromIndex, toIndex)
        }

    LazyColumn(
        modifier = modifier.dragGestureHandler(coroutineScope, dragDropListState, overscrollJob),
        state = dragDropListState.getLazyListState(),
        contentPadding = contentPadding
    ) {
        itemsIndexed(itemsState.value) { index, item ->
            val displacementOffset =
                if (index == dragDropListState.getCurrentIndexOfDraggedListItem()) {
                    dragDropListState.elementDisplacement.takeIf { it != 0f }
                } else {
                    null
                }
            DragDropListItem(displacementOffset) {
                itemComposable.invoke(item)
            }
        }
    }
}

@Composable
private fun DragDropListItem(
    displacementOffset: Float?,
    content: @Composable (() -> Unit)
) {
    Box(
        modifier = Modifier
            .graphicsLayer { translationY = displacementOffset ?: 0f }
            .fillMaxSize()
    ) {
        content()
    }
}