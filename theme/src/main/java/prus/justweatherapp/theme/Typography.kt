package prus.justweatherapp.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//val fontFamily = FontFamily(
//    Font(R.font.some_font_regular, FontWeight.Normal),
//    Font(R.font.some_font_medium, FontWeight.Medium),
//    Font(R.font.some_font_bold, FontWeight.Bold)
//)

val fontFamily = FontFamily.Default

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)