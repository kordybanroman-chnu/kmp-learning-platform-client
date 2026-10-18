package rommmmaha.learning_platform_client.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import rommmmaha.learning_platform_client.settings.ThemeMode

private val AppShapes = Shapes(
  extraSmall = RoundedCornerShape(4.dp),
  small = RoundedCornerShape(8.dp),
  medium = RoundedCornerShape(12.dp),
  large = RoundedCornerShape(16.dp),
  extraLarge = RoundedCornerShape(20.dp),
)

@Composable
fun LearningPlatformTheme(
  themeMode: ThemeMode = ThemeMode.System,
  content: @Composable () -> Unit,
) {
  val darkTheme = when (themeMode) {
    ThemeMode.System -> platformDarkTheme()
    ThemeMode.Light -> false
    ThemeMode.Dark -> true
  }
  val colorScheme = when (themeMode) {
    ThemeMode.System -> platformColorScheme(darkTheme) ?: defaultColors(darkTheme)
    ThemeMode.Light -> LightColors
    ThemeMode.Dark -> DarkColors
  }
  MaterialTheme(
    colorScheme = colorScheme,
    typography = appTypography(),
    shapes = AppShapes,
    content = content,
  )
}

private fun defaultColors(darkTheme: Boolean) = if (darkTheme) DarkColors else LightColors
