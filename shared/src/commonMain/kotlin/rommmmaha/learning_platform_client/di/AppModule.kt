package rommmmaha.learning_platform_client.di

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import rommmmaha.learning_platform_client.data.auth.TokenStore
import rommmmaha.learning_platform_client.data.network.AuthApi
import rommmmaha.learning_platform_client.data.network.KtorAuthApi
import rommmmaha.learning_platform_client.data.network.createAuthedHttpClient
import rommmmaha.learning_platform_client.data.network.createPlainHttpClient
import rommmmaha.learning_platform_client.domain.auth.AuthRepository
import rommmmaha.learning_platform_client.domain.auth.IsAuthorizedUseCase
import rommmmaha.learning_platform_client.domain.auth.NetworkAuthRepository
import rommmmaha.learning_platform_client.domain.auth.RefreshSessionUseCase
import rommmmaha.learning_platform_client.domain.auth.SignInUseCase
import rommmmaha.learning_platform_client.domain.auth.SignOutUseCase
import rommmmaha.learning_platform_client.domain.onboarding.CompleteOnboardingUseCase
import rommmmaha.learning_platform_client.domain.onboarding.IsOnboardingCompletedUseCase
import rommmmaha.learning_platform_client.domain.onboarding.OnboardingRepository
import rommmmaha.learning_platform_client.domain.onboarding.RestartOnboardingUseCase
import rommmmaha.learning_platform_client.domain.onboarding.SettingsOnboardingRepository
import rommmmaha.learning_platform_client.platform.API_BASE_URL
import rommmmaha.learning_platform_client.settings.UserSettings
import rommmmaha.learning_platform_client.ui.auth.AuthViewModel
import rommmmaha.learning_platform_client.ui.main.MainViewModel
import rommmmaha.learning_platform_client.ui.onboarding.OnboardingViewModel

fun appModule(settings: Settings, tokenStore: TokenStore) = module {
  single { UserSettings(settings) }
  single { tokenStore }
  single<OnboardingRepository> { SettingsOnboardingRepository(get()) }
  single<AuthApi> { KtorAuthApi(createPlainHttpClient(), API_BASE_URL) }
  single<HttpClient> { createAuthedHttpClient(get(), get()) }
  single<AuthRepository> { NetworkAuthRepository(get(), get()) }

  factory { IsOnboardingCompletedUseCase(get()) }
  factory { CompleteOnboardingUseCase(get()) }
  factory { RestartOnboardingUseCase(get()) }
  factory { SignInUseCase(get()) }
  factory { SignOutUseCase(get()) }
  factory { IsAuthorizedUseCase(get()) }
  factory { RefreshSessionUseCase(get()) }

  viewModel { MainViewModel(get(), get(), get()) }
  viewModel { OnboardingViewModel(get()) }
  viewModel { AuthViewModel(get()) }
}
