package rommmmaha.learning_platform_client.settings

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

fun createSettings(context: Context): Settings =
  SharedPreferencesSettings(
    context.getSharedPreferences("learning_platform_client", Context.MODE_PRIVATE)
  )
