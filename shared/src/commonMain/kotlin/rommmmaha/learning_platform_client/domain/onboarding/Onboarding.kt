package rommmmaha.learning_platform_client.domain.onboarding

import rommmmaha.learning_platform_client.settings.UserSettings

interface OnboardingRepository {
  fun isCompleted(): Boolean
  fun setCompleted(completed: Boolean)
}

class SettingsOnboardingRepository(private val userSettings: UserSettings) : OnboardingRepository {
  override fun isCompleted(): Boolean = userSettings.onboardingCompleted

  override fun setCompleted(completed: Boolean) {
    if (completed) {
      userSettings.completeOnboarding()
    } else {
      userSettings.restartOnboarding()
    }
  }
}

class IsOnboardingCompletedUseCase(private val repository: OnboardingRepository) {
  operator fun invoke(): Boolean = repository.isCompleted()
}

class CompleteOnboardingUseCase(private val repository: OnboardingRepository) {
  operator fun invoke() = repository.setCompleted(true)
}

class RestartOnboardingUseCase(private val repository: OnboardingRepository) {
  operator fun invoke() = repository.setCompleted(false)
}
