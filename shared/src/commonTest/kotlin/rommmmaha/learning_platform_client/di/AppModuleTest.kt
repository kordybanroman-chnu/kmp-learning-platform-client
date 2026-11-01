package rommmmaha.learning_platform_client.di

import com.russhwolf.settings.MapSettings
import kotlin.test.Test
import kotlin.test.assertEquals
import org.koin.dsl.koinApplication
import rommmmaha.learning_platform_client.settings.UserSettings
import rommmmaha.learning_platform_client.ui.auth.AuthViewModel
import rommmmaha.learning_platform_client.ui.main.MainUiState
import rommmmaha.learning_platform_client.ui.main.MainViewModel
import rommmmaha.learning_platform_client.ui.main.StartDestination
import rommmmaha.learning_platform_client.ui.onboarding.OnboardingViewModel

class AppModuleTest {
  @Test
  fun dependencyGraphResolves() {
    val koin = koinApplication {
      modules(appModule(MapSettings()))
    }.koin

    assertEquals(
      MainUiState.Ready(StartDestination.Onboarding),
      koin.get<MainViewModel>().state.value,
    )
    assertEquals(false, koin.get<UserSettings>().onboardingCompleted)
    koin.get<OnboardingViewModel>()
    koin.get<AuthViewModel>()

    koin.close()
  }
}
