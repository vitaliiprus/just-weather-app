package prus.justweatherapp.core.ui.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.preview.parameterprovider.BooleanPreviewParameterProvider
import prus.justweatherapp.theme.AppTheme

@Composable
fun ShimmerRectangle(
    modifier: Modifier = Modifier,
    isLoadingCompleted: Boolean = false,
    isLightModeActive: Boolean = !isSystemInDarkTheme(),
) {
    Box(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)
    )
}

@PreviewLightDark
@Composable
private fun ShimmerRectanglePreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) boolean: Boolean
) {
    AppTheme {
        Surface {
            ShimmerRectangle(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                isLoadingCompleted = boolean,
                isLightModeActive = boolean,
            )
        }
    }
}