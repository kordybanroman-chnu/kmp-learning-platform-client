package rommmmaha.learning_platform_client.ui.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rommmmaha.learning_platform_client.ui.components.LogoMark

import kmp_learning_platform_client.shared.generated.resources.Res
import kmp_learning_platform_client.shared.generated.resources.app_name
import kmp_learning_platform_client.shared.generated.resources.auth_error_password_required
import kmp_learning_platform_client.shared.generated.resources.auth_error_password_short
import kmp_learning_platform_client.shared.generated.resources.auth_error_sign_in
import kmp_learning_platform_client.shared.generated.resources.auth_error_username_required
import kmp_learning_platform_client.shared.generated.resources.auth_error_username_short
import kmp_learning_platform_client.shared.generated.resources.auth_hide_password
import kmp_learning_platform_client.shared.generated.resources.auth_no_account
import kmp_learning_platform_client.shared.generated.resources.auth_password
import kmp_learning_platform_client.shared.generated.resources.auth_show_password
import kmp_learning_platform_client.shared.generated.resources.auth_sign_in
import kmp_learning_platform_client.shared.generated.resources.auth_signing_in
import kmp_learning_platform_client.shared.generated.resources.auth_subtitle
import kmp_learning_platform_client.shared.generated.resources.auth_title
import kmp_learning_platform_client.shared.generated.resources.auth_username

@Composable
fun AuthScreen(
  onSignedIn: () -> Unit,
  viewModel: AuthViewModel = koinViewModel(),
) {
  val state by viewModel.state.collectAsState()
  val passwordFocus = remember { FocusRequester() }

  LaunchedEffect(state.isSignedIn) {
    if (state.isSignedIn) onSignedIn()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .safeContentPadding()
      .imePadding(),
  ) {
    Column(
      modifier = Modifier
        .widthIn(max = 400.dp)
        .fillMaxWidth()
        .align(Alignment.Center)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        LogoMark(size = 28.dp)
        Spacer(Modifier.width(10.dp))
        Text(
          text = stringResource(Res.string.app_name),
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurface,
        )
      }

      Spacer(Modifier.height(36.dp))

      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        LockBadge()
        Spacer(Modifier.height(14.dp))
        Text(
          text = stringResource(Res.string.auth_title),
          style = MaterialTheme.typography.headlineLarge,
          color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(6.dp))
        Text(
          text = stringResource(Res.string.auth_subtitle),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
        )
      }

      Spacer(Modifier.height(28.dp))

      AuthField(
        label = stringResource(Res.string.auth_username),
        value = state.username,
        onValueChange = { viewModel.onIntent(AuthIntent.UsernameChanged(it)) },
        error = state.usernameError,
        enabled = !state.isSubmitting,
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Text,
          imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
          onNext = { passwordFocus.requestFocus() },
        ),
      )

      Spacer(Modifier.height(14.dp))

      AuthField(
        label = stringResource(Res.string.auth_password),
        value = state.password,
        onValueChange = { viewModel.onIntent(AuthIntent.PasswordChanged(it)) },
        error = state.passwordError,
        enabled = !state.isSubmitting,
        fieldModifier = Modifier.focusRequester(passwordFocus),
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Password,
          imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
          onDone = { viewModel.onIntent(AuthIntent.Submit) },
        ),
        visualTransformation = if (state.isPasswordVisible) {
          VisualTransformation.None
        } else {
          PasswordVisualTransformation()
        },
        trailingIcon = {
          TextButton(
            onClick = { viewModel.onIntent(AuthIntent.TogglePasswordVisibility) },
            contentPadding = PaddingValues(horizontal = 8.dp),
            enabled = !state.isSubmitting,
          ) {
            Text(
              text = stringResource(
                if (state.isPasswordVisible) {
                  Res.string.auth_hide_password
                } else {
                  Res.string.auth_show_password
                },
              ),
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.primary,
            )
          }
        },
      )

      if (state.signInFailed) {
        Spacer(Modifier.height(12.dp))
        Text(
          text = stringResource(Res.string.auth_error_sign_in),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.error,
        )
      }

      Spacer(Modifier.height(20.dp))

      Button(
        onClick = { viewModel.onIntent(AuthIntent.Submit) },
        enabled = !state.isSubmitting,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp),
        shape = MaterialTheme.shapes.medium,
      ) {
        Text(
          text = stringResource(
            if (state.isSubmitting) Res.string.auth_signing_in else Res.string.auth_sign_in,
          ),
          style = MaterialTheme.typography.labelLarge,
        )
      }

      Spacer(Modifier.height(16.dp))

      Text(
        text = stringResource(Res.string.auth_no_account),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
      )
    }
  }
}

@Composable
private fun LockBadge() {
  val color = MaterialTheme.colorScheme.primary
  Box(
    modifier = Modifier
      .size(56.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colorScheme.primaryContainer),
    contentAlignment = Alignment.Center,
  ) {
    Canvas(modifier = Modifier.size(26.dp)) {
      val stroke = 2.dp.toPx()
      drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.12f, size.height * 0.44f),
        size = Size(size.width * 0.76f, size.height * 0.44f),
        cornerRadius = CornerRadius(3.dp.toPx()),
        style = Stroke(stroke),
      )
      drawArc(
        color = color,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(size.width * 0.25f, size.height * 0.1f),
        size = Size(size.width * 0.5f, size.height * 0.56f),
        style = Stroke(stroke, cap = StrokeCap.Round),
      )
      drawCircle(
        color = color,
        radius = size.width * 0.055f,
        center = Offset(size.width * 0.5f, size.height * 0.66f),
      )
    }
  }
}

@Composable
private fun AuthField(
  label: String,
  value: String,
  onValueChange: (String) -> Unit,
  error: AuthFieldError?,
  enabled: Boolean,
  keyboardOptions: KeyboardOptions,
  keyboardActions: KeyboardActions,
  fieldModifier: Modifier = Modifier,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  trailingIcon: @Composable (() -> Unit)? = null,
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(Modifier.height(6.dp))
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .then(fieldModifier),
      enabled = enabled,
      singleLine = true,
      isError = error != null,
      shape = MaterialTheme.shapes.medium,
      textStyle = MaterialTheme.typography.bodyLarge,
      keyboardOptions = keyboardOptions,
      keyboardActions = keyboardActions,
      visualTransformation = visualTransformation,
      trailingIcon = trailingIcon,
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        errorBorderColor = MaterialTheme.colorScheme.error,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        errorContainerColor = MaterialTheme.colorScheme.surface,
      ),
    )
    if (error != null) {
      Spacer(Modifier.height(6.dp))
      Text(
        text = authErrorMessage(error),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error,
      )
    }
  }
}

@Composable
private fun authErrorMessage(error: AuthFieldError): String = when (error) {
  AuthFieldError.UsernameRequired -> stringResource(Res.string.auth_error_username_required)
  AuthFieldError.UsernameTooShort -> stringResource(Res.string.auth_error_username_short)
  AuthFieldError.PasswordRequired -> stringResource(Res.string.auth_error_password_required)
  AuthFieldError.PasswordTooShort -> stringResource(Res.string.auth_error_password_short)
}
