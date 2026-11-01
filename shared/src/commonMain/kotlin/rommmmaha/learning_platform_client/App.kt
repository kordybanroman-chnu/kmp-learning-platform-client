package rommmmaha.learning_platform_client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.russhwolf.settings.Settings
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.koinConfiguration
import rommmmaha.learning_platform_client.di.appModule
import rommmmaha.learning_platform_client.domain.onboarding.RestartOnboardingUseCase
import rommmmaha.learning_platform_client.platform.LocalAppLocale
import rommmmaha.learning_platform_client.settings.UserSettings
import rommmmaha.learning_platform_client.theme.LearningPlatformTheme
import rommmmaha.learning_platform_client.ui.auth.AuthScreen
import rommmmaha.learning_platform_client.ui.components.LogoMark
import rommmmaha.learning_platform_client.ui.home.HomeScreen
import rommmmaha.learning_platform_client.ui.main.MainUiState
import rommmmaha.learning_platform_client.ui.main.MainViewModel
import rommmmaha.learning_platform_client.ui.main.StartDestination
import rommmmaha.learning_platform_client.ui.onboarding.OnboardingScreen

private data object OnboardingKey : NavKey
private data object AuthKey : NavKey
private data object HomeKey : NavKey

private fun StartDestination.toKey(): NavKey = when (this) {
  StartDestination.Onboarding -> OnboardingKey
  StartDestination.Auth -> AuthKey
}

@Composable
fun App(settings: Settings) {
  val koinConfig = remember(settings) {
    koinConfiguration { modules(appModule(settings)) }
  }
  KoinApplication(configuration = koinConfig) {
    AppContent()
  }
}

@Composable
private fun AppContent() {
  val userSettings = koinInject<UserSettings>()
  val restartOnboarding = koinInject<RestartOnboardingUseCase>()
  val platformOwner = LocalViewModelStoreOwner.current
  val owner: ViewModelStoreOwner = platformOwner ?: rememberViewModelStoreOwner(parent = null)

  LaunchedEffect(Unit) {
    log.i { "App started on ${getPlatform().name}" }
  }

  CompositionLocalProvider(
    LocalViewModelStoreOwner provides owner,
    LocalAppLocale provides userSettings.language,
  ) {
    val mainViewModel: MainViewModel = koinViewModel()
    val mainState by mainViewModel.state.collectAsState()

    LearningPlatformTheme(themeMode = userSettings.themeMode) {
      Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
      ) {
        when (val state = mainState) {
          MainUiState.Loading -> Splash()
          is MainUiState.Ready -> {
            val backStack = remember(state.start) {
              NavBackStack<NavKey>(state.start.toKey())
            }
            key(userSettings.language) {
              val saveableDecorator = rememberSaveableStateHolderNavEntryDecorator<NavKey>()
              val storeProvider = rememberViewModelStoreProvider(parent = owner)
              val viewModelDecorator =
                rememberViewModelStoreNavEntryDecorator<NavKey>(storeProvider)
              val decorators: List<NavEntryDecorator<NavKey>> =
                remember(saveableDecorator, viewModelDecorator) {
                  listOf(saveableDecorator, viewModelDecorator)
                }

              NavDisplay(
                backStack = backStack,
                entryDecorators = decorators,
                onBack = {
                  if (backStack.size > 1) backStack.removeLastOrNull()
                },
                entryProvider = entryProvider<NavKey> {
                  entry<OnboardingKey> {
                    OnboardingScreen(
                      onFinished = {
                        backStack.clear()
                        backStack.add(AuthKey)
                      },
                    )
                  }
                  entry<AuthKey> {
                    AuthScreen(
                      onSignedIn = {
                        backStack.clear()
                        backStack.add(HomeKey)
                      },
                    )
                  }
                  entry<HomeKey> {
                    HomeScreen(
                      userSettings = userSettings,
                      onRestartOnboarding = {
                        restartOnboarding()
                        backStack.clear()
                        backStack.add(OnboardingKey)
                      },
                      onSignOut = {
                        backStack.clear()
                        backStack.add(AuthKey)
                      },
                    )
                  }
                },
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun Splash() {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    LogoMark(size = 48.dp)
  }
}
