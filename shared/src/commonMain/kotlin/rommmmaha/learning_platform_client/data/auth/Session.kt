package rommmmaha.learning_platform_client.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthSession(
  val accessToken: String,
  val refreshToken: String,
  val expiresAtEpochMs: Long,
  val userId: String,
  val email: String,
  val displayName: String,
)

fun AuthSession.isExpired(nowMs: Long): Boolean = nowMs >= expiresAtEpochMs
