package prus.justweatherapp.feature.weather.location.current.daylight

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.components.JwaLabeledText
import prus.justweatherapp.feature.weather.R
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.accent
import prus.justweatherapp.theme.purple
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun DaylightUI(
    modifier: Modifier = Modifier,
    data: DaylightUiModel
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {

        val filledColor = MaterialTheme.colorScheme.onSurface
        val dottedColor = MaterialTheme.colorScheme.outline

        val image = ImageVector.vectorResource(
            id = if (data.isDay) R.drawable.ic_sun else R.drawable.ic_moon
        )
        val imageTint = ColorFilter.tint(
            if (data.isDay) accent else purple
        )
        val imagePainter = rememberVectorPainter(image = image)

        Canvas(
            modifier = Modifier
                .width(160.dp)
                .height(80.dp),
        ) {

            val height = size.height
            val width = size.width

            val radius = (width - 40) / 2
            val circleSize = Size(radius * 2, radius * 2 - 20)
            val circleOffset = Offset(20f, 15f)

            drawArc(
                color = filledColor,
                startAngle = 180f,
                sweepAngle = 180f * (data.percentage.toFloat() / 100f),
                useCenter = false,
                size = circleSize,
                topLeft = circleOffset,
                style = Stroke(
                    width = 4f
                )
            )

            drawArc(
                color = dottedColor,
                startAngle = 180f + 180f * (data.percentage.toFloat() / 100f),
                sweepAngle = 180f * (1 - data.percentage.toFloat() / 100f),
                useCenter = false,
                size = circleSize,
                topLeft = circleOffset,
                style = Stroke(
                    width = 2f,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = FloatArray(2) { 12f }
                    )
                )
            )

            drawCircle(
                color = filledColor,
                center = Offset(20f, height - 18),
                radius = 8f,
            )

            drawCircle(
                color = dottedColor,
                center = Offset(width - 20f, height - 18),
                radius = 8f,
            )

            val percentage = data.percentage.coerceIn(2.0, 98.0)
            val angle = (180f + 180f * percentage / 100).toFloat()

            translate(
                left = radius + radius * cos(Math.PI * 2 * angle / 360).toFloat() + 5,
                top = radius + radius * sin(Math.PI * 2 * angle / 360).toFloat()
            ) {
                with(imagePainter) {
                    draw(
                        size = Size(30f, 30f),
                        colorFilter = imageTint
                    )
                }
            }
        }

        JwaLabeledText(
            label = stringResource(id = R.string.daylight),
            text = data.text
        )
    }
}

@PreviewLightDark
@Composable
private fun DaylightUIPreview(
) {
    AppTheme {
        Surface {
            DaylightUI(
                data = DaylightUiModel(
                    text = "12h 01m",
                    percentage = 30.0,
                    isDay = false
                )
            )
        }
    }
}