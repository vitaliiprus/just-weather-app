package prus.justweatherapp.core.ui.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import prus.justweatherapp.core.ui.shape.ArcShape
import prus.justweatherapp.theme.JwaTheme

@Composable
fun ShimmerSemiCircle(
    modifier: Modifier = Modifier,
    isLoadingCompleted: Boolean = false,
    isLightModeActive: Boolean = !isSystemInDarkTheme(),
) {
    Box(
        modifier = modifier
            .clip(shape = ArcShape())
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .shimmerLoadingAnimation(isLoadingCompleted, isLightModeActive)
    )
}

@PreviewLightDark
@Composable
private fun ShimmerHalfCirclePreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) boolean: Boolean
) {
    JwaTheme {
        Surface {
            ShimmerSemiCircle(
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
                isLoadingCompleted = boolean,
                isLightModeActive = boolean,
            )
        }
    }
}