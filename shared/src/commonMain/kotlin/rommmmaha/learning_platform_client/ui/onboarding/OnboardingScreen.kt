package rommmmaha.learning_platform_client.ui.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rommmmaha.learning_platform_client.ui.components.LogoMark

import kmp_learning_platform_client.shared.generated.resources.Res
import kmp_learning_platform_client.shared.generated.resources.app_name
import kmp_learning_platform_client.shared.generated.resources.onboarding_next
import kmp_learning_platform_client.shared.generated.resources.onboarding_skip
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_catalog_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_catalog_title
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_enrollment_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_enrollment_title
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_learning_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_learning_title
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_progress_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_progress_title
import kmp_learning_platform_client.shared.generated.resources.onboarding_demo_course_aspnet
import kmp_learning_platform_client.shared.generated.resources.onboarding_demo_course_csharp
import kmp_learning_platform_client.shared.generated.resources.onboarding_demo_meta_aspnet
import kmp_learning_platform_client.shared.generated.resources.onboarding_demo_meta_csharp
import kmp_learning_platform_client.shared.generated.resources.onboarding_start

private data class OnboardingSlide(val title: StringResource, val body: StringResource)

private val slides = listOf(
  OnboardingSlide(
    title = Res.string.onboarding_slide_catalog_title,
    body = Res.string.onboarding_slide_catalog_body,
  ),
  OnboardingSlide(
    title = Res.string.onboarding_slide_enrollment_title,
    body = Res.string.onboarding_slide_enrollment_body,
  ),
  OnboardingSlide(
    title = Res.string.onboarding_slide_learning_title,
    body = Res.string.onboarding_slide_learning_body,
  ),
  OnboardingSlide(
    title = Res.string.onboarding_slide_progress_title,
    body = Res.string.onboarding_slide_progress_body,
  ),
)

@Composable
fun OnboardingScreen(
  onFinished: () -> Unit,
  viewModel: OnboardingViewModel = koinViewModel(),
) {
  val state by viewModel.state.collectAsState()
  val pagerState = rememberPagerState(pageCount = { slides.size })
  val scope = rememberCoroutineScope()

  LaunchedEffect(pagerState.currentPage) {
    viewModel.onIntent(OnboardingIntent.PageChanged(pagerState.currentPage))
  }
  LaunchedEffect(state.isFinished) {
    if (state.isFinished) onFinished()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .safeContentPadding()
      .padding(vertical = 16.dp),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .padding(horizontal = 20.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      LogoMark(size = 28.dp)
      Spacer(Modifier.width(10.dp))
      Text(
        text = stringResource(Res.string.app_name),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
      )
      Spacer(Modifier.weight(1f))
      if (!state.isLastPage) {
        TextButton(
          onClick = { viewModel.onIntent(OnboardingIntent.Skip) },
          contentPadding = PaddingValues(horizontal = 8.dp),
        ) {
          Text(
            text = stringResource(Res.string.onboarding_skip),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
          )
        }
      }
    }

    HorizontalPager(
      state = pagerState,
      contentPadding = PaddingValues(horizontal = 20.dp),
      pageSpacing = 20.dp,
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(top = 16.dp),
    ) { page ->
      val slide = slides[page]
      OnboardingPage(
        page = page,
        title = stringResource(slide.title),
        body = stringResource(slide.body),
      )
    }

    Spacer(Modifier.height(12.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      slides.indices.forEach { index ->
        val active = index == state.page
        Box(
          modifier = Modifier
            .padding(horizontal = 3.dp)
            .size(width = if (active) 20.dp else 6.dp, height = 6.dp)
            .clip(CircleShape)
            .background(
              if (active) {
                MaterialTheme.colorScheme.primary
              } else {
                MaterialTheme.colorScheme.outlineVariant
              },
            ),
        )
      }
    }

    Spacer(Modifier.height(16.dp))

    Button(
      onClick = {
        if (state.isLastPage) {
          viewModel.onIntent(OnboardingIntent.Finish)
        } else {
          scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .height(48.dp),
      shape = MaterialTheme.shapes.medium,
    ) {
      Text(
        text = stringResource(
          if (state.isLastPage) Res.string.onboarding_start else Res.string.onboarding_next,
        ),
        style = MaterialTheme.typography.labelLarge,
      )
    }
  }
}

@Composable
private fun OnboardingPage(
  page: Int,
  title: String,
  body: String,
) {
  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
    val halfHeight = maxHeight / 2
    val panelHeight = (halfHeight - 16.dp).coerceIn(140.dp, 318.dp)
    Column(modifier = Modifier.fillMaxSize()) {
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        contentAlignment = Alignment.Center,
      ) {
        IllustrationPanel(page = page, height = panelHeight)
      }
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart,
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Text(
            text = title,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Start,
          )
          Text(
            text = body,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }
  }
}

@Composable
private fun IllustrationPanel(
  page: Int,
  height: Dp,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(height)
      .clip(RoundedCornerShape(28.dp))
      .background(MaterialTheme.colorScheme.primaryContainer),
    contentAlignment = Alignment.Center,
  ) {
    Surface(
      modifier = Modifier.width(252.dp),
      shape = RoundedCornerShape(18.dp),
      color = MaterialTheme.colorScheme.surface,
      border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
      shadowElevation = 8.dp,
    ) {
      Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center,
      ) {
        when (page) {
          0 -> CatalogIllustration()
          1 -> EnrollmentIllustration()
          2 -> LearningIllustration()
          else -> ProgressIllustration()
        }
      }
    }
  }
}

@Composable
private fun CatalogIllustration() {
  Column {
    MiniCourseRow(
      initial = "О",
      title = stringResource(Res.string.onboarding_demo_course_csharp),
      meta = stringResource(Res.string.onboarding_demo_meta_csharp),
    )
    HorizontalDivider(
      thickness = 1.dp,
      color = MaterialTheme.colorScheme.outlineVariant,
    )
    MiniCourseRow(
      initial = "A",
      title = stringResource(Res.string.onboarding_demo_course_aspnet),
      meta = stringResource(Res.string.onboarding_demo_meta_aspnet),
    )
  }
}

@Composable
private fun MiniCourseRow(
  initial: String,
  title: String,
  meta: String,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 9.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    Box(
      modifier = Modifier
        .size(28.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.primaryContainer),
      contentAlignment = Alignment.Center,
    ) {
      Text(
        text = initial,
        style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
        color = MaterialTheme.colorScheme.primary,
      )
    }
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp, lineHeight = 16.sp),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
      )
      Text(
        text = meta,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
      )
    }
    Text(
      text = "→",
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Composable
private fun EnrollmentIllustration() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(14.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      StepperCell("−")
      Text(
        text = "1",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
      )
      StepperCell("+")
    }
    Box(
      modifier = Modifier
        .width(150.dp)
        .height(8.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.surfaceVariant),
    )
  }
}

@Composable
private fun StepperCell(text: String) {
  Box(
    modifier = Modifier
      .size(34.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}

@Composable
private fun PlayGlyph() {
  val color = MaterialTheme.colorScheme.primary
  Canvas(modifier = Modifier.size(12.dp)) {
    val path = Path().apply {
      moveTo(0f, 0f)
      lineTo(size.width, size.height / 2f)
      lineTo(0f, size.height)
      close()
    }
    drawPath(path = path, color = color)
  }
}

@Composable
private fun CheckGlyph() {
  val color = MaterialTheme.colorScheme.primary
  Canvas(modifier = Modifier.size(24.dp)) {
    val stroke = 2.5.dp.toPx()
    drawLine(
      color = color,
      start = Offset(size.width * 0.12f, size.height * 0.55f),
      end = Offset(size.width * 0.42f, size.height * 0.82f),
      strokeWidth = stroke,
      cap = StrokeCap.Round,
    )
    drawLine(
      color = color,
      start = Offset(size.width * 0.42f, size.height * 0.82f),
      end = Offset(size.width * 0.9f, size.height * 0.18f),
      strokeWidth = stroke,
      cap = StrokeCap.Round,
    )
  }
}

@Composable
private fun LearningIllustration() {
  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
      ) {
        PlayGlyph()
      }
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(5.dp),
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(8.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        )
        Box(
          modifier = Modifier
            .fillMaxWidth(0.55f)
            .height(6.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        )
      }
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
      Box(
        modifier = Modifier
          .weight(0.42f)
          .fillMaxHeight()
          .background(MaterialTheme.colorScheme.primary),
      )
      Spacer(Modifier.weight(0.58f))
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Box(
        modifier = Modifier
          .size(20.dp)
          .clip(RoundedCornerShape(6.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant),
      )
      Box(
        modifier = Modifier
          .width(110.dp)
          .height(6.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.surfaceVariant),
      )
    }
  }
}

@Composable
private fun ProgressIllustration() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Box(
      modifier = Modifier
        .size(56.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primaryContainer),
      contentAlignment = Alignment.Center,
    ) {
      CheckGlyph()
    }
    Text(
      text = "80%",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onSurface,
    )
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
      Box(
        modifier = Modifier
          .weight(0.8f)
          .fillMaxHeight()
          .background(MaterialTheme.colorScheme.primary),
      )
      Spacer(Modifier.weight(0.2f))
    }
  }
}
