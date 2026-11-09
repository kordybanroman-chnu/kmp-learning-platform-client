package rommmmaha.learning_platform_client

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import eu.anifantakis.lib.ksafe.KSafe
import rommmmaha.learning_platform_client.settings.createSettings

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "Learning Platform",
  ) {
    val settings = remember { createSettings() }
    val ksafe = remember { KSafe() }
    App(settings = settings, ksafe = ksafe)
  }
}
