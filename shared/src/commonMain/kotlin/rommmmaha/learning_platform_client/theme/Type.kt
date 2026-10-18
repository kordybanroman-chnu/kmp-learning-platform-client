package rommmmaha.learning_platform_client.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kmp_learning_platform_client.shared.generated.resources.Res
import kmp_learning_platform_client.shared.generated.resources.inter_bold
import kmp_learning_platform_client.shared.generated.resources.inter_medium
import kmp_learning_platform_client.shared.generated.resources.inter_regular
import kmp_learning_platform_client.shared.generated.resources.inter_semibold
import org.jetbrains.compose.resources.Font

@Composable
private fun interFontFamily(): FontFamily = FontFamily(
  Font(Res.font.inter_regular, FontWeight.Normal),
  Font(Res.font.inter_medium, FontWeight.Medium),
  Font(Res.font.inter_semibold, FontWeight.SemiBold),
  Font(Res.font.inter_bold, FontWeight.Bold),
)

@Composable
internal fun appTypography(): Typography {
  val fontFamily = interFontFamily()
  return Typography(
    displayLarge = TextStyle(
      fontFamily = fontFamily,
      fontSize = 56.sp,
      lineHeight = 64.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = (-1.12).sp,
    ),
    headlineLarge = TextStyle(
      fontFamily = fontFamily,
      fontSize = 32.sp,
      lineHeight = 40.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = (-0.48).sp,
    ),
    headlineMedium = TextStyle(
      fontFamily = fontFamily,
      fontSize = 28.sp,
      lineHeight = 36.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = (-0.28).sp,
    ),
    headlineSmall = TextStyle(
      fontFamily = fontFamily,
      fontSize = 24.sp,
      lineHeight = 32.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = (-0.24).sp,
    ),
    titleLarge = TextStyle(
      fontFamily = fontFamily,
      fontSize = 20.sp,
      lineHeight = 28.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = (-0.1).sp,
    ),
    titleMedium = TextStyle(
      fontFamily = fontFamily,
      fontSize = 16.sp,
      lineHeight = 24.sp,
      fontWeight = FontWeight.SemiBold,
    ),
    titleSmall = TextStyle(
      fontFamily = fontFamily,
      fontSize = 14.sp,
      lineHeight = 20.sp,
      fontWeight = FontWeight.Medium,
      letterSpacing = 0.14.sp,
    ),
    bodyLarge = TextStyle(
      fontFamily = fontFamily,
      fontSize = 18.sp,
      lineHeight = 28.sp,
      fontWeight = FontWeight.Normal,
    ),
    bodyMedium = TextStyle(
      fontFamily = fontFamily,
      fontSize = 15.sp,
      lineHeight = 24.sp,
      fontWeight = FontWeight.Normal,
    ),
    bodySmall = TextStyle(
      fontFamily = fontFamily,
      fontSize = 13.sp,
      lineHeight = 20.sp,
      fontWeight = FontWeight.Normal,
      letterSpacing = 0.07.sp,
    ),
    labelLarge = TextStyle(
      fontFamily = fontFamily,
      fontSize = 14.sp,
      lineHeight = 20.sp,
      fontWeight = FontWeight.Medium,
      letterSpacing = 0.14.sp,
    ),
    labelMedium = TextStyle(
      fontFamily = fontFamily,
      fontSize = 12.sp,
      lineHeight = 16.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = 0.24.sp,
    ),
    labelSmall = TextStyle(
      fontFamily = fontFamily,
      fontSize = 11.sp,
      lineHeight = 14.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = 0.33.sp,
    ),
  )
}
