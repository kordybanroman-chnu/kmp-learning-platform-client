package rommmmaha.learning_platform_client.settings

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

fun createSettings(): Settings =
  PreferencesSettings(Preferences.userRoot().node("rommmmaha/learning-platform-client"))
