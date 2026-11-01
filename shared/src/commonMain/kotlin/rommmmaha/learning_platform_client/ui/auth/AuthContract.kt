package rommmmaha.learning_platform_client.ui.auth

enum class AuthFieldError {
  UsernameRequired,
  UsernameTooShort,
  PasswordRequired,
  PasswordTooShort,
}

data class AuthUiState(
  val username: String = "",
  val password: String = "",
  val isPasswordVisible: Boolean = false,
  val usernameError: AuthFieldError? = null,
  val passwordError: AuthFieldError? = null,
  val isSubmitting: Boolean = false,
  val isSignedIn: Boolean = false,
  val signInFailed: Boolean = false,
)

sealed interface AuthIntent {
  data class UsernameChanged(val value: String) : AuthIntent
  data class PasswordChanged(val value: String) : AuthIntent
  data object TogglePasswordVisibility : AuthIntent
  data object Submit : AuthIntent
}
