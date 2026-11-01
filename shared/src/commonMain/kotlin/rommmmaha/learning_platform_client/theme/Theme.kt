package rommmmaha.learning_platform_client.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import rommmmaha.learning_platform_client.settings.ThemeMode

private val AppShapes = Shapes(
  extraSmall = RoundedCornerShape(6.dp),
  small = RoundedCornerShape(10.dp),
  medium = RoundedCornerShape(12.dp),
  large = RoundedCornerShape(16.dp),
  extraLarge = RoundedCornerShape(28.dp),
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
  val colorScheme = if (darkTheme) DarkColors else LightColors
  MaterialTheme(
    colorScheme = colorScheme,
    typography = appTypography(),
    shapes = AppShapes,
    content = content,
  )
}
