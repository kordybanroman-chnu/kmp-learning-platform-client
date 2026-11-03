package rommmmaha.learning_platform_client.ui.main

import androidx.lifecycle.ViewModel
import rommmmaha.learning_platform_client.domain.onboarding.IsOnboardingCompletedUseCase

enum class StartDestination {
  Onboarding,
  Auth,
}

class MainViewModel(isOnboardingCompleted: IsOnboardingCompletedUseCase) : ViewModel() {
  val start: StartDestination =
    if (isOnboardingCompleted()) StartDestination.Auth else StartDestination.Onboarding
}
