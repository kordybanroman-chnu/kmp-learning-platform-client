package rommmmaha.learning_platform_client

class Greeting {
  private val platform = getPlatform()

  fun greet(): String {
    return sayHello(platform.name)
  }
}
