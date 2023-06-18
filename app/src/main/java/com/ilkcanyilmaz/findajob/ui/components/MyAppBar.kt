package com.ilkcanyilmaz.findajob.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.ilkcanyilmaz.findajob.navigation.ROUTE_HOME
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme
import com.ilkcanyilmaz.findajob.R
import com.ilkcanyilmaz.findajob.navigation.ROUTE_LOGIN

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    title: String,
    navController: NavController?,
    isBackButton: Boolean
) {
    TopAppBar(
        title = { Text(text = title, color = White) },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            if (navController?.currentBackStackEntry == navController?.getBackStackEntry(ROUTE_HOME)) {
                Icon(
                    modifier = Modifier.clickable(onClick = {
                        FirebaseAuth.getInstance().signOut()
                    }),
                    painter = painterResource(id = R.drawable.ic_signout),
                    contentDescription = ""
                )
            }
        },
        navigationIcon = {
            if (isBackButton)
                IconButton(onClick = {
                    navController?.popBackStack()
                    Log.d("TAG", "MyAppBar: Click ")
                }) {
                    Icon(Icons.Outlined.ArrowBack, "icon", tint = White)
                }
        },
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MyAppBar() {
    AppTheme {
        MyAppBar("null", null, true)
    }
}