package prus.justweatherapp.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import prus.justweatherapp.theme.JwaTheme

@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imagePainter: Painter? = null,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = ColorPainter(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.FillBounds
            )
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imagePainter != null) {
                Image(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    painter = imagePainter,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = "image"
                )
                Spacer(Modifier.height(12.dp))
            }
            Text(
                text = title,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold

            )
            Spacer(Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .alpha(0.6f),
                text = subtitle,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )

            if (buttonText != null) {
                Spacer(Modifier.height(20.dp))
                JwaButton(
                    text = buttonText,
                    onClick = { onButtonClick?.invoke() }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MessageScreenPreview() {
    JwaTheme {
        MessageScreen(
            title = "No results found",
            subtitle = "This message explains what is wrong and how to fix this.",
            buttonText = "Button"
        )
    }
}
