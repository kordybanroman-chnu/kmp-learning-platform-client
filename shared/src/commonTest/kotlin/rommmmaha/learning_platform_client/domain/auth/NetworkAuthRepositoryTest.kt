package rommmmaha.learning_platform_client.domain.auth

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import rommmmaha.learning_platform_client.data.auth.AuthSession
import rommmmaha.learning_platform_client.data.auth.InMemoryTokenStore
import rommmmaha.learning_platform_client.data.network.AuthApi

class NetworkAuthRepositoryTest {
  @Test
  fun signInStoresSessionAndAuthorizes() = runTest {
    val store = InMemoryTokenStore()
    val api = FakeAuthApi()
    val repository = NetworkAuthRepository(api, store)

    val result = repository.signIn("student@example.com", "secret1")

    assertTrue(result.isSuccess)
    assertEquals("student@example.com", store.current()?.email)
    assertTrue(repository.isAuthorized.value)
    assertEquals("student@example.com", repository.currentUser()?.email)
  }

  @Test
  fun refreshRunsWhenAccessTokenExpired() = runTest {
    val store = InMemoryTokenStore()
    store.save(testSession(expiresAt = 0))
    val api = FakeAuthApi()
    val repository = NetworkAuthRepository(api, store)

    repository.refreshIfNeeded()

    assertEquals(1, api.refreshCalls)
    assertTrue(repository.isAuthorized.value)
  }

  @Test
  fun refreshSkipsWhenAccessTokenFresh() = runTest {
    val store = InMemoryTokenStore()
    store.save(testSession(expiresAt = Long.MAX_VALUE))
    val api = FakeAuthApi()
    val repository = NetworkAuthRepository(api, store)

    repository.refreshIfNeeded()

    assertEquals(0, api.refreshCalls)
    assertTrue(repository.isAuthorized.value)
  }

  @Test
  fun refreshFailureClearsTokens() = runTest {
    val store = InMemoryTokenStore()
    store.save(testSession(expiresAt = 0))
    val api = FakeAuthApi(fail = true)
    val repository = NetworkAuthRepository(api, store)

    repository.refreshIfNeeded()

    assertEquals(null, store.current())
    assertFalse(repository.isAuthorized.value)
  }

  @Test
  fun signOutClearsTokens() = runTest {
    val store = InMemoryTokenStore()
    store.save(testSession(expiresAt = Long.MAX_VALUE))
    val repository = NetworkAuthRepository(FakeAuthApi(), store)

    repository.signOut()

    assertEquals(null, store.current())
    assertFalse(repository.isAuthorized.value)
  }

  private class FakeAuthApi(private val fail: Boolean = false) : AuthApi {
    var refreshCalls = 0

    override suspend fun login(email: String, password: String): Result<AuthSession> =
      Result.success(testSession(Long.MAX_VALUE).copy(email = email))

    override suspend fun refresh(refreshToken: String): Result<AuthSession> {
      refreshCalls += 1
      return if (fail) {
        Result.failure(AuthException(AuthError.InvalidCredentials()))
      } else {
        Result.success(testSession(Long.MAX_VALUE).copy(accessToken = "new-access"))
      }
    }
  }
}

private fun testSession(expiresAt: Long): AuthSession = AuthSession(
  accessToken = "old-access",
  refreshToken = "old-refresh",
  expiresAtEpochMs = expiresAt,
  userId = "1",
  email = "student@example.com",
  displayName = "Student",
)
