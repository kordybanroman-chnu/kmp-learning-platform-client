package rommmmaha.learning_platform_client.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
expect fun platformDarkTheme(): Boolean

@Composable
expect fun platformColorScheme(darkTheme: Boolean): ColorScheme?
