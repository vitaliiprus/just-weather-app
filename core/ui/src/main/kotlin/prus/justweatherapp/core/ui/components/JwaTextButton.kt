package prus.justweatherapp.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import prus.justweatherapp.theme.accent
import prus.justweatherapp.theme.textButtonStyle

@Composable
fun JwaTextButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = accent,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = text,
            color = color,
            style = textButtonStyle,
        )
    }
}