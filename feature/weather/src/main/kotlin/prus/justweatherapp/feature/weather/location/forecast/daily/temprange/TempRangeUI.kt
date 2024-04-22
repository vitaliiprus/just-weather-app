package prus.justweatherapp.feature.weather.location.forecast.daily.temprange

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import prus.justweatherapp.theme.JwaTheme
import kotlin.math.roundToInt

@Composable
internal fun TempRangeUI(
    modifier: Modifier = Modifier,
    data: TempRangeModel
) {
    Column(
        modifier = Modifier
            .height(32.dp)
            .then(modifier),
        verticalArrangement = Arrangement.Center
    ) {

        Row {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "${data.dayMinTemp.roundToInt()}º",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Text(
                text = "${data.dayMaxTemp.roundToInt()}º",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        val colorBackground = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        val colorRight = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f)

        val colorCircle = MaterialTheme.colorScheme.onSurface
        val colorCircleBackground = MaterialTheme.colorScheme.surface

        val strokeWidth = 10f
        val lineOffset = 5f
        val gradientColors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onSurface,
        )

        Canvas(
            modifier = Modifier
                .width(160.dp)
                .height(80.dp),
        ) {
            val rangeWidth = data.rangeMaxTemp - data.rangeMinTemp
            val gradientStart =
                ((data.dayMinTemp - data.rangeMinTemp) / rangeWidth * size.width).toFloat() + lineOffset
            val gradientEnd =
                ((data.dayMaxTemp - data.rangeMinTemp) / rangeWidth * size.width).toFloat() - lineOffset


            drawLine(
                color = colorBackground,
                start = Offset(lineOffset, 0f),
                end = Offset(size.width - lineOffset, 0f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = colorRight,
                start = Offset(gradientEnd, 0f),
                end = Offset(size.width - lineOffset, 0f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                brush = Brush.linearGradient(
                    colors = gradientColors
                ),
                start = Offset(gradientStart, 0f),
                end = Offset(gradientEnd, 0f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            if (data.currentTemp != null) {
                val circleOffset = Offset(
                    x = ((data.currentTemp - data.rangeMinTemp) / rangeWidth * size.width).toFloat() - lineOffset,
                    y = strokeWidth / 2f - strokeWidth / 2f
                )

                drawCircle(
                    color = colorCircleBackground,
                    radius = strokeWidth + 2f,
                    center = circleOffset
                )

                drawCircle(
                    color = colorCircle,
                    radius = strokeWidth - 2f,
                    center = circleOffset
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TempRangeUiPreview(
    @PreviewParameter(TempRangeModelParameterProvider::class) data: TempRangeModel
) {
    JwaTheme {
        Surface {
            TempRangeUI(
                modifier = Modifier
                    .width(150.dp),
                data = data
            )
        }
    }
}

class TempRangeModelParameterProvider : PreviewParameterProvider<TempRangeModel> {
    override val values = sequenceOf(
        TempRangeModel(
            dayMinTemp = -2.0,
            dayMaxTemp = 9.0,
            currentTemp = 6.0,
            rangeMinTemp = -2.0,
            rangeMaxTemp = 12.0
        ),
        TempRangeModel(
            dayMinTemp = 1.0,
            dayMaxTemp = 10.0,
            rangeMinTemp = -2.0,
            rangeMaxTemp = 12.0
        ),
        TempRangeModel(
            dayMinTemp = 7.0,
            dayMaxTemp = 12.0,
            rangeMinTemp = -2.0,
            rangeMaxTemp = 12.0
        ),
        TempRangeModel(
            dayMinTemp = 5.0,
            dayMaxTemp = 12.0,
            rangeMinTemp = -2.0,
            rangeMaxTemp = 12.0
        ),
    )
}