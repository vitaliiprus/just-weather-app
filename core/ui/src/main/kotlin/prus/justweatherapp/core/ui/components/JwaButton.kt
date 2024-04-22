package prus.justweatherapp.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import prus.justweatherapp.core.ui.preview.parameterprovider.BooleanPreviewParameterProvider
import prus.justweatherapp.theme.JwaTheme
import prus.justweatherapp.theme.accent
import prus.justweatherapp.theme.textButtonStyle

@Composable
fun JwaButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    AnimatedContent(
        modifier = modifier,
        targetState = enabled,
        label = "",
        transitionSpec = {
            fadeIn().togetherWith(fadeOut())
        }
    ) {

        Button(
            modifier = Modifier,
            shape = MaterialTheme.shapes.small,
            colors = ButtonColors(
                containerColor = accent,
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = accent.copy(alpha = 0.5f),
                disabledContentColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            ),
            enabled = it,
            onClick = onClick
        ) {

            Text(
                text = text,
                style = textButtonStyle,
            )

        }

    }
}

@PreviewLightDark
@Composable
private fun JwaButtonPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) enabled: Boolean
) {
    JwaTheme {
        Surface {
            JwaButton(
                text = "Button",
                enabled = enabled
            )
        }
    }
}