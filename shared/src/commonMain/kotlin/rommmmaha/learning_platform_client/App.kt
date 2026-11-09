package rommmmaha.learning_platform_client

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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
import eu.anifantakis.lib.ksafe.KSafe
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.koinConfiguration
import rommmmaha.learning_platform_client.data.auth.KSafeTokenStore
import rommmmaha.learning_platform_client.di.appModule
import rommmmaha.learning_platform_client.domain.auth.SignOutUseCase
import rommmmaha.learning_platform_client.domain.onboarding.RestartOnboardingUseCase
import rommmmaha.learning_platform_client.platform.LocalAppLocale
import rommmmaha.learning_platform_client.settings.UserSettings
import rommmmaha.learning_platform_client.theme.LearningPlatformTheme
import rommmmaha.learning_platform_client.ui.auth.AuthScreen
import rommmmaha.learning_platform_client.ui.home.HomeScreen
import rommmmaha.learning_platform_client.ui.main.MainViewModel
import rommmmaha.learning_platform_client.ui.main.StartDestination
import rommmmaha.learning_platform_client.ui.onboarding.OnboardingScreen

private data object OnboardingKey : NavKey
private data object AuthKey : NavKey
private data object HomeKey : NavKey

private fun StartDestination.toKey(): NavKey = when (this) {
  StartDestination.Onboarding -> OnboardingKey
  StartDestination.Auth -> AuthKey
  StartDestination.Home -> HomeKey
}

@Composable
fun App(settings: Settings, ksafe: KSafe) {
  val koinConfig = remember(settings, ksafe) {
    koinConfiguration { modules(appModule(settings, KSafeTokenStore(ksafe))) }
  }
  KoinApplication(configuration = koinConfig) {
    AppContent()
  }
}

@Composable
private fun AppContent() {
  val userSettings = koinInject<UserSettings>()
  val restartOnboarding = koinInject<RestartOnboardingUseCase>()
  val signOut = koinInject<SignOutUseCase>()
  val platformOwner = LocalViewModelStoreOwner.current
  val owner: ViewModelStoreOwner = platformOwner ?: rememberViewModelStoreOwner(parent = null)
  val scope = rememberCoroutineScope()

  CompositionLocalProvider(
    LocalViewModelStoreOwner provides owner,
    LocalAppLocale provides userSettings.language,
  ) {
    val mainViewModel: MainViewModel = koinViewModel()
    val start by mainViewModel.start.collectAsState()

    LearningPlatformTheme(themeMode = userSettings.themeMode) {
      Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
      ) {
        val backStack = rememberSaveable(start, saver = navBackStackSaver) {
          NavBackStack<NavKey>(start.toKey())
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
                    scope.launch {
                      signOut()
                    }
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

private val navBackStackSaver = Saver<NavBackStack<NavKey>, List<String>>(
  save = { stack -> stack.map { it.routeName() } },
  restore = { routes ->
    NavBackStack<NavKey>().apply { addAll(routes.map { it.toRouteKey() }) }
  },
)

private fun NavKey.routeName(): String = when (this) {
  OnboardingKey -> "onboarding"
  AuthKey -> "auth"
  HomeKey -> "home"
  else -> error("Unknown navigation key")
}

private fun String.toRouteKey(): NavKey = when (this) {
  "onboarding" -> OnboardingKey
  "home" -> HomeKey
  else -> AuthKey
}
