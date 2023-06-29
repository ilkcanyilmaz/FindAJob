package com.ilkcanyilmaz.findajob.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.ilkcanyilmaz.findajob.navigation.ROUTE_LOGIN
import com.ilkcanyilmaz.findajob.navigation.ROUTE_NEW_JOB
import com.ilkcanyilmaz.findajob.ui.components.ErrorPopup
import com.ilkcanyilmaz.findajob.ui.components.MyAppBar
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme
import com.ilkcanyilmaz.findajob.ui.theme.spacing
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel? = hiltViewModel(),
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            MyAppBar("Find A Job", navController, false)
        },
        content = { it ->

            val errorMessage = viewModel?.errMsg?.collectAsState()

            val jobs = viewModel?.jobs?.collectAsState()

            var openBottomSheet by rememberSaveable { mutableStateOf(false) }
            val skipPartiallyExpanded by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            val bottomSheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = skipPartiallyExpanded
            )

            LaunchedEffect(Unit) {
                viewModel?.getJobs()
                FirebaseAuth.getInstance().addAuthStateListener { auth ->
                    if (auth.currentUser == null) {
                        navController.navigate(ROUTE_LOGIN)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(it)
                    .background(color = Color(0xFFFFFFFF))
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        jobs?.value?.let { job ->
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(job) { item ->
                                    JobCardItem(item)
                                }
                            }
                        }

                        errorMessage?.value?.let {
                            ErrorPopup(errorMsg = errorMessage.value.toString()) {
                                viewModel.errMsg.value = null
                            }
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomEnd)
                            .padding(8.dp),
                        onClick = {
                            navController.navigate(ROUTE_NEW_JOB) {
                                popUpTo(ROUTE_NEW_JOB) { inclusive = true }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add FAB",
                            tint = Color.Black,
                        )
                    }

                    if (openBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { openBottomSheet = false },
                            sheetState = bottomSheetState,
                        ) {
                            var name by remember { mutableStateOf(TextFieldValue("")) }
                            var surname by remember { mutableStateOf(TextFieldValue("")) }

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                                    // you must additionally handle intended state cleanup, if any.
                                    onClick = {
                                        scope.launch { bottomSheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!bottomSheetState.isVisible) {
                                                    openBottomSheet = false
                                                }
                                            }
                                    }
                                ) {
                                    Text("Hide Bottom Sheet")
                                }
                            }
                            Column(Modifier.fillMaxWidth()) {
                                TextField(value = name, onValueChange = { newText ->
                                    name = newText
                                })
                                TextField(value = surname, onValueChange = { newText ->
                                    surname = newText
                                })
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun InfoHeader(modifier: Modifier, title: String, value: Int) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSurface),
        elevation = CardDefaults.cardElevation(MaterialTheme.spacing.extraSmall)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = value.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        HomeScreen(null, rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    AppTheme {
        HomeScreen(null, rememberNavController())
    }
}