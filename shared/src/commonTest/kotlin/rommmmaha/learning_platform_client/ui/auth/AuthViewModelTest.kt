package rommmmaha.learning_platform_client.ui.auth

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import rommmmaha.learning_platform_client.domain.auth.AuthRepository
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
    assertEquals(AuthFieldError.UsernameRequired, state.usernameError)
    assertEquals(AuthFieldError.PasswordRequired, state.passwordError)
    assertFalse(state.isSubmitting)
  }

  @Test
  fun shortFieldsShowLengthErrors() {
    val viewModel = AuthViewModel(SignInUseCase(RecordingAuthRepository()))

    viewModel.onIntent(AuthIntent.UsernameChanged("ab"))
    viewModel.onIntent(AuthIntent.PasswordChanged("12345"))
    viewModel.onIntent(AuthIntent.Submit)

    val state = viewModel.state.value
    assertEquals(AuthFieldError.UsernameTooShort, state.usernameError)
    assertEquals(AuthFieldError.PasswordTooShort, state.passwordError)
  }

  @Test
  fun validCredentialsSignInWithTrimmedUsername() = runTest(dispatcher) {
    val repository = RecordingAuthRepository()
    val viewModel = AuthViewModel(SignInUseCase(repository))

    viewModel.onIntent(AuthIntent.UsernameChanged("  student  "))
    viewModel.onIntent(AuthIntent.PasswordChanged("secret1"))
    viewModel.onIntent(AuthIntent.Submit)
    advanceUntilIdle()

    val state = viewModel.state.value
    assertTrue(state.isSignedIn)
    assertFalse(state.isSubmitting)
    assertEquals("student", repository.lastUsername)
  }

  @Test
  fun failedSignInKeepsUserOnForm() = runTest(dispatcher) {
    val viewModel = AuthViewModel(SignInUseCase(FailingAuthRepository()))

    viewModel.onIntent(AuthIntent.UsernameChanged("student"))
    viewModel.onIntent(AuthIntent.PasswordChanged("secret1"))
    viewModel.onIntent(AuthIntent.Submit)
    advanceUntilIdle()

    val state = viewModel.state.value
    assertFalse(state.isSignedIn)
    assertTrue(state.signInFailed)
  }

  private class RecordingAuthRepository : AuthRepository {
    var lastUsername: String? = null

    override suspend fun signIn(username: String, password: String): Result<Unit> {
      lastUsername = username
      return Result.success(Unit)
    }
  }

  private class FailingAuthRepository : AuthRepository {
    override suspend fun signIn(username: String, password: String): Result<Unit> =
      Result.failure(IllegalStateException("offline"))
  }
}
