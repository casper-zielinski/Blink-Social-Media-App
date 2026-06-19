package com.CU.blink.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.CU.blink.R
import com.CU.blink.composables.NameAndUsername

@Composable
fun HeaderSection(
    name: String,
    username: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.aq),
            contentDescription = stringResource(R.string.header_image_desc),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = stringResource(R.string.user_image_desc),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(Color.Black)
                .border(4.dp, Color.LightGray, CircleShape)
        )
        NameAndUsername(
            name = name,
            username = username,
            Modifier
                .align(Alignment.BottomStart)
                .padding(top = 24.dp, bottom = 8.dp, start = 8.dp),
            nameStyle = MaterialTheme.typography.headlineMedium,
            usernameStyle = MaterialTheme.typography.labelLarge,
        )
    }
}
