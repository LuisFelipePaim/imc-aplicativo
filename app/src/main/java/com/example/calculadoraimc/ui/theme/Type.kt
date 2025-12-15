package com.example.calculadoraimc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.R

val ibmPlexSansFamily = FontFamily(
    Font(R.font.ibm_plex_sans_light, FontWeight.Light),
    Font(R.font.ibm_plex_sans_extralight, FontWeight.ExtraLight),
    Font(R.font.ibm_plex_sans_thin, FontWeight.Thin),
    Font(R.font.ibm_plex_sans_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_sans_regular, FontWeight.Normal),
    Font(R.font.ibm_plex_sans_semibold, FontWeight.SemiBold),
    Font(R.font.ibm_plex_sans_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = BlackFont
    ),
    titleLarge = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = BlackFont
    ),
    labelSmall = TextStyle(
        fontFamily = ibmPlexSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = BlackFont
    )
)