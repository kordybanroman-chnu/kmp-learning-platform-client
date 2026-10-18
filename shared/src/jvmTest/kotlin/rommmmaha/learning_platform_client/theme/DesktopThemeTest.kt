package rommmmaha.learning_platform_client.theme

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DesktopThemeTest {

  private val kdeGlobals = """
    [Colors:Window]
    BackgroundNormal=0,0,0
    ForegroundNormal=239,240,241

    [Colors:View]
    BackgroundNormal=0,0,0
    ForegroundNormal=239,240,241
    ForegroundNegative=218,68,83

    [Colors:Selection]
    BackgroundNormal=61,182,242
    ForegroundNormal=255,255,255
  """.trimIndent()

  @Test
  fun parsesWindowAndSelectionColors() {
    val sections = parseKdeGlobals(kdeGlobals)
    assertEquals(Color(0, 0, 0), sections.kdeColor("Colors:Window", "BackgroundNormal"))
    assertEquals(Color(61, 182, 242), sections.kdeColor("Colors:Selection", "BackgroundNormal"))
    assertNull(sections.kdeColor("Colors:Window", "Missing"))
  }

  @Test
  fun buildsSchemeFromPalette() {
    val scheme = kdeColorScheme(parseKdeGlobals(kdeGlobals))
    assertNotNull(scheme)
    assertEquals(Color(0, 0, 0), scheme.background)
    assertEquals(Color(61, 182, 242), scheme.primary)
    assertEquals(Color(255, 255, 255), scheme.onPrimary)
    assertEquals(Color(218, 68, 83), scheme.error)
    assertEquals(Color(239, 240, 241), scheme.onSurface)
  }

  @Test
  fun rejectsMalformedValues() {
    assertNull(parseKdeColor("not-a-color"))
    assertNull(parseKdeColor("12,34"))
  }
}
