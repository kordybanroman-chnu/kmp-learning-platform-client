package rommmmaha.learning_platform_client.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rommmmaha.learning_platform_client.domain.auth.SignInUseCase

class AuthViewModel(
  private val signIn: SignInUseCase,
) : ViewModel() {
  private val _state = MutableStateFlow(AuthUiState())
  val state: StateFlow<AuthUiState> = _state.asStateFlow()

  fun onIntent(intent: AuthIntent) {
    when (intent) {
      is AuthIntent.UsernameChanged -> _state.update {
        it.copy(username = intent.value, usernameError = null, signInFailed = false)
      }

      is AuthIntent.PasswordChanged -> _state.update {
        it.copy(password = intent.value, passwordError = null, signInFailed = false)
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

    val username = current.username.trim()
    val usernameError = when {
      username.isEmpty() -> AuthFieldError.UsernameRequired
      username.length < USERNAME_MIN_LENGTH -> AuthFieldError.UsernameTooShort
      else -> null
    }
    val passwordError = when {
      current.password.isEmpty() -> AuthFieldError.PasswordRequired
      current.password.length < PASSWORD_MIN_LENGTH -> AuthFieldError.PasswordTooShort
      else -> null
    }

    if (usernameError != null || passwordError != null) {
      _state.update {
        it.copy(usernameError = usernameError, passwordError = passwordError, signInFailed = false)
      }
      return
    }

    _state.update {
      it.copy(isSubmitting = true, usernameError = null, passwordError = null, signInFailed = false)
    }
    viewModelScope.launch {
      val result = signIn(username, current.password)
      _state.update {
        it.copy(
          isSubmitting = false,
          isSignedIn = result.isSuccess,
          signInFailed = result.isFailure,
        )
      }
    }
  }

  private companion object {
    const val USERNAME_MIN_LENGTH = 3
    const val PASSWORD_MIN_LENGTH = 6
  }
}
