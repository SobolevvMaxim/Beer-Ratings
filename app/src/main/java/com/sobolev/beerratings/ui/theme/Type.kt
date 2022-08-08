package com.sobolev.beerratings.ui.theme

import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sobolev.beerratings.R

val fonts = FontFamily(
    Font(R.font.sfpro)
)

val Typography = Typography(
    titleLarge = TextStyle(
        color = Color.Black,
        fontFamily = fonts,
        fontSize = 36.sp,
        fontWeight = FontWeight.W600
    ),
    labelSmall = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    labelMedium = TextStyle(
        color = DarkGray,
        fontSize = 20.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    headlineMedium = TextStyle(
        color = Color.Black,
        fontSize = 25.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.W600
    ),
    headlineSmall = TextStyle(
        color = Color.Black,
        fontSize = 20.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.W600
    ),
)