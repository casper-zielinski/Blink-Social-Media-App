package com.CU.blink.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.R
import com.CU.blink.composables.NameAndUsername

@Composable
fun HeaderSection(
    headerImageUrl: String,
    userImageUrl: String,
    name: String,
    username: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.aq),
            contentDescription = "Header Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = "User's image",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(Color.Black)
                .border(4.dp, Color.LightGray, CircleShape)
        )

        NameAndUsername(
            name = "Cas",
            username = "@cas",
            Modifier.align(Alignment.BottomStart),
            nameStyle = MaterialTheme.typography.headlineMedium,
            usernameStyle = MaterialTheme.typography.labelLarge,
        )
    }
}