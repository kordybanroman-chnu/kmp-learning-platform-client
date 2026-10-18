package rommmmaha.learning_platform_client

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import rommmmaha.learning_platform_client.settings.createSettings

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "Learning Platform",
  ) {
    val settings = remember { createSettings() }
    App(settings = settings)
  }
}
