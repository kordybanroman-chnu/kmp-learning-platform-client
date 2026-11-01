package rommmmaha.learning_platform_client.ui.onboarding

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import rommmmaha.learning_platform_client.domain.onboarding.CompleteOnboardingUseCase
import rommmmaha.learning_platform_client.domain.onboarding.OnboardingRepository

class OnboardingViewModelTest {
  @Test
  fun pageIsClampedToThePageCount() {
    val viewModel = OnboardingViewModel(CompleteOnboardingUseCase(FakeOnboardingRepository()))

    viewModel.onIntent(OnboardingIntent.PageChanged(2))
    assertEquals(2, viewModel.state.value.page)

    viewModel.onIntent(OnboardingIntent.PageChanged(9))
    assertEquals(2, viewModel.state.value.page)
  }

  @Test
  fun skipCompletesOnboarding() {
    val repository = FakeOnboardingRepository()
    val viewModel = OnboardingViewModel(CompleteOnboardingUseCase(repository))

    viewModel.onIntent(OnboardingIntent.Skip)

    assertTrue(repository.onboardingCompleted)
    assertTrue(viewModel.state.value.isFinished)
  }

  @Test
  fun finishIsIdempotent() {
    val repository = FakeOnboardingRepository()
    val viewModel = OnboardingViewModel(CompleteOnboardingUseCase(repository))

    viewModel.onIntent(OnboardingIntent.PageChanged(3))
    viewModel.onIntent(OnboardingIntent.Finish)
    viewModel.onIntent(OnboardingIntent.Finish)

    assertTrue(repository.onboardingCompleted)
    assertTrue(viewModel.state.value.isFinished)
    assertEquals(1, repository.completions)
  }

  private class FakeOnboardingRepository : OnboardingRepository {
    var onboardingCompleted = false
    var completions = 0

    override fun isCompleted(): Boolean = onboardingCompleted

    override fun setCompleted(completed: Boolean) {
      onboardingCompleted = completed
      completions++
    }
  }
}
