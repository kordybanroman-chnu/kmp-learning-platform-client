package rommmmaha.learning_platform_client.ui.onboarding

const val ONBOARDING_PAGE_COUNT = 4

data class OnboardingUiState(
  val page: Int = 0,
  val pageCount: Int = ONBOARDING_PAGE_COUNT,
  val isFinished: Boolean = false,
) {
  val isLastPage: Boolean get() = page == pageCount - 1
}

sealed interface OnboardingIntent {
  data class PageChanged(val page: Int) : OnboardingIntent
  data object Skip : OnboardingIntent
  data object Finish : OnboardingIntent
}
