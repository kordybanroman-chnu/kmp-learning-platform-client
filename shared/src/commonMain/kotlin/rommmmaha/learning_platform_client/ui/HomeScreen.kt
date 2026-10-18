package rommmmaha.learning_platform_client.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rommmmaha.learning_platform_client.settings.ThemeMode
import rommmmaha.learning_platform_client.settings.UserSettings

import kmp_learning_platform_client.shared.generated.resources.Res
import kmp_learning_platform_client.shared.generated.resources.app_name
import kmp_learning_platform_client.shared.generated.resources.home_language
import kmp_learning_platform_client.shared.generated.resources.home_placeholder
import kmp_learning_platform_client.shared.generated.resources.home_show_onboarding
import kmp_learning_platform_client.shared.generated.resources.home_theme
import kmp_learning_platform_client.shared.generated.resources.language_en
import kmp_learning_platform_client.shared.generated.resources.language_uk
import kmp_learning_platform_client.shared.generated.resources.theme_dark
import kmp_learning_platform_client.shared.generated.resources.theme_light
import kmp_learning_platform_client.shared.generated.resources.theme_system

@Composable
fun HomeScreen(userSettings: UserSettings) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .safeContentPadding()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(24.dp),
  ) {
    Text(
      text = stringResource(Res.string.app_name),
      style = MaterialTheme.typography.headlineMedium,
    )
    ThemeModeSelector(userSettings)
    LanguageSelector(userSettings)
    Text(
      text = stringResource(Res.string.home_placeholder),
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    TextButton(onClick = userSettings::restartOnboarding) {
      Text(stringResource(Res.string.home_show_onboarding))
    }
  }
}

@Composable
private fun ThemeModeSelector(userSettings: UserSettings) {
  val options = listOf(
    stringResource(Res.string.theme_system) to ThemeMode.System,
    stringResource(Res.string.theme_light) to ThemeMode.Light,
    stringResource(Res.string.theme_dark) to ThemeMode.Dark,
  )
  Selector(
    label = stringResource(Res.string.home_theme),
    options = options,
    selected = userSettings.themeMode,
    onSelect = userSettings::selectThemeMode,
  )
}

@Composable
private fun LanguageSelector(userSettings: UserSettings) {
  val options = listOf(
    stringResource(Res.string.language_uk) to "uk",
    stringResource(Res.string.language_en) to "en",
  )
  Selector(
    label = stringResource(Res.string.home_language),
    options = options,
    selected = userSettings.language ?: Locale.current.language,
    onSelect = userSettings::selectLanguage,
  )
}

@Composable
private fun <T> Selector(
  label: String,
  options: List<Pair<String, T>>,
  selected: T,
  onSelect: (T) -> Unit,
) {
  Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelLarge,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
      options.forEachIndexed { index, (optionLabel, value) ->
        SegmentedButton(
          selected = value == selected,
          onClick = { onSelect(value) },
          shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
        ) {
          Text(optionLabel)
        }
      }
    }
  }
}
