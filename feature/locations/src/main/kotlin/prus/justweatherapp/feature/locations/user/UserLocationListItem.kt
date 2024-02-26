package prus.justweatherapp.feature.locations.user

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.core.ui.dragdrop.dragDropStateChangeHandler
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.feature.locations.model.LocationUiModel
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.Dimens
import prus.justweatherapp.theme.contentPaddings

@Composable
fun UserLocationListItem(
    modifier: Modifier = Modifier,
    location: LocationUiModel,
    isEditing: Boolean = false,
    onEditClicked: (String) -> Unit = {},
    onDeleteClicked: (String) -> Unit = {},
    onDragDropStateChanged: (Boolean) -> Unit = {},
) {
    AnimatedContent(
        targetState = isEditing,
        label = ""
    ) { isEditMode ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = if (isEditMode) 0.dp else Dimens.screenPaddings.start,
                    end = if (isEditMode) 0.dp else Dimens.screenPaddings.start,
                    top = Dimens.screenPaddings.start / 2,
                    bottom = Dimens.screenPaddings.start / 2
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isEditMode) {
                Card(
                    modifier = Modifier
                        .size(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.error,
                        disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    )
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onDeleteClicked(location.id)

                            }
                            .padding(12.dp),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.ic_minus_fill),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                        contentDescription = "delete location"
                    )

                }
            }


            Card(
                modifier = modifier
                    .weight(1f)
                    .height(100.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.onTertiary
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
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

                        if (!isEditMode) {

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = location.weatherConditions.asString(),
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    if (!isEditMode) {
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
                            painter = painterResource(id = location.conditionImageResId),
                            contentDescription = location.weatherConditions.asString()
                        )
                    }

                    if (isEditMode) {

                        Card(
                            modifier = Modifier
                                .size(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = CardDefaults.cardColors().copy(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        onEditClicked(location.id)
                                    }
                                    .padding(16.dp),
                                contentScale = ContentScale.Crop,
                                painter = painterResource(id = R.drawable.ic_edit_fill),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiaryContainer),
                                contentDescription = location.weatherConditions.asString()
                            )
                        }
                    }
                }
            }

            if (isEditMode) {

                Image(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(16.dp)
                        .dragDropStateChangeHandler(onDragDropStateChanged),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_menu),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiaryContainer),
                    contentDescription = location.weatherConditions.asString()
                )

            }
        }
    }
}

@PreviewLightDark
@Composable
private fun UserLocationListItemPreview() {
    AppTheme {
        Surface {
            UserLocationListItem(
                location = LocationUiModel(
                    id = "1",
                    name = "Saint Petersburg",
                    time = "12:05",
                    weatherConditions = UiText.DynamicString("Partially cloudy"),
                    currentTemp = "-2º",
                    minMaxTemp = "↓-10º  ↑4º",
                    conditionImageResId = prus.justweatherapp.core.ui.R.drawable.mostlysunny
                ),
                isEditing = false
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun UserLocationListItemEditingPreview() {
    AppTheme {
        Surface {
            UserLocationListItem(
                location = LocationUiModel(
                    id = "1",
                    name = "Saint Petersburg",
                    time = "12:05",
                    weatherConditions = UiText.DynamicString("Partially cloudy"),
                    currentTemp = "-2º",
                    minMaxTemp = "↓-10º  ↑4º",
                    conditionImageResId = prus.justweatherapp.core.ui.R.drawable.mostlysunny
                ),
                isEditing = true
            )
        }
    }
}