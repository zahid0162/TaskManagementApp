package com.zahid.taskmaster.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zahid.taskmaster.R

val appFonts = FontFamily(
    Font(R.font.roboto_condensed_variable_font_wght),
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W900,
        fontFamily = appFonts,
        fontSize = 30.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W900,
        fontFamily = appFonts,
        fontSize = 20.sp,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W900,
        fontFamily = appFonts,
        fontSize = 20.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = appFonts,
        fontSize = 17.sp,
    ),
    bodySmall =  TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = appFonts,
        fontSize = 13.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = appFonts,
        fontSize = 12.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = appFonts,
        fontSize = 16.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = appFonts,
        fontSize = 17.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = appFonts,
        fontSize = 15.sp,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.W600,
        fontFamily = appFonts,
        fontSize = 13.sp,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = appFonts,
        fontSize = 16.sp,
    ),
    displayLarge = TextStyle(
        fontWeight = FontWeight.W900,
        fontFamily = appFonts,
        fontSize = 25.sp,
    ),
)