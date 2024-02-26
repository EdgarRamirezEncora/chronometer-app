package com.example.cronoapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val ColorBlindLightColorScheme = lightColorScheme(
    primary = colorBlindLightPrimary,
    secondary = colorBlindLightSecondary,
    tertiary = colorBlindLightTertiary
)

private val ColorBlindDarkColorScheme = darkColorScheme(
    primary = colorBlindDarkPrimary,
    secondary = colorBlindDarkSecondary,
    tertiary = colorBlindDarkTertiary
)

@Composable
fun CronoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    colorBlindMode: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*
        Does not allow to apply the schemes

        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        */

        darkTheme && !colorBlindMode -> DarkColorScheme
        darkTheme && colorBlindMode -> ColorBlindDarkColorScheme
        !darkTheme && colorBlindMode -> ColorBlindLightColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}