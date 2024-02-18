package prus.justweatherapp.feature.locations.search

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import prus.justweatherapp.feature.locations.model.SearchLocationUiModel
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.accent

@Composable
fun SearchLocationListItem(
    location: SearchLocationUiModel,
    onClick: (locationId: String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(location.id)
            }
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = getAnnotatedString(location)
        )
    }
}

@Composable
fun getAnnotatedString(
    location: SearchLocationUiModel
) = buildAnnotatedString {

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.inversePrimary

    append(
        getAnnotatedPart(
            location.city,
            location.cityOccurrences,
            primaryColor
        )
    )

    location.adminName?.let { adminName ->
        withStyle(style = SpanStyle(secondaryColor)) {
            append(", ")
        }
        append(
            getAnnotatedPart(
                adminName,
                location.adminNameOccurrences,
                secondaryColor
            )
        )
    }

    location.country?.let { country ->
        withStyle(style = SpanStyle(secondaryColor)) {
            append(", ")
        }
        append(
            getAnnotatedPart(
                country,
                location.countryOccurrences,
                secondaryColor
            )
        )
    }
}

@Composable
fun getAnnotatedPart(name: String, occurrences: List<Pair<Int, Int>>, unselectedColor: Color) =
    buildAnnotatedString {
        var index = 0
        occurrences.forEach {
            withStyle(style = SpanStyle(unselectedColor)) {
                append(name.substring(index, it.first))
            }
            withStyle(style = SpanStyle(accent)) {
                append(name.substring(it.first, it.second + 1))
            }
            index = it.second + 1
        }
        withStyle(style = SpanStyle(unselectedColor)) {
            append(name.substring(index, name.length))
        }
    }

@PreviewLightDark
@Composable
private fun SearchLocationListItemPreview() {
    AppTheme {
        Surface {
            SearchLocationListItem(
                location = SearchLocationUiModel(
                    id = "1",
                    city = "City1",
                    adminName = "AdminName1",
                    country = "Country1"
                ).apply {
                    findOccurrences("c")
                }
            )
        }
    }
}