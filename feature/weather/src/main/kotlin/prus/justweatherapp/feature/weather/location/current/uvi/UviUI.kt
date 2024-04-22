package prus.justweatherapp.feature.weather.location.current.uvi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import prus.justweatherapp.feature.weather.R
import prus.justweatherapp.theme.JwaTheme
import prus.justweatherapp.theme.green
import prus.justweatherapp.theme.orange
import prus.justweatherapp.theme.red
import prus.justweatherapp.theme.violet
import prus.justweatherapp.theme.yellow

@Composable
internal fun UviUI(
    modifier: Modifier = Modifier,
    uvIndex: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.uv_index),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontSize = 14.sp
        )

        Row {
            for (i in 0..10) {
                UviItem(
                    itemIndex = i,
                    currentIndex = uvIndex
                )
            }
            if (uvIndex > 10) {
                UviItem(
                    itemIndex = uvIndex,
                    currentIndex = uvIndex
                )
            }
        }
    }
}

@Composable
private fun UviItem(
    itemIndex: Int,
    currentIndex: Int
) {
    Text(
        text = if (itemIndex != currentIndex) "-" else currentIndex.toString(),
        fontWeight = FontWeight.SemiBold,
        color = getUviColor(itemIndex),
        fontSize = 16.sp
    )
}

@Composable
private fun getUviColor(uviIndex: Int): Color {
    return when (uviIndex) {
        0, 1, 2 -> green
        3, 4, 5 -> yellow
        6, 7 -> orange
        8, 9, 10 -> red
        else -> violet
    }
}

@PreviewLightDark
@Composable
private fun UviUIPreview(
    @PreviewParameter(UviValuePreviewParameterProvider::class) value: Int
) {
    JwaTheme {
        Surface {
            UviUI(
                uvIndex = value
            )
        }
    }
}

class UviValuePreviewParameterProvider : PreviewParameterProvider<Int> {
    private fun createValues(): Sequence<Int> {
        var values = sequenceOf<Int>()
        for (i in 0..12) {
            values = values.plus(i)
        }
        return values
    }

    override val values = createValues()
}