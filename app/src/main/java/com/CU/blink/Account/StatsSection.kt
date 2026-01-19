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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatsSection(likesCount: Int, followersCount: Int, followingCount: Int,
                 modifier: Modifier = Modifier,   statsStyle: TextStyle = MaterialTheme.typography.headlineLarge
) {
    Column(modifier) {
        Text(text = "Stats", style = statsStyle)

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row() {
                Text(text = "Likes:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(100.dp))
                Text(text = "1100", modifier = Modifier.padding(start = 44.dp))
            }

            Row() {
                Text(text = "Followers",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(100.dp)
                    )
                Text(text = "2", modifier = Modifier.padding(start = 44.dp))
            }

            Row() {
                Text(text = "Following",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(100.dp))
                Text(text = "45", modifier = Modifier.padding(start = 44.dp))
            }
        }
    }
}