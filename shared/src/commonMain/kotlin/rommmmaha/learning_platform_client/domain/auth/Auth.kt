package rommmmaha.learning_platform_client.domain.auth

import rommmmaha.learning_platform_client.log

interface AuthRepository {
  suspend fun signIn(username: String, password: String): Result<Unit>
}

class FakeAuthRepository : AuthRepository {
  override suspend fun signIn(username: String, password: String): Result<Unit> {
    log.i { "Sign in accepted for $username" }
    return Result.success(Unit)
  }
}

class SignInUseCase(private val repository: AuthRepository) {
  suspend operator fun invoke(username: String, password: String): Result<Unit> =
    repository.signIn(username, password)
}
