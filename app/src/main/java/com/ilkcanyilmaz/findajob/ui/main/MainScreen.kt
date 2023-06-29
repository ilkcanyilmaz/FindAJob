package com.ilkcanyilmaz.findajob.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.ilkcanyilmaz.findajob.R
import com.ilkcanyilmaz.findajob.navigation.ROUTE_HOME
import com.ilkcanyilmaz.findajob.navigation.ROUTE_LOGIN
import com.ilkcanyilmaz.findajob.ui.components.MyAppBar
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme
import com.ilkcanyilmaz.findajob.ui.theme.spacing
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel? = hiltViewModel(),
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
                    .background(color = Color(0xFFFFFFFF)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(164.dp, 164.dp),
                    painter = painterResource(id = R.drawable.logo_no_background),
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Text(
                    text = "Your Text",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1.0f, true))
                Button(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .width(OutlinedTextFieldDefaults.MinWidth)
                        .height(OutlinedTextFieldDefaults.MinHeight),
                    onClick = {
                        navController.navigate(ROUTE_HOME) {
                            popUpTo(ROUTE_HOME) { inclusive = true }
                        }
                    },
                    shape = ShapeDefaults.Medium,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        stringResource(R.string.jobs),
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
        MainScreen(null, rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    AppTheme {
        MainScreen(null, rememberNavController())
    }
}