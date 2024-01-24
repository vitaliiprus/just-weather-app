package prus.justweatherapp.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val fontFamilySome = FontFamily(
//    Font(R.font.some_font_regular, FontWeight.Normal),
//    Font(R.font.some_font_medium, FontWeight.Medium),
//    Font(R.font.some_font_bold, FontWeight.Bold)
)

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = fontFamilySome,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = ColorTextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamilySome,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = ColorTextPrimary
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilySome,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = ColorTextSecondary
    )
)