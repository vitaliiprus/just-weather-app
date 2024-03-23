package prus.justweatherapp.core.ui.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
fun ShimmerCircle(
    modifier: Modifier = Modifier,
    isLoadingCompleted: Boolean = false,
    isLightModeActive: Boolean = !isSystemInDarkTheme(),
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)
    )
}

@PreviewLightDark
@Composable
private fun ShimmerCirclePreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) boolean: Boolean
) {
    AppTheme {
        Surface {
            ShimmerCircle(
                modifier = Modifier
                    .size(100.dp),
                isLoadingCompleted = boolean,
                isLightModeActive = boolean,
            )
        }
    }
}