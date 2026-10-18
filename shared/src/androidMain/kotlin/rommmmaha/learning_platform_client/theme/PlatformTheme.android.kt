package rommmmaha.learning_platform_client.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun platformDarkTheme(): Boolean = isSystemInDarkTheme()

@Composable
actual fun platformColorScheme(darkTheme: Boolean): ColorScheme? {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return null
  val context = LocalContext.current
  return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
}
