package prus.justweatherapp.feature.locations.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.feature.locations.model.LocationUiModel
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.contentPaddings

@Composable
fun UserLocationListItem(
    location: LocationUiModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .contentPaddings(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {

                Text(
                    modifier = Modifier
                        .alpha(0.5f),
                    text = location.time,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.labelSmall
                )

                Text(
                    text = location.name,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = location.weatherConditions.asString(),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodySmall
                )

            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .offset(
                            y = 2.dp
                        ),
                    text = location.currentTemp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 34.sp,
                )

                Text(
                    modifier = Modifier
                        .offset(
                            y = (-2).dp
                        ),
                    text = location.minMaxTemp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )

            }

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                modifier = Modifier
                    .size(50.dp),
                painter = painterResource(id = R.drawable.ic_close_circle),
                contentDescription = location.weatherConditions.asString()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun UserLocationListItemPreview() {
    AppTheme {
        UserLocationListItem(
            location = LocationUiModel(
                name = "Saint Petersburg",
                time = "12:05",
                weatherConditions = UiText.DynamicString("Partially cloudy"),
                currentTemp = "-2º",
                minMaxTemp = "↓-10º  ↑4º",
                conditionImageResId = R.drawable.ic_close_circle
            )
        )
    }
}