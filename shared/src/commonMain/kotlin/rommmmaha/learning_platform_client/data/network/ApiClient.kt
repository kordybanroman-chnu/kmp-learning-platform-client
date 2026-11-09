package rommmmaha.learning_platform_client.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import rommmmaha.learning_platform_client.data.auth.TokenStore

val apiJson: Json = Json {
  ignoreUnknownKeys = true
  explicitNulls = false
}

fun createPlainHttpClient(): HttpClient = HttpClient(OkHttp) {
  install(ContentNegotiation) {
    json(apiJson)
  }
  install(Logging) {
    level = LogLevel.INFO
  }
}

fun createAuthedHttpClient(authApi: AuthApi, tokenStore: TokenStore): HttpClient =
  HttpClient(OkHttp) {
    install(ContentNegotiation) {
      json(apiJson)
    }
    install(Logging) {
      level = LogLevel.INFO
    }
    install(Auth) {
      bearer {
        loadTokens {
          tokenStore.current()?.let { BearerTokens(it.accessToken, it.refreshToken) }
        }
        refreshTokens {
          val current = tokenStore.current() ?: return@refreshTokens null
          val session = authApi.refresh(current.refreshToken).getOrNull()
          if (session == null) {
            tokenStore.clear()
          } else {
            tokenStore.save(session)
          }
          session?.let { BearerTokens(it.accessToken, it.refreshToken) }
        }
      }
    }
  }
