package com.ilkcanyilmaz.findajob.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ilkcanyilmaz.findajob.R
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme


@Composable
fun InfoAlertDialog(type:AlertType=AlertType.INFO, title: String, text: String, confirmClick: () -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            val titleColor=when(type){
                AlertType.INFO->MaterialTheme.colorScheme.onSurface
                AlertType.SUCCESS-> MaterialTheme.colorScheme.primary
                AlertType.WARNING->MaterialTheme.colorScheme.onSurface
                AlertType.ERROR->MaterialTheme.colorScheme.error
            }
            if (openDialog.value) {
                AlertDialog(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(text = title, color = titleColor)
                    },
                    text = {
                        Text(text, color = MaterialTheme.colorScheme.surface)
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary),
                            onClick = {
                                openDialog.value = false
                                confirmClick()
                            }) {
                            Text(
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.ok),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        InfoAlertDialog(AlertType.SUCCESS,"Başarılı", "Kayıt edildi") {

        }
    }
}

enum class AlertType{
    INFO,
    SUCCESS,
    WARNING,
    ERROR
}