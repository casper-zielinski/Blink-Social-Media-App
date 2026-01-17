package com.CU.blink.HomePage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CU.blink.composables.AccountIcon

@Composable
fun CommentFeed(modifier: Modifier, comments: List<Comment>) {
    Column(modifier) {

        comments.forEach {
            Column(
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                AccountIcon(
                    modifier = Modifier
                        .height(46.dp)
                        .width(46.dp), "Account Icon of${it.name}"
                )
                NameAndUsername(it.name, it.username, Modifier.padding(start = 8.dp))
            }
            Text(it.content, style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(top = 6.dp), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.inverseSurface, modifier = Modifier.padding(vertical = 4.dp))
        }
        }
    }
}


