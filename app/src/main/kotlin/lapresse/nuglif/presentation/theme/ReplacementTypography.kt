package lapresse.nuglif.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class ReplacementTypography(
    val bigTitle: TextStyle = TextStyle.Default,
    val headline: TextStyle = TextStyle.Default,
    val body: TextStyle = TextStyle.Default
)


val LocalReplacementTypography = staticCompositionLocalOf { ReplacementTypography() }