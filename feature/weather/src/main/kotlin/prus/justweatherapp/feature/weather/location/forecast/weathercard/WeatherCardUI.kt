package prus.justweatherapp.feature.weather.location.forecast.weathercard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun WeatherCardUI(
    weatherConditionImageResId: Int,
    weatherConditionString: String,
    precipitationProb: String?
) {
    Card(
        modifier = Modifier
            .size(60.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(id = weatherConditionImageResId),
                contentDescription = weatherConditionString,
                alignment = Alignment.Center
            )

            if (precipitationProb != null) {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    bottomStart = 0.dp,
                                    topEnd = 0.dp
                                )
                            )
                            .padding(
                                start = 6.dp,
                                end = 8.dp,
                                top = 1.dp
                            ),
                        text = precipitationProb,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun WeatherCardUIPreview(
) {
    AppTheme {
        Surface {
            WeatherCardUI(
                weatherConditionImageResId = prus.justweatherapp.core.ui.R.drawable.mostlycloudy,
                weatherConditionString = "",
                precipitationProb = "23%"
            )
        }
    }
}