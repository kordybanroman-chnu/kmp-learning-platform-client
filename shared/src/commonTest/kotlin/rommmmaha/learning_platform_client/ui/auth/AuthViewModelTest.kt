package rommmmaha.learning_platform_client.ui.auth

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import rommmmaha.learning_platform_client.domain.auth.AuthError
import rommmmaha.learning_platform_client.domain.auth.AuthException
import rommmmaha.learning_platform_client.domain.auth.AuthRepository
import rommmmaha.learning_platform_client.domain.auth.AuthUser
import rommmmaha.learning_platform_client.domain.auth.SignInUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
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
  fun blankFieldsShowRequiredErrors() {
    val viewModel = AuthViewModel(SignInUseCase(RecordingAuthRepository()))

    viewModel.onIntent(AuthIntent.Submit)

    val state = viewModel.state.value
    assertEquals(EmailError.Required, state.emailError)
    assertEquals(PasswordError.Required, state.passwordError)
    assertFalse(state.isSubmitting)
  }

  @Test
  fun invalidEmailShowsFormatError() {
    val viewModel = AuthViewModel(SignInUseCase(RecordingAuthRepository()))

    viewModel.onIntent(AuthIntent.EmailChanged("student"))
    viewModel.onIntent(AuthIntent.PasswordChanged("secret1"))
    viewModel.onIntent(AuthIntent.Submit)

    val state = viewModel.state.value
    assertEquals(EmailError.Invalid, state.emailError)
    assertFalse(state.isSubmitting)
  }

  @Test
  fun shortPasswordShowsLengthError() {
    val viewModel = AuthViewModel(SignInUseCase(RecordingAuthRepository()))

    viewModel.onIntent(AuthIntent.EmailChanged("student@example.com"))
    viewModel.onIntent(AuthIntent.PasswordChanged("12345"))
    viewModel.onIntent(AuthIntent.Submit)

    val state = viewModel.state.value
    assertEquals(PasswordError.TooShort, state.passwordError)
  }

  @Test
  fun validCredentialsSignInWithTrimmedEmail() = runTest(dispatcher) {
    val repository = RecordingAuthRepository()
    val viewModel = AuthViewModel(SignInUseCase(repository))

    viewModel.onIntent(AuthIntent.EmailChanged("  student@example.com  "))
    viewModel.onIntent(AuthIntent.PasswordChanged("secret1"))
    viewModel.onIntent(AuthIntent.Submit)
    advanceUntilIdle()

    val state = viewModel.state.value
    assertTrue(state.isSignedIn)
    assertFalse(state.isSubmitting)
    assertEquals("student@example.com", repository.lastEmail)
  }

  @Test
  fun failedSignInKeepsUserOnFormAndEmitsEffect() = runTest(dispatcher) {
    val viewModel = AuthViewModel(
      SignInUseCase(FailingAuthRepository(AuthError.InvalidCredentials())),
    )
    val collected = mutableListOf<AuthEffect>()
    val collector = launch { viewModel.effects.toList(collected) }
    runCurrent()

    viewModel.onIntent(AuthIntent.EmailChanged("student@example.com"))
    viewModel.onIntent(AuthIntent.PasswordChanged("secret1"))
    viewModel.onIntent(AuthIntent.Submit)
    advanceUntilIdle()
    collector.cancel()

    val state = viewModel.state.value
    assertFalse(state.isSignedIn)
    assertFalse(state.isSubmitting)
    assertEquals(
      listOf<AuthEffect>(AuthEffect.ShowError(SignInError.InvalidCredentials)),
      collected,
    )
  }

  @Test
  fun networkFailureEmitsNetworkEffect() = runTest(dispatcher) {
    val viewModel = AuthViewModel(
      SignInUseCase(FailingAuthRepository(AuthError.Network)),
    )
    val collected = mutableListOf<AuthEffect>()
    val collector = launch { viewModel.effects.toList(collected) }
    runCurrent()

    viewModel.onIntent(AuthIntent.EmailChanged("student@example.com"))
    viewModel.onIntent(AuthIntent.PasswordChanged("secret1"))
    viewModel.onIntent(AuthIntent.Submit)
    advanceUntilIdle()
    collector.cancel()

    assertEquals(listOf<AuthEffect>(AuthEffect.ShowError(SignInError.Network)), collected)
  }

  private class RecordingAuthRepository : AuthRepository {
    var lastEmail: String? = null
    private val _authorized = MutableStateFlow(false)
    override val isAuthorized: StateFlow<Boolean> = _authorized

    override suspend fun signIn(email: String, password: String): Result<AuthUser> {
      lastEmail = email
      _authorized.value = true
      return Result.success(AuthUser("1", email, "Student"))
    }

    override suspend fun refreshIfNeeded() = Unit

    override suspend fun signOut() {
      _authorized.value = false
    }

    override fun currentUser(): AuthUser? = null
  }

  private class FailingAuthRepository(private val error: AuthError) : AuthRepository {
    private val _authorized = MutableStateFlow(false)
    override val isAuthorized: StateFlow<Boolean> = _authorized

    override suspend fun signIn(email: String, password: String): Result<AuthUser> =
      Result.failure(AuthException(error))

    override suspend fun refreshIfNeeded() = Unit

    override suspend fun signOut() = Unit

    override fun currentUser(): AuthUser? = null
  }
}
