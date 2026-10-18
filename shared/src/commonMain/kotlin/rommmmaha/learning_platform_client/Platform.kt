package rommmmaha.learning_platform_client

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform
