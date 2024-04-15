package prus.justweatherapp.feature.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.contentPaddings
import prus.justweatherapp.theme.screenContentPaddings

@Composable
fun SettingsUI() {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsUI(
        state = state
    )
}

@Composable
private fun SettingsUI(
    state: SettingsUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    )
    {
        val context = LocalContext.current

        when (state) {
            is SettingsUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }

            is SettingsUiState.Loading -> {
                CircularProgressIndicator()
            }

            is SettingsUiState.Success -> {
                SettingsUI(
                    settings = state.settings
                )
            }
        }
    }
}

@Composable
private fun SettingsUI(
    settings: SettingsUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.title_settings),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .screenContentPaddings(),
            text = stringResource(id = R.string.label_units).uppercase(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onTertiary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                SettingsLabel(
                    text = stringResource(id = R.string.label_degrees),
                )

                SettingsLabel(
                    text = stringResource(id = R.string.label_wind),
                )

                SettingsLabel(
                    text = stringResource(id = R.string.label_pressure),
                )

            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                SettingsValue(
                    value = settings.tempValue.asString(),
                ) {

                }

                SettingsValue(
                    value = settings.windValue.asString(),
                ) {

                }

                SettingsValue(
                    value = settings.pressureValue.asString(),
                ) {

                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .screenContentPaddings(),
            text = stringResource(id = R.string.label_interface).uppercase(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onTertiary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                SettingsLabel(
                    text = stringResource(id = R.string.label_language),
                )

                SettingsLabel(
                    text = stringResource(id = R.string.label_theme),
                )

            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                SettingsValue(
                    value = settings.languageValue.asString(),
                ) {

                }

                SettingsValue(
                    value = settings.themeValue.asString(),
                ) {

                }
            }
        }
    }
}

@Composable
private fun SettingsLabel(
    text: String
) {
    Text(
        modifier = Modifier.contentPaddings(),
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
}

@Composable
private fun SettingsValue(
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .contentPaddings()
            .clickable {
                onClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(
                id = R.drawable.ic_arrow_down
            ),
            tint = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            contentDescription = ""
        )
    }
}

@PreviewLightDark
@Composable
private fun SettingsUISuccessPreview(
) {
    AppTheme {
        Surface {
            SettingsUI(
                state = SettingsUiState.Success(
                    settings = SettingsUiModel(
                        tempValue = UiText.StringResource(R.string.scale_celsius),
                        pressureValue = UiText.StringResource(R.string.scale_mm_hg),
                        windValue = UiText.StringResource(R.string.scale_m_s),
                        languageValue = UiText.StringResource(R.string.language_english),
                        themeValue = UiText.StringResource(R.string.theme_system),
                    )
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsUILoadingPreview(
) {
    AppTheme {
        Surface {
            SettingsUI(
                state = SettingsUiState.Loading
            )
        }
    }
}
