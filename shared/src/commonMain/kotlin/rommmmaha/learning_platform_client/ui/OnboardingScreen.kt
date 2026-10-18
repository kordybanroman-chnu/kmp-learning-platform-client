package rommmmaha.learning_platform_client.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

import kmp_learning_platform_client.shared.generated.resources.Res
import kmp_learning_platform_client.shared.generated.resources.app_name
import kmp_learning_platform_client.shared.generated.resources.onboarding_next
import kmp_learning_platform_client.shared.generated.resources.onboarding_skip
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_catalog_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_catalog_title
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_enrollment_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_enrollment_title
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_language_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_language_title
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_status_body
import kmp_learning_platform_client.shared.generated.resources.onboarding_slide_status_title
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
    title = Res.string.onboarding_slide_status_title,
    body = Res.string.onboarding_slide_status_body,
  ),
  OnboardingSlide(
    title = Res.string.onboarding_slide_language_title,
    body = Res.string.onboarding_slide_language_body,
  ),
)

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
  val pagerState = rememberPagerState(pageCount = { slides.size })
  val scope = rememberCoroutineScope()
  val lastPage = slides.lastIndex

  Column(
    modifier = Modifier
      .fillMaxSize()
      .safeContentPadding()
      .padding(16.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = stringResource(Res.string.app_name),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.weight(1f),
      )
      TextButton(onClick = onFinished) {
        Text(stringResource(Res.string.onboarding_skip))
      }
    }

    HorizontalPager(
      state = pagerState,
      modifier = Modifier.weight(1f),
    ) { page ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Illustration(page)
        Text(
          text = stringResource(slides[page].title),
          style = MaterialTheme.typography.headlineMedium,
          modifier = Modifier.fillMaxWidth(),
        )
        Text(
          text = stringResource(slides[page].body),
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.fillMaxWidth(),
        )
      }
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      horizontalArrangement = Arrangement.Center,
    ) {
      slides.indices.forEach { index ->
        val active = index == pagerState.currentPage
        Box(
          modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(width = if (active) 24.dp else 8.dp, height = 8.dp)
            .background(
              color = if (active) {
                MaterialTheme.colorScheme.primary
              } else {
                MaterialTheme.colorScheme.surfaceVariant
              },
              shape = CircleShape,
            ),
        )
      }
    }

    Button(
      onClick = {
        if (pagerState.currentPage == lastPage) {
          onFinished()
        } else {
          scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
        }
      },
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(
        stringResource(
          if (pagerState.currentPage == lastPage) {
            Res.string.onboarding_start
          } else {
            Res.string.onboarding_next
          },
        ),
      )
    }
  }
}

@Composable
private fun Illustration(page: Int) {
  Box(
    modifier = Modifier
      .size(width = 220.dp, height = 160.dp)
      .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.extraLarge),
    contentAlignment = Alignment.Center,
  ) {
    when (page) {
      0 -> CatalogIllustration()
      1 -> CounterIllustration()
      2 -> StepsIllustration()
      else -> LanguagesIllustration()
    }
  }
}

@Composable
private fun CatalogIllustration() {
  val widths = listOf(160, 130, 100)
  Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    widths.forEach { width ->
      Box(
        modifier = Modifier
          .width(width.dp)
          .height(24.dp)
          .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small),
      )
    }
  }
}

@Composable
private fun CounterIllustration() {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    StepperCell("−")
    Text(
      text = "1",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
    StepperCell("+")
  }
}

@Composable
private fun StepperCell(text: String) {
  Box(
    modifier = Modifier
      .size(32.dp)
      .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small),
    contentAlignment = Alignment.Center,
  ) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
  }
}

@Composable
private fun StepsIllustration() {
  Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    repeat(5) { index ->
      Box(
        modifier = Modifier
          .size(16.dp)
          .background(
            color = if (index < 3) {
              MaterialTheme.colorScheme.primary
            } else {
              MaterialTheme.colorScheme.surface
            },
            shape = CircleShape,
          ),
      )
    }
  }
}

@Composable
private fun LanguagesIllustration() {
  Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    LanguageCell("УКР", selected = true)
    LanguageCell("EN", selected = false)
  }
}

@Composable
private fun LanguageCell(text: String, selected: Boolean) {
  Box(
    modifier = Modifier
      .background(MaterialTheme.colorScheme.surface, CircleShape)
      .border(
        width = 1.dp,
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
        shape = CircleShape,
      )
      .padding(horizontal = 16.dp, vertical = 8.dp),
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelLarge,
      color = if (selected) {
        MaterialTheme.colorScheme.primary
      } else {
        MaterialTheme.colorScheme.onSurfaceVariant
      },
    )
  }
}
