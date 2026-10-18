package rommmmaha.learning_platform_client.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale

actual object LocalAppLocale {
  private var default: Locale? = null
  private val localeLocal = staticCompositionLocalOf { Locale.getDefault().toLanguageTag() }

  actual val current: String
    @Composable get() = localeLocal.current

  @Composable
  actual infix fun provides(value: String?): ProvidedValue<*> {
    if (default == null) {
      default = Locale.getDefault()
    }
    val new = if (value == null) default!! else Locale.forLanguageTag(value)
    Locale.setDefault(new)
    return localeLocal.provides(new.toLanguageTag())
  }
}
