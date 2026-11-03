package rommmmaha.learning_platform_client.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import rommmmaha.learning_platform_client.theme.LogoDarkContainer
import rommmmaha.learning_platform_client.theme.LogoDarkContent

@Composable
fun LogoMark(
  modifier: Modifier = Modifier,
  size: Dp = 28.dp,
) {
  val dark = MaterialTheme.colorScheme.background.luminance() < 0.5f
  val container = if (dark) LogoDarkContainer else MaterialTheme.colorScheme.primary
  val content = if (dark) LogoDarkContent else MaterialTheme.colorScheme.onPrimary

  Box(
    modifier = modifier
      .size(size)
      .clip(RoundedCornerShape(size * 0.22f))
      .background(container),
    contentAlignment = Alignment.Center,
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val unit = this.size.width / 108f
      val glyph = Path().apply {
        moveTo(33.4f * unit, 25.6f * unit)
        lineTo(50f * unit, 25.6f * unit)
        lineTo(50f * unit, 68.4f * unit)
        lineTo(74.6f * unit, 68.4f * unit)
        lineTo(74.6f * unit, 82.4f * unit)
        lineTo(33.4f * unit, 82.4f * unit)
        close()
      }
      drawPath(path = glyph, color = content)
    }
  }
}

@Composable
fun AppCard(
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit,
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surface,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    shadowElevation = 1.dp,
  ) {
    Column(content = content)
  }
}

@Composable
fun SectionLabel(
  text: String,
  modifier: Modifier = Modifier,
) {
  Text(
    text = text,
    style = MaterialTheme.typography.labelMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier = modifier.padding(start = 4.dp, top = 20.dp, bottom = 8.dp),
  )
}

@Composable
fun SegmentedControl(
  options: List<String>,
  selectedIndex: Int,
  onSelect: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  val dark = MaterialTheme.colorScheme.background.luminance() < 0.5f
  val containerColor = if (dark) {
    MaterialTheme.colorScheme.surfaceContainerHigh
  } else {
    MaterialTheme.colorScheme.surfaceVariant
  }
  val activeColor = if (dark) {
    MaterialTheme.colorScheme.surfaceContainerHighest
  } else {
    MaterialTheme.colorScheme.surface
  }
  val activeShape = RoundedCornerShape(9.dp)

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(containerColor)
      .padding(4.dp),
    horizontalArrangement = Arrangement.spacedBy(2.dp),
  ) {
    options.forEachIndexed { index, label ->
      val selected = index == selectedIndex
      Box(
        modifier = Modifier
          .weight(1f)
          .then(if (selected) Modifier.shadow(1.dp, activeShape) else Modifier)
          .clip(activeShape)
          .background(if (selected) activeColor else Color.Transparent)
          .selectable(
            selected = selected,
            onClick = { onSelect(index) },
            role = Role.RadioButton,
          )
          .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center,
      ) {
        Text(
          text = label,
          style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
          ),
          color = if (selected) {
            MaterialTheme.colorScheme.onSurface
          } else {
            MaterialTheme.colorScheme.onSurfaceVariant
          },
          maxLines = 1,
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}
