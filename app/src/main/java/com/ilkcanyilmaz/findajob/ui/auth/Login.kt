package com.ilkcanyilmaz.findajob.ui.auth

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ilkcanyilmaz.findajob.R
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme
import com.ilkcanyilmaz.findajob.data.Resource
import com.ilkcanyilmaz.findajob.navigation.ROUTE_HOME
import com.ilkcanyilmaz.findajob.navigation.ROUTE_LOGIN
import com.ilkcanyilmaz.findajob.navigation.ROUTE_MAIN
import com.ilkcanyilmaz.findajob.navigation.ROUTE_SIGNUP
import com.ilkcanyilmaz.findajob.ui.auth.AuthViewModel
import com.ilkcanyilmaz.findajob.ui.theme.spacing

@Composable
fun LoginScreen(
    viewModel: AuthViewModel? = hiltViewModel(),
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    val loginFlow = viewModel?.loginFlow?.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
        )

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (refHeader, refEmail, refPassword, refButtonLogin, refTextSignup, refLoader) = createRefs()
            val spacing = MaterialTheme.spacing

            Box(
                modifier = Modifier
                    .constrainAs(refHeader) {
                        top.linkTo(parent.top, spacing.extraLarge)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .wrapContentSize()
            ) {
                AuthHeader()
            }


            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                modifier = Modifier.constrainAs(refEmail) {
                    top.linkTo(refHeader.bottom, spacing.extraLarge)
                    start.linkTo(parent.start, spacing.large)
                    end.linkTo(parent.end, spacing.large)
                    width = Dimension.fillToConstraints
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                modifier = Modifier.constrainAs(refPassword) {
                    top.linkTo(refEmail.bottom, spacing.medium)
                    start.linkTo(parent.start, spacing.large)
                    end.linkTo(parent.end, spacing.large)
                    width = Dimension.fillToConstraints
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_visibility_off),
                            "visibility"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Button(
                onClick = {
                    viewModel?.login(email, password)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                shape = ShapeDefaults.Medium,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .constrainAs(refButtonLogin) {
                    top.linkTo(refPassword.bottom, spacing.large)
                    start.linkTo(parent.start, spacing.large)
                    end.linkTo(parent.end, spacing.large)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.titleMedium,
                    color= MaterialTheme.colorScheme.surfaceVariant
                )
            }


            Text(
                modifier = Modifier
                    .constrainAs(refTextSignup) {
                        top.linkTo(refButtonLogin.bottom, spacing.medium)
                        start.linkTo(parent.start, spacing.extraLarge)
                        end.linkTo(parent.end, spacing.extraLarge)
                    }
                    .clickable {
                        navController.navigate(ROUTE_SIGNUP) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    },
                text = stringResource(id = R.string.dont_have_account),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            loginFlow?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        LaunchedEffect(Unit) {
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.constrainAs(refLoader) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })
                    }

                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(ROUTE_MAIN) {
                                popUpTo(ROUTE_MAIN) { inclusive = true }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun LoginScreenPreviewLight() {
    AppTheme {
        LoginScreen(null, rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreviewDark() {
    AppTheme {
        LoginScreen(null, rememberNavController())
    }
}