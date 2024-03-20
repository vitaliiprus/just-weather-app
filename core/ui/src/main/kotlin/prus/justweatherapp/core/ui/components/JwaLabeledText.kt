package prus.justweatherapp.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import prus.justweatherapp.theme.AppTheme

@Composable
fun JwaLabeledText(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontSize = 14.sp
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
    }

}

@PreviewLightDark
@Composable
private fun JwaLabeledTextPreview(
) {
    AppTheme {
        Surface {
            JwaLabeledText(
                label = "Label",
                text = "Value",
            )
        }
    }
}