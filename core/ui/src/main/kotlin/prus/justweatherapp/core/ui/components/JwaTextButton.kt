package prus.justweatherapp.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import prus.justweatherapp.core.ui.preview.parameterprovider.BooleanPreviewParameterProvider
import prus.justweatherapp.theme.JwaTheme
import prus.justweatherapp.theme.accent
import prus.justweatherapp.theme.textButtonStyle

@Composable
fun JwaTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    color: Color = accent,
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

        TextButton(
            modifier = Modifier,
            colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = color,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = color.copy(alpha = 0.5f)
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
private fun JwaTextButtonPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) enabled: Boolean
) {
    JwaTheme {
        Surface {
            JwaTextButton(
                text = "Button",
                enabled = enabled
            )
        }
    }
}