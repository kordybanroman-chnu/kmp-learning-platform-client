package rommmmaha.learning_platform_client.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rommmmaha.learning_platform_client.domain.auth.AuthError
import rommmmaha.learning_platform_client.domain.auth.AuthException
import rommmmaha.learning_platform_client.domain.auth.SignInUseCase

class AuthViewModel(
  private val signIn: SignInUseCase,
) : ViewModel() {
  private val _state = MutableStateFlow(AuthUiState())
  val state: StateFlow<AuthUiState> = _state.asStateFlow()

  private val _effects = Channel<AuthEffect>(Channel.BUFFERED)
  val effects = _effects.receiveAsFlow()

  fun onIntent(intent: AuthIntent) {
    when (intent) {
      is AuthIntent.EmailChanged -> _state.update {
        it.copy(email = intent.value, emailError = null)
      }

      is AuthIntent.PasswordChanged -> _state.update {
        it.copy(password = intent.value, passwordError = null)
      }

      AuthIntent.TogglePasswordVisibility -> _state.update {
        it.copy(isPasswordVisible = !it.isPasswordVisible)
      }

      AuthIntent.Submit -> submit()
    }
  }

  private fun submit() {
    val current = _state.value
    if (current.isSubmitting) return

    val email = current.email.trim()
    val emailError = when {
      email.isEmpty() -> EmailError.Required
      '@' !in email -> EmailError.Invalid
      else -> null
    }
    val passwordError = when {
      current.password.isEmpty() -> PasswordError.Required
      current.password.length < PASSWORD_MIN_LENGTH -> PasswordError.TooShort
      else -> null
    }

    if (emailError != null || passwordError != null) {
      _state.update {
        it.copy(emailError = emailError, passwordError = passwordError)
      }
      return
    }

    _state.update {
      it.copy(isSubmitting = true, emailError = null, passwordError = null)
    }
    viewModelScope.launch {
      val result = signIn(email, current.password)
      val error = (result.exceptionOrNull() as? AuthException)?.error
      if (result.isSuccess) {
        _state.update { it.copy(isSubmitting = false, isSignedIn = true) }
      } else {
        _state.update { it.copy(isSubmitting = false) }
        _effects.send(AuthEffect.ShowError(error.toSignInError()))
      }
    }
  }

  private fun AuthError?.toSignInError(): SignInError = when (this) {
    is AuthError.InvalidCredentials -> SignInError.InvalidCredentials
    is AuthError.InvalidRequest -> SignInError.InvalidRequest
    is AuthError.Network -> SignInError.Network
    is AuthError.Unknown -> SignInError.Unknown
    null -> SignInError.Unknown
  }

  private companion object {
    const val PASSWORD_MIN_LENGTH = 6
  }
}
