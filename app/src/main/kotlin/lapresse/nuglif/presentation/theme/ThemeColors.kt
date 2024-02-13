package lapresse.nuglif.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
class ThemeColors(
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,
    val accent: Color = Color.Unspecified,
    val onAccent: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified
)

val LocalAppThemeColors = compositionLocalOf { ThemeColors() }