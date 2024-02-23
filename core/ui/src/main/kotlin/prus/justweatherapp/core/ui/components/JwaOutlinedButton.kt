package prus.justweatherapp.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.preview.parameterprovider.BooleanPreviewParameterProvider
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.accent
import prus.justweatherapp.theme.textButtonStyle

@Composable
fun JwaOutlinedButton(
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

        OutlinedButton(
            modifier = Modifier,
            shape = MaterialTheme.shapes.small,
            colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = accent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = accent.copy(alpha = 0.5f)
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (it) accent else accent.copy(0.5f)
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
private fun JwaOutlinedButtonPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) enabled: Boolean
) {
    AppTheme {
        Surface {
            JwaOutlinedButton(
                text = "Button",
                enabled = enabled
            )
        }
    }
}
