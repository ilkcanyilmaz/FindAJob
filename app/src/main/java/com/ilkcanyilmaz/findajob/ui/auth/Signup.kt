package com.ilkcanyilmaz.findajob.ui.auth

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.fonts.Font
import android.graphics.fonts.FontFamily
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ilkcanyilmaz.findajob.R
import com.ilkcanyilmaz.findajob.data.Resource
import com.ilkcanyilmaz.findajob.navigation.ROUTE_HOME
import com.ilkcanyilmaz.findajob.navigation.ROUTE_LOGIN
import com.ilkcanyilmaz.findajob.navigation.ROUTE_SIGNUP
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme
import com.ilkcanyilmaz.findajob.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    viewModel: AuthViewModel? = hiltViewModel(),
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signupFlow = viewModel?.signupFlow?.collectAsState()
    
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
            val (refHeader, refName, refEmail, refPassword, refButtonSignup, refTextSignup, refLoader) = createRefs()
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
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val spacing = MaterialTheme.spacing

                    Image(
                        modifier = Modifier
                            .size(164.dp, 164.dp),
                        painter = painterResource(id = R.drawable.logo_no_background),
                        contentDescription = stringResource(id = R.string.app_name)
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = spacing.medium),
                        text = stringResource(id = R.string.signup),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            TextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(text = stringResource(id = R.string.name))
                },
                modifier = Modifier.constrainAs(refName) {
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
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                modifier = Modifier.constrainAs(refEmail) {
                    top.linkTo(refName.bottom, spacing.medium)
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
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Button(
                onClick = {
                    viewModel?.signup(name, email, password)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                shape = ShapeDefaults.Medium,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .constrainAs(refButtonSignup) {
                        top.linkTo(refPassword.bottom, spacing.large)
                        start.linkTo(parent.start, spacing.large)
                        end.linkTo(parent.end, spacing.large)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.signup),
                    style = MaterialTheme.typography.titleMedium,
                    color= MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(refTextSignup) {
                        top.linkTo(refButtonSignup.bottom, spacing.medium)
                        start.linkTo(parent.start, spacing.extraLarge)
                        end.linkTo(parent.end, spacing.extraLarge)
                    }
                    .clickable {
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(ROUTE_SIGNUP) { inclusive = true }
                        }
                    },
                text = stringResource(id = R.string.already_have_account),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            signupFlow?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
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
                            navController.navigate(ROUTE_HOME) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
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
fun SignupScreenPreviewLight() {
    AppTheme {
        SignupScreen(null, rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SignupScreenPreviewDark() {
    AppTheme {
        SignupScreen(null, rememberNavController())
    }
}