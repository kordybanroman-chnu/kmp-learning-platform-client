package rommmmaha.learning_platform_client.ui.auth

enum class EmailError {
  Required,
  Invalid,
}

enum class PasswordError {
  Required,
  TooShort,
}

enum class SignInError {
  InvalidCredentials,
  InvalidRequest,
  Network,
  Unknown,
}

data class AuthUiState(
  val email: String = "",
  val password: String = "",
  val isPasswordVisible: Boolean = false,
  val emailError: EmailError? = null,
  val passwordError: PasswordError? = null,
  val isSubmitting: Boolean = false,
  val isSignedIn: Boolean = false,
)

sealed interface AuthIntent {
  data class EmailChanged(val value: String) : AuthIntent
  data class PasswordChanged(val value: String) : AuthIntent
  data object TogglePasswordVisibility : AuthIntent
  data object Submit : AuthIntent
}

sealed interface AuthEffect {
  data class ShowError(val error: SignInError) : AuthEffect
}
