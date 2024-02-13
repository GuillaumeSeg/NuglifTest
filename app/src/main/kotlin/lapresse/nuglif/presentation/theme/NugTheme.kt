package lapresse.nuglif.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Composable
fun NugTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (isDarkTheme) { lightThemeColors } else { lightThemeColors }

    // Provides the colors and typo used in the Screens
    CompositionLocalProvider(
        LocalReplacementTypography provides StandardTypography,
        LocalAppThemeColors provides colors
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            // To set the color of NavigationBar and StatusBar
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = lightThemeColors.primary.toArgb()
                window.navigationBarColor = lightThemeColors.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
        MaterialTheme(
            content = { content.invoke() }
        )
    }
}

val lightThemeColors = ThemeColors(
    primary = NugUiColor.RedLaPress,
    onPrimary = NugUiColor.White,
    background = NugUiColor.White,
    onBackground = NugUiColor.Black,
    accent = NugUiColor.RedLaPress,
    onAccent = NugUiColor.White,
    surface = NugUiColor.DarkGrey,
    secondary = NugUiColor.Blue
)

val StandardTypography = ReplacementTypography(
    bigTitle = TextStyle(
        fontSize = 40.sp,
        textAlign = TextAlign.Start
    ),
    headline = TextStyle(
        fontSize = 34.sp,
        textAlign = TextAlign.Start
    ),
    body = TextStyle(
        fontSize = 15.sp,
        textAlign = TextAlign.Start
    )
)