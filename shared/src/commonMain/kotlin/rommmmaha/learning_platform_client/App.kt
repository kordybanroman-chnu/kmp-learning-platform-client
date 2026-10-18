package rommmmaha.learning_platform_client

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import com.russhwolf.settings.Settings
import rommmmaha.learning_platform_client.platform.LocalAppLocale
import rommmmaha.learning_platform_client.settings.UserSettings
import rommmmaha.learning_platform_client.theme.LearningPlatformTheme
import rommmmaha.learning_platform_client.ui.HomeScreen
import rommmmaha.learning_platform_client.ui.OnboardingScreen

@Composable
fun App(settings: Settings) {
  val userSettings = remember(settings) { UserSettings(settings) }
  LaunchedEffect(Unit) {
    log.i { "App started on ${getPlatform().name}" }
  }
  CompositionLocalProvider(LocalAppLocale provides userSettings.language) {
    key(userSettings.language) {
      LearningPlatformTheme(themeMode = userSettings.themeMode) {
        Surface(color = MaterialTheme.colorScheme.background) {
          if (userSettings.onboardingCompleted) {
            HomeScreen(userSettings)
          } else {
            OnboardingScreen(onFinished = userSettings::completeOnboarding)
          }
        }
      }
    }
  }
}
