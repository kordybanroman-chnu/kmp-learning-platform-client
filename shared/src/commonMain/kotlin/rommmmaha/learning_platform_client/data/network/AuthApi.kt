package rommmmaha.learning_platform_client.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive
import rommmmaha.learning_platform_client.data.auth.AuthSession
import rommmmaha.learning_platform_client.domain.auth.AuthError
import rommmmaha.learning_platform_client.domain.auth.AuthException
import rommmmaha.learning_platform_client.platform.nowMillis

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class RefreshRequest(val refreshToken: String)

@Serializable
data class AuthResponse(
  val accessToken: String,
  val refreshToken: String,
  val tokenType: String = "Bearer",
  val expiresIn: Long,
  val user: UserResponse,
)

@Serializable
data class UserResponse(
  val id: JsonElement,
  val email: String,
  val displayName: String,
  val role: String = "student",
)

@Serializable
data class ProblemDetails(
  val title: String? = null,
  val status: Int? = null,
  val detail: String? = null,
  val traceId: String? = null,
  val errors: Map<String, List<String>>? = null,
)

interface AuthApi {
  suspend fun login(email: String, password: String): Result<AuthSession>
  suspend fun refresh(refreshToken: String): Result<AuthSession>
}

class KtorAuthApi(
  private val client: HttpClient,
  private val baseUrl: String,
) : AuthApi {
  override suspend fun login(email: String, password: String): Result<AuthSession> =
    request("/api/auth/login", LoginRequest(email, password))

  override suspend fun refresh(refreshToken: String): Result<AuthSession> =
    request("/api/auth/refresh", RefreshRequest(refreshToken))

  private suspend fun request(path: String, body: Any): Result<AuthSession> {
    return try {
      val response = client.post(baseUrl.trimEnd('/') + path) {
        contentType(ContentType.Application.Json)
        setBody(body)
      }
      if (response.status == HttpStatusCode.OK) {
        Result.success(response.toSession())
      } else {
        Result.failure(AuthException(mapError(response)))
      }
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      Result.failure(AuthException(AuthError.Network))
    }
  }

  private suspend fun HttpResponse.toSession(): AuthSession {
    val parsed = body<AuthResponse>()
    return AuthSession(
      accessToken = parsed.accessToken,
      refreshToken = parsed.refreshToken,
      expiresAtEpochMs = nowMillis() + parsed.expiresIn * 1000,
      userId = parsed.user.id.jsonPrimitive.content,
      email = parsed.user.email,
      displayName = parsed.user.displayName,
    )
  }

  private suspend fun mapError(response: HttpResponse): AuthError {
    val problem = runCatching {
      apiJson.decodeFromString<ProblemDetails>(response.bodyAsTextForError())
    }.getOrNull()
    return when (response.status) {
      HttpStatusCode.Unauthorized ->
        AuthError.InvalidCredentials(problem?.detail)
      HttpStatusCode.BadRequest ->
        AuthError.InvalidRequest(problem?.detail)
      else ->
        AuthError.Unknown(problem?.detail ?: response.status.description)
    }
  }

  private suspend fun HttpResponse.bodyAsTextForError(): String = try {
    bodyAsText()
  } catch (_: Exception) {
    ""
  }
}
