package rommmmaha.learning_platform_client.di

import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import rommmmaha.learning_platform_client.domain.auth.AuthRepository
import rommmmaha.learning_platform_client.domain.auth.FakeAuthRepository
import rommmmaha.learning_platform_client.domain.auth.SignInUseCase
import rommmmaha.learning_platform_client.domain.onboarding.CompleteOnboardingUseCase
import rommmmaha.learning_platform_client.domain.onboarding.IsOnboardingCompletedUseCase
import rommmmaha.learning_platform_client.domain.onboarding.OnboardingRepository
import rommmmaha.learning_platform_client.domain.onboarding.RestartOnboardingUseCase
import rommmmaha.learning_platform_client.domain.onboarding.SettingsOnboardingRepository
import rommmmaha.learning_platform_client.settings.UserSettings
import rommmmaha.learning_platform_client.ui.auth.AuthViewModel
import rommmmaha.learning_platform_client.ui.main.MainViewModel
import rommmmaha.learning_platform_client.ui.onboarding.OnboardingViewModel

fun appModule(settings: Settings) = module {
  single { UserSettings(settings) }
  single<OnboardingRepository> { SettingsOnboardingRepository(get()) }
  single<AuthRepository> { FakeAuthRepository() }

  factory { IsOnboardingCompletedUseCase(get()) }
  factory { CompleteOnboardingUseCase(get()) }
  factory { RestartOnboardingUseCase(get()) }
  factory { SignInUseCase(get()) }

  viewModel { MainViewModel(get()) }
  viewModel { OnboardingViewModel(get()) }
  viewModel { AuthViewModel(get()) }
}
