package rommmmaha.learning_platform_client.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rommmmaha.learning_platform_client.domain.auth.AuthRepository
import rommmmaha.learning_platform_client.domain.auth.RefreshSessionUseCase
import rommmmaha.learning_platform_client.domain.onboarding.IsOnboardingCompletedUseCase

enum class StartDestination {
  Onboarding,
  Auth,
  Home,
}

class MainViewModel(
  private val isOnboardingCompleted: IsOnboardingCompletedUseCase,
  private val authRepository: AuthRepository,
  private val refreshSession: RefreshSessionUseCase,
) : ViewModel() {
  private val _start = MutableStateFlow(resolveStart())
  val start: StateFlow<StartDestination> = _start.asStateFlow()

  init {
    viewModelScope.launch {
      authRepository.isAuthorized.collect { authorized ->
        _start.value = when {
          !isOnboardingCompleted() -> StartDestination.Onboarding
          !authorized -> StartDestination.Auth
          else -> StartDestination.Home
        }
      }
    }
    viewModelScope.launch {
      refreshSession()
    }
  }

  private fun resolveStart(): StartDestination = when {
    !isOnboardingCompleted() -> StartDestination.Onboarding
    !authRepository.isAuthorized.value -> StartDestination.Auth
    else -> StartDestination.Home
  }
}
