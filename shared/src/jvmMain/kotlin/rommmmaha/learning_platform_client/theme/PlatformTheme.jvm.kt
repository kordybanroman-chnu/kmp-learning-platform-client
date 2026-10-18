package rommmmaha.learning_platform_client.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import java.io.File

@Composable
actual fun platformDarkTheme(): Boolean = remember { DesktopTheme.prefersDark() }

@Composable
actual fun platformColorScheme(darkTheme: Boolean): ColorScheme? = remember {
  readKdeGlobals()?.let(::kdeColorScheme)
}

private object DesktopTheme {
  fun prefersDark(): Boolean {
    val kde = readKdeGlobals()
    val windowBackground = kde?.kdeColor("Colors:Window", "BackgroundNormal")
    if (windowBackground != null) return windowBackground.luminance() < 0.5f
    return gtkPrefersDark()
  }
}

internal fun kdeColorScheme(kde: Map<String, Map<String, String>>): ColorScheme? {
  val background = kde.kdeColor("Colors:Window", "BackgroundNormal") ?: return null
  val dark = background.luminance() < 0.5f
  val foreground = kde.kdeColor("Colors:Window", "ForegroundNormal")
    ?: if (dark) Color(0xFFF8FAFC) else Color(0xFF0F172A)
  val viewForeground = kde.kdeColor("Colors:View", "ForegroundNormal") ?: foreground
  val accent = kde.kdeColor("Colors:Selection", "BackgroundNormal")
    ?: if (dark) Color(0xFF60A5FA) else Color(0xFF1D4ED8)
  val onAccent = kde.kdeColor("Colors:Selection", "ForegroundNormal")
    ?: if (accent.luminance() > 0.5f) Color(0xFF0F172A) else Color(0xFFF8FAFC)
  val error = kde.kdeColor("Colors:View", "ForegroundNegative")
    ?: if (dark) Color(0xFFFFB4AB) else Color(0xFFBA1A1A)
  val mutedForeground = lerp(viewForeground, background, 0.35f)
  val base = if (dark) darkColorScheme() else lightColorScheme()
  return base.copy(
    primary = accent,
    onPrimary = onAccent,
    primaryContainer = lerp(background, accent, 0.25f),
    onPrimaryContainer = viewForeground,
    secondary = mutedForeground,
    onSecondary = background,
    secondaryContainer = lerp(background, viewForeground, 0.1f),
    onSecondaryContainer = viewForeground,
    tertiary = accent,
    background = background,
    onBackground = foreground,
    surface = lerp(background, viewForeground, 0.05f),
    onSurface = viewForeground,
    surfaceVariant = lerp(background, viewForeground, 0.12f),
    onSurfaceVariant = mutedForeground,
    surfaceContainerLowest = background,
    surfaceContainerLow = lerp(background, viewForeground, 0.05f),
    surfaceContainer = lerp(background, viewForeground, 0.08f),
    surfaceContainerHigh = lerp(background, viewForeground, 0.12f),
    surfaceContainerHighest = lerp(background, viewForeground, 0.16f),
    outline = lerp(background, viewForeground, 0.24f),
    outlineVariant = lerp(background, viewForeground, 0.16f),
    error = error,
    errorContainer = lerp(background, error, 0.25f),
    onErrorContainer = viewForeground,
    inverseSurface = viewForeground,
    inverseOnSurface = background,
  )
}

internal fun readKdeGlobals(): Map<String, Map<String, String>>? {
  val file = File(System.getProperty("user.home"), ".config/kdeglobals")
  if (!file.isFile) return null
  return parseKdeGlobals(file.readText())
}

internal fun parseKdeGlobals(text: String): Map<String, Map<String, String>> {
  val sections = mutableMapOf<String, MutableMap<String, String>>()
  var section = ""
  for (raw in text.lineSequence()) {
    val line = raw.trim()
    when {
      line.isEmpty() -> Unit
      line.startsWith("[") && line.endsWith("]") -> section = line.removeSurrounding("[", "]")
      line.contains('=') -> {
        sections.getOrPut(section) { mutableMapOf() }[line.substringBefore('=').trim()] =
          line.substringAfter('=').trim()
      }
    }
  }
  return sections
}

internal fun Map<String, Map<String, String>>.kdeColor(section: String, key: String): Color? =
  this[section]?.get(key)?.let(::parseKdeColor)

internal fun parseKdeColor(value: String): Color? {
  val parts = value.split(',').mapNotNull { it.trim().toIntOrNull() }
  if (parts.size < 3) return null
  return Color(
    red = parts[0].coerceIn(0, 255),
    green = parts[1].coerceIn(0, 255),
    blue = parts[2].coerceIn(0, 255),
  )
}

private fun gtkPrefersDark(): Boolean {
  val paths = listOf(".config/gtk-4.0/settings.ini", ".config/gtk-3.0/settings.ini")
  for (path in paths) {
    val file = File(System.getProperty("user.home"), path)
    if (!file.isFile) continue
    for (raw in file.readLines()) {
      val line = raw.trim()
      val value = line.substringAfter('=', "").trim()
      if (line.startsWith("gtk-application-prefer-dark-theme") &&
        (value == "1" || value.equals("true", ignoreCase = true))
      ) {
        return true
      }
      if (line.startsWith("gtk-theme-name") && value.contains("dark", ignoreCase = true)) {
        return true
      }
    }
  }
  return false
}
