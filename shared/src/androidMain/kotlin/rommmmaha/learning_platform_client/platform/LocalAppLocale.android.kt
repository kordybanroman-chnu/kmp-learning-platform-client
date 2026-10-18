package rommmmaha.learning_platform_client.platform

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual object LocalAppLocale {
  private var default: Locale? = null

  actual val current: String
    @Composable get() = LocalConfiguration.current.locales[0].toLanguageTag()

  @Suppress("DEPRECATION")
  @Composable
  actual infix fun provides(value: String?): ProvidedValue<*> {
    val configuration = LocalConfiguration.current
    if (default == null) {
      default = Locale.getDefault()
    }
    val new = if (value == null) default!! else Locale.forLanguageTag(value)
    Locale.setDefault(new)
    val newConfiguration = Configuration(configuration).apply { setLocale(new) }
    val resources = LocalContext.current.resources
    resources.updateConfiguration(newConfiguration, resources.displayMetrics)
    return LocalConfiguration.provides(newConfiguration)
  }
}
