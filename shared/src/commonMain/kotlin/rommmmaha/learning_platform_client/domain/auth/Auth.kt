package rommmmaha.learning_platform_client.domain.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import rommmmaha.learning_platform_client.data.auth.AuthSession
import rommmmaha.learning_platform_client.data.auth.TokenStore
import rommmmaha.learning_platform_client.data.auth.isExpired
import rommmmaha.learning_platform_client.data.network.AuthApi
import rommmmaha.learning_platform_client.log
import rommmmaha.learning_platform_client.platform.nowMillis

data class AuthUser(
  val id: String,
  val email: String,
  val displayName: String,
)

sealed interface AuthError {
  data class InvalidCredentials(val detail: String? = null) : AuthError
  data class InvalidRequest(val detail: String? = null) : AuthError
  data object Network : AuthError
  data class Unknown(val detail: String? = null) : AuthError
}

class AuthException(val error: AuthError) : Exception("Auth error: $error")

interface AuthRepository {
  val isAuthorized: StateFlow<Boolean>
  suspend fun signIn(email: String, password: String): Result<AuthUser>
  suspend fun refreshIfNeeded()
  suspend fun signOut()
  fun currentUser(): AuthUser?
}

class NetworkAuthRepository(
  private val api: AuthApi,
  private val store: TokenStore,
) : AuthRepository {
  private val _isAuthorized =
    MutableStateFlow(store.current()?.let { !it.isExpired(nowMillis()) } ?: false)
  override val isAuthorized: StateFlow<Boolean> = _isAuthorized.asStateFlow()

  override suspend fun signIn(email: String, password: String): Result<AuthUser> {
    val result = api.login(email, password)
    return result.fold(
      onSuccess = { session ->
        store.save(session)
        _isAuthorized.value = true
        Result.success(session.toUser())
      },
      onFailure = { Result.failure(it) },
    )
  }

  override suspend fun refreshIfNeeded() {
    val session = store.current() ?: run {
      _isAuthorized.value = false
      return
    }
    if (!session.isExpired(nowMillis())) {
      _isAuthorized.value = true
      return
    }
    val refreshed = api.refresh(session.refreshToken)
    refreshed.fold(
      onSuccess = { fresh ->
        store.save(fresh)
        _isAuthorized.value = true
        log.i { "Session refreshed" }
      },
      onFailure = {
        store.clear()
        _isAuthorized.value = false
        log.i { "Session refresh failed, tokens cleared" }
      },
    )
  }

  override suspend fun signOut() {
    store.clear()
    _isAuthorized.value = false
  }

  override fun currentUser(): AuthUser? = store.current()?.toUser()
}

private fun AuthSession.toUser(): AuthUser = AuthUser(
  id = userId,
  email = email,
  displayName = displayName,
)

class SignInUseCase(private val repository: AuthRepository) {
  suspend operator fun invoke(email: String, password: String): Result<AuthUser> =
    repository.signIn(email, password)
}

class SignOutUseCase(private val repository: AuthRepository) {
  suspend operator fun invoke() = repository.signOut()
}

class IsAuthorizedUseCase(private val repository: AuthRepository) {
  operator fun invoke(): Boolean = repository.isAuthorized.value
}

class RefreshSessionUseCase(private val repository: AuthRepository) {
  suspend operator fun invoke() = repository.refreshIfNeeded()
}
