package rommmmaha.learning_platform_client.ui.main

import kotlin.test.Test
import kotlin.test.assertEquals
import rommmmaha.learning_platform_client.domain.onboarding.IsOnboardingCompletedUseCase
import rommmmaha.learning_platform_client.domain.onboarding.OnboardingRepository

class MainViewModelTest {
  @Test
  fun completedOnboardingStartsAtAuth() {
    val viewModel = MainViewModel(
      IsOnboardingCompletedUseCase(FakeOnboardingRepository(completed = true)),
    )

    assertEquals(
      MainUiState.Ready(StartDestination.Auth),
      viewModel.state.value,
    )
  }

  @Test
  fun missingOnboardingStartsAtOnboarding() {
    val viewModel = MainViewModel(
      IsOnboardingCompletedUseCase(FakeOnboardingRepository(completed = false)),
    )

    assertEquals(
      MainUiState.Ready(StartDestination.Onboarding),
      viewModel.state.value,
    )
  }

  private class FakeOnboardingRepository(private val completed: Boolean) : OnboardingRepository {
    override fun isCompleted(): Boolean = completed

    override fun setCompleted(completed: Boolean) = Unit
  }
}
