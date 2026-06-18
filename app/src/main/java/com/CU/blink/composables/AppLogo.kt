package com.CU.blink.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.CU.blink.R

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.blink_logo_512x512),
        contentDescription =  "Logo",
        contentScale = ContentScale.Crop,
        modifier =  modifier.size(120.dp)
    )
}