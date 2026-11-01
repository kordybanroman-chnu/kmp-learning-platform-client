package rommmmaha.learning_platform_client.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rommmmaha.learning_platform_client.settings.ThemeMode
import rommmmaha.learning_platform_client.settings.UserSettings
import rommmmaha.learning_platform_client.ui.components.AppCard
import rommmmaha.learning_platform_client.ui.components.LogoMark
import rommmmaha.learning_platform_client.ui.components.SectionLabel
import rommmmaha.learning_platform_client.ui.components.SegmentedControl

import kmp_learning_platform_client.shared.generated.resources.Res
import kmp_learning_platform_client.shared.generated.resources.app_name
import kmp_learning_platform_client.shared.generated.resources.home_app_section
import kmp_learning_platform_client.shared.generated.resources.home_client_subtitle
import kmp_learning_platform_client.shared.generated.resources.home_language
import kmp_learning_platform_client.shared.generated.resources.home_show_onboarding
import kmp_learning_platform_client.shared.generated.resources.home_sign_out
import kmp_learning_platform_client.shared.generated.resources.home_subtitle
import kmp_learning_platform_client.shared.generated.resources.home_theme
import kmp_learning_platform_client.shared.generated.resources.home_title
import kmp_learning_platform_client.shared.generated.resources.language_en
import kmp_learning_platform_client.shared.generated.resources.language_uk
import kmp_learning_platform_client.shared.generated.resources.theme_dark
import kmp_learning_platform_client.shared.generated.resources.theme_light
import kmp_learning_platform_client.shared.generated.resources.theme_system

@Composable
fun HomeScreen(
  userSettings: UserSettings,
  onRestartOnboarding: () -> Unit,
  onSignOut: () -> Unit,
) {
  val themeOptions = listOf(
    stringResource(Res.string.theme_system),
    stringResource(Res.string.theme_light),
    stringResource(Res.string.theme_dark),
  )
  val languageOptions = listOf(
    stringResource(Res.string.language_uk),
    stringResource(Res.string.language_en),
  )
  val effectiveLanguage = userSettings.language ?: Locale.current.language

  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .widthIn(max = 560.dp)
        .fillMaxWidth()
        .align(Alignment.TopCenter)
        .safeContentPadding()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
      Text(
        text = stringResource(Res.string.home_title),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
      )
      Spacer(Modifier.height(4.dp))
      Text(
        text = stringResource(Res.string.home_subtitle),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )

      Spacer(Modifier.height(20.dp))

      AppCard {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          LogoMark(size = 48.dp)
          Spacer(Modifier.width(14.dp))
          Column {
            Text(
              text = stringResource(Res.string.app_name),
              style = MaterialTheme.typography.titleLarge,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(2.dp))
            Text(
              text = stringResource(Res.string.home_client_subtitle),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }

      SectionLabel(stringResource(Res.string.home_theme))
      AppCard {
        Box(modifier = Modifier.padding(6.dp)) {
          SegmentedControl(
            options = themeOptions,
            selectedIndex = ThemeMode.entries.indexOf(userSettings.themeMode),
            onSelect = { index ->
              ThemeMode.entries.getOrNull(index)?.let(userSettings::selectThemeMode)
            },
          )
        }
      }

      SectionLabel(stringResource(Res.string.home_language))
      AppCard {
        Box(modifier = Modifier.padding(6.dp)) {
          SegmentedControl(
            options = languageOptions,
            selectedIndex = if (effectiveLanguage == "en") 1 else 0,
            onSelect = { index ->
              userSettings.selectLanguage(if (index == 0) "uk" else "en")
            },
          )
        }
      }

      SectionLabel(stringResource(Res.string.home_app_section))
      AppCard {
        SettingsRow(
          label = stringResource(Res.string.home_show_onboarding),
          onClick = onRestartOnboarding,
        )
        HorizontalDivider(
          thickness = 1.dp,
          color = MaterialTheme.colorScheme.outlineVariant,
        )
        SettingsRow(
          label = stringResource(Res.string.home_sign_out),
          onClick = onSignOut,
          danger = true,
        )
      }

      Spacer(Modifier.height(24.dp))
    }
  }
}

@Composable
private fun SettingsRow(
  label: String,
  onClick: () -> Unit,
  danger: Boolean = false,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp, vertical = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
      color = if (danger) {
        MaterialTheme.colorScheme.error
      } else {
        MaterialTheme.colorScheme.onSurface
      },
      modifier = Modifier.weight(1f),
    )
    Text(
      text = "→",
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}
