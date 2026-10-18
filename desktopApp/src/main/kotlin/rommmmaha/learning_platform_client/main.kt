package rommmmaha.learning_platform_client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "kmp-learning-platform-client",
  ) {
    App()
  }
}
