package rommmmaha.learning_platform_client.ui.main

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import rommmmaha.learning_platform_client.domain.auth.AuthRepository
import rommmmaha.learning_platform_client.domain.auth.AuthUser
import rommmmaha.learning_platform_client.domain.auth.RefreshSessionUseCase
import rommmmaha.learning_platform_client.domain.onboarding.IsOnboardingCompletedUseCase
import rommmmaha.learning_platform_client.domain.onboarding.OnboardingRepository

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
  private val dispatcher = StandardTestDispatcher()

  @BeforeTest
  fun setUp() {
    Dispatchers.setMain(dispatcher)
  }

  @AfterTest
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun completedOnboardingStartsAtAuth() = runTest(dispatcher) {
    val viewModel = MainViewModel(
      IsOnboardingCompletedUseCase(FakeOnboardingRepository(completed = true)),
      FakeAuthRepository(authorized = false),
      RefreshSessionUseCase(FakeAuthRepository(authorized = false)),
    )

    assertEquals(StartDestination.Auth, viewModel.start.value)
  }

  @Test
  fun missingOnboardingStartsAtOnboarding() = runTest(dispatcher) {
    val viewModel = MainViewModel(
      IsOnboardingCompletedUseCase(FakeOnboardingRepository(completed = false)),
      FakeAuthRepository(authorized = true),
      RefreshSessionUseCase(FakeAuthRepository(authorized = true)),
    )

    assertEquals(StartDestination.Onboarding, viewModel.start.value)
  }

  @Test
  fun completedOnboardingAndAuthorizedStartsAtHome() = runTest(dispatcher) {
    val viewModel = MainViewModel(
      IsOnboardingCompletedUseCase(FakeOnboardingRepository(completed = true)),
      FakeAuthRepository(authorized = true),
      RefreshSessionUseCase(FakeAuthRepository(authorized = true)),
    )

    assertEquals(StartDestination.Home, viewModel.start.value)
  }

  @Test
  fun startupTriggersSessionRefresh() = runTest(dispatcher) {
    val auth = FakeAuthRepository(authorized = true)
    MainViewModel(
      IsOnboardingCompletedUseCase(FakeOnboardingRepository(completed = true)),
      auth,
      RefreshSessionUseCase(auth),
    )
    advanceUntilIdle()

    assertEquals(1, auth.refreshCalls)
  }

  private class FakeOnboardingRepository(private val completed: Boolean) : OnboardingRepository {
    override fun isCompleted(): Boolean = completed

    override fun setCompleted(completed: Boolean) = Unit
  }

  private class FakeAuthRepository(authorized: Boolean) : AuthRepository {
    var refreshCalls = 0
    private val _authorized = MutableStateFlow(authorized)
    override val isAuthorized: StateFlow<Boolean> = _authorized

    override suspend fun signIn(email: String, password: String): Result<AuthUser> =
      Result.success(AuthUser("1", email, "Student"))

    override suspend fun refreshIfNeeded() {
      refreshCalls += 1
    }

    override suspend fun signOut() {
      _authorized.value = false
    }

    override fun currentUser(): AuthUser? = null
  }
}
