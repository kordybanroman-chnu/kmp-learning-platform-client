package rommmmaha.learning_platform_client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import rommmmaha.learning_platform_client.settings.createSettings

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    setContent {
      val settings = remember { createSettings(applicationContext) }
      App(settings = settings)
    }
  }
}
