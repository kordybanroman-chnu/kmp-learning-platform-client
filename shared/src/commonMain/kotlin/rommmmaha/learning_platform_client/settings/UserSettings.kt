package rommmmaha.learning_platform_client.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.russhwolf.settings.Settings
import rommmmaha.learning_platform_client.log

class UserSettings(private val settings: Settings) {
  var themeMode by mutableStateOf(ThemeMode.fromKey(settings.getStringOrNull(KEY_THEME_MODE)))
    private set

  var language by mutableStateOf(settings.getStringOrNull(KEY_LANGUAGE))
    private set

  var onboardingCompleted by mutableStateOf(settings.getBoolean(KEY_ONBOARDING, false))
    private set

  fun selectThemeMode(mode: ThemeMode) {
    themeMode = mode
    settings.putString(KEY_THEME_MODE, mode.key)
    log.i { "Theme mode: ${mode.key}" }
  }

  fun selectLanguage(languageTag: String?) {
    language = languageTag
    if (languageTag == null) {
      settings.remove(KEY_LANGUAGE)
    } else {
      settings.putString(KEY_LANGUAGE, languageTag)
    }
    log.i { "Language: ${languageTag ?: "system"}" }
  }

  fun completeOnboarding() {
    onboardingCompleted = true
    settings.putBoolean(KEY_ONBOARDING, true)
    log.i { "Onboarding completed" }
  }

  fun restartOnboarding() {
    onboardingCompleted = false
    settings.putBoolean(KEY_ONBOARDING, false)
    log.i { "Onboarding restarted" }
  }

  private companion object {
    const val KEY_THEME_MODE = "theme_mode"
    const val KEY_LANGUAGE = "language"
    const val KEY_ONBOARDING = "onboarding_completed"
  }
}
