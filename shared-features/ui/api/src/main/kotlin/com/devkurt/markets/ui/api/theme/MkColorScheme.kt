package com.devkurt.markets.ui.api.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class MkColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val error: Color,
    val onError: Color,
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val info: Color,
    val onInfo: Color,
) {
    companion object {
        fun light() = MkColorScheme(
            primary = Color(0xFF00B386),
            onPrimary = Color.White,
            secondary = Color(0xFF12324B),
            onSecondary = Color.White,
            background = Color(0xFFF6F8FA),
            onBackground = Color(0xFF0E1116),
            surface = Color.White,
            onSurface = Color(0xFF0E1116),
            surfaceVariant = Color(0xFFEDF1F5),
            onSurfaceVariant = Color(0xFF5B6470),
            outline = Color(0xFFCBD3DB),
            error = Color(0xFFE5484D),
            onError = Color.White,
            success = Color(0xFF1E9553),
            onSuccess = Color.White,
            warning = Color(0xFFBF7732),
            onWarning = Color.White,
            info = Color(0xFF008C9D),
            onInfo = Color.White,
        )

        fun dark() = MkColorScheme(
            primary = Color(0xFF33C9A3),
            onPrimary = Color(0xFF00281E),
            secondary = Color(0xFF9DB8CC),
            onSecondary = Color(0xFF0E1116),
            background = Color(0xFF0E1116),
            onBackground = Color(0xFFECEFF3),
            surface = Color(0xFF161A20),
            onSurface = Color(0xFFECEFF3),
            surfaceVariant = Color(0xFF1F242B),
            onSurfaceVariant = Color(0xFF9AA4AF),
            outline = Color(0xFF39424C),
            error = Color(0xFFFF6B6B),
            onError = Color(0xFF3A0A0A),
            success = Color(0xFF2DD4A0),
            onSuccess = Color(0xFF00281E),
            warning = Color(0xFFE0A458),
            onWarning = Color(0xFF0E1116),
            info = Color(0xFF3FC9DC),
            onInfo = Color(0xFF0E1116),
        )
    }
}

val LocalMkColorScheme = staticCompositionLocalOf<MkColorScheme> {
    error("No MkColorScheme provided. Wrap your content in MarketsTheme.")
}
