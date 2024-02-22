package prus.justweatherapp.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.R
import prus.justweatherapp.theme.AppTheme

@Composable
fun JwaTextField(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onValueChanged: (String) -> Unit,
    onFocused: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholderValue: String = "",
) {
    val clearButtonVisible = textFieldValue.isNotEmpty()

    TextField(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused)
                    onFocused.invoke()
            },
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.onTertiary,
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        value = textFieldValue,
        onValueChange = onValueChanged,
        leadingIcon = leadingIcon,
        trailingIcon = {
            AnimatedVisibility(
                visible = clearButtonVisible,
                enter = fadeIn(animationSpec = tween(150))
                        + scaleIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(150))
                        + scaleOut(animationSpec = tween(100))
            ) {
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp)
                        .clickable {
                            onValueChanged("")
                        },
                    painter = painterResource(id = R.drawable.ic_close_circle),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "clear"
                )
            }
        },
        placeholder = {
            Text(
                text = placeholderValue,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        singleLine = true
    )
}

@PreviewLightDark
@Composable
private fun FindLocationsSearchBarPreview() {
    AppTheme {
        Surface {
            JwaTextField(
                textFieldValue = "Saint Petersburg",
                placeholderValue = "",
                onValueChanged = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FindLocationsSearchBarEmptyPreview() {
    AppTheme {
        Surface {
            JwaTextField(
                textFieldValue = "",
                placeholderValue = "Find locations",
                onValueChanged = {}
            )
        }
    }
}