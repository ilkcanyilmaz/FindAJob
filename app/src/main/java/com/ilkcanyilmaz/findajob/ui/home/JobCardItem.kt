package com.ilkcanyilmaz.findajob.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilkcanyilmaz.findajob.R
import com.ilkcanyilmaz.findajob.data.Job
import com.ilkcanyilmaz.findajob.ui.theme.AppTheme

@Composable
fun JobCardItem(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_black),
                        contentDescription = ""
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                    )
                    Text(
                        text = job.companyName,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = job.description,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(1.dp)
            )

            Row(Modifier.padding(top = 4.dp)) {
                Row(
                    modifier = Modifier
                        .heightIn(min = 0.dp, max = 56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "Lokasyon İkonu",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = job.location,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f, true))
                Row(
                    modifier = Modifier
                        .heightIn(min = 0.dp, max = 56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Zaman İkonu",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = job.timeFrame,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun JobCardItemPreviewLight() {
    AppTheme {
        JobCardItem(
            Job(
                "Title",
                "Company Name",
                "Description",
                "Location",
                "timeFrame",
                "qualifations",
                "salary",
                "applicationProcess",
                "applicationDeadline",
                "ContactInformation",
                "JobId"
            )
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun JobCardItemPreviewDark() {
    AppTheme {
        JobCardItem(Job())
    }
}