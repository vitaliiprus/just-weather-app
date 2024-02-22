package prus.justweatherapp.core.ui.preview.parameterprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BooleanPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(
        true,
        false
    )
}