package prus.justweatherapp.feature.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import prus.justweatherapp.theme.JwaTheme
import prus.justweatherapp.theme.accent
import prus.justweatherapp.theme.screenContentPaddings

@Composable
fun SettingsUI() {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsUI(
        state = state,
        callbacks = viewModel.uiCallbacks
    )
}

@Composable
private fun SettingsUI(
    state: SettingsUiState,
    callbacks: SettingsCallbacks,
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
                    settings = state.settings,
                    callbacks = callbacks
                )
            }
        }
    }
}

@Composable
private fun SettingsUI(
    settings: SettingsUiModel,
    callbacks: SettingsCallbacks,
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

        Spacer(modifier = Modifier.height(10.dp))

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
                .background(MaterialTheme.colorScheme.onTertiary)
                .padding(8.dp),
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
                    menuOptions = settings.menuOptions.tempOptions,
                    onMenuOptionSelected = callbacks.onTempChanged
                )

                SettingsValue(
                    value = settings.windValue.asString(),
                    menuOptions = settings.menuOptions.windOptions,
                    onMenuOptionSelected = callbacks.onWindChanged
                )

                SettingsValue(
                    value = settings.pressureValue.asString(),
                    menuOptions = settings.menuOptions.pressureOptions,
                    onMenuOptionSelected = callbacks.onPressureChanged
                )
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
                .background(MaterialTheme.colorScheme.onTertiary)
                .padding(8.dp),
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
                    menuOptions = settings.menuOptions.languageOptions,
                    onMenuOptionSelected = callbacks.onLanguageChanged
                )

                SettingsValue(
                    value = settings.themeValue.asString(),
                    menuOptions = settings.menuOptions.themeOptions,
                    onMenuOptionSelected = callbacks.onThemeChanged
                )
            }
        }
    }
}

private val textPadding = PaddingValues(
    start = 16.dp,
    end = 16.dp,
    top = 14.dp,
    bottom = 14.dp,
)

@Composable
private fun SettingsLabel(
    text: String
) {
    Text(
        modifier = Modifier.padding(
            textPadding
        ),
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
    menuOptions: List<MenuOption>,
    onMenuOptionSelected: (Int) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clickable {
                menuExpanded = true
            }
            .padding(
                textPadding
            ),
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

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            menuOptions.forEachIndexed { index, option ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onMenuOptionSelected(index)
                            menuExpanded = false
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (option.selected) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_check
                                ),
                                tint = accent,
                                contentDescription = "selected"
                            )
                        }
                    }

                    Text(
                        modifier = Modifier
                            .padding(
                                start = 0.dp,
                                end = 20.dp,
                                top = 16.dp,
                                bottom = 16.dp,
                            ),
                        text = option.value.asString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }

            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsUISuccessPreview(
) {
    JwaTheme {
        Surface {
            SettingsUI(
                state = SettingsUiState.Success(
                    settings = SettingsUiModel(
                        tempValue = UiText.StringResource(R.string.scale_celsius),
                        pressureValue = UiText.StringResource(R.string.scale_mm_hg),
                        windValue = UiText.StringResource(R.string.scale_m_s),
                        languageValue = UiText.StringResource(R.string.language_english),
                        themeValue = UiText.StringResource(R.string.theme_system),
                        menuOptions = MenuOptionsUiModel(
                            tempOptions = listOf(),
                            pressureOptions = listOf(),
                            windOptions = listOf(),
                            languageOptions = listOf(),
                            themeOptions = listOf(),
                        )
                    )
                ),
                callbacks = SettingsCallbacks(
                    onTempChanged = {},
                    onWindChanged = {},
                    onPressureChanged = {},
                    onLanguageChanged = {},
                    onThemeChanged = {},
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsValuePreview(
) {
    JwaTheme {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SettingsValue(
                    value = "System",
                    menuOptions = listOf(
                        MenuOption(
                            value = UiText.DynamicString("Light"),
                            selected = false
                        ),
                        MenuOption(
                            value = UiText.DynamicString("Dark"),
                            selected = false
                        ),
                        MenuOption(
                            value = UiText.DynamicString("System"),
                            selected = true
                        ),
                    ),
                    onMenuOptionSelected = {}
                )
            }
        }
    }
}
