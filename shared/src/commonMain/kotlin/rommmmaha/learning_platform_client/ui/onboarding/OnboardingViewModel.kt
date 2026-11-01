package rommmmaha.learning_platform_client.ui.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import rommmmaha.learning_platform_client.domain.onboarding.CompleteOnboardingUseCase

class OnboardingViewModel(
  private val completeOnboarding: CompleteOnboardingUseCase,
) : ViewModel() {
  private val _state = MutableStateFlow(OnboardingUiState())
  val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

  fun onIntent(intent: OnboardingIntent) {
    when (intent) {
      is OnboardingIntent.PageChanged -> {
        if (intent.page in 0 until _state.value.pageCount) {
          _state.update { it.copy(page = intent.page) }
        }
      }

      OnboardingIntent.Skip, OnboardingIntent.Finish -> finish()
    }
  }

  private fun finish() {
    if (_state.value.isFinished) return
    completeOnboarding()
    _state.update { it.copy(isFinished = true) }
  }
}
