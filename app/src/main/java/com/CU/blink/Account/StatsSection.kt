package com.CU.blink.Account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.CU.blink.R

@Composable
fun StatsSection(likesCount: Int, followersCount: Int, followingCount: Int,
                 modifier: Modifier = Modifier,   statsStyle: TextStyle = MaterialTheme.typography.headlineLarge
) {
    Column(modifier) {
        Text(text = stringResource(R.string.stats_title), style = statsStyle)

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row() {
                Text(text = stringResource(R.string.likes_label),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(100.dp))
                Text(text = likesCount.toString(), modifier = Modifier.padding(start = 44.dp))
            }

            Row() {
                Text(text = stringResource(R.string.followers_label),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(100.dp)
                    )
                Text(text = followersCount.toString(), modifier = Modifier.padding(start = 44.dp))
            }

            Row() {
                Text(text = stringResource(R.string.following_label),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(100.dp))
                Text(text = followingCount.toString(), modifier = Modifier.padding(start = 44.dp))
            }
        }
    }
}