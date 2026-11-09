package rommmmaha.learning_platform_client.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
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
import kmp_learning_platform_client.shared.generated.resources.auth_email
import kmp_learning_platform_client.shared.generated.resources.auth_error_email_invalid
import kmp_learning_platform_client.shared.generated.resources.auth_error_email_required
import kmp_learning_platform_client.shared.generated.resources.auth_error_invalid_credentials
import kmp_learning_platform_client.shared.generated.resources.auth_error_invalid_request
import kmp_learning_platform_client.shared.generated.resources.auth_error_network
import kmp_learning_platform_client.shared.generated.resources.auth_error_password_required
import kmp_learning_platform_client.shared.generated.resources.auth_error_password_short
import kmp_learning_platform_client.shared.generated.resources.auth_error_unknown
import kmp_learning_platform_client.shared.generated.resources.auth_hide_password
import kmp_learning_platform_client.shared.generated.resources.auth_no_account
import kmp_learning_platform_client.shared.generated.resources.auth_password
import kmp_learning_platform_client.shared.generated.resources.auth_show_password
import kmp_learning_platform_client.shared.generated.resources.auth_sign_in
import kmp_learning_platform_client.shared.generated.resources.auth_signing_in
import kmp_learning_platform_client.shared.generated.resources.auth_subtitle
import kmp_learning_platform_client.shared.generated.resources.auth_title

@Composable
fun AuthScreen(
  onSignedIn: () -> Unit,
  viewModel: AuthViewModel = koinViewModel(),
) {
  val state by viewModel.state.collectAsState()
  val passwordFocus = remember { FocusRequester() }
  val snackbar = remember { SnackbarHostState() }
  val invalidCredentialsMsg = stringResource(Res.string.auth_error_invalid_credentials)
  val invalidRequestMsg = stringResource(Res.string.auth_error_invalid_request)
  val networkMsg = stringResource(Res.string.auth_error_network)
  val unknownMsg = stringResource(Res.string.auth_error_unknown)

  LaunchedEffect(state.isSignedIn) {
    if (state.isSignedIn) onSignedIn()
  }

  LaunchedEffect(viewModel) {
    viewModel.effects.collect { effect ->
      val error = (effect as? AuthEffect.ShowError)?.error ?: return@collect
      val message = when (error) {
        SignInError.InvalidCredentials -> invalidCredentialsMsg
        SignInError.InvalidRequest -> invalidRequestMsg
        SignInError.Network -> networkMsg
        SignInError.Unknown -> unknownMsg
      }
      snackbar.showSnackbar(message)
    }
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
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        LogoMark(size = 56.dp)
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
        label = stringResource(Res.string.auth_email),
        value = state.email,
        onValueChange = { viewModel.onIntent(AuthIntent.EmailChanged(it)) },
        emailError = state.emailError,
        enabled = !state.isSubmitting,
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Email,
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
        passwordError = state.passwordError,
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

    SnackbarHost(
      hostState = snackbar,
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(horizontal = 20.dp, vertical = 16.dp),
    )
  }
}

@Composable
private fun AuthField(
  label: String,
  value: String,
  onValueChange: (String) -> Unit,
  emailError: EmailError? = null,
  passwordError: PasswordError? = null,
  enabled: Boolean,
  keyboardOptions: KeyboardOptions,
  keyboardActions: KeyboardActions,
  fieldModifier: Modifier = Modifier,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  trailingIcon: @Composable (() -> Unit)? = null,
) {
  val errorText = when {
    emailError != null -> emailErrorMessage(emailError)
    passwordError != null -> passwordErrorMessage(passwordError)
    else -> null
  }

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
        .then(fieldModifier)
        .then(
          if (errorText != null) {
            Modifier.semantics { this.error(errorText) }
          } else {
            Modifier
          },
        ),
      enabled = enabled,
      singleLine = true,
      isError = errorText != null,
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
    if (errorText != null) {
      Spacer(Modifier.height(6.dp))
      Text(
        text = errorText,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error,
      )
    }
  }
}

@Composable
private fun emailErrorMessage(error: EmailError): String = when (error) {
  EmailError.Required -> stringResource(Res.string.auth_error_email_required)
  EmailError.Invalid -> stringResource(Res.string.auth_error_email_invalid)
}

@Composable
private fun passwordErrorMessage(error: PasswordError): String = when (error) {
  PasswordError.Required -> stringResource(Res.string.auth_error_password_required)
  PasswordError.TooShort -> stringResource(Res.string.auth_error_password_short)
}
