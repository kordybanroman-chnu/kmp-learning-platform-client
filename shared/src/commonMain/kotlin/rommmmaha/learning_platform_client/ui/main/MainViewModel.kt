package rommmmaha.learning_platform_client.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import rommmmaha.learning_platform_client.domain.onboarding.IsOnboardingCompletedUseCase

enum class StartDestination {
  Onboarding,
  Auth,
}

sealed interface MainUiState {
  data object Loading : MainUiState
  data class Ready(val start: StartDestination) : MainUiState
}

class MainViewModel(isOnboardingCompleted: IsOnboardingCompletedUseCase) : ViewModel() {
  private val _state = MutableStateFlow<MainUiState>(MainUiState.Loading)
  val state: StateFlow<MainUiState> = _state.asStateFlow()

  init {
    _state.value = MainUiState.Ready(
      if (isOnboardingCompleted()) StartDestination.Auth else StartDestination.Onboarding,
    )
  }
}
