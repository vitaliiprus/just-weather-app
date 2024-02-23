package prus.justweatherapp.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
val fontFamily = FontFamily(
    Font(
        resId = R.font.nunito,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(FontWeight.Medium.weight)
        )
    )
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 11.sp
    )
)

val textButtonStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)