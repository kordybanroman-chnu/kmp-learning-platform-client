package rommmmaha.learning_platform_client.data.network

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.json.jsonPrimitive

class AuthApiDecodingTest {
  @Test
  fun loginResponseWithNumericUserIdDecodes() {
    val json = """
      {"accessToken":"a","refreshToken":"r","tokenType":"Bearer","expiresIn":3600,"user":{"id":1,"email":"student1@example.com","displayName":"Олена Петренко","role":"student"}}
    """.trimIndent()

    val parsed = apiJson.decodeFromString<AuthResponse>(json)

    assertEquals("a", parsed.accessToken)
    assertEquals("1", parsed.user.id.jsonPrimitive.content)
    assertEquals("student1@example.com", parsed.user.email)
  }

  @Test
  fun problemDetailsDecodes() {
    val json = """
      {"title":"Unauthorized","status":401,"detail":"Invalid email or password.","traceId":"abc"}
    """.trimIndent()

    val parsed = apiJson.decodeFromString<ProblemDetails>(json)

    assertEquals(401, parsed.status)
    assertEquals("Invalid email or password.", parsed.detail)
  }
}
