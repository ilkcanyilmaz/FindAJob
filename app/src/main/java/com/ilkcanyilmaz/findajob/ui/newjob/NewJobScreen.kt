package com.ilkcanyilmaz.findajob.ui.newjob

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ilkcanyilmaz.findajob.R
import com.ilkcanyilmaz.findajob.data.Job
import com.ilkcanyilmaz.findajob.ui.components.AlertType
import com.ilkcanyilmaz.findajob.ui.components.InfoAlertDialog
import com.ilkcanyilmaz.findajob.ui.components.MyAppBar
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme
import com.ilkcanyilmaz.findajob.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewJobScreen(
    viewModel: NewJobViewModel? = hiltViewModel(),
    navController: NavHostController
) {
    val errorMessage = viewModel?.errMsg?.collectAsState()
    val isNewUserSuccess = viewModel?.isNewUserSuccess?.collectAsState()
    val isTextFieldEmpty = remember { mutableStateOf(true) }
    val showProgressIndicator = remember { mutableStateOf(false) }
    val showErrorMessage = remember { mutableStateOf(Pair(false, "")) }

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var companyName by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var timeFrame by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyAppBar("Yeni İlan", navController, true)
        },
        content = { paddingValues ->
            val spacing = MaterialTheme.spacing


            LaunchedEffect(Unit) {

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .background(color = MaterialTheme.colorScheme.onBackground),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(180.dp, 180.dp),
                    painter = painterResource(id = R.drawable.logo_no_background),
                    contentDescription = stringResource(id = R.string.app_name),
                    contentScale = ContentScale.Fit
                )

                OutlinedTextField(
                    value = title,
                    colors = OutlinedTextFieldDefaults.colors(
                        MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                    ),
                    label = {
                        Text(
                            text = "Pozisyon",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onValueChange = { title = it }
                )
                Spacer(modifier = Modifier.height(spacing.small))
                OutlinedTextField(
                    value = companyName,
                    colors = OutlinedTextFieldDefaults.colors(
                        MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                    ),
                    label = { Text(text = "Şirket", color = MaterialTheme.colorScheme.onSurface) },
                    onValueChange = { companyName = it }
                )
                Spacer(modifier = Modifier.height(spacing.small))
                OutlinedTextField(
                    value = description,
                    colors = OutlinedTextFieldDefaults.colors(
                        MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                    ),
                    label = {
                        Text(
                            text = "Açıklama",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onValueChange = { description = it }
                )
                Spacer(modifier = Modifier.height(spacing.small))
                OutlinedTextField(
                    value = location,
                    colors = OutlinedTextFieldDefaults.colors(
                        MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Konum", color = MaterialTheme.colorScheme.onSurface) },
                    onValueChange = {
                        location = it
                    }
                )
                Spacer(modifier = Modifier.height(spacing.small))
                OutlinedTextField(
                    value = timeFrame,
                    colors = OutlinedTextFieldDefaults.colors(
                        MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                    ),
                    label = { Text(text = "Zaman", color = MaterialTheme.colorScheme.onSurface) },
                    onValueChange = {
                        timeFrame = it
                    }
                )
                Spacer(modifier = Modifier.height(spacing.medium))
                Button(
                    modifier = Modifier
                        .width(OutlinedTextFieldDefaults.MinWidth)
                        .height(OutlinedTextFieldDefaults.MinHeight),
                    onClick = {
                        if (!areTextFieldsEmpty(
                                title.text,
                                companyName.text,
                                description.text,
                                location.text,
                                timeFrame.text
                            )
                        ) {
                            viewModel?.addJobFirestore(
                                Job(
                                    title.text,
                                    companyName.text,
                                    description.text,
                                    location.text,
                                    timeFrame.text,
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                )
                            )
                        } else {
                            isTextFieldEmpty.value = true
                        }
                    },
                    shape = ShapeDefaults.Medium,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.save), color = MaterialTheme.colorScheme.onBackground)
                }
            }

            if (showErrorMessage.value.first) {
                InfoAlertDialog(
                    type = AlertType.ERROR, title = "Hata", text = showErrorMessage.value.second
                ) {
                    showErrorMessage.value = Pair(false, "")
                }
            }

            if (showProgressIndicator.value) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        strokeWidth = 10.dp
                    )
                }
            }

            isNewUserSuccess?.value.let {
                if (it == true) {
                    InfoAlertDialog(
                        type = AlertType.SUCCESS,
                        title = "İlan yayında",
                        text = "İlan başarıyla yayınlandı."
                    ) {
                        navController.popBackStack()
                    }
                }
            }

            errorMessage?.value?.let {
                InfoAlertDialog(
                    type = AlertType.ERROR, title = "Hata", text = it
                ) {}
            }

            isTextFieldEmpty.let {
                if (it.value) {
                    isTextFieldEmpty.value = false
                    InfoAlertDialog(
                        type = AlertType.WARNING,
                        title = "Hata",
                        text = "Kayıt işlemine devam etmek için lütfen bütün alanları doldurun."
                    ) {}
                }
            }

        }
    )
}

fun areTextFieldsEmpty(vararg fields: String): Boolean {
    return fields.any { it.isEmpty() }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        NewJobScreen(null, rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    AppTheme {
        NewJobScreen(null, rememberNavController())
    }
}