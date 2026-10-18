package rommmmaha.learning_platform_client.settings

enum class ThemeMode(val key: String) {
  System("system"),
  Light("light"),
  Dark("dark");

  companion object {
    fun fromKey(key: String?): ThemeMode = entries.firstOrNull { it.key == key } ?: System
  }
}
