import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
}

dependencies {
  implementation(project(":shared"))

  implementation(compose.desktop.currentOs)
  implementation(libs.kotlinx.coroutinesSwing)
}

compose.desktop {
  application {
    mainClass = "rommmmaha.learning_platform_client.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "rommmmaha.learning_platform_client"
      packageVersion = "1.0.0"
      linux {
        modules("jdk.unsupported", "java.management")
      }
    }
  }
}
